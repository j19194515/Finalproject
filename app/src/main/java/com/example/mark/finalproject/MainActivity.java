package com.example.mark.finalproject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends Activity {

    Calendar calendar = Calendar.getInstance();
    final int Dialog_time = 0;
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Button buttonNew = (Button)findViewById(R.id.buttonNew);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(Dialog_time);
            }
        });
    }

    public void showdialog(int Dialog_id) {

        Dialog dialog = null;
        switch (Dialog_id){
            case Dialog_time:
                dialog = new TimePickerDialog(
                        this,
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker tp, int hourOfDay, int minute){
                                Calendar c = Calendar.getInstance();
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set (Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set (Calendar.MINUTE, minute);
                                c.set (Calendar.SECOND, 0);
                                c.set (Calendar.MILLISECOND, 0);
                                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 1, intent, 0);
                                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                                Toast.makeText(MainActivity.this,  "設定成功", Toast.LENGTH_LONG).show();
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                dialog.show();
                break;
            default:
                break;
        }
        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
