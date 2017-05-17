package com.sttech.supervisor.ui.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.orhanobut.logger.Logger;
import com.sttech.supervisor.MyApp;
import com.sttech.supervisor.R;
import com.sttech.supervisor.entity.Project;
import com.sttech.supervisor.http.HttpManager;
import com.sttech.supervisor.http.callback.OnResultCallBack;
import com.sttech.supervisor.http.subscriber.HttpSubscriber;
import com.sttech.supervisor.map.LocationService;
import com.sttech.supervisor.ui.activity.ProjectDetailActivity;
import com.sttech.supervisor.ui.adapter.AreaAdapter;
import com.sttech.supervisor.ui.adapter.GirdDropDownAdapter;
import com.sttech.supervisor.ui.adapter.ProjectListAdapter;
import com.sttech.supervisor.ui.fragment.dialog.DialogFragmentHelper;
import com.sttech.supervisor.ui.widget.DropDownMenu;
import com.sttech.supervisor.ui.widget.SpacesItemDecoration;
import com.sttech.supervisor.ui.widget.citypicker.adapter.CityListAdapter;
import com.sttech.supervisor.ui.widget.citypicker.adapter.ResultListAdapter;
import com.sttech.supervisor.ui.widget.citypicker.db.DBManager;
import com.sttech.supervisor.ui.widget.citypicker.model.City;
import com.sttech.supervisor.ui.widget.citypicker.model.LocateState;
import com.sttech.supervisor.ui.widget.citypicker.view.SideLetterBar;
import com.sttech.supervisor.ui.widget.xrecyclerview.RecyclerItemCallback;
import com.sttech.supervisor.ui.widget.xrecyclerview.XRecyclerView;
import com.sttech.supervisor.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * function :
 * Created by 韦国旺 on 2017/5/2.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class HomeFragment extends TFragment {

    @Override
    public int provideContentViewId() {
        return R.layout.fr_home;
    }

    private XRecyclerView xRecyclerView;
    private ProjectListAdapter projectListAdapter;
    private static int position = 0;


    public void initPopupViews(View view) {

        xRecyclerView = (XRecyclerView) view.findViewById(R.id.recyclerview);
        projectListAdapter = new ProjectListAdapter(getActivity());
        xRecyclerView.verticalLayoutManager().setAdapter(projectListAdapter);
        xRecyclerView.setOnRefreshListener(new XRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 3; i++)
                            projectListAdapter.addElement(0, new Project("高档写字楼装修" + (++position), "12", "写字楼", "韦先生", "广东省深圳市宝安区坂田大道"));
                        xRecyclerView.refreshComlete();
                    }
                }, 500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 2; i++)
                            projectListAdapter.addElement(new Project("创业公司装修" + (++position), "0", "办公室", "陈先生", "广东省深圳市宝安区西乡大厦"));
                        xRecyclerView.refreshComlete();
                    }
                }, 400);
            }
        });

        xRecyclerView.autoRefresh();
        projectListAdapter.setRecItemClick(new RecyclerItemCallback<Project, ProjectListAdapter.RecViewHolder>() {
            @Override
            public void onItemClick(int position, Project model, ProjectListAdapter.RecViewHolder holder) {
                super.onItemClick(position, model, holder);
                ProjectDetailActivity.start(getActivity(), "123456", true);

            }
        });

        xRecyclerView.addItemDecoration(new SpacesItemDecoration(0, dip2px(15)));
    }

    private DropDownMenu mDropDownMenu;
    //    private String headers[] = {"省市", "项目经理", "工作记录", "地域"};
    private String headers[] = {"项目经理", "工作记录", "地域"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter cityAdapter;
    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String managers[] = {"不限", "张三", "李四", "赵五", "陈六", "麻七", "JACK", "PETER"};
    private String workRecords[] = {"不限", "有工作记录", "无工作记录"};
    private String areas[] = {"不限", "福田区", "南山区", "龙华新区", "龙岗区", "宝安区"};

    private AreaAdapter managerAdapter;
    private GirdDropDownAdapter workRecordAdapter;
    private AreaAdapter areaAdapter;
    private int areaPosition = 0;
    private int managerPostion = 0;

    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private ImageView backBtn;
    private ViewGroup emptyView;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City> mAllCities;
    private DBManager dbManager;

    private LocationService locationService;

    private Handler mHandlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mCityAdapter.updateLocateState(LocateState.SUCCESS, (String) msg.obj);
                    break;
                case 1:
                    Logger.d("HomeFragment定位失败");
                    mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    break;
            }

        }
    };

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                mHandlder.obtainMessage(0, location.getCity()).sendToTarget();
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
//                logMsg(sb.toString());  //todo 传递定位结果
//                Logger.d("HomeFragment" + sb.toString());
            } else {
                mHandlder.obtainMessage(1, location.getCity()).sendToTarget();
            }

        }

        public void onConnectHotSpotMessage(String s, int i) {

        }
    };

    private DialogFragment mDialogFragment;

    private void test() {
        HttpSubscriber httpSubscriber = new HttpSubscriber(new OnResultCallBack<String>() {

            @Override
            public void onStart() {
                mDialogFragment = DialogFragmentHelper.showProgress(getActivity().getSupportFragmentManager(), "数据加载中...");
            }

            @Override
            public void onSuccess(String data) {
                toastInfo(data);
            }

            @Override
            public void onError(int code, String errorMsg) {
                toastInfo(errorMsg);

            }

            @Override
            public void onCompleted() {
                mDialogFragment.dismiss();
            }
        });
        HttpManager.getInstance().getLocation(httpSubscriber);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onStart", "onStart");
        locationService = ((MyApp) getActivity().getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getActivity().getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();
    }

    @Override
    public void initView(View view) {
        initNavigation(view, "监管项目");
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);

        //init city view
        final View cityView = getActivity().getLayoutInflater().inflate(R.layout.ly_city_view, null);
        dbManager = new DBManager(getActivity());
        dbManager.copyDBFile();
        mAllCities = dbManager.getAllCities();
        mCityAdapter = new CityListAdapter(getActivity(), mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
//                back(name);
                toastInfo("选择的city是" + name);
                mDropDownMenu.setTabText(name);
                mDropDownMenu.closeMenu();
            }

            @Override
            public void onLocateClick() {
                toastInfo("重新定位...");
                Log.e("onLocateClick", "重新定位...");
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
//                mLocationClient.startLocation();
                locationService.start();// 定位SDK
            }
        });

        mResultAdapter = new ResultListAdapter(getContext(), null);
        mListView = (ListView) cityView.findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);

        TextView overlay = (TextView) cityView.findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) cityView.findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox = (EditText) cityView.findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        emptyView = (ViewGroup) cityView.findViewById(R.id.empty_view);
        mResultListView = (ListView) cityView.findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                back(mResultAdapter.getItem(position).getName());

                String cityName = mResultAdapter.getItem(position).getName();
                toastInfo("选择的city是" + cityName);
                mDropDownMenu.setTabText(cityName);
                mDropDownMenu.closeMenu();
                CommonUtils.closeKeybord(searchBox, getActivity()); //关闭软键盘
            }
        });

        clearBtn = (ImageView) cityView.findViewById(R.id.iv_search_clear);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBox.setText("");
                clearBtn.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                mResultListView.setVisibility(View.GONE);
            }
        });

        //init manager menu
        final View managerView = getActivity().getLayoutInflater().inflate(R.layout.ly_drop_area, null);
        GridView managerGv = (GridView) managerView.findViewById(R.id.area);
        managerAdapter = new AreaAdapter(getActivity(), Arrays.asList(managers));
        managerGv.setAdapter(managerAdapter);
        Button okBtn = (Button) managerView.findViewById(R.id.ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(managerPostion == 0 ? headers[0] : managers[managerPostion]);
                mDropDownMenu.closeMenu();
            }
        });
        managerGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                managerAdapter.setCheckItem(position);
                managerPostion = position;
            }
        });


        //init workRecord menu
        final ListView workRecordView = new ListView(getActivity());
        workRecordView.setDividerHeight(0);
        workRecordAdapter = new GirdDropDownAdapter(getActivity(), Arrays.asList(workRecords));
        workRecordView.setDividerHeight(0);
        workRecordView.setAdapter(workRecordAdapter);
        workRecordView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                workRecordAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : workRecords[position]);
                mDropDownMenu.closeMenu();
            }
        });


        //init area menu
        final View areaView = getActivity().getLayoutInflater().inflate(R.layout.ly_drop_area, null);
        GridView areaGv = (GridView) areaView.findViewById(R.id.area);
        areaAdapter = new AreaAdapter(getActivity(), Arrays.asList(areas));
        areaGv.setAdapter(areaAdapter);
        Button ok = (Button) areaView.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(areaPosition == 0 ? headers[2] : areas[areaPosition]);
                mDropDownMenu.closeMenu();
            }
        });
        areaGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaAdapter.setCheckItem(position);
                areaPosition = position;
            }
        });

        //init popupViews
//        popupViews.add(cityView);
        popupViews.add(managerView);
        popupViews.add(workRecordView);
        popupViews.add(areaView);

        //init body view
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.ly_fr_home_body, null);
        initPopupViews(contentView);
        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

    }

    @Override
    public void onStop() {
        super.onStop();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }


}
