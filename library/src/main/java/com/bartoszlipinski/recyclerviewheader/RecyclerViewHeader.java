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

package com.bartoszlipinski.recyclerviewheader;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Bartosz Lipinski
 * 31.03.15
 */
public class RecyclerViewHeader extends RelativeLayout {

    private boolean mAlreadyAligned;
    private int mCurrentScroll;

    /**
     * Inflates layout from <code>xml</code> and encapsulates it with <code>RecyclerViewHeader</code>.
     *
     * @param context   application context.
     * @param layoutRes layout resource to be inflated.
     * @return <code>RecyclerViewHeader</code> view object.
     */
    public static RecyclerViewHeader fromXml(Context context, @LayoutRes int layoutRes) {
        RecyclerViewHeader header = new RecyclerViewHeader(context);
        View.inflate(context, layoutRes, header);
        return header;
    }

    public RecyclerViewHeader(Context context) {
        super(context);
    }

    public RecyclerViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Attaches <code>RecyclerViewHeader</code> to <code>RecyclerView</code>.
     * This method will perform necessary actions to properly align the header within <code>RecyclerView</code>.
     * Be sure that <code>setLayoutManager(...)</code> has been called for <code>RecyclerView</code> before calling this method.
     * Also, if you were planning to use <code>setOnScrollListener(...)</code> method for your <code>RecyclerView</code>, be sure to do it before calling this method.
     *
     * @param recycler <code>RecyclerView</code> to attach <code>RecyclerViewHeader</code> to.
     */
    public void attachTo(RecyclerView recycler) {
        attachTo(recycler, false);
    }

    /**
     * Attaches <code>RecyclerViewHeader</code> to <code>RecyclerView</code>.
     * Be sure that <code>setLayoutManager(...)</code> has been called for <code>RecyclerView</code> before calling this method.
     * Also, if you were planning to use <code>setOnScrollListener(...)</code> method for your <code>RecyclerView</code>, be sure to do it before calling this method.
     *
     * @param recycler             <code>RecyclerView</code> to attach <code>RecyclerViewHeader</code> to.
     * @param headerAlreadyAligned If set to <code>false</code>, method will perform necessary actions to properly align
     *                             the header within <code>RecyclerView</code>. If set to <code>true</code> method will assume,
     *                             that user has already aligned <code>RecyclerViewHeader</code> properly.
     */
    public void attachTo(RecyclerView recycler, boolean headerAlreadyAligned) {
        validateRecycler(recycler, headerAlreadyAligned);

        mAlreadyAligned = headerAlreadyAligned;

        setupAlignment(recycler);
        setupHeader(recycler);
    }

    private void setupAlignment(RecyclerView recycler) {
        if (!mAlreadyAligned) {
            //setting alignment of header
            ViewGroup.LayoutParams headerParams = getLayoutParams();
            FrameLayout.LayoutParams newHeaderParams;
            if (headerParams != null) {
                newHeaderParams = new FrameLayout.LayoutParams(headerParams);
                if (headerParams instanceof LinearLayout.LayoutParams) {
                    newHeaderParams.gravity = ((LinearLayout.LayoutParams) headerParams).gravity;
                    newHeaderParams.bottomMargin = ((LinearLayout.LayoutParams) headerParams).bottomMargin;
                    newHeaderParams.topMargin = ((LinearLayout.LayoutParams) headerParams).topMargin;
                    newHeaderParams.leftMargin = ((LinearLayout.LayoutParams) headerParams).leftMargin;
                    newHeaderParams.rightMargin = ((LinearLayout.LayoutParams) headerParams).rightMargin;
                } else if (headerParams instanceof RelativeLayout.LayoutParams) {
                    newHeaderParams.gravity = convertRulesToGravity(((RelativeLayout.LayoutParams) headerParams).getRules());
                    newHeaderParams.bottomMargin = ((RelativeLayout.LayoutParams) headerParams).bottomMargin;
                    newHeaderParams.topMargin = ((RelativeLayout.LayoutParams) headerParams).topMargin;
                    newHeaderParams.leftMargin = ((RelativeLayout.LayoutParams) headerParams).leftMargin;
                    newHeaderParams.rightMargin = ((RelativeLayout.LayoutParams) headerParams).rightMargin;
                }
            } else {
                newHeaderParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            }
            RecyclerViewHeader.this.setLayoutParams(newHeaderParams);

            //setting alignment of recycler
            FrameLayout newParent = new FrameLayout(recycler.getContext());
            newParent.setLayoutParams(recycler.getLayoutParams());
            ViewParent currentParent = recycler.getParent();
            if (currentParent instanceof ViewGroup) {
                int indexWithinParent = ((ViewGroup) currentParent).indexOfChild(recycler);

                ((ViewGroup) currentParent).removeViewAt(indexWithinParent);
                recycler.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                newParent.addView(RecyclerViewHeader.this);
                newParent.addView(recycler);
                ((ViewGroup) currentParent).addView(newParent, indexWithinParent);
            }
        }
    }

    private void setupHeader(final RecyclerView recycler) {
        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentScroll += dy;

                RecyclerViewHeader.this.setTranslationY(-mCurrentScroll);
            }
        });

        RecyclerViewHeader.this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = RecyclerViewHeader.this.getHeight();
                if (height > 0) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        RecyclerViewHeader.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        RecyclerViewHeader.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    if (mAlreadyAligned) {
                        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
                        height += params.topMargin;
                        height += params.bottomMargin;
                    }

                    recycler.addItemDecoration(new HeaderItemDecoration(recycler.getLayoutManager(), height), 0);
                }
            }
        });
    }

    private int convertRulesToGravity(int[] rules) {
        int gravity = 0;
        if (rules.length < RelativeLayout.ALIGN_PARENT_END) {
            throw new IllegalArgumentException("Argument of convertRulesToGravity(int[] rules) must be an array obtained with getRules() method of RelativeLayout.LayoutParams object.");
        }

        if (rules[RelativeLayout.ALIGN_PARENT_LEFT] == RelativeLayout.TRUE) {
            gravity |= Gravity.LEFT;
        }
        if (rules[RelativeLayout.ALIGN_PARENT_RIGHT] == RelativeLayout.TRUE) {
            gravity |= Gravity.RIGHT;
        }
        if (rules[RelativeLayout.ALIGN_PARENT_BOTTOM] == RelativeLayout.TRUE) {
            gravity |= Gravity.BOTTOM;
        }
        if (rules[RelativeLayout.ALIGN_PARENT_TOP] == RelativeLayout.TRUE) {
            gravity |= Gravity.TOP;
        }
        if (rules[RelativeLayout.CENTER_IN_PARENT] == RelativeLayout.TRUE) {
            gravity |= Gravity.CENTER;
        }
        if (rules[RelativeLayout.CENTER_HORIZONTAL] == RelativeLayout.TRUE) {
            gravity |= Gravity.CENTER_HORIZONTAL;
        }
        if (rules[RelativeLayout.CENTER_VERTICAL] == RelativeLayout.TRUE) {
            gravity |= Gravity.CENTER_VERTICAL;
        }
        if (rules[RelativeLayout.ALIGN_PARENT_START] == RelativeLayout.TRUE) {
            gravity |= Gravity.START;
        }
        if (rules[RelativeLayout.ALIGN_PARENT_END] == RelativeLayout.TRUE) {
            gravity |= Gravity.END;
        }
        return gravity;
    }

    private void validateRecycler(RecyclerView recycler, boolean headerAlreadyAligned) {
        RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
        if (layoutManager == null) {
            throw new IllegalStateException("Be sure to call RecyclerViewHeader constructor after setting your RecyclerView's LayoutManager.");
        } else if (layoutManager.getClass() != LinearLayoutManager.class    //not using instanceof on purpose
                && layoutManager.getClass() != GridLayoutManager.class) {
            throw new IllegalArgumentException("For now RecyclerViewHeader supports only LinearLayoutManager and GridLayoutManager.");
        }

        if (!headerAlreadyAligned) {
            ViewParent parent = recycler.getParent();
            if (parent != null &&
                    !(parent instanceof LinearLayout) &&
                    !(parent instanceof FrameLayout) &&
                    !(parent instanceof RelativeLayout)) {
                throw new IllegalStateException("For now, NOT already aligned RecyclerViewHeader " +
                        "can only be used for RecyclerView with a parent of one of types: LinearLayout, FrameLayout, RelativeLayout.");
            }
        }
    }

    private class HeaderItemDecoration extends RecyclerView.ItemDecoration {
        private int mHeaderHeight;
        private int mNumberOfChildren;

        public HeaderItemDecoration(RecyclerView.LayoutManager layoutManager, int height) {
            if (layoutManager.getClass() == LinearLayoutManager.class) {
                mNumberOfChildren = 1;
            } else if (layoutManager.getClass() == GridLayoutManager.class) {
                mNumberOfChildren = ((GridLayoutManager) layoutManager).getSpanCount();
            }
            mHeaderHeight = height;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildPosition(view) < mNumberOfChildren) {
                outRect.top = mHeaderHeight;
            } else {
                outRect.top = 0;
            }
        }
    }
}