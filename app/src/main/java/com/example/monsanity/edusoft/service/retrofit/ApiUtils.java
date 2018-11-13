package com.example.monsanity.edusoft.service.retrofit;

public class ApiUtils {

    private static APIService APISERVICE = null;

    public static final String URL = "https://fcm.googleapis.com/";

    public static final String SEND_ALL = "fcm/send";

    public ApiUtils() {

    }

    public static APIService getRetrofitService() {
        if (APISERVICE == null) {
            APISERVICE = RetrofitClient.getClient(URL).create(APIService.class);
        }
        return APISERVICE;
    }
}
