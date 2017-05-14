package com.sttech.supervisor.ui.fragment.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sttech.supervisor.R;

import java.util.Calendar;


/**
 * Created by Haoz on 2017/4/6 0006.
 */

public class DialogFragmentHelper {

    private static final String DIALOG_POSITIVE = "确定";
    private static final String DIALOG_NEGATIVE = "取消";

    private static final String TAG_HEAD = DialogFragmentHelper.class.getSimpleName();

    /**
     * 加载中的弹出窗
     */
    private static final int PROGRESS_THEME = R.style.Base_AlertDialog;
    private static final String PROGRESS_TAG = TAG_HEAD + ":progress";

    public static CommonDialogFragment showProgress(FragmentManager fragmentManager, String message) {
        return showProgress(fragmentManager, message, true, null);
    }

    public static CommonDialogFragment showProgress(FragmentManager fragmentManager, String message, boolean cancelable) {
        return showProgress(fragmentManager, message, cancelable, null);
    }

    public static CommonDialogFragment showProgress(FragmentManager fragmentManager, final String message, boolean cancelable
            , CommonDialogFragment.OnDialogCancelListener cancelListener) {

        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(Context context) {
                ProgressDialog progressDialog = new ProgressDialog(context, PROGRESS_THEME);
                progressDialog.setMessage(message);
                return progressDialog;
            }
        }, cancelable, cancelListener);
        dialogFragment.show(fragmentManager, PROGRESS_TAG);
        return dialogFragment;
    }


    /**
     * 简单提示弹出窗
     */
    private static final int TIPS_THEME = R.style.Base_AlertDialog;
    private static final String TIPS_TAG = TAG_HEAD + ":tips";

    public static void showTips(FragmentManager fragmentManager, String message) {
        showTips(fragmentManager, message, true, null);
    }

    public static void showTips(FragmentManager fragmentManager, String message, boolean cancelable) {
        showTips(fragmentManager, message, cancelable, null);
    }

    public static void showTips(FragmentManager fragmentManager, final String message, boolean cancelable
            , CommonDialogFragment.OnDialogCancelListener cancelListener) {

        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(Context context) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, TIPS_THEME);
                builder.setMessage(message);
                return builder.create();
            }
        }, cancelable, cancelListener);
        dialogFragment.show(fragmentManager, TIPS_TAG);
    }


    /**
     * 确定取消框
     */
    private static final int CONFIRM_THEME = R.style.Base_AlertDialog;
    private static final String CONfIRM_TAG = TAG_HEAD + ":confirm";

    public static void showConfirmDialog(FragmentManager fragmentManager, final String message, final IDialogResultListener<Integer> listener
            , boolean cancelable, CommonDialogFragment.OnDialogCancelListener cancelListener) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(Context context) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context, CONFIRM_THEME);
//                builder.setMessage(message);
//                builder.setPositiveButton(DIALOG_POSITIVE, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(listener != null){
//                            listener.onDataResult(which);
//                        }
//                    }
//                });
//                builder.setNegativeButton(DIALOG_NEGATIVE, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(listener != null){
//                            listener.onDataResult(which);
//                        }
//                    }
//                });
//                return builder.create();

//                Dialog dialog = new Dialog(context);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//设置Dialog没有标题。需在setContentView之前设置，在之后设置会报错
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
//
//
//                dialog.setContentView(R.layout.dialog_revocation);
//                dialog.show();
//                return dialog;

                View view = LayoutInflater.from(context).inflate(R.layout.dialog_revocation, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(context).setView(view).create();
                TextView msgTv = (TextView) view.findViewById(R.id.tv_title);
                msgTv.setText(message);
                Button cancelBtn = (Button) view.findViewById(R.id.cancel_btn);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        if (listener != null) {
                            listener.onDataResult(DialogInterface.BUTTON_NEGATIVE);
                        }

                    }
                });
                Button okBtn = (Button) view.findViewById(R.id.ok_btn);
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        if (listener != null) {
                            listener.onDataResult(DialogInterface.BUTTON_POSITIVE);
                        }
                    }
                });


                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
                return alertDialog;

            }
        }, cancelable, cancelListener);
        dialogFragment.show(fragmentManager, CONfIRM_TAG);

    }

    /**
     * 带列表的弹出窗
     */
    private static final int LIST_THEME = R.style.Base_AlertDialog;
    private static final String LIST_TAG = TAG_HEAD + ":list";

    public static DialogFragment showListDialog(FragmentManager fragmentManager, final String title, final String[] items
            , final IDialogResultListener<Integer> resultListener, boolean cancelable) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(Context context) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, LIST_THEME);
                builder.setTitle(title);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (resultListener != null) {
                            resultListener.onDataResult(which);
                        }
                    }
                });
                return builder.create();
            }
        }, cancelable, null);
        dialogFragment.show(fragmentManager, LIST_TAG);
        return null;
    }

    /**
     * 选择日期
     */
    private static final int DATE_THEME = R.style.Base_AlertDialog;
    private static final String DATE_TAG = TAG_HEAD + ":date";

    public static DialogFragment showDateDialog(FragmentManager fragmentManager, final String title, final Calendar calendar
            , final IDialogResultListener<Calendar> resultListener, final boolean cancelable) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(Context context) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(context, DATE_THEME, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        resultListener.onDataResult(calendar);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setTitle(title);
                datePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(DIALOG_POSITIVE);
                        datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(DIALOG_NEGATIVE);
                    }
                });
                return datePickerDialog;

            }
        }, cancelable, null);
        dialogFragment.show(fragmentManager, DATE_TAG);
        return null;
    }


    /**
     * 选择时间
     */
    private static final int TIME_THEME = R.style.Base_AlertDialog;
    private static final String TIME_TAG = TAG_HEAD + ":time";

    public static void showTimeDialog(FragmentManager manager, final String title, final Calendar calendar, final IDialogResultListener<Calendar> resultListener, final boolean cancelable) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(Context context) {
                final TimePickerDialog dateDialog = new TimePickerDialog(context, TIME_THEME, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (resultListener != null) {
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            resultListener.onDataResult(calendar);
                        }
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                dateDialog.setTitle(title);
                dateDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        dateDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(DIALOG_POSITIVE);
                        dateDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(DIALOG_NEGATIVE);
                    }
                });

                return dateDialog;
            }
        }, cancelable, null);
        dialogFragment.show(manager, DATE_TAG);
    }

    /**
     * 带输入框的弹出窗
     */
    private static final int INSERT_THEME = R.style.Base_AlertDialog;
    private static final String INSERT_TAG = TAG_HEAD + ":insert";

    public static void showInsertDialog(FragmentManager manager, final String title, final IDialogResultListener<String> resultListener, final boolean cancelable) {

        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public Dialog getDialog(Context context) {
                final EditText editText = new EditText(context);
                editText.setBackground(null);
                editText.setPadding(60, 40, 0, 0);
                AlertDialog.Builder builder = new AlertDialog.Builder(context, INSERT_THEME);
                builder.setTitle(title);
                builder.setView(editText);
                builder.setPositiveButton(DIALOG_POSITIVE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (resultListener != null) {
                            resultListener.onDataResult(editText.getText().toString());
                        }
                    }
                });
                builder.setNegativeButton(DIALOG_NEGATIVE, null);
                return builder.create();

            }
        }, cancelable, null);
        dialogFragment.show(manager, INSERT_TAG);

    }


    /**
     * 带输入密码框的弹出窗
     */
    private static final int PASSWORD_INSER_THEME = R.style.Base_AlertDialog;
    private static final String PASSWORD_INSERT_TAG = TAG_HEAD + ":insert";

    public static void showPasswordInsertDialog(FragmentManager manager, final String title, final IDialogResultListener<String> resultListener, final boolean cancelable) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(Context context) {
                final EditText editText = new EditText(context);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setEnabled(true);
                AlertDialog.Builder builder = new AlertDialog.Builder(context, PASSWORD_INSER_THEME);
                builder.setTitle(title);
                builder.setView(editText);
                builder.setPositiveButton(DIALOG_POSITIVE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (resultListener != null) {
                            resultListener.onDataResult(editText.getText().toString());
                        }
                    }
                });
                builder.setNegativeButton(DIALOG_NEGATIVE, null);
                return builder.create();
            }
        }, cancelable, null);
        dialogFragment.show(manager, INSERT_TAG);
    }

//    /**
//     * 两个输入框的弹出窗
//     */
//    private static final int INTERVAL_INSERT_THEME = R.style.Base_AlertDialog;
//    private static final String INTERVAL_INSERT_TAG = TAG_HEAD + ":interval_insert";
//
//    public static void showIntervalInsertDialog(FragmentManager manager, final String title, final IDialogResultListener<String[]> resultListener, final boolean cancelable) {
//        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
//            @Override
//            public Dialog getDialog(Context context) {
//                View view = LayoutInflater.from(context).inflate(R.layout.dialog_interval_insert, null);
//                final EditText minEditText = (EditText) view.findViewById(R.id.interval_insert_et_min);
//                final EditText maxEditText = (EditText) view.findViewById(R.id.interval_insert_et_max);
//                AlertDialog.Builder builder = new AlertDialog.Builder(context, INTERVAL_INSERT_THEME);
//                return builder.setTitle(title)
//                        .setView(view)
//                        .setPositiveButton(DIALOG_POSITIVE, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (resultListener != null) {
//                                    resultListener.onDataResult(new String[]{minEditText.getText().toString(), maxEditText.getText().toString()});
//                                }
//                            }
//                        }).setNegativeButton(DIALOG_NEGATIVE, null)
//                        .create();
//            }
//        }, cancelable, null);
//        dialogFragment.show(manager, INTERVAL_INSERT_TAG);
//    }


}

//package com.example.haoz.dialogfragmentdemos.activity;
//
//        import android.os.Bundle;
//        import android.support.annotation.Nullable;
//        import android.support.v4.app.DialogFragment;
//        import android.support.v7.app.AppCompatActivity;
//        import android.view.Menu;
//        import android.view.MenuInflater;
//        import android.view.MenuItem;
//        import android.widget.Toast;
//        import com.example.haoz.dialogfragmentdemos.R;
//        import com.example.haoz.dialogfragmentdemos.utils.CommonDialogFragment;
//        import com.example.haoz.dialogfragmentdemos.utils.DialogFragmentHelper;
//        import com.example.haoz.dialogfragmentdemos.utils.IDialogResultListener;
//        import java.util.Calendar;
//
///**
// * Created by Haoz on 2017/4/6 0006.
// */
//
//public class DialogActivity extends AppCompatActivity {
//
//    private DialogFragment mDialogFragment;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dialog);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = new MenuInflater(this);
//        inflater.inflate(R.menu.menu_dialog, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//
//            case R.id.showConfirmDialog:
//                showConfirmDialog();
//                break;
//
//            case R.id.showDateDialog:
//                showDateDialog();
//                break;
//
//            case R.id.showInsertDialog:
//                showInsertDialog();
//                break;
//
//            case R.id.showIntervalInsertDialog:
//                showIntervalInsertDialog();
//                break;
//
//            case R.id.showListDialog:
//                showListDialog();
//                break;
//
//            case R.id.showPasswordInsertDialog:
//                showPasswordInsertDialog();
//                break;
//
//            case R.id.showProgress:
//                mDialogFragment = DialogFragmentHelper.showProgress(getSupportFragmentManager(), "正在加载中");
//                break;
//
//            case R.id.showTimeDialog:
//                showTimeDialog();
//                break;
//
//            case R.id.showTips:
//                DialogFragmentHelper.showTips(getSupportFragmentManager(), "你进入了无网的异次元中");
//                break;
//
//            default:break;
//        }
//        return true;
//    }
//
//    /**
//     * 选择时间的弹出窗
//     */
//    private void showTimeDialog() {
//        String titleTime = "请选择时间";
//        Calendar calendarTime = Calendar.getInstance();
//        DialogFragmentHelper.showTimeDialog(getSupportFragmentManager(), titleTime, calendarTime, new IDialogResultListener<Calendar>() {
//            @Override
//            public void onDataResult(Calendar result) {
//                showToast(String.valueOf(result.getTime().getDate()));
//            }
//        }, true);
//    }
//
//    /**
//     * 输入密码的弹出窗
//     */
//    private void showPasswordInsertDialog() {
//        String titlePassword = "请输入密码";
//        DialogFragmentHelper.showPasswordInsertDialog(getSupportFragmentManager(), titlePassword, new IDialogResultListener<String>() {
//            @Override
//            public void onDataResult(String result) {
//                showToast("密码为：" + result);
//            }
//        }, true);
//    }
//
//    /**
//     * 显示列表的弹出窗
//     */
//    private void showListDialog() {
//        String titleList = "选择哪种方向？";
//        final String [] languanges = new String[]{"Android", "iOS", "web 前端", "Web 后端", "老子不打码了"};
//
//        DialogFragmentHelper.showListDialog(getSupportFragmentManager(), titleList, languanges, new IDialogResultListener<Integer>() {
//            @Override
//            public void onDataResult(Integer result) {
//                showToast(languanges[result]);
//            }
//        }, true);
//    }
//
//    /**
//     * 两个输入框的弹出窗
//     */
//    private void showIntervalInsertDialog() {
//        String title = "请输入想输入的内容";
//        DialogFragmentHelper.showIntervalInsertDialog(getSupportFragmentManager(), title, new IDialogResultListener<String[]>() {
//            @Override
//            public void onDataResult(String[] result) {
//                showToast(result[0] + result[1]);
//            }
//        }, true);
//    }
//
//    private void showInsertDialog() {
//        String titleInsert  = "请输入想输入的内容";
//        DialogFragmentHelper.showInsertDialog(getSupportFragmentManager(), titleInsert, new IDialogResultListener<String>() {
//            @Override
//            public void onDataResult(String result) {
//                showToast(result);
//            }
//        }, true);
//    }
//
//    /**
//     * 选择日期的弹出窗
//     */
//    private void showDateDialog() {
//        String titleDate = "请选择日期";
//        Calendar calendar = Calendar.getInstance();
//        mDialogFragment = DialogFragmentHelper.showDateDialog(getSupportFragmentManager(), titleDate, calendar, new IDialogResultListener<Calendar>() {
//            @Override
//            public void onDataResult(Calendar result) {
//                showToast(String.valueOf(result.getTime().getDate()));
//            }
//        }, true);
//    }
//
//    /**
//     * 确认和取消的弹出窗
//     */
//    private void showConfirmDialog() {
//        DialogFragmentHelper.showConfirmDialog(getSupportFragmentManager(), "是否选择 Android？", new IDialogResultListener<Integer>() {
//            @Override
//            public void onDataResult(Integer result) {
//                showToast("You Click Ok");
//            }
//        }, true, new CommonDialogFragment.OnDialogCancelListener() {
//            @Override
//            public void onCancel() {
//                showToast("You Click Cancel");
//            }
//        });
//    }
//
//
//    /**
//     * 对 Toast 进行封藏，减少代码量
//     *
//     * @param message 想要显示的信息
//     */
//    private void showToast(String message){
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//}

















