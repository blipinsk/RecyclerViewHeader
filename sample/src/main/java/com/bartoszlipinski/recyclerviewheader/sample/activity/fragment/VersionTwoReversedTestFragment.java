package com.bartoszlipinski.recyclerviewheader.sample.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.sample.R;
import com.bartoszlipinski.recyclerviewheader.sample.activity.adapter.ColorItemsAdapter;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader2;

/**
 * Created by Bartosz Lipinski
 * 06.02.2016
 */
public class VersionTwoReversedTestFragment extends Fragment {

    private RecyclerViewHeader2 recyclerHeader;
    private RecyclerView recycler;

    public static VersionTwoReversedTestFragment newInstance() {
        VersionTwoReversedTestFragment fragment = new VersionTwoReversedTestFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_version_two_reversed_test, container, false);
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        recycler.setAdapter(new ColorItemsAdapter(getActivity(), 110));

        recyclerHeader = (RecyclerViewHeader2) view.findViewById(R.id.header);
        recyclerHeader.attachTo(recycler);
    }

}