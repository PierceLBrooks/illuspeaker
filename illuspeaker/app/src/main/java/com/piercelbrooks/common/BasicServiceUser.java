
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.app.Service;

public interface BasicServiceUser <T extends Service>
{
    public Class<?> getServiceClass();
    public T getService();
}
