package com.yujian.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yujian.di.module.FitnessRoomModule;
import com.yujian.mvp.contract.FitnessRoomContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yujian.mvp.ui.fragment.main.FitnessRoomFragment;


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
@Component(modules = FitnessRoomModule.class, dependencies = AppComponent.class)
public interface FitnessRoomComponent {
    void inject(FitnessRoomFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FitnessRoomComponent.Builder view(FitnessRoomContract.View view);

        FitnessRoomComponent.Builder appComponent(AppComponent appComponent);

        FitnessRoomComponent build();
    }
}