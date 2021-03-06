
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.app.Service;
import android.os.Binder;

public abstract class BasicServiceBinder <T extends Service> extends Binder implements BasicServiceUser<T>
{
    private static final String TAG = "PLB-BaseServeBind";

    private T service;

    public BasicServiceBinder(T service)
    {
        super();
        this.service = service;
    }

    @Override
    public T getService()
    {
        return service;
    }
}
