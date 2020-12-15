package com.example.realreal.adapter;

import androidx.fragment.app.Fragment;


/**
 * 식물화면 ContacksFragment와 이어짐
 */
public class ChildFragmentAdapter implements FragmentNavigatorAdapter {

    @Override
    public Fragment onCreateFragment(int position) {
        return null;
    }

    @Override
    public String getTag(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
