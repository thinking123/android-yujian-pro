package com.yujian.mvp.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yujian.R;
import com.yujian.app.BaseSupportFragment;
import com.yujian.di.component.DaggerFitnessRoomComponent;
import com.yujian.mvp.contract.FitnessRoomContract;
import com.yujian.mvp.model.entity.FitnessRoomBean;
import com.yujian.mvp.presenter.FitnessRoomPresenter;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;

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
public class FitnessRoomFragment extends BaseSupportFragment<FitnessRoomPresenter> implements FitnessRoomContract.View {
    private ISupportFragment[] mFragments = new ISupportFragment[2];
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    private int currentFragment = FIRST;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    public static FitnessRoomFragment newInstance() {
        FitnessRoomFragment fragment = new FitnessRoomFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFitnessRoomComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fitness_room, container, false);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fitness_room_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.switch_map:
                if(currentFragment == FIRST){
                    showHideFragment(mFragments[SECOND],mFragments[FIRST]);
                    currentFragment = SECOND;
                }else{
                    showHideFragment(mFragments[FIRST],mFragments[SECOND]);
                    currentFragment = FIRST;
                }

                title.setVisibility(currentFragment == FIRST ? View.VISIBLE :View.INVISIBLE);
                item.setIcon(currentFragment == FIRST ? R.drawable.map_list_menu : R.drawable.map_location_menu);
                break;
        }


        return true;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(ContextCompat.getColor(getContext() ,
                R.color.white));
//        toolbar.setSubtitle(R.string.fitness_room_title);
//        toolbar.setTitleTextColor(ContextCompat.getColor(getContext() ,
//                R.color.text_black));
//        ((AppCompatActivity)getActivity()).setTitle(R.string.fitness_room_title);

        title.setText(getResources().getString(R.string.fitness_room_title));
        ISupportFragment fragment = findChildFragment(FitnessRoomMapFragment.class);
        if (fragment == null) {
            mFragments[FIRST] = FitnessRoomMapFragment.newInstance();
            mFragments[SECOND] = FitnessRoomListFragment.newInstance();
            loadMultipleRootFragment(R.id.fitnessroom_container, FIRST, mFragments[FIRST], mFragments[SECOND]);
        } else {
            mFragments[FIRST] = fragment;
            mFragments[SECOND] = findChildFragment(FitnessRoomListFragment.class);
        }
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void getNearbyFitnessRoomResult(FitnessRoomBean fitnessRoomBean) {

    }

    @Override
    public void attentionResult() {

    }
}
