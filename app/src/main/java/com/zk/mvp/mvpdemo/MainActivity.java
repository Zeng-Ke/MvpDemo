package com.zk.mvp.mvpdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zk.mvp.mvpdemo.base.BasePresenterActivity;

import java.util.List;

public class MainActivity extends BasePresenterActivity<MainPresenter, MainPresenter.MainView> implements MainPresenter.MainView {


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        getPresenter().getData();
    }

    @Override
    public void onGetDataSuccess(List<String> strings) {
        Log.d("===", strings.get(0));
        Toast.makeText(this, strings.get(0), Toast.LENGTH_SHORT).show();
    }


}
