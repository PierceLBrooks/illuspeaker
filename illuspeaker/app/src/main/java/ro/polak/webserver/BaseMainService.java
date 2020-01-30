/**************************************************
 * Android Web Server
 * Based on JavaLittleWebServer (2008)
 * <p/>
 * Copyright (c) Piotr Polak 2008-2017
 **************************************************/

package ro.polak.webserver;

import com.piercelbrooks.common.BasicService;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import ro.polak.http.configuration.ServerConfig;
import ro.polak.http.configuration.ServerConfigFactory;
import ro.polak.http.controller.Controller;
import ro.polak.http.controller.impl.ControllerImpl;
import ro.polak.http.gui.ServerGui;
import ro.polak.webserver.base.BaseServerSocketFactory;
import ro.polak.webserver.base.ConfigurationException;
import ro.polak.webserver.base.impl.BaseAndroidServerConfigFactory;
import ro.polak.webserver.base.logic.AssetUtil;

import static ro.polak.http.configuration.impl.ServerConfigImpl.PROPERTIES_FILE_NAME;

/**
 * Main application service that holds http server.
 *
 * @author Piotr Polak piotr [at] polak [dot] ro
 * @since 201709
 */
public abstract class BaseMainService <T extends Service> extends BasicService<BaseMainService<T>> implements ServerGui {

    private static final Logger LOGGER = Logger.getLogger(BaseMainService.class.getName());
    private static final int NOTIFICATION_ID = 0;
    private static final int DEFAULT_HTTP_PORT = 80;

    @Nullable
    private BaseMainServiceClient client = null;
    private Controller controller;
    private boolean isServiceStarted = false;

    @Override
    protected void create() {
        isServiceStarted = true;

        ServerConfigFactory serverConfigFactory = getServerConfigFactory(this);

        doFirstRunChecks(serverConfigFactory);

        controller = new ControllerImpl(serverConfigFactory, new BaseServerSocketFactory(), this);
        controller.start();
    }

    @Override
    protected void destroy() {
        if (getServiceState().isWebServerStarted()) {
            controller.stop();
        }

        isServiceStarted = false;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    /**
     * Allows overwriting server config factory.
     *
     * @param context
     * @return
     */
    @NonNull
    protected BaseAndroidServerConfigFactory getServerConfigFactory(final Context context) {
        return new BaseAndroidServerConfigFactory(context);
    }

    /**
     * Returns registered client.
     */
    public BaseMainServiceClient getClient() {
        return client;
    }

    /**
     * Registers client to allow activity-service communication.
     *
     * @param client
     */
    public void registerClient(final BaseMainServiceClient client) {
        this.client = client;
    }

    /**
     * Returns webserver controller.
     *
     * @return
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Returns current service state.
     *
     * @return
     */
    public ServiceStateDTO getServiceState() {
        String accessUrl = "Initializing";
        if (controller != null && controller.getWebServer() != null) {
            accessUrl = "http://"
                    + getLocalIpAddress()
                    + getPort(controller.getWebServer().getServerConfig().getListenPort())
                    + '/';
        }

        boolean isWebserverStarted = controller != null
                && controller.getWebServer() != null
                && controller.getWebServer().isRunning();

        return new ServiceStateDTO(isServiceStarted, isWebserverStarted, accessUrl);
    }

    @NonNull
    private String getPort(final int port) {
        if (port != DEFAULT_HTTP_PORT) {
            return ":" + port;
        }
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        if (client != null) {
            client.notifyStateChanged();
        }

        Intent notificationIntent = new Intent(this, getActivityClass());
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        setNotification(getNotificationBuilder(pIntent, "Started", ro.polak.webserver.base.R.drawable.online).build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        if (client != null) {
            client.notifyStateChanged();
        }

        Intent notificationIntent = new Intent(this, getActivityClass());
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        setNotification(getNotificationBuilder(pIntent, "Stopped", ro.polak.webserver.base.R.drawable.offline).build());
    }

    /**
     * Returns the the class of the activity to be registered.
     *
     * @return
     */
    @NonNull
    protected abstract Class<? extends Activity> getActivityClass();

    private void doFirstRunChecks(final ServerConfigFactory serverConfigFactory) {
        ServerConfig serverConfig = serverConfigFactory.getServerConfig();
        String basePath = Environment.getExternalStorageDirectory() + serverConfig.getBasePath();
        String staticDirPath = Environment.getExternalStorageDirectory() + serverConfig.getDocumentRootPath();

        File baseDir = new File(basePath);
        if (!baseDir.exists() && !baseDir.mkdirs()) {
            throw new ConfigurationException("Unable to create directory " + baseDir.getAbsolutePath());
        }

        File staticDir = new File(staticDirPath);
        if (!staticDir.exists() && !staticDir.mkdirs()) {
            throw new ConfigurationException("Unable to create directory " + staticDir.getAbsolutePath());
        }

        AssetManager assetManager = this.getResources().getAssets();

        File config = new File(basePath + PROPERTIES_FILE_NAME);
        if (!config.exists()) {
            try {
                config.createNewFile();
                AssetUtil.copyAssetToFile(assetManager, "conf" + File.separator + PROPERTIES_FILE_NAME, config);
            } catch (IOException e) {
                throw new ConfigurationException(e);
            }
        }

        File mimeType = new File(basePath + "mime.type");
        if (!mimeType.exists()) {
            try {
                mimeType.createNewFile();
                AssetUtil.copyAssetToFile(assetManager, "conf" + File.separator + "mime.type", mimeType);
            } catch (IOException e) {
                throw new ConfigurationException(e);
            }
        }
    }


    private void setNotification(final Notification notification) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @SuppressWarnings("deprecation")
    private Notification.Builder getNotificationBuilder(final PendingIntent pIntent, final String text, final int icon) {
        return new Notification.Builder(this)
                .setContentTitle("HTTPServer")
                .setContentText(text)
                .setSmallIcon(icon)
                .setContentIntent(pIntent)
                .setOngoing(true)
                .addAction(ro.polak.webserver.base.R.drawable.online, "Open", pIntent);
    }

    /**
     * Helper method returning the current IP address.
     *
     * @return String
     */
    private String getLocalIpAddress() {
        try {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            int ipAddress = wifiInfo.getIpAddress();
            if (java.nio.ByteOrder.nativeOrder().equals(java.nio.ByteOrder.LITTLE_ENDIAN)) {
                ipAddress = Integer.reverseBytes(ipAddress);
            }

            InetAddress inetAddress = InetAddress.getByAddress(BigInteger.valueOf(ipAddress).toByteArray());
            return inetAddress.getHostAddress();

        } catch (Exception e) {
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            } catch (SocketException ex) {
                LOGGER.log(Level.SEVERE, "Unable to obtain own IP address", e);
            }
        }

        return "127.0.0.1";
    }

    /**
     * Represents the service state.
     */
    public static final class ServiceStateDTO {
        private boolean isServiceStarted;
        private boolean isWebServerStarted;
        private String accessUrl;

        public ServiceStateDTO(final boolean isServiceStarted, final boolean isWebServerStarted, final String accessUrl) {
            this.isServiceStarted = isServiceStarted;
            this.isWebServerStarted = isWebServerStarted;
            this.accessUrl = accessUrl;
        }

        public boolean isServiceStarted() {
            return isServiceStarted;
        }

        public boolean isWebServerStarted() {
            return isWebServerStarted;
        }

        public String getAccessUrl() {
            return accessUrl;
        }

    }
}
