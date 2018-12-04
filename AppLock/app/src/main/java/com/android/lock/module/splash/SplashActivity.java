package com.android.lock.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.android.lock.R;
import com.android.lock.module.lock.GestureSelfUnlockActivity;
import com.android.lock.base.BaseActivity;
import com.android.lock.base.AppConstants;
import com.android.lock.module.pwd.CreatePwdActivity;
import com.android.lock.service.LoadAppListService;
import com.android.lock.service.LockService;
import com.android.lock.utils.AppUtils;
import com.android.lock.utils.LockUtil;
import com.android.lock.utils.SpUtil;
import com.android.lock.utils.ToastUtil;
import com.android.lock.widget.DialogPermission;

/**
 * Created by admin on 2018/12/03.
 */

public class SplashActivity extends BaseActivity {

    private int RESULT_ACTION_USAGE_ACCESS_SETTINGS = 1;
    private int RESULT_ACTION_MANAGE_OVERLAY_PERMISSION = 2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        AppUtils.hideStatusBar(getWindow(), true);
    }

    @Override
    protected void initData() {
        startService(new Intent(this, LoadAppListService.class));
        if (SpUtil.getInstance().getBoolean(AppConstants.LOCK_STATE, false)) {
            startService(new Intent(this, LockService.class));
        }
        boolean isFirstLock = SpUtil.getInstance().getBoolean(AppConstants.LOCK_IS_FIRST_LOCK, true);
        if (isFirstLock) { //如果第一次
            //showDialog();
            gotoCreatePwdActivity();
        } else {
            Intent intent = new Intent(SplashActivity.this, GestureSelfUnlockActivity.class);
            intent.putExtra(AppConstants.LOCK_PACKAGE_NAME, AppConstants.APP_PACKAGE_NAME); //传自己的包名
            intent.putExtra(AppConstants.LOCK_FROM, AppConstants.LOCK_FROM_LOCK_MAIN_ACITVITY);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    /**
     * 弹出dialog
     */
    private void showDialog() {
        //如果没有获得查看使用情况权限和手机存在查看使用情况这个界面
        if (!LockUtil.isStatAccessPermissionSet(SplashActivity.this) && LockUtil.isNoOption(SplashActivity.this)) {
            DialogPermission dialog = new DialogPermission(SplashActivity.this);
            dialog.show();
            dialog.setOnClickListener(new DialogPermission.onClickListener() {
                @Override
                public void onClick() {
                    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                    startActivityForResult(intent, RESULT_ACTION_USAGE_ACCESS_SETTINGS);
                }
            });
        } else {
            gotoCreatePwdActivity();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_ACTION_USAGE_ACCESS_SETTINGS) {
            if (LockUtil.isStatAccessPermissionSet(SplashActivity.this)) {
                gotoCreatePwdActivity();
            } else {
                //ToastUtil.showToast("没有权限");
                finish();
            }
        }
    }

    private void gotoCreatePwdActivity() {
        Intent intent = new Intent(SplashActivity.this, CreatePwdActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void initAction() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
