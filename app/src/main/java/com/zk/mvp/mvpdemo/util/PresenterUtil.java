package com.zk.mvp.mvpdemo.util;

import android.util.Log;

import com.zk.mvp.mvpdemo.base.BasePresenter;
import com.zk.mvp.mvpdemo.interface_.IView;
import com.zk.mvp.mvpdemo.base.BasePresenterActivity;
import com.zk.mvp.mvpdemo.thread.ThreadExecutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * author: ZK.
 * date:   On 2017/7/18.
 */
public class PresenterUtil {

    //IView中的方法，没返回值并且以“on”开头的方法都会在主线程中运行；
    private static final String RUN_ON_MAIN_METHOD_NAME_PREFIX = "on";


    public static <T extends BasePresenter<E>, E extends IView> T createPresenter(BasePresenterActivity<T, E> activity) {
        return createPresenter(activity.getClass(), (E)activity);
    }


    public static <T extends BasePresenter<E>, E extends IView> T createPresenter(Class classOfView, E iView) {
        //获取超类的泛型参数的实际类型数组，Type[0]--> T,Type[1]--> E
        Type[] genericTypes = ((ParameterizedType) classOfView.getGenericSuperclass()).getActualTypeArguments();
        Class<?> presenterClass = null;
        Class<?> viewClass = null;
        Constructor constructor = null;
        try {
            presenterClass = TypeUtil.getClass(genericTypes[0]);
            viewClass = TypeUtil.getClass(genericTypes[1]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //返回指定参数匹配的公共构造函数的构造函数对象：http://www.yiibai.com/javalang/class_getconstructor.html
            constructor = presenterClass.getConstructor(viewClass);
        } catch (NoSuchMethodException e) {
            Log.e("BasePresenterActivity", "generic type <T> must has constructor with paramter type <E>");
            e.printStackTrace();
        }
        T presenter = null;
        if (constructor != null) {
            try {
                //利用反射生成实例。可以通过这个newinstance方法调用有参的私有的构造函数：http://www.cnblogs.com/yunger/p/5793632.html
                presenter = (T) constructor.newInstance(createProxyView(iView));
            } catch (Exception e) {
                Log.d("BasePresenterActivity", classOfView.getName() + "must  implements" + viewClass.getName());
                e.printStackTrace();
            }
        }
        return presenter;
    }


    private static <E extends IView> E createProxyView(E view) {
        return (E) Proxy.newProxyInstance(view.getClass().getClassLoader(),
                view.getClass().getInterfaces(),
                new ViewInvocationHandler(view));
    }



    private static class ViewInvocationHandler<E extends IView> implements InvocationHandler {

        private E view;

        public ViewInvocationHandler(E view) {
            this.view = view;
        }

        @Override
        public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
            //判断IView中的方法是否有返回值的
            boolean returnVoid = method.getReturnType().isAssignableFrom(void.class);
            if (returnVoid && method.getName().startsWith(RUN_ON_MAIN_METHOD_NAME_PREFIX)) {
                ThreadExecutor.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            method.invoke(view, args);
                        } catch (Exception e) {
                            e.printStackTrace();
                            view.onExection(e);
                        }
                    }
                });
                return null;
            } else
                return method.invoke(view, args);
        }
    }
}
