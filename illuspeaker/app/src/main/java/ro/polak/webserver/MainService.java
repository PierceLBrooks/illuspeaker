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
import com.piercelbrooks.illuspeaker.R;

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
        return getString(R.string.service_name);
    }

    @Override
    protected Integer getNotification() {
        return new Integer(Integer.parseInt(getString(R.string.service_notification_code)));
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
        return getString(R.string.service_description);
    }

    @NonNull
    @Override
    protected BaseAndroidServerConfigFactory getServerConfigFactory(final Context context) {
        return new AndroidServerConfigFactory(context);
    }

    @Override
    public Class<?> getServiceClass() {
        return MainService.class;
    }
}
