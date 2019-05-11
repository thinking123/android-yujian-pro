package com.yujian.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yujian.di.component.DaggerUserProfileComponent;
import com.yujian.mvp.contract.UserProfileContract;
import com.yujian.mvp.presenter.UserProfilePresenter;

import com.yujian.R;
import com.yujian.mvp.ui.fragment.userProfile.UserProfileMainFragment;


import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class UserProfileActivity extends SupportActivity {
    private String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        userId = bundle.getString("id");

        setContentView(R.layout.activity_user_profile);


        ISupportFragment fragment = findFragment(UserProfileMainFragment.class);

        if(fragment == null){
            loadRootFragment(R.id.user_profile_container ,
                    UserProfileMainFragment.newInstance(userId));
        }

    }



}
