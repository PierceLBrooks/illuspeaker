
// Author: Pierce Brooks

package com.piercelbrooks.illuspeaker;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toolbar;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.piercelbrooks.common.BasicServiceActivity;
import com.piercelbrooks.common.BasicServiceBinder;
import com.piercelbrooks.common.BasicServiceConnector;
import com.piercelbrooks.common.Mayor;

import ro.polak.webserver.BaseMainService;
import ro.polak.webserver.BaseMainServiceClient;
import ro.polak.webserver.MainService;

public class MainActivity extends BasicServiceActivity<MayoralFamily, MainService> implements BaseMainServiceClient {

    private static final String TAG = "ILL-MainAct";

    @Override
    protected BasicServiceConnector<MayoralFamily, MainService> getConnector(BasicServiceActivity activity) {
        return new MainServiceConnector(this);
    }

    public MainActivity() {
        super();
    }

    @Override
    protected void landscapeOrientation() {

    }

    @Override
    protected void portraitOrientation() {

    }

    @Override
    public void onKey(KeyEvent event) {
        super.onKey(event);
    }

    @Override
    public void onTouch(MotionEvent event) {
        super.onTouch(event);
    }

    @Override
    protected void create() {
        beginService();
    }

    @Override
    protected void destroy() {
        endService();
    }

    @Override
    protected void start() {

    }

    @Override
    protected void stop() {

    }

    @Override
    protected void resume() {
        showDraw();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        //getActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    protected void pause() {
        //Log.d(TAG, "endService = "+endService());
    }

    @Override
    protected @IdRes int getFragmentSlot() {
        return R.id.fragment_slot;
    }

    @Override
    public @LayoutRes int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public Class<?> getCitizenClass() {
        return MainActivity.class;
    }

    @Override
    public <T extends Fragment & Mayor<MayoralFamily>> boolean getIsTemporary(@Nullable T fragment) {
        return false;
    }

    @Override
    public <T extends Fragment & Mayor<MayoralFamily>> void preShow(@Nullable T fragment) {

    }

    @Override
    public <T extends Fragment & Mayor<MayoralFamily>> void onShow(@Nullable T fragment) {

    }

    @Override
    public <T extends Fragment & Mayor<MayoralFamily>> T getNewMayor(@Nullable MayoralFamily mayoralFamily) {
        T mayor = null;
        if (mayoralFamily == null) {
            return mayor;
        }
        switch (mayoralFamily) {
            case MAIN:
                mayor = (T)(new MainFragment());
                break;
            case DRAW:
                mayor = (T)(new DrawFragment());
                break;
        }
        return mayor;
    }

    public void showMain() {
        MainFragment fragment = new MainFragment();
        show(fragment);
    }

    public void showDraw() {
        DrawFragment fragment = new DrawFragment();
        show(fragment);
    }

    public boolean show(@Nullable MayoralFamily mayoralFamily) {
        return show(getNewMayor(mayoralFamily));
    }

    @Override
    public Class<?> getServiceClass() {
        return MainService.class;
    }

    @Override
    public void notifyStateChanged() {

    }
}
