RecyclerViewHeader
==================

[![License](https://img.shields.io/github/license/blipinsk/RecyclerViewHeader.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RecyclerViewHeader-green.svg?style=flat)](http://android-arsenal.com/details/1/1853)
[![Maven Central](https://img.shields.io/maven-central/v/com.bartoszlipinski.recyclerviewheader/library.svg)](http://gradleplease.appspot.com/#recyclerviewheader)

Super fast and easy way to create header for Android `RecyclerView`.

Lets you create header `View` for any `RecyclerView` that uses `LinearLayoutManager`, `GridLayoutManager` or `StaggeredGridLayoutManager` with just a simple method call.

![ ](/RecyclerViewHeader.png)

Limitations
===========
Although the library is super easy to use, it has some limitations.

`RecyclerViewHeader` is based on a `ViewGroup` being hovered over the `RecyclerView` (and properly shifted on scroll).

You need to bare in mind that the library is created to be used with relatively simple headers.

E.g. if your header uses a complex layout (with multiple scrolls and focusable elements) or a complicated `Touch` management system I advice you not to use `RecyclerViewHeader`, but a proper `RecyclerView` `Adapter` that incorporates inflating multiple types of views.
There are other libraries on github (for example [HeaderRecyclerView by Karumi][1]) that might seem a bit more complicated to use, but are implementing mentioned approach and will fit your needs better.

Usage
=====
*For a working implementation of this library see the `sample/` folder.*

Basically, there are two ways of using `RecyclerViewHeader`.

**Regular approach** (super easy to use, but it uses additional `Layouts` so it's a bit less efficient than the second approach):

  1. Create an xml layout file for your header (file can contain any type of `View` or `ViewGroup`)

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
        // set LayoutManager for your RecyclerView
        header.attachTo(recyclerView);

**Header-already-aligned approach** (does not introduce any additional `Layouts`):

  1. Place `RecyclerViewHeader`layout above your `RecyclerView` at the top part of it.

        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <android.support.v7.widget.RecyclerView
                android:id="@+id/recycler"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal|top" />

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

        </FrameLayout>

  2. Get the `RecyclerViewHeader` view object with:

        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.header);

  3. Attach `RecyclerViewHeader` to your `RecyclerView` (with `attachTo(RecyclerView recycler, boolean headerAlreadyAligned)`) and that's it.

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // set LayoutManager for your RecyclerView
        header.attachTo(recyclerView, true);


Important notes
---------------

`RecyclerViewHeader` needs to be called after you've set the `LayoutManager` for your `RecyclerView`.

Current implementation of the library can be used with `RecyclerViews` that use `LinearLayoutManager`, `GridLayoutManager` or `StaggeredGridLayoutManager`.

Currently only `vertical` implementations of `LayoutManagers` are supported.

If you are planning to use `setOnScrollListener(...)` method for your `RecyclerView`, be sure to do it before calling `attachTo(...)` method of the `RecyclerViewHeader`.


Including In Your Project
-------------------------
You can grab the library via Maven Central. Just add a proper dependency inside your `build.gradle`. Like this:

```xml
dependencies {
    compile 'com.bartoszlipinski.recyclerviewheader:library:1.2.0'
}
```

Developed by
============
 * Bartosz Lipiński

License
=======

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


 [1]: https://github.com/Karumi/HeaderRecyclerView
