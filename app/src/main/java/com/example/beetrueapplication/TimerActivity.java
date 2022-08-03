package com.example.beetrueapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private EditText etSetTimer;

    private TextView tvCountdown;
    private Button btnStartTimer, btnResetTimer, btnSetTimer;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis = mStartTimeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        etSetTimer = findViewById(R.id.etSetTimer);
        tvCountdown = findViewById(R.id.tvCountdown);

        btnStartTimer = findViewById(R.id.btnStartTimer);
        btnResetTimer = findViewById(R.id.btnResetTimer);
        btnSetTimer = findViewById(R.id.btnSetTimer);

        btnSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etSetTimer.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(TimerActivity.this, "Field can not be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if(millisInput == 0) {
                    Toast.makeText(TimerActivity.this, "Please enter a positive number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                etSetTimer.setText("");
            }
        });

        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning){
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        btnResetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                btnStartTimer.setText("Start");
                btnStartTimer.setVisibility(View.INVISIBLE);
                btnResetTimer.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        btnStartTimer.setText("Pause");
        btnResetTimer.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        btnStartTimer.setText("Start");
        btnResetTimer.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        btnResetTimer.setVisibility(View.INVISIBLE);
        btnStartTimer.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600; // Convert hours
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60; // Convert minutes
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60; // Convert seconds

        String timeLeftFormatted;
        if(hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        }

        tvCountdown.setText(timeLeftFormatted);
    }

}