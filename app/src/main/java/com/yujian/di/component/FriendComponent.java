package com.yujian.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yujian.di.module.FriendModule;
import com.yujian.mvp.contract.FriendContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yujian.mvp.ui.fragment.main.FriendFragment;
import com.yujian.mvp.ui.fragment.main.FriendListFragment;
import com.yujian.mvp.ui.fragment.main.FriendMapFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/06/2019 14:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = FriendModule.class, dependencies = AppComponent.class)
public interface FriendComponent {
    void inject(FriendFragment fragment);
    void inject(FriendMapFragment fragment);
    void inject(FriendListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FriendComponent.Builder view(FriendContract.View view);

        FriendComponent.Builder appComponent(AppComponent appComponent);

        FriendComponent build();
    }
}