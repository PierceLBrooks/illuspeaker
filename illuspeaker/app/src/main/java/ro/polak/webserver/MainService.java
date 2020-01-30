/**************************************************
 * Android Web Server
 * Based on JavaLittleWebServer (2008)
 * <p/>
 * Copyright (c) Piotr Polak 2008-2017
 **************************************************/

package ro.polak.webserver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.piercelbrooks.common.BasicServiceBinder;

import java.util.List;

import impl.AndroidServerConfigFactory;
import ro.polak.webserver.base.impl.BaseAndroidServerConfigFactory;

/**
 * Main application service that holds http server.
 *
 * @author Piotr Polak piotr [at] polak [dot] ro
 * @since 201709
 */
public final class MainService extends BaseMainService<MainService> {

    @Override
    protected BasicServiceBinder<BaseMainService<MainService>> getBinder(BaseMainService service) {
        return null;
    }

    @Override
    protected String getName() {
        return null;
    }

    @Override
    protected Integer getNotification() {
        return null;
    }

    @Override
    protected List<NotificationCompat.Action> getNotificationActions() {
        return null;
    }

    @NonNull
    @Override
    protected Class<MainActivity> getActivityClass() {
        return MainActivity.class;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @NonNull
    @Override
    protected BaseAndroidServerConfigFactory getServerConfigFactory(final Context context) {
        return new AndroidServerConfigFactory(context);
    }

    @Override
    public Class<?> getServiceClass() {
        return null;
    }
}
