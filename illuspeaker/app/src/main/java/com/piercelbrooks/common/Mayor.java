
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.View;

public interface Mayor <T extends Enum<T>> extends Citizen
{
    public void onBackPressed();
    public void onCreateOptionsMenu(Menu menu);
    public void onPrepareOptionsMenu(Menu menu);
    public void onBirth();
    public void onDeath();
    public T getMayoralFamily();
    public Municipality<T> getMunicipality();
    public @LayoutRes int getLayout();
    public void createView(@NonNull View view);
}
