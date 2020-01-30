
// Author: Pierce Brooks

package com.piercelbrooks.illuspeaker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toolbar;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.piercelbrooks.common.BasicServiceActivity;
import com.piercelbrooks.common.BasicServiceConnector;
import com.piercelbrooks.common.Mayor;
import com.piercelbrooks.p2p.WiFiDirectServicesList;
import com.piercelbrooks.p2p.WiFiP2pService;

import ro.polak.webserver.BaseMainServiceClient;
import ro.polak.webserver.MainService;

public class MainActivity extends BasicServiceActivity<MayoralFamily, MainService> implements BaseMainServiceClient, WiFiDirectServicesList.DeviceClickListener, WifiP2pManager.ConnectionInfoListener {

    private static final String TAG = "ILL-MainAct";
    private static final int PERMISSIONS_REQUEST_CODE = 1001;

    public MainActivity() {
        super();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if  (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "Permission is not granted!");
                    finish();
                } else {
                    beginService();
                }
                break;
        }
    }

    @Override
    protected BasicServiceConnector<MayoralFamily, MainService> getConnector(BasicServiceActivity activity) {
        return new MainServiceConnector(this);
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
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) &&
                ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                        (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                        (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_CODE);
            // After this point you wait for callback in
            // onRequestPermissionsResult(int, String[], int[]) overridden method
        } else {
            beginService();
        }
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

    @Override
    public void connectP2p(WiFiP2pService service) {
        getService().connectP2p(service);
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        getService().onConnectionInfoAvailable(info);
    }
}
