package com.gopi.bottomsheet.bottomsheetpractisegopi.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.GridView;

/**
 * Created by gopikrishna on 6/8/16.
 */
public class CustomGridView extends GridView {
    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent p_event){
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent p_event){
        if (canScrollVertically(this)){
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(p_event);
    }

    public boolean canScrollVertically(AbsListView view) {

        boolean canScroll = false;

        if (view != null && view.getChildCount() > 0) {

//            boolean isOnTop = view.getFirstVisiblePosition() != 0 || view.getChildAt(0).getTop() != 0;
//            boolean isAllItemsVisible = isOnTop && getLastVisiblePosition() == view.getChildCount();

            boolean isOnTop = view.getFirstVisiblePosition() != 0 || view.getChildAt(0).getTop() != 0;
            boolean bottomItemVisible = view.getLastVisiblePosition() != (view.getChildCount()-1);

            if (isOnTop || bottomItemVisible) {
                canScroll = true;
            }
        }

        return false;
    }
}
