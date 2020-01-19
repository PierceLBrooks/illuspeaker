
// Author: Pierce Brooks

package com.piercelbrooks.illuspeaker;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.piercelbrooks.common.BasicFragment;

public class DrawFragment extends BasicFragment<MayoralFamily>
{
    private static final String TAG = "ILL-DrawFrag";

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
        return MayoralFamily.DRAW;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return DrawFragment.class;
    }
}
