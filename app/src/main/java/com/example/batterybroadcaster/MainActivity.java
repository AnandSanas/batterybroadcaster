package com.example.batterybroadcaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView chargingpercetageresult, volteageresult, temp_result, healthresult, source_result, typeresult, status_result, publisher;
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chargingpercetageresult = findViewById(R.id.chargingpercetageresult);
        volteageresult =findViewById(R.id.volteageresult);
        temp_result = findViewById(R.id.temp_result);
        healthresult = findViewById(R.id.healthresult);
        source_result = findViewById(R.id.source_result);
        typeresult = findViewById(R.id.typeresult);
        status_result = findViewById(R.id.status_result);
        publisher = findViewById(R.id.publisher);

        publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Published By Anand Sanas", Toast.LENGTH_LONG).show();
            }
        });


        batterystatus();

    }

    private void batterystatus() {

        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);// change made in manifest with this line
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                float percentage = intent.getIntExtra("level",0);
                float voltage = intent.getIntExtra("voltage",0);
                float temp = intent.getIntExtra("temperature",0);
                //int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0); //or
                int health = intent.getIntExtra("health",0);
                int status = intent.getIntExtra("status",0);
                int source = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
                String type = intent.getStringExtra("technology");


                if (Intent.ACTION_BATTERY_CHANGED==intent.getAction())//here comparison is between Manifest value vs  set above and Intent.ACTION_BATTERY_CHANGED
                {

                    if (percentage!=0)
                    {
                        chargingpercetageresult.setText(percentage+" %");
                        volteageresult.setText(voltage/1000+" Volts");
                        temp_result.setText(temp/10+" C");

                        switch (health)
                        {
                            case BatteryManager.BATTERY_HEALTH_COLD:
                                healthresult.setText("Cold");
                                break;

                            case BatteryManager.BATTERY_HEALTH_DEAD:
                                healthresult.setText("Dead");
                                break;

                            case BatteryManager.BATTERY_HEALTH_GOOD:
                                healthresult.setText("Good");
                                break;

                            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                                healthresult.setText("Over Voltage");
                                break;

                            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                                healthresult.setText("OverHeat");
                                break;

                            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                                healthresult.setText("Unknown");
                                break;

                            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                                healthresult.setText("Unspecified Failure");
                                break;

                        }

                        switch (status)
                        {
                            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                                status_result.setText("Unknown");
                                break;

                            case BatteryManager.BATTERY_STATUS_CHARGING:
                                status_result.setText("Charging");
                                break;

                            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                                status_result.setText("Discharging");
                                break;

                            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                                status_result.setText("Not Charing");
                                break;

                            case BatteryManager.BATTERY_STATUS_FULL:
                                status_result.setText("Battery Full");
                                break;
                        }


                        typeresult.setText(type);

                        switch (source){

                            case BatteryManager.BATTERY_PLUGGED_AC:
                                source_result.setText("AC");
                                break;

                            case BatteryManager.BATTERY_PLUGGED_USB:
                                source_result.setText("USB");
                                break;

                            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                                source_result.setText("Wireless");
                                break;

                        }

                    }

                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}