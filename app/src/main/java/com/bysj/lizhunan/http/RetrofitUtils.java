package com.bysj.lizhunan.http;

import android.util.Log;

import com.bysj.lizhunan.base.Constants;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.internal.Util.UTF_8;

public class RetrofitUtils {

    private static final String TAG = "RetrofitUtils";

    public static RequestBody body(String appName, String info) {
        return new FormBody.Builder()
                .add("appName",appName)
                .add("info", info)
                .build();
    }

    public static Request request(String appName, String info) {
        return new Request.Builder()
                .url(Constants.COLLECTION_URL)
                .post(body(appName, info)).build();
    }


    public static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                //设置读取数据的时间
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                //对象的创建
                .build();
        return client;
    }

    /**
     * 请求访问拦截器
     */
    private static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.d(TAG, "----------Request Start----------------");
            printParams(request.body());
            Log.d(TAG, "| " + request.toString() + "===========" + request.headers().toString());
            Log.d(TAG, "| " + content);
            Log.d(TAG, "----------Request End:" + duration + "毫秒----------");
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    };

    /**
     * 打印请求参数
     *
     * @param body 请求
     */
    private static void printParams(RequestBody body) {
        if (body != null) {
            Buffer buffer = new Buffer();
            try {
                body.writeTo(buffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF_8);
                }
                String params = buffer.readString(charset);
                Log.d(TAG, "| 请求参数：" + params);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
