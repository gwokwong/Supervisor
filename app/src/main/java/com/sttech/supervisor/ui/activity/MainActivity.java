package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;
import com.sttech.supervisor.Constant;
import com.sttech.supervisor.R;
import com.sttech.supervisor.event.MeEvent;
import com.sttech.supervisor.http.HttpManager;
import com.sttech.supervisor.http.callback.OnResultCallBack;
import com.sttech.supervisor.http.subscriber.HttpSubscriber;
import com.sttech.supervisor.ui.fragment.HomeFragment;
import com.sttech.supervisor.ui.fragment.MeFragment;
import com.sttech.supervisor.ui.fragment.dialog.DialogFragmentHelper;
import com.sttech.supervisor.utils.SpUtils;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public class MainActivity extends TActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content, homeFragment).commit();
                    return true;
                case R.id.navigation_me:
                    transaction.replace(R.id.content, meFragment).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        initView();
        initData();
        test();
    }

    private DialogFragment mDialogFragment;

    private void test() {

        boolean isLogin = (boolean) SpUtils.get(Constant.SP_KEY_IS_LOGIN, false);
//        Logger.d("isLogin->" + isLogin);

        HttpSubscriber httpSubscriber = new HttpSubscriber(new OnResultCallBack<String>() {

            @Override
            public void onStart() {
//                Logger.d("onStart");
                mDialogFragment = DialogFragmentHelper.showProgress(getSupportFragmentManager(), "数据加载中...");
            }

            @Override
            public void onSuccess(String data) {
                Logger.d("onSuccess" + data);
                toast(data);
            }

            @Override
            public void onError(int code, String errorMsg) {
                Logger.d("onError" + errorMsg);
                toaste(errorMsg);

            }

            @Override
            public void onCompleted() {
//                Logger.d("onCompleted");
                mDialogFragment.dismiss();
            }
        });

        HttpManager.getInstance().getLocation(httpSubscriber);


//        String name = "韦国旺";
//        Logger.d("加密前->" + name);
//        String name1 = Base64Utils.encryptBase64(name);
//        Logger.d("加密后->" + name1);
//        String name2 = Base64Utils.decryptBase64(name1);
//        Logger.d("解密后->" + name2);
//        HttpSubscriber httpSubscriber = new HttpSubscriber(new OnResultCallBack<Daily>() {
//
//            @Override
//            public void onStart() {
//                toast("开始请求");
//                Logger.d("开始请求");
//            }
//
//            @Override
//            public void onSuccess(Daily daily) {
//                toast("请求成功" + daily.toString());
//                Logger.d("请求成功" + daily.toString());
//
//            }
//
//            @Override
//            public void onError(int code, String errorMsg) {
//                toast("请求失败");
//                Logger.d("请求失败");
//
//            }
//
//            @Override
//            public void onCompleted() {
//                toast("请求结束");
//                Logger.d("请求结束");
//
//            }
//        });
//        HttpManager.getInstance().getDailyWithCache2(httpSubscriber, true, 1);
    }


    private final int SDK_PERMISSION_REQUEST = 127;

//    private String permissionInfo;

//    @TargetApi(23)
//    private void getPersimmions() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ArrayList<String> permissions = new ArrayList<>();
//            /***
//             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
//             */
//            // 定位精确位置
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//            }
//            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//            }
//            /*
//             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
//			 */
//            // 读写权限
//            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
//            }
//            // 读取电话状态权限
//            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
//                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
//            }
//
//            if (permissions.size() > 0) {
//                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
//            }
//        }
//    }
//
//    @TargetApi(23)
//    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
//        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
//            if (shouldShowRequestPermissionRationale(permission)) {
//                return true;
//            } else {
//                permissionsList.add(permission);
//                return false;
//            }
//
//        } else {
//            return true;
//        }
//    }
//
//    @TargetApi(23)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        // TODO Auto-generated method stub
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//    }


//    private HttpSubscriber mHttpObserver;

    private void initData() {

//        mHttpObserver = new HttpSubscriber(new OnResultCallBack<Daily>() {
//            @Override
//            public void onSuccess(Daily daily) {
////                Logger.d(daily.toString());
//            }
//
//            @Override
//            public void onError(int code, String errorMsg) {
////                Logger.d("onError: code:" + code + "  errorMsg:" + errorMsg);
//            }
//        });


    }

    @Override
    public void onCreateBinding() {

    }

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    private HomeFragment homeFragment;
    private MeFragment meFragment;

    private void initView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        meFragment = new MeFragment();
        fragmentManager.beginTransaction().replace(R.id.content, homeFragment).commit();
    }

    /*个人建议在onResume注册EventBus
     *在可见可交互状态下注册，尽可能少的占用内存
     */
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    /*个人建议在onPause注册EventBus(将当前Activity注册为事件订阅者)
     *不影响功能的情况下提早解除注册，尽可能少的占用内存
     */
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMeEvent(MeEvent event) {
        Logger.d("收到消息" + event.number);
        meFragment.updateCount(event.type, event.number);

    }


}

