package com.pommerening.quinn.trashfinder.activities;

import android.support.v4.app.Fragment;
import com.pommerening.quinn.trashfinder.fragments.HomeFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new HomeFragment();
    }
}
