/*
 * Copyright (C) 2014 Valerio Bozzolan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package anupam.acrylic;

import android.widget.Toast;

import com.piercelbrooks.common.BasicApplication;
import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;
import com.piercelbrooks.illuspeaker.MayoralFamily;

abstract class GraphicsFragment extends BasicFragment<MayoralFamily> {
    public GraphicsFragment() {
        super();
    }

    protected Toast makeText(CharSequence text, int duration) {
        return Toast.makeText(getActivity(), text, duration);
    }

    protected Toast makeText(int resource, int duration) {
        return Toast.makeText(getActivity(), resource, duration);
    }

    protected BasicApplication getGovernor() {
        return (BasicApplication) Governor.getInstance().getCitizen(Family.GOVERNOR);
    }
}

