package com.android.lock.mvp.contract;

import android.content.Context;

import com.android.lock.base.BasePresenter;
import com.android.lock.base.BaseView;
import com.android.lock.bean.CommLockInfo;

import java.util.List;

/**
 * Created by admin on 2018/12/03.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void loadAppInfoSuccess(List<CommLockInfo> list);
    }

    interface Presenter extends BasePresenter {
        void loadAppInfo(Context context, boolean isSort);

        void loadLockAppInfo(Context context);

        void onDestroy();
    }
}
