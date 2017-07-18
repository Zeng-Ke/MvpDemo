package com.zk.mvp.mvpdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zk.mvp.mvpdemo.interface_.IView;
import com.zk.mvp.mvpdemo.util.PresenterUtil;

/**
 * author: ZK.
 * date:   On 2017/7/18.
 */
public abstract class BasePresenterActivity<T extends BasePresenter<E>, E extends IView> extends AppCompatActivity implements IView {


    private T mPresenter;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
        initView(savedInstanceState);
        mPresenter.onViewCreate();
    }

    protected abstract void initView(Bundle savedInstanceState);


    private void createPresenter() {
        mPresenter = PresenterUtil.createPresenter(this);
    }


    public T getPresenter() {
        return mPresenter;
    }

    @Override
    public void onExection(Exception e) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onViewPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onViewResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDestory();
    }


}
