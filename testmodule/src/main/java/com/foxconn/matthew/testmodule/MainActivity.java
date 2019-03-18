package com.foxconn.matthew.testmodule;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int DATA_PICK_DIALOG = 0;
    private final static int TIME_DIALOG = 1;
    private Calendar c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DATA_PICK_DIALOG:
                c = Calendar.getInstance();
                dialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                                //et.setText("您选择了：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                                Log.e(TAG, year + "-" + month + "-" + dayOfMonth);
                            }
                        },
                        c.get(Calendar.YEAR), // 传入年份
                        c.get(Calendar.MONTH), // 传入月份
                        c.get(Calendar.DAY_OF_MONTH) // 传入天数
                );
                break;
            case TIME_DIALOG:
                c = Calendar.getInstance();
                dialog = new TimePickerDialog(
                        this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                                et.setText("您选择了："+hourOfDay+"时"+minute+"分");
                                Log.e(TAG, hourOfDay + "-" + minute);
                            }
                        },
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        false
                );
                break;
            default:
                break;
        }
        return dialog;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_dialog:
                View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(dialogView);
                final Dialog dialog = builder.create();
                TextView textView = (TextView) dialogView.findViewById(R.id.dialogTitle);
                final EditText editText = (EditText) dialogView.findViewById(R.id.dialogEdit);
                Button positiveButton = (Button) dialogView.findViewById(R.id.positiveButton);
                Button negativeButton = (Button) dialogView.findViewById(R.id.negativeButton);
                textView.setText("请输入价格");
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.date_pick:
                showDialog(DATA_PICK_DIALOG);
                break;
            case R.id.time_pic:
                showDialog(TIME_DIALOG);
                break;
            default:
                break;
        }
    }
}
