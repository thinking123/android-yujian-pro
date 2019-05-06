package com.yujian.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yujian.di.module.DynamicModule;
import com.yujian.mvp.contract.DynamicContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yujian.mvp.ui.fragment.main.DynamicFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/06/2019 14:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DynamicModule.class, dependencies = AppComponent.class)
public interface DynamicComponent {
    void inject(DynamicFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DynamicComponent.Builder view(DynamicContract.View view);

        DynamicComponent.Builder appComponent(AppComponent appComponent);

        DynamicComponent build();
    }
}