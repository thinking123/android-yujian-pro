package com.yujian.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.jess.arms.di.scope.FragmentScope;
import com.yujian.di.module.UserProfileModule;
import com.yujian.mvp.contract.UserProfileContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yujian.mvp.ui.activity.UserProfileActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/11/2019 13:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = UserProfileModule.class, dependencies = AppComponent.class)
public interface UserProfileComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserProfileComponent.Builder view(UserProfileContract.View view);

        UserProfileComponent.Builder appComponent(AppComponent appComponent);

        UserProfileComponent build();
    }
}