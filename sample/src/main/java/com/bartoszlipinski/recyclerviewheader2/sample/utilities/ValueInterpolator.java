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
package com.bartoszlipinski.recyclerviewheader2.sample.utilities;

/**
 * Created by Bartosz Lipinski
 * 12.12.14
 */
public class ValueInterpolator {

    private float mRangeMapFromMin;
    private float mRangeMapToMin;

    private float mScaleFactor;

    public ValueInterpolator(float[] rangeMapFrom, float[] rangeMapTo) {
        this(rangeMapFrom[0], rangeMapFrom[1], rangeMapTo[0], rangeMapTo[1]);
    }

    public ValueInterpolator(float rangeMapFromMin, float rangeMapFromMax, float rangeMapToMin, float rangeMapToMax) {
        mRangeMapFromMin = rangeMapFromMin;
        mRangeMapToMin = rangeMapToMin;

        float rangeMapFromSpan = rangeMapFromMax - rangeMapFromMin;
        float rangeMapToSpan = rangeMapToMax - rangeMapToMin;

        mScaleFactor = rangeMapToSpan / rangeMapFromSpan;
    }

    public float map(float value) {
        return mRangeMapToMin + ((value - mRangeMapFromMin) * mScaleFactor);
    }

}
