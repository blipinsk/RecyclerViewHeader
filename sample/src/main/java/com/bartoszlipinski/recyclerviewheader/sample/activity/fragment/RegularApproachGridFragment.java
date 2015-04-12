/**
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

package com.bartoszlipinski.recyclerviewheader.sample.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.bartoszlipinski.recyclerviewheader.sample.R;
import com.bartoszlipinski.recyclerviewheader.sample.activity.adapter.ColorItemsAdapter;

/**
 * Created by Bartosz Lipinski
 * 01.04.15
 */
public class RegularApproachGridFragment extends Fragment {

    private RecyclerViewHeader mRecyclerHeader;
    private RecyclerView mRecycler;

    public static RegularApproachGridFragment newInstance() {
        RegularApproachGridFragment fragment = new RegularApproachGridFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regular_approach, container, false);
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecycler.setAdapter(new ColorItemsAdapter(getActivity(), 21));

        mRecyclerHeader = RecyclerViewHeader.fromXml(getActivity(), R.layout.layout_header);
        mRecyclerHeader.attachTo(mRecycler);
    }

}
