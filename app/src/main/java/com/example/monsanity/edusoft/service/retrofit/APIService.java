package com.example.monsanity.edusoft.service.retrofit;

import com.example.monsanity.edusoft.service.retrofit.FCMUtils.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    String key = "AAAAgGMnUro:APA91bGa14kHCXthXpxbVMY6waMf3Qdmtfm_WeQmCg0TQPERCj0c-MvuoTVamX_XAZh_JzNteioijiXzj0L5OTK82gprc6J3yn_gdnzqx0xL7km6Qawv8tgvLY1AJNTbJ1uJQrYSaeAH";

    // Request 3D object
//    @FormUrlEncoded
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=" + key
    })

    @POST(ApiUtils.SEND_ALL)
    Call<String> sendAll(@Body Sender sender);

}
