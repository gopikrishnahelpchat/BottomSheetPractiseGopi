package com.gopi.bottomsheet.bottomsheetpractisegopi.customviews;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;

/**
 * Created by gopikrishna on 6/9/16.
 */
public class CustomBottomSheet extends BottomSheetDialog {
    public CustomBottomSheet(Context context, int theme) {
        super(context, theme);
    }

    public CustomBottomSheet(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
