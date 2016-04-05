/**
 * Copyright 2016 Bartosz Lipinski
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bartoszlipinski.recyclerviewheader2;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Bartosz Lipinski
 * 31.03.15
 */
public class RecyclerViewHeader2 extends RelativeLayout {

    @Visibility
    private int intendedVisiblity = VISIBLE;
    private int downTranslation;
    private boolean hidden = false;
    private boolean recyclerWantsTouch;
    private boolean isAttachedToRecycler;
    private RecyclerView recycler;
    private RecyclerView.OnScrollListener scrollListener;
    private HeaderItemDecoration decoration;
    private LayoutManagerDelegate layoutManager;

    public RecyclerViewHeader2(Context context) {
        super(context);
    }

    public RecyclerViewHeader2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewHeader2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected final void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && ViewCompat.isAttachedToWindow(this) && decoration != null) {
            decoration.setHeight(b - t);
            recycler.invalidateItemDecorations();
        }
    }

    public final void attachTo(@NonNull RecyclerView recycler) {
        validate(recycler);
        this.layoutManager = LayoutManagerDelegate.with(recycler.getLayoutManager());
        this.recycler = recycler;
        isAttachedToRecycler = true;
        decoration = new HeaderItemDecoration();
        scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                hidden = !layoutManager.isFirstRowVisible();
                RecyclerViewHeader2.super.setVisibility(hidden ? INVISIBLE : intendedVisiblity);
                if (!hidden) {
                    setTranslationY(calculateTranslation());
                }
            }
        };
        recycler.addItemDecoration(decoration, 0);
        recycler.addOnScrollListener(scrollListener);
    }

    public final void detach() {
        if (isAttachedToRecycler) {
            isAttachedToRecycler = false;
            recycler.removeItemDecoration(decoration);
            recycler.removeOnScrollListener(scrollListener);
            decoration = null;
            scrollListener = null;
            recycler = null;
        }
    }

    private int calculateTranslation() {
        int offset = recycler.computeVerticalScrollOffset();
        int base = layoutManager.isReversed() ?
                recycler.computeVerticalScrollRange() - recycler.getHeight() : 0;
        return base - offset;
    }

    @Override
    public final void setVisibility(@Visibility int visibility) {
        this.intendedVisiblity = visibility;
        if (!hidden) {
            super.setVisibility(intendedVisiblity);
        }
    }

    @Visibility
    @Override
    public final int getVisibility() {
        return intendedVisiblity;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        recyclerWantsTouch = isAttachedToRecycler && recycler.onInterceptTouchEvent(ev);
        if (recyclerWantsTouch && ev.getAction() == MotionEvent.ACTION_DOWN) {
            downTranslation = calculateTranslation();
        }
        return recyclerWantsTouch || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (recyclerWantsTouch) { //this cannot be true if recycler is not attached
            int scrollDiff = downTranslation - calculateTranslation();
            MotionEvent recyclerEvent =
                    MotionEvent.obtain(event.getDownTime(),
                            event.getEventTime(),
                            event.getAction(),
                            event.getX(),
                            event.getY() - scrollDiff,
                            event.getMetaState());
            recycler.onTouchEvent(recyclerEvent);
            return false;
        }
        return super.onTouchEvent(event);
    }

    private void validate(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() == null) {
            throw new IllegalStateException("Be sure to call RecyclerViewHeader constructor after setting your RecyclerView's LayoutManager.");
        }
    }

    private class HeaderItemDecoration extends RecyclerView.ItemDecoration {
        private int headerHeight;
        private int firstRowSpan;

        public HeaderItemDecoration() {
            firstRowSpan = layoutManager.getFirstRowSpan();
        }

        public void setHeight(int height) {
            headerHeight = height;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int value = (parent.getChildLayoutPosition(view) < firstRowSpan) ? headerHeight : 0;
            if (layoutManager.isReversed()) {
                outRect.bottom = value;
            } else {
                outRect.top = value;
            }
        }
    }

    private static class RecyclerViewDelegate {
        @NonNull
        private final RecyclerView recyclerView;
        private RecyclerView.OnScrollListener onScrollListener;
        private HeaderItemDecoration decoration;

        private RecyclerViewDelegate(@NonNull RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        public static RecyclerViewDelegate with(@NonNull RecyclerView recyclerView) {
            return new RecyclerViewDelegate(recyclerView);
        }

        public void setHeaderDecoration(HeaderItemDecoration decoration) {
            clearHeaderDecoration();
            this.decoration = decoration;
            recyclerView.addItemDecoration(this.decoration, 0);
        }

        private void clearHeaderDecoration() {
            if (this.decoration != null) {
                recyclerView.removeItemDecoration(this.decoration);
            }
        }

        public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
            clearOnScrollListener();
            this.onScrollListener = onScrollListener;
            recyclerView.addOnScrollListener(this.onScrollListener);
        }

        private void clearOnScrollListener() {
            if (this.onScrollListener != null) {
                recyclerView.removeOnScrollListener(this.onScrollListener);
            }
        }


    }

    private static class LayoutManagerDelegate {
        @Nullable
        private final LinearLayoutManager linear;
        @Nullable
        private final GridLayoutManager grid;
        @Nullable
        private final StaggeredGridLayoutManager staggeredGrid;

        private LayoutManagerDelegate(@NonNull RecyclerView.LayoutManager manager) {
            final Class<? extends RecyclerView.LayoutManager> managerClass = manager.getClass();
            if (managerClass == LinearLayoutManager.class) { //not using instanceof on purpose
                linear = (LinearLayoutManager) manager;
                grid = null;
                staggeredGrid = null;
            } else if (managerClass == GridLayoutManager.class) {
                linear = null;
                grid = (GridLayoutManager) manager;
                staggeredGrid = null;
//            } else if (manager instanceof StaggeredGridLayoutManager) { //TODO: 05.04.2016 implement staggered
//                linear = null;
//                grid = null;
//                staggeredGrid = (StaggeredGridLayoutManager) manager;
            } else {
                throw new IllegalArgumentException("Currently RecyclerViewHeader supports only LinearLayoutManager and GridLayoutManager.");
            }
        }

        public static LayoutManagerDelegate with(@NonNull RecyclerView.LayoutManager layoutManager) {
            return new LayoutManagerDelegate(layoutManager);
        }

        public final int getFirstRowSpan() {
            if (linear != null) {
                return 1;
            } else if (grid != null) {
                return grid.getSpanCount();
//            } else if (staggeredGrid != null) {
//                return staggeredGrid.getSpanCount(); //TODO: 05.04.2016 implement staggered
            }
            return 0; //shouldn't get here
        }

        public final boolean isFirstRowVisible() {
            if (linear != null) {
                return linear.findFirstVisibleItemPosition() == 0;
            } else if (grid != null) {
                return grid.findFirstVisibleItemPosition() == 0;
//            } else if (staggeredGrid != null) {
//                return staggeredGrid.findFirstCompletelyVisibleItemPositions() //TODO: 05.04.2016 implement staggered
            }
            return false; //shouldn't get here
        }

        public final boolean isReversed() {
            if (linear != null) {
                return linear.getReverseLayout();
            } else if (grid != null) {
                return grid.getReverseLayout();
//            } else if (staggeredGrid != null) {
//                return ; //TODO: 05.04.2016 implement staggered
            }
            return false; //shouldn't get here
        }
    }

    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Visibility {
    }

}