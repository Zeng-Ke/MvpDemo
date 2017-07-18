package com.zk.mvp.mvpdemo;

import com.zk.mvp.mvpdemo.base.BasePresenter;
import com.zk.mvp.mvpdemo.interface_.IView;
import com.zk.mvp.mvpdemo.thread.ThreadExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * author: ZK.
 * date:   On 2017/7/18.
 */
public  class MainPresenter extends BasePresenter<MainPresenter.MainView> {


    public MainPresenter(MainView viewer) {
        super(viewer);
    }

    public interface MainView extends IView {
        void onGetDataSuccess(List<String> strings);
    }


    public void getData() {
        ThreadExecutor.runInAsync(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<String>();
                list.add("1");
                list.add("2");
                list.add("3");
                list.add("4");
                list.add("5");
                getView().onGetDataSuccess(list);
            }
        }, 3000);
    }

}