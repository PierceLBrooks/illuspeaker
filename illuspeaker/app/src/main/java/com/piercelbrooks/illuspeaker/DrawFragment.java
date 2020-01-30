
// Author: Pierce Brooks

package com.piercelbrooks.illuspeaker;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.p2p.WiFiDirectServicesList;

import anupam.acrylic.EasyPaint;
import ro.polak.webserver.MainService;

public class DrawFragment extends BasicFragment<MayoralFamily>
{
    public static class DrawRealFragment extends EasyPaint
    {
        private static final String TAG = "ILL-DrawRealFrag";

        public DrawRealFragment() {
            super();
        }

        @Override
        public void createView(@NonNull View view) {
            super.createView(view);
            getFragmentManager().beginTransaction().replace(R.id.preview, new WiFiDirectServicesList(), MainService.FRAGMENT_TAG).commitAllowingStateLoss();
        }

        @Override
        public void onDestroyView() {
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(MainService.FRAGMENT_TAG)).commitAllowingStateLoss();
            super.onDestroyView();
        }

        @Override
        public MayoralFamily getMayoralFamily() {
            return MayoralFamily.DRAW_REAL;
        }

        @Override
        public int getLayout() {
            return R.layout.draw_real_fragment;
        }

        @Override
        public Class<?> getCitizenClass() {
            return DrawRealFragment.class;
        }
    }

    private static final String TAG = "ILL-DrawFrag";

    private DrawRealFragment real;

    public DrawFragment()
    {
        super();
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.draw_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        real = new DrawRealFragment();
        getFragmentManager().beginTransaction().replace(R.id.easy_paint, real).commitAllowingStateLoss();
    }

    @Override
    public void onBirth()
    {
        real = null;
    }

    @Override
    public void onDeath()
    {
        if (real != null)
        {
            getFragmentManager().beginTransaction().remove(real).commitAllowingStateLoss();
        }
    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.DRAW;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return DrawFragment.class;
    }
}
