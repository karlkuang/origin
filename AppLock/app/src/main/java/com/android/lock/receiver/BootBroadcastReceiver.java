package com.android.lock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.lock.base.AppConstants;
import com.android.lock.service.LoadAppListService;
import com.android.lock.service.LockService;
import com.android.lock.utils.LogUtil;
import com.android.lock.utils.SpUtil;

/**
 * 开机启动广播
 * Created by admin on 2018/12/03.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.i("开机启动服务....");
        context.startService(new Intent(context, LoadAppListService.class));
        if (SpUtil.getInstance().getBoolean(AppConstants.LOCK_STATE, false)) {
            context.startService(new Intent(context, LockService.class));
        }
    }
}
