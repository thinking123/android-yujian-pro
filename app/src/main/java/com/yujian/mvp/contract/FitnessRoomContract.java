package com.yujian.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.yujian.entity.BaseResponse;
import com.yujian.mvp.model.entity.AttentionRequestBean;
import com.yujian.mvp.model.entity.FitnessRoomBean;

import java.util.HashMap;

import io.reactivex.Observable;


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
public interface FitnessRoomContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        public void getNearbyFitnessRoomResult(FitnessRoomBean fitnessRoomBean);

        public void attentionResult();
        public void unfollowResult();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<FitnessRoomBean>> getNearbyFitnessRoom(
                String longitude,
                String latitude,
                String Kilometres
        );

        Observable<BaseResponse<String>> attention(
                String collectType,String collectUserId
        );

        Observable<BaseResponse<String>> unfollow(
                HashMap<String, String> map
        );
    }
}
