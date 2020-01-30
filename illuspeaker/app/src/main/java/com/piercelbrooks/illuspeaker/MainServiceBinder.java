
// Author: Pierce Brooks

package com.piercelbrooks.illuspeaker;

import com.piercelbrooks.common.BasicServiceBinder;

import ro.polak.webserver.BaseMainService;
import ro.polak.webserver.MainService;

public class MainServiceBinder extends BasicServiceBinder<BaseMainService<MainService>> {
    public MainServiceBinder(BaseMainService<MainService> service) {
        super(service);
    }

    @Override
    public Class<?> getServiceClass() {
        return MainService.class;
    }
}
