RecyclerViewHeader
===============
Super fast and easy way to create header for Android `RecyclerView`.

![ ](/RecyclerViewHeader.png)


Usage
=====
*For a working implementation of this library see the `sample/` folder.*

Basically, there are two ways of using `RecyclerViewHeader`.

**Regular approach** (super easy to use, but it uses additional `FrameLayouts` so it's a bit less efficient than the second approach):

  1. Create an xml layout file for your header (the file can contain any type of `View` or `ViewGroup`)

        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="100dp">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                android:text="header"/>

        </FrameLayout>

  2. Create `RecyclerViewHeader` from `xml` using static initializer.

        RecyclerViewHeader header = RecyclerViewHeader.fromXml(context, R.layout.header);

  3. Attach `RecyclerViewHeader` to your `RecyclerView` and you're done!

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // setting LayoutManager for your RecyclerView
        header.attachTo(recyclerView);

**Header-already-aligned approach** (does not introduce any additional `FrameLayouts`):

  1. Place `RecyclerViewHeader`layout under your `RecyclerView` at the top part of it.

        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader
                android:id="@+id/header"
                android:layout_width="match_parent"
                android:layout_height="100dp"
                android:layout_gravity="center_horizontal|top">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerInParent="true"
                    android:text="header"/>

            </com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader>

            <android.support.v7.widget.RecyclerView
                android:id="@+id/recycler"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal|top" />

        </FrameLayout>

  2. Get the `RecyclerViewHeader` view object with:

        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.header);

  3. Attach `RecyclerViewHeader` to your `RecyclerView` (with `attachTo(RecyclerView recycler, boolean headerAlreadyAligned)`) and that's it.

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // setting LayoutManager for your RecyclerView
        header.attachTo(recyclerView, true);


**Important Notes:**

`RecyclerViewHeader` needs to be called after you've set the `LayoutManager` for your `RecyclerView`.

Current implementation of the library can be used for `RecyclerViews` using `LinearLayoutManager` or `GridLayoutManager`.

If you were planning to use `setOnScrollListener(...)` method for your `RecyclerView`, be sure to do it before calling `attachTo(...)` method of the `RecyclerViewHeader`.


Including In Your Project
-------------------------
I will upload the library to Maven Central as soon as I finish `Javadoc`.

It will be ready really, really soon... stay tuned.

Developed by
==========
 * Bartosz Lipiński

License
======

    Copyright 2015 Bartosz Lipiński
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
