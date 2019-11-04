package com.app.framework.net.interceptor;

import com.app.framework.utils.LogUtils;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String TAG = "LoggingInterceptor";
        LogUtils.v(TAG, "request:" + request.toString()+"\r\nrequest body:"+request.body());
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        LogUtils.v(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtils.i(TAG, "response body:" + content);
        return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, content)).build();
    } 
}