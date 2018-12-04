package com.android.lock.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by admin on 2018/12/03.
 *推荐加锁的应用信息
 */

public class FaviterInfo extends DataSupport {
    public String packageName;

    public FaviterInfo() {
    }

    public FaviterInfo(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
