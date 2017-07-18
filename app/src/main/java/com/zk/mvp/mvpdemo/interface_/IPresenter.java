package com.zk.mvp.mvpdemo.interface_;

/**
 * author: ZK.
 * date:   On 2017/7/18.
 */
public interface IPresenter<T extends IView> {

    T getView();

    void onViewCreate();

    void onViewPause();

    void onViewResume();

    void onViewDestory();
}
