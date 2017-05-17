package com.sttech.supervisor;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.orhanobut.logger.Logger;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.sttech.supervisor.crash.CrashHandler;
import com.sttech.supervisor.db.MobileLoginResult;
import com.sttech.supervisor.http.HttpManager;
import com.sttech.supervisor.map.LocationService;
import com.sttech.supervisor.task.Const;
import com.sttech.supervisor.task.LocationSe;
import com.sttech.supervisor.task.ProjectSe;
import com.sttech.supervisor.task.TaskManager;
import com.sttech.supervisor.utils.SpUtils;

import java.util.List;

public class MyApp extends Application {

    private static MyApp instance;
    private static Context mContext;
    private static String TAG = "debug";

    public LocationService locationService;
    public Vibrator mVibrator;

    private JobManager jobManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Logger.init(TAG);
        HttpManager.init(this);
        //异常记录日志
//        AppCrash.getInstance().init(this);

        CrashHandler mCrashHandler = CrashHandler.getInstance();
        mCrashHandler.setCustomCrashHanler(getApplicationContext());

        //初始化定位sdk，建议在Application中创建
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);

        instance = this;
        getJobManager();
        TaskManager.getInstance().init(this);

        FlowManager.init(getApplicationContext());  //db init
        if (Const.isUseService) {
            Intent intent = new Intent(this, LocationSe.class);
            startService(intent);
            Intent intent2 = new Intent(this, ProjectSe.class);
            startService(intent2);
        } else {
            TaskManager.getInstance().init(this).setLocationAlarm();
            TaskManager.getInstance().init(this).setProjectAlarm();
        }
    }

    public static MyApp getInstance() {
        return instance;
    }


    public synchronized JobManager getJobManager() {
        if (jobManager == null) {
            configureJobManager();
        }
        return jobManager;
    }

    //JobManager的配置器
    private void configureJobManager() {
        Configuration.Builder builder = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";

                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }

                    @Override
                    public void v(String text, Object... args) {
                        Log.v(TAG, String.format(text, args));

                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(5);//wait 2 minute
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder.scheduler(FrameworkJobSchedulerService.createSchedulerFor(this,
//                    MyJobService.class), true);
//        } else {
//            int enableGcm = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
//            if (enableGcm == ConnectionResult.SUCCESS) {
//                builder.scheduler(GcmJobSchedulerService.createSchedulerFor(this,
//                        MyGcmJobService.class), true);
//            }
//        }
        jobManager = new JobManager(builder.build());
    }

    /**
     * @return 全局的上下文
     */
    public static Context getmContext() {
        return mContext;
    }

    public boolean isLogin() {
        List<MobileLoginResult> results = new Select().from(MobileLoginResult.class).queryList();
//        if(results.size()>0){
//
//        }
//
//        List<Integer> uidList = new ArrayList<>();
//        SQLCondition condition = Condition.column(Person_Table.uid.getNameAlias()).in(uidList);
//        this.login = (boolean) SpUtils.get(Constant.SP_KEY_IS_LOGIN, false);  //TODO 模拟登录
//        return login;

        int mobileLoginResultSize = new Select().from(MobileLoginResult.class).queryList().size();

        Logger.d("MobileLoginResult size --------------------->" + mobileLoginResultSize);
//        Logger.d("MobileLoginResult value --------------------->" + results.get(0).toString());

        return new Select().from(MobileLoginResult.class).queryList().size() > 0;
    }

    public void setLogin(boolean login) {
        this.login = login;
        SpUtils.put(Constant.SP_KEY_IS_LOGIN, login);  //TODO 模拟登录
    }

    /**
     * 是否登录状态
     */
    public boolean login;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
