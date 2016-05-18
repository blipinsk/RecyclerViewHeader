/*
 * Copyright 2015 Bartosz Lipinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bartoszlipinski.recyclerviewheader2.sample.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.bartoszlipinski.recyclerviewheader2.sample.R;
import com.bartoszlipinski.recyclerviewheader2.sample.fragment.SampleGridFragment;
import com.bartoszlipinski.recyclerviewheader2.sample.fragment.SampleListFragment;
import com.bartoszlipinski.recyclerviewheader2.sample.fragment.SampleListReversedFragment;

/**
 * Created by Bartosz Lipinski
 * 01.02.15
 */
public class MainActivity extends FragmentActivity {
    public static final int FRAGMENT_COUNT = 3;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new RecyclerFragmentPagerAdapter(getSupportFragmentManager()));
    }

    private class RecyclerFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public RecyclerFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SampleListFragment.newInstance();
                case 1:
                    return SampleGridFragment.newInstance();
                case 2:
                    return SampleListReversedFragment.newInstance();
                default:
                    return SampleListFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }
    }
}
