package com.example.diegomello.clickvalidationapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.diegomello.clickvalidationapp.utils.Constants;
import com.example.diegomello.clickvalidationapp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    Button mBathroomButton;
    Button mEmergencyButton;
    Button mAssistenceButton;
    Button mThirstyButton;
    Button mOkButton;

    ImageView mGreenLed;

    Animation mLedAnime;
    MediaPlayer mNecessityButtonSound;
    MediaPlayer mOkButtonSound;
    MediaPlayer mBlinkWaitingAtendenceSound;

    boolean mCallOntheWay;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        Utils.setContext(this);

        mCallOntheWay = false;
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        mNecessityButtonSound = MediaPlayer.create(this, R.raw.ok_button_sound);
        mOkButtonSound = MediaPlayer.create(this, R.raw.necesity_button_sound);

        mLedAnime = AnimationUtils.loadAnimation(this, R.anim.tween);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toggle();
                return false;
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.config_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullscreenActivity.this, ConfigActivity.class);
                //EditText editText = (EditText) findViewById(R.id.editText);
               // String message = editText.getText().toString();
                //intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });
        findViewById(R.id.config_button).setOnTouchListener(mDelayHideTouchListener);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);

        mEmergencyButton = (Button) mContentView.findViewById(R.id.emergency_button);
        mBathroomButton = (Button) mContentView.findViewById(R.id.bathroom_button);
        mAssistenceButton = (Button) mContentView.findViewById(R.id.assistence_button);
        mThirstyButton = (Button) mContentView.findViewById(R.id.thirsty_button);
        mOkButton = (Button) mContentView.findViewById(R.id.activity_fullscreen_ok_button);
        mGreenLed = (ImageView) mContentView.findViewById(R.id.green_led);
        mBathroomButton.getBackground().setColorFilter(0xFF4CAF50, PorterDuff.Mode.MULTIPLY);
        mEmergencyButton.getBackground().setColorFilter(0xFFF2372A, PorterDuff.Mode.MULTIPLY);
        mAssistenceButton.getBackground().setColorFilter(0xFFFFEB3B, PorterDuff.Mode.MULTIPLY);
        mThirstyButton.getBackground().setColorFilter(0xFF2196F3, PorterDuff.Mode.MULTIPLY);

        //Suport colors for APi SDK16
        if(Build.VERSION.SDK_INT == 16){
            Drawable d = mBathroomButton.getBackground();
            d.setColorFilter(0xFF4CAF50, PorterDuff.Mode.MULTIPLY);
            mBathroomButton.setBackground(d);
            d = mEmergencyButton.getBackground();
            d.setColorFilter(0xFFF2372A, PorterDuff.Mode.MULTIPLY);
            mEmergencyButton.setBackground(d);
            d = mAssistenceButton.getBackground();
            d.setColorFilter(0xFFFFEB3B, PorterDuff.Mode.MULTIPLY);
            mAssistenceButton.setBackground(d);
            d = mThirstyButton.getBackground();
            d.setColorFilter(0xFF2196F3, PorterDuff.Mode.MULTIPLY);
            mThirstyButton.setBackground(d);
        }

        mEmergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callServiceButtonPushed(Constants.EMERGENCY_CALL_NUMBER,Constants.CALL_STATUS_ON_THE_WAY);
            }
        });

        mBathroomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callServiceButtonPushed(Constants.BATHROOM_CALL_NUMBER,Constants.CALL_STATUS_ON_THE_WAY);
            }
        });

        mAssistenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callServiceButtonPushed(Constants.DISCOMFORT_CALL_NUMBER,Constants.CALL_STATUS_ON_THE_WAY);

            }
        });

        mThirstyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callServiceButtonPushed(Constants.WATER_CALL_NUMBER,Constants.CALL_STATUS_ON_THE_WAY);
            }
        });
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callServiceButtonPushed(Constants.Ok_CALL_NUMBER,Constants.CALL_STATUS_SERVED);
            }
        });
    }

    private void callServiceButtonPushed(Integer callTypeInteger,Integer callStatusInteger){

        if(Utils.isNetworkConnected()){
            Patient p = Utils.checkForDeviceIdentification();
            if(p!=null){
                if(callTypeInteger == Constants.Ok_CALL_NUMBER){
                    mGreenLed.clearAnimation();
                    mOkButtonSound.start();
                    mCallOntheWay = false;
                    String call_id = p.getCalls().get(p.getCalls().size()-1);
                    if(call_id!=null){
                        RestApiAdapter.getInstance().putSolveCallRestApi(new Callback<Calling>() {
                            @Override
                            public void onResponse(Call<Calling> call, Response<Calling> response) {
                                mOkButton.setVisibility(View.GONE);

                                Toast.makeText(Utils.mContext,"DEU CERTO CHAMADA ATLZD",Toast.LENGTH_SHORT).show();
                                Log.d("OKBUTTONPRESSED",call.toString());
                                Log.d("RESPONSE",response.toString());
                            }

                            @Override
                            public void onFailure(Call<Calling> call, Throwable t) {

                            }
                        },callTypeInteger,callStatusInteger,call_id,p.get_id());
                    }else{

                    }
                }else{
                    if(!mCallOntheWay) {
                        mCallOntheWay = true;
                        mNecessityButtonSound.start();
                        mGreenLed.startAnimation(mLedAnime);
                        mOkButton.setVisibility(View.VISIBLE);

                        RestApiAdapter.getInstance().postCreateCallRestApi(new Callback<Patient>() {
                            @Override
                            public void onResponse(Call<Patient> call, Response<Patient> response) {
                                Utils.savePatientOnChash(response.body());
                            }

                            @Override
                            public void onFailure(Call<Patient> call, Throwable t) {

                            }
                        }, callTypeInteger, callStatusInteger, p.get_id());
                    }
                }
            }
        }
        else
            Toast.makeText(Utils.mContext,"Sem conexão com a INTERNET",Toast.LENGTH_LONG).show();
    }

    void animate(final MediaPlayer player, final Animation animation){

    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
