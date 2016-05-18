Version 2.0.1 *(2016-05-18)*
----------------------------

 * Fix: Scroll jump bug
 * Fix: Header not displaying if there are no items in the adapter
 * Headers exceeding `RecyclerView's` height (for vertical) or width (for horizontal) are no longer supported (you cannot scroll them if there's no items in the adapter)

Version 2.0.0 *(2016-04-06)*
----------------------------
RecyclerViewHeader 2 is a major release that focuses on fixing all of the previously reported issues.

 * Attaching `RecyclerViewHeader` simplified. There's only one approach left (previously named `Header-already-aligned approach`).
 * New: Horizontal `RecyclerViews` support added
 * Fix: Header in wrong position after reusing fragment with `RecyclerView`
 * Fix: Removing an item misplacing header.
 
`StaggeredGridLayoutManager` support has been temporarily disabled. This is planned to be re-enabled in a future release. 
 
*Important*:
 1. `RecyclerViewHeader` 2 uses a different package (`com.bartoszlipinski.recyclerviewheader2`)
 2. Maven `group` and `artifact` has been changed.

Version 1.2.1 *(2016-02-04)*
----------------------------

 * Using `addOnScrollListener` instead of `setOnScrollListener`
 * `RecyclerView` support library package no longer included in the library

Version 1.2.0 *(2015-04-13)*
----------------------------

 * `StaggeredGridLayoutManager` support
 * Reversed `LayoutManagers` support
 * Fix: scrolling with touch starting on a focusable element of the header

 Version 1.1.0 *(2015-04-05)*
----------------------------

 * Decreased minSdk version
 * Support Libraries version updated
 * Fix: Removed unnecessary things

Version 1.0.1 *(2015-04-02)*
----------------------------

 * Fix: Header clickability fixed

Version 1.0.0 *(2015-04-02)*
----------------------------

Initial release.