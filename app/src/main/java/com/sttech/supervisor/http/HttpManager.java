package com.sttech.supervisor.http;

import android.content.Context;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sttech.supervisor.Constant;
import com.sttech.supervisor.dto.ImageDto;
import com.sttech.supervisor.dto.MobileResponse;
import com.sttech.supervisor.dto.ProjectAttachDto;
import com.sttech.supervisor.dto.ProjectPageDto;
//import com.sttech.supervisor.entity.Daily;
import com.sttech.supervisor.http.api.RestApi;
import com.sttech.supervisor.http.cache.CacheProvider;
import com.sttech.supervisor.http.cookies.NovateCookieManger;
import com.sttech.supervisor.http.exception.ApiException;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

public class HttpManager {
    public static final String TAG = HttpManager.class.getSimpleName();
    private static final int DEFAULT_TIMEOUT = 10;
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
                Log.d(TAG, message);
            }
        });
        loggingInterceptor.setLevel(level);
        //拦截请求和响应日志并输出，其实有很多封装好的日志拦截插件，大家也可以根据个人喜好选择。
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(false)
                .addInterceptor(loggingInterceptor)
                .cookieJar(new NovateCookieManger(mContext))
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
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

//    public void getDaily(Observer<Daily> subscriber) {
////        mRestApi.getDaily().subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(subscriber);
//    }

    /**
     * 登录
     *
     * @param subscriber
     * @param account
     * @param password
     */
    public void login(Observer<String> subscriber, String account, String password) {
        toSubscribe(mRestApi.login(account, password), subscriber);
    }

    /**
     * 省市区信息
     *
     * @param subscriber
     */
    public void getLocation(Observer<String> subscriber) {
        toSubscribe(mRestApi.getLocation(), subscriber);
    }

    /**
     * 发送验证码
     *
     * @param subscriber
     * @param cellPhone
     */
    public void sendVerificationCode(Observer<String> subscriber, String cellPhone) {
        toSubscribe(mRestApi.sendVerificationCode(cellPhone), subscriber);
    }


    /**
     * 重置密码
     *
     * @param subscriber
     * @param cellPhone
     * @param newPassword
     */
    public void resetPwd(Observer<String> subscriber, String cellPhone, String newPassword) {
        toSubscribe(mRestApi.resetPwd(cellPhone, newPassword), subscriber);
    }


    /**
     * 项目详情
     *
     * @param subscriber
     */
    public void getProjectDetail(Observer<ProjectPageDto> subscriber, String projectId) {
        toSubscribe(cacheProvider.getProjectDetail(mRestApi.projectDetail(projectId), new EvictDynamicKey(true), new DynamicKey(projectId)), subscriber);
    }


    /**
     * 上传位置信息
     *
     * @param subscriber
     * @param latitude
     * @param lontitude
     */
    public void uploadLocation(Observer<String> subscriber, double latitude, double lontitude) {
        toSubscribe(mRestApi.uploadLocation(latitude, lontitude), subscriber);
    }


    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

    /**
     * 上传项目资料
     *
     * @param subscriber
     * @param projectAttachDto
     */
    public void uploadProjectAttach(Observer<String> subscriber, ProjectAttachDto projectAttachDto) {
        Map<String, RequestBody> param = new HashMap<>();

        param.put("createUserId", toRequestBodyOfText(projectAttachDto.getCreateUserId()));
        param.put("createUserName", toRequestBodyOfText(projectAttachDto.getCreateUserName()));
        param.put("createTime", toRequestBodyOfText(projectAttachDto.getCreateTime()));
        param.put("createUserAvatarPath", toRequestBodyOfText(projectAttachDto.getCreateUserAvatarPath()));
        param.put("categoryLabel", toRequestBodyOfText(projectAttachDto.getCategoryLabel()));
        param.put("content", toRequestBodyOfText(projectAttachDto.getContent()));

        List<ImageDto> imageList = projectAttachDto.getImageList();
        int imageListSize = imageList.size();
        if (imageListSize > 0) {
//            for (ImageDto imageDto : imageList) {
//                File f = new File(imageDto.getPath());
//                RequestBody _requestBody = toRequestBodyOfImage(f);
//                param.put("file\"; filename=\"" + f.getName() + "", _requestBody);
//            }

            for (int i = 0; i < imageListSize; i++) {
                File f = new File(imageList.get(i).getPath());
                RequestBody _requestBody = toRequestBodyOfImage(f);
                param.put("file\"; filename=\"" + f.getName() + "", _requestBody);

            }
        }


        toSubscribe(mRestApi.uploadProjectAttach(param), subscriber);
    }


    public static RequestBody toRequestBodyOfText(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public static RequestBody toRequestBodyOfImage(File pFile) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), pFile);

//        new MultipartBody.Builder().setType(MultipartBody.FORM)
//                        .addFormDataPart("file", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
        return fileBody;
    }


//    public void getDailyWithCache2(Observer<Daily> subscriber, boolean update, int idLastUserQueried) {
////        toSubscribe2(cacheProvider.getDaily2(mRestApi.getDaily(), new EvictDynamicKey(update), new DynamicKey(idLastUserQueried)), subscriber);
////    }

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
