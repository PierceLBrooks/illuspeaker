
// Author: Pierce Brooks

package com.piercelbrooks.illuspeaker;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import android.view.View;

import com.piercelbrooks.common.BasicFragment;

public class MainFragment extends BasicFragment<MayoralFamily>
{
    private static final String TAG = "ILL-MainFrag";

    public MainFragment()
    {
        super();
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.main_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {

    }

    @Override
    public void onBirth()
    {

    }

    @Override
    public void onDeath()
    {

    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.MAIN;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return MainFragment.class;
    }
}
