package com.example.gowarrior.demoofpwm;

import android.app.Activity;
import android.gowarrior.GPIO;
import android.gowarrior.PWM;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    EditText editDutyCycle;
    EditText editFrequency;
    private static float dutyCycle;
    private static float frequency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton= (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDutyCycle= (EditText) findViewById(R.id.editDutyCycle);
                editFrequency= (EditText) findViewById(R.id.editFrequency);
                dutyCycle=new Float(editDutyCycle.getText().toString());
                frequency=new Float(editFrequency.getText().toString());
                startPWM();
            }
        });
    }
    private void startPWM(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int channel=10;
                int channelCompare=11;
                float f=1.0f;
                GPIO gpio=new GPIO();
                gpio.setmode(GPIO.BCM);
                gpio.setup(channel, GPIO.OUTPUT);
                gpio.setup(channelCompare,GPIO.OUTPUT);
                PWM pwm=gpio.PWM(channel,f);
                PWM pwmCom=gpio.PWM(channelCompare,f);
                pwm.start(50.0f);
                pwmCom.start(50.0f);
                pwm.ChangeDutyCycle(dutyCycle);
                pwm.ChangeFrequency(frequency);
            }
        }).start();
    }
}
