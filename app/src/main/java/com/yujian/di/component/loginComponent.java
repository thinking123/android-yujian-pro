package com.yujian.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yujian.di.module.loginModule;
import com.yujian.mvp.contract.loginContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yujian.mvp.ui.activity.LoginActivity;
import com.yujian.mvp.ui.fragment.login.loginFragment;
import com.yujian.mvp.ui.fragment.login.registerFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/27/2019 23:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = loginModule.class, dependencies = AppComponent.class)
public interface loginComponent {
    void inject(loginFragment fragment);
    void inject(registerFragment fragment);
    void inject(LoginActivity loginActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        loginComponent.Builder view(loginContract.View view);

        loginComponent.Builder appComponent(AppComponent appComponent);

        loginComponent build();
    }
}