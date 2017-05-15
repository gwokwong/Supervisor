package com.sttech.supervisor.http;

import android.content.Context;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sttech.supervisor.Constant;
import com.sttech.supervisor.http.api.RestApi;
import com.sttech.supervisor.http.cache.CacheProvider;
import com.sttech.supervisor.http.cookies.NovateCookieManger;
import com.sttech.supervisor.http.entity.MobileLoginResultDto;
import com.sttech.supervisor.http.entity.MobileResponse;
import com.sttech.supervisor.http.exception.ApiException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    public static final String TAG = HttpManager.class.getSimpleName();
    private static final int DEFAULT_TIMEOUT = 8;
    private Retrofit mRetrofit;
    private RestApi mRestApi;
    private final CacheProvider cacheProvider;
    private static Context mContext;
    private volatile static HttpManager instance;

    private HttpManager() {
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("HttpManager", message);
            }
        });
        loggingInterceptor.setLevel(level);
        //拦截请求和响应日志并输出，其实有很多封装好的日志拦截插件，大家也可以根据个人喜好选择。
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .cookieJar(new NovateCookieManger(mContext))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
        ;

        OkHttpClient okHttpClient = builder.build();

        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .build();

        //缓存处理
        cacheProvider = new RxCache.Builder()
                .persistence(mContext.getFilesDir(), new GsonSpeaker())
                .using(CacheProvider.class);

        mRestApi = mRetrofit.create(RestApi.class);
    }

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public static void init(Context context) {
        mContext = context;
    }


//    private <T> void toSubscribe(Observable<ApiResponse<T>> o, Observer<T> s) {
//        o.subscribeOn(Schedulers.io())
//                .map(new Function<ApiResponse<T>, T>() {
//                    @Override
//                    public T apply(@NonNull ApiResponse<T> response) throws Exception {
//                        int code = Integer.parseInt(response.getCode());
//                        if (code != Constant.SUCCESS_CODE) {
//                            throw new ApiException(code, response.getMsg());
//                        } else {
//                            return response.getDatas();
//                        }
//                    }
//                })
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s);
//    }
//
//    private <T> void toSubscribe2(Observable<T> o, Observer<T> s) {
//        o.subscribeOn(Schedulers.io())
////                .map(Function)
////                .map(new Function<T>, T>() {
////                    @Override
////                    public T apply(@NonNull ApiResponse<T> response) throws Exception {
////                        int code = Integer.parseInt(response.getCode());
////                        if (code != Constant.SUCCESS_CODE) {
////                            throw new ApiException(code, response.getMsg());
////                        } else {
////                            return response.getDatas();
////                        }
////                    }
////                })
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s);
//    }


//
//    public void getDatasWithCache(Observer<TestBean> subscriber, int pno, int ps, String dtype, boolean update) {
//        toSubscribe(cacheProvider.getDatas(mRestApi.getDatas(pno, ps, dtype), new EvictProvider(update)), subscriber);
//    }
//
//    public void getDailyWithCache(Observer<Daily> subscriber, boolean update) {
//        toSubscribe2(cacheProvider.getDaily(mRestApi.getDaily(), new EvictProvider(update)), subscriber);
//    }
//
//    public void getDailyWithCache2(Observer<Daily> subscriber, boolean update, int idLastUserQueried) {
//        toSubscribe2(cacheProvider.getDaily2(mRestApi.getDaily(), new EvictDynamicKey(update), new DynamicKey(idLastUserQueried)), subscriber);
//    }
//
//
//    public void getDatasNoCache(Observer<TestBean> subscriber, int pno, int ps, String dtype) {
//        toSubscribe(mRestApi.getDatas(pno, ps, dtype), subscriber);
//    }


    public void login(Observer<String> subscriber, String account, String password) {
        toSubscribe(mRestApi.login(account, password), subscriber);
    }

    public void getLocation(Observer<String> subscriber) {
        toSubscribe(mRestApi.getLocation(), subscriber);
    }

    //    private <T> void toSubscribe(Observable<ApiResponse<T>> o, Observer<T> s) {
//        o.subscribeOn(Schedulers.io())
//                .map(new Function<ApiResponse<T>, T>() {
//                    @Override
//                    public T apply(@NonNull ApiResponse<T> response) throws Exception {
//                        int code = Integer.parseInt(response.getCode());
//                        if (code != Constant.SUCCESS_CODE) {
//                            throw new ApiException(code, response.getMsg());
//                        } else {
//                            return response.getDatas();
//                        }
//                    }
//                })
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s);
//    }

    private <T> void toSubscribe(Observable<MobileResponse<T>> o, Observer<T> s) {
        o.subscribeOn(Schedulers.io())
                .map(new Function<MobileResponse<T>, T>() {
                    @Override
                    public T apply(@NonNull MobileResponse<T> tMobileResponse) throws Exception {
                        int code = tMobileResponse.getCode();
                        if (code == MobileResponse.CODE_OK) {
                            return tMobileResponse.getData();
                        } else if (code == MobileResponse.CODE_INVALID_SESSION) {
                            throw new ApiException(code, "请求超时");
                        } else if (code == MobileResponse.CODE_LOGIC_EXCEPTION) {
                            throw new ApiException(code, tMobileResponse.getMessage());
                        }
                        return null;
                    }
                })
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);

    }


}
