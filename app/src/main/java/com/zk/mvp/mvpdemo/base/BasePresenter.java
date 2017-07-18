package com.zk.mvp.mvpdemo.base;

import com.zk.mvp.mvpdemo.interface_.IPresenter;
import com.zk.mvp.mvpdemo.interface_.IView;

/**
 * author: ZK.
 * date:   On 2017/7/18.
 */
public class BasePresenter<T extends IView> implements IPresenter<T> {

    private T mViewer;

    public BasePresenter(T viewer) {
        mViewer = viewer;
    }

    @Override
    public T getView() {
        return mViewer;
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewPause() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestory() {
        mViewer = null;
    }
}
