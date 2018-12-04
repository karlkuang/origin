package com.android.lock.widget;

import android.app.Activity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by admin on 2018/12/03.
 */

public class UnLockMenuPopWindow extends PopupWindow implements View.OnClickListener {

    public UnLockMenuPopWindow(final Activity context, String pkgName, boolean isShowCheckboxPattern) {
        super(context);

    }


    @Override
    public void onClick(View view) {

    }
}
