
// Author: Pierce Brooks

package com.piercelbrooks.illuspeaker;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toolbar;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.piercelbrooks.common.BasicActivity;
import com.piercelbrooks.common.Mayor;

public class MainActivity extends BasicActivity<MayoralFamily> {

    private static final String TAG = "ILL-MainAct";

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

    }

    @Override
    protected void destroy() {

    }

    @Override
    protected void start() {

    }

    @Override
    protected void stop() {

    }

    @Override
    protected void resume() {
        showMain();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
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
        }
        return mayor;
    }

    public void showMain() {
        MainFragment fragment = new MainFragment();
        show(fragment);
    }

    public boolean show(@Nullable MayoralFamily mayoralFamily) {
        return show(getNewMayor(mayoralFamily));
    }
}
