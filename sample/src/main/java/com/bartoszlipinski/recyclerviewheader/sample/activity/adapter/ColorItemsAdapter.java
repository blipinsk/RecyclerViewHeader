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

package com.bartoszlipinski.recyclerviewheader.sample.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.sample.R;
import com.bartoszlipinski.recyclerviewheader.sample.activity.utilities.ValueInterpolator;

/**
 * Created by Bartosz Lipinski
 * 01.04.15
 */
public class ColorItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int[] mColors;

    public ColorItemsAdapter(Context context, int numberOfItems) {
        mColors = new int[numberOfItems];

        int startColor = context.getResources().getColor(R.color.wisteria);
        int startR = Color.red(startColor);
        int startG = Color.green(startColor);
        int startB = Color.blue(startColor);

        int endColor = context.getResources().getColor(R.color.emerald);
        int endR = Color.red(endColor);
        int endG = Color.green(endColor);
        int endB = Color.blue(endColor);

        ValueInterpolator interpolatorR = new ValueInterpolator(0, numberOfItems - 1, endR, startR);
        ValueInterpolator interpolatorG = new ValueInterpolator(0, numberOfItems - 1, endG, startG);
        ValueInterpolator interpolatorB = new ValueInterpolator(0, numberOfItems - 1, endB, startB);

        for (int i = 0; i < numberOfItems; ++i) {
            mColors[i] = Color.argb(255, (int) interpolatorR.map(i), (int) interpolatorG.map(i), (int) interpolatorB.map(i));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SampleViewHolder viewHolder = (SampleViewHolder) holder;

        LayerDrawable bgDrawable = (LayerDrawable) viewHolder.mMainLayout.getBackground();
        GradientDrawable shape = (GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.background_shape);
        shape.setColor(mColors[position]);
    }

    @Override
    public int getItemCount() {
        return mColors.length;
    }

    public static class SampleViewHolder extends RecyclerView.ViewHolder {
        public View mMainLayout;

        public SampleViewHolder(View view) {
            super(view);
            mMainLayout = view.findViewById(R.id.layout);
        }
    }
}