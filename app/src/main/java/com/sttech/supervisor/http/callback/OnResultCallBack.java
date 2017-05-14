package com.sttech.supervisor.http.callback;

public interface OnResultCallBack<T> {

    void onStart();

    void onSuccess(T t);

    void onError(int code, String errorMsg);

    void onCompleted();
}
