package com.ming.codecreator;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author ming
 * @version $Rev$
 * @des RecycleView 间距
 */
class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            outRect.top = mSpace;
        }

    public SpaceItemDecoration(int space) {
            this.mSpace = space;
    }
}
