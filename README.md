# DEPRECATED

I created this library back in the day when I thought RecyclerView was all new and difficult. Writing an adapter that could inflate multiple types of Views seemed like a difficult job to do. In reality, `RecyclerViewHeader` is just a complex solution to a simple problem.
Instead of using this library, just learn how to create a multi-type `RecyclerView.Adapter`. It will bring you a lot of value in the long run, and it is not difficult at all. Check the `Migration` section for the simplest example of such `Adapter.`

**No new development will be taking place.**

Thanks for all the support!

------

RecyclerViewHeader
------------------

[![License](https://img.shields.io/github/license/blipinsk/RecyclerViewHeader.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/com.bartoszlipinski/recyclerviewheader2.svg)](http://gradleplease.appspot.com/#recyclerviewheader2)

If you still wanna use this library, check the old [README.md](https://github.com/blipinsk/RecyclerViewHeader/blob/2a0679f7abcb8cb94e3985272cf19bde90c6cbe4/README.md).

Migration
---------
**Just use a `RecyclerView.Adapter` that can inflate multiple types of items.**

Here's the simplest one you could use:

```kotlin
class ExampleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 4815
        private const val VIEW_TYPE_ITEM = 1623
    }

    private val itemDataSetSize: Int get() = TODO("provide the size of your `ITEM` dataset")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_HEADER -> TODO("create your HEADER ViewHolder")
            VIEW_TYPE_ITEM -> TODO("create your ITEM ViewHolder")
            else -> error("Unhandled viewType=$viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val viewType = getItemViewType(position)) {
            VIEW_TYPE_HEADER -> TODO("bind your HEADER ViewHolder")
            VIEW_TYPE_ITEM -> TODO("bind your ITEM ViewHolder")
            else -> error("Unhandled viewType=$viewType")
        }
    }

    override fun getItemCount(): Int = itemDataSetSize + 1 // 1 for header

    override fun getItemViewType(position: Int) = when (position) {
        0 -> VIEW_TYPE_HEADER
        else -> VIEW_TYPE_ITEM
    }
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
