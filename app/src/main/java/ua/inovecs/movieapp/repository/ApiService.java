package ua.inovecs.movieapp.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiService {

    private static final int MAX_SIZE = 10 * 1024 * 1024;
    private static final int MAX_STALE = 60 * 60 * 24;

    @NonNull
    static Retrofit getRetrofit(String baseUrl, Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "httpCache");
        Cache cache = new Cache(httpCacheDirectory, MAX_SIZE);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .cache(cache)
                        .addInterceptor(chain -> {
                            try {
                                return chain.proceed(chain.request());
                            } catch (Exception e) {
                                Request offlineRequest = chain.request().newBuilder()
                                        .header("Cache-Control", "public, only-if-cached," +
                                                "max-stale=" + MAX_STALE)
                                        .build();
                                return chain.proceed(offlineRequest);
                            }
                        })
                        .build())
                .build();
    }


}
