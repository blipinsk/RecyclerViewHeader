RecyclerViewHeader
==================

[![License](https://img.shields.io/github/license/blipinsk/RecyclerViewHeader.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RecyclerViewHeader-green.svg?style=flat)](http://android-arsenal.com/details/1/1853)
[![Maven Central](https://img.shields.io/maven-central/v/com.bartoszlipinski/recyclerviewheader2.svg)](http://gradleplease.appspot.com/#recyclerviewheader2)

Super fast and easy way to create header for Android `RecyclerView`.

Lets you create header `View` for any `RecyclerView` that uses `LinearLayoutManager` or `GridLayoutManager` with just a simple method call.

![ ](/RecyclerViewHeader.png)

Limitations
===========
Although the library is super easy to use, it has some limitations.

`RecyclerViewHeader` is based on a `ViewGroup` being hovered over the `RecyclerView` (and properly shifted on scroll).

You need to bare in mind that the library is created to be used with relatively simple headers.

E.g. if your header uses a complex layout (with multiple scrolls and focusable elements) or a complicated `Touch` management system I advice you not to use `RecyclerViewHeader`, but a proper `RecyclerView` `Adapter` that incorporates inflating multiple types of views.
There are other libraries on github (for example [HeaderRecyclerView by Karumi][1]) that might seem a bit more complicated to use, but are implementing mentioned approach and will fit your needs better.

TLDR:

  1. Simple header
  2. Header smaller than screen height (for vertical `RecyclerView`) or width (for horizontal `RecyclerView`)
  3. No complicated touch system (including scrollable elements)
  
On version 2
------------
I created this library back in the day when I thought `RecyclerView` was all new and difficult. Writing an adapter that could inflate multiple types of `Views` seemed like a difficult job to do.
Not long after I released `RecyclerViewHeader` I found out that's an incredibly easy task to perform especially if you have tools like [`HeaderRecyclerView` by Karumi][1].
Using a specialized adapter was fixing all the flaws that `RecyclerViewHeader` was suffering from.

The aim of the second version was to fix all the issues users were facing. The problem is, while I was developing the second version, I was thinking about killing this library multiple times. 
I was finding multiple aspects of the approach it presents, to be problematic for someone who doesn't understand the concept of the library thoroughly (usage with simple headers).

I decided to finish the second version as a token of gratitude, for all your support. Thank you!

Nonetheless, **as of today (06/04/2016) I'm beginning the process of retiring of the `RecyclerViewHeader` library.**
Version `2.0.0` will be the last *major* release. The only new development will contain fixes for critical bugs (with the exception of re-enabling `StaggeredGridLayoutManager` support).

Usage
=====
*For a working implementation of this library see the `sample/` folder.*

  1. Place `RecyclerViewHeader` layout above (z-order-wise) your `RecyclerView` at the top part of it.

        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <android.support.v7.widget.RecyclerView
                android:id="@+id/recycler"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal|top" />

            <com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader
                android:id="@+id/header"
                android:layout_width="match_parent"
                android:layout_height="100dp"
                android:layout_gravity="center_horizontal|top">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerInParent="true"
                    android:text="header"/>

            </com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader>

        </FrameLayout>

  2. Get the `RecyclerViewHeader` view object with:

        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.header);

  3. Attach `RecyclerViewHeader` to your `RecyclerView`:

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // set LayoutManager for your RecyclerView
        header.attachTo(recyclerView);


Important notes
---------------

`RecyclerViewHeader` needs to be called after you've set the `LayoutManager` for your `RecyclerView`.

Current implementation of the library can be used with `RecyclerViews` that use `LinearLayoutManager` or `GridLayoutManager`.


Including In Your Project
-------------------------
You can grab the library via Maven Central. Just add a proper dependency inside your `build.gradle`. Like this:

```xml
dependencies {
    compile 'com.bartoszlipinski:recyclerviewheader2:2.0.1'
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
