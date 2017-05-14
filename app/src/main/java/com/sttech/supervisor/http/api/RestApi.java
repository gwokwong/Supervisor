package com.sttech.supervisor.http.api;


import com.sttech.supervisor.entity.Daily;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface RestApi {

    @FormUrlEncoded
    @POST("query?key=7c2d1da3b8634a2b9fe8848c3a9edcba")
    Observable<ApiResponse<TestBean>> getDatas(@Field("pno") int pno, @Field("ps") int ps, @Field("dtype") String dtype);

    @GET("news/latest")
    Observable<Daily> getDaily();

    @POST("{url}")
    Observable<Daily> executePost(
            @Path("url") String url,
            @QueryMap Map<String, String> maps);

    @FormUrlEncoded
    @POST("/newfind/index_ask")
    Observable<Daily> getDaJia(@Field("page") int page,
                               @Field("pageSize") int size,
                               @Field("tokenMark") long tokenMark,
                               @Field("token") String token
    );

    @FormUrlEncoded
    @POST("FundPaperTrade/AppUserLogin")
    Observable<Daily> getTransData(@FieldMap Map<String, String> map);

    @Multipart
    @POST("v1/public/core/?service=user.updateAvatar")
    Observable<Daily> uploadMultipleTypeFile(@Part("data") String des, @PartMap Map<String, RequestBody> params);

//    public void uploadFile() {
//        String path1 = Environment.getExternalStorageDirectory() + File.separator + "test.png";
//        String path2 = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
//        ArrayList<String> pathList = new ArrayList<>();
//        pathList.add(path1);
//        pathList.add(path2);
//
//        Map<String, RequestBody> bodyMap = new HashMap<>();
//        if(pathList.size() > 0) {
//            for (int i = 0; i < pathList.size(); i++) {
//                File file = new File(pathList.get(i));
//                bodyMap.put("file"+i+"\"; filename=\""+file.getName(), RequestBody.create(MediaType.parse("image/png"),file));
//            }
//        }
//
//        APIWrapper.getInstance().uploadMultipleTypeFile("jdsjlzx",bodyMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<HttpResponse<List<String>>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        TLog.error("onError " + e.toString());
//                    }
//
//                    @Override
//                    public void onNext(HttpResponse<List<String>> response) {
//                        TLog.error("onNext " + response.toString());
//
//                    }
//                });
//
//    }
//
//    private static APIWrapper mAPIWrapper;
//
//    public APIWrapper() {
//    }
//
//    public static APIWrapper getInstance() {
//        if (mAPIWrapper == null) {
//            mAPIWrapper = new APIWrapper();
//        }
//        return mAPIWrapper;
//    }
//
//    public Observable<HttpResponse<List<String>>> uploadMultipleTypeFile(String des, Map<String,RequestBody> params) {
//        return getAPIService().uploadMultipleTypeFile(des, params);
//    }


}
