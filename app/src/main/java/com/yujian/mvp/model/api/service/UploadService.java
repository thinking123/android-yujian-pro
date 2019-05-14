package com.yujian.mvp.model.api.service;

import com.yujian.entity.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadService {
    /*
    * POST /api/upload/uploadFileSingle
    * */
    @Multipart
    @POST("/api/upload/uploadFileSingle")
    Observable<BaseResponse<String>> uploadImage(
            @Part MultipartBody.Part upload_file
    );

    /*
    * POST /api/upload/upload
    * */
    @Multipart
    @POST("/api/upload/upload")
    Observable<BaseResponse<List<String>>> uploadImages(
            @Part List<MultipartBody.Part> upload_file
    );
}
