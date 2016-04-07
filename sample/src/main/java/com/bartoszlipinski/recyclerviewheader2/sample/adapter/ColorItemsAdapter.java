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
package com.bartoszlipinski.recyclerviewheader2.sample.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader2.sample.R;
import com.bartoszlipinski.recyclerviewheader2.sample.utilities.ValueInterpolator;

public class ColorItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int[] colors;
    private boolean test = false;

    public ColorItemsAdapter(Context context, int numberOfItems) {
        colors = new int[numberOfItems];

        int startColor = ContextCompat.getColor(context, R.color.wisteria);
        int startR = Color.red(startColor);
        int startG = Color.green(startColor);
        int startB = Color.blue(startColor);

        int endColor = ContextCompat.getColor(context, R.color.emerald);
        int endR = Color.red(endColor);
        int endG = Color.green(endColor);
        int endB = Color.blue(endColor);

        ValueInterpolator interpolatorR = new ValueInterpolator(0, numberOfItems - 1, endR, startR);
        ValueInterpolator interpolatorG = new ValueInterpolator(0, numberOfItems - 1, endG, startG);
        ValueInterpolator interpolatorB = new ValueInterpolator(0, numberOfItems - 1, endB, startB);

        for (int i = 0; i < numberOfItems; ++i) {
            colors[i] = Color.argb(255, (int) interpolatorR.map(i), (int) interpolatorG.map(i), (int) interpolatorB.map(i));
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

        LayerDrawable bgDrawable = (LayerDrawable) viewHolder.mainLayout.getBackground();
        GradientDrawable shape = (GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.background_shape);
        shape.setColor(colors[position]);
    }

    @Override
    public int getItemCount() {
        return colors.length - (test ? 1 : 0);
    }

    public static class SampleViewHolder extends RecyclerView.ViewHolder {
        public View mainLayout;

        public SampleViewHolder(View view) {
            super(view);
            mainLayout = view.findViewById(R.id.layout);
        }
    }
}