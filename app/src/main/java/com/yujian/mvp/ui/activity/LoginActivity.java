package com.yujian.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yujian.BuildConfig;
import com.yujian.app.MyBaseActivity;
import com.yujian.di.component.DaggerloginComponent;
import com.yujian.mvp.contract.loginContract;
import com.yujian.mvp.presenter.loginPresenter;

import com.yujian.R;
import com.yujian.mvp.ui.fragment.login.loginFragment;
import com.yujian.mvp.ui.fragment.login.registerFragment;


import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.ISupportFragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/29/2019 19:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 建议在Application里初始化
//        Fragmentation.builder()
//                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(BuildConfig.DEBUG)
////             ... // 更多查看wiki或demo
//             .install();



        ISupportFragment fragment = findFragment(loginFragment.class);

        if (fragment == null) {
//            loadMultipleRootFragment(R.id.login_container, 0 ,loginFragment.newInstance() , registerFragment.newInstance());
            loadRootFragment(R.id.login_container , loginFragment.newInstance());
        }
    }
}
