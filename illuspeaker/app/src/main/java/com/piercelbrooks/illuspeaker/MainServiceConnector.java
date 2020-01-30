
// Author: Pierce Brooks

package com.piercelbrooks.illuspeaker;

import com.piercelbrooks.common.BasicServiceActivity;
import com.piercelbrooks.common.BasicServiceConnector;

import ro.polak.webserver.MainService;

public class MainServiceConnector extends BasicServiceConnector<MayoralFamily, MainService> {
    public MainServiceConnector(BasicServiceActivity<MayoralFamily, MainService> activity) {
        super(activity);
    }

    @Override
    public Class<?> getServiceClass() {
        return MainService.class;
    }
}
