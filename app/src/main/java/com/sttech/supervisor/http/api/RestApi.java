package com.sttech.supervisor.http.api;


import com.sttech.supervisor.dto.MessageDto;
import com.sttech.supervisor.dto.MobileResponse;
import com.sttech.supervisor.dto.ProjectPageDto;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * 请求接口配置
 */
public interface RestApi {

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<MobileResponse<String>> login(@Field("accountText") String account, @Field("password") String password);

    /**
     * 省市区信息
     *
     * @return
     */
    @POST("data/location")
    Observable<MobileResponse<String>> getLocation();

    /**
     * 我的消息
     *
     * @return
     */
    @POST("data/message")
    Observable<MobileResponse<List<MessageDto>>> getMessage();

    /**
     * 项目详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("data/projectDetail")
    Observable<MobileResponse<ProjectPageDto>> projectDetail(@Field("projectId") String projectId);

    /**
     * 发送验证码
     *
     * @param cellPhone
     * @return
     */
    @FormUrlEncoded
    @POST("data/sendVerificationCode")
    Observable<MobileResponse<String>> sendVerificationCode(@Field("cellPhone") String cellPhone);

    /**
     * 重置密码
     *
     * @param cellPhone
     * @param newPassword
     * @return
     */
    @FormUrlEncoded
    @POST("data/resetPwd")
    Observable<MobileResponse<String>> resetPwd(@Field("cellPhone") String cellPhone, @Field("newPassword") String newPassword);


    /**
     * 上传 位置信息
     *
     * @param latitude  经度
     * @param lontitude 纬度
     * @return
     */
    @FormUrlEncoded
    @POST("data/uploadLocation")
    Observable<MobileResponse<String>> uploadLocation(@Field("latitude") double latitude, @Field("lontitude") double lontitude);


//    /**
//     * 上传 项目资料
//     *
//     * @param param
//     * @param files
//     * @return
//     */

    /**
     * 上传 项目资料
     *
     * @param params
     * @return
     */
    @Multipart
    @POST("/uploadProjectAttach")
//    Observable<MobileResponse<String>> uploadProjectAttach(@FieldMap Map<String, String> param, @PartMap Map<String, RequestBody> files);
    Observable<MobileResponse<String>> uploadProjectAttach(@PartMap Map<String, RequestBody> params);


    //    @FormUrlEncoded
//    @POST("query?key=7c2d1da3b8634a2b9fe8848c3a9edcba")
//    Observable<ApiResponse<TestBean>> getDatas(@Field("pno") int pno, @Field("ps") int ps, @Field("dtype") String dtype);
//
//    @GET("news/latest")
//

//
//    @POST("{url}")
//    Observable<Daily> executePost(
//            @Path("url") String url,
//            @QueryMap Map<String, String> maps);
//
//    @FormUrlEncoded
//    @POST("/newfind/index_ask")
//    Observable<Daily> getDaJia(@Field("page") int page,
//                               @Field("pageSize") int size,
//                               @Field("tokenMark") long tokenMark,
//                               @Field("token") String token
//    );
//
//    @FormUrlEncoded
//    @POST("FundPaperTrade/AppUserLogin")
//    Observable<Daily> getTransData(@FieldMap Map<String, String> map);
//
//    @Multipart
//    @POST("v1/public/core/?service=user.updateAvatar")
//    Observable<Daily> uploadMultipleTypeFile(@Part("data") String des, @PartMap Map<String, RequestBody> params);

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
