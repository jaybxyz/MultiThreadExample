package com.example.sonic.multithreadexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    int numA = 0;
    int numB = 0;
    private TextView threadA;
    private TextView threadB;

    // [START ALL, RESTART ALL, STOP ALL]
    private Button mStartAll;
    private Button mRestartAll;
    private Button mStopAll;

    // Thread A Buttons [PAUSE, RESUME, STOP]
    private Button mPause_A;
    private Button mResume_A;
    private Button mStop_A;

    // Thread B Buttons [PAUSE, RESUME, STOP]
    private Button mPause_B;
    private Button mResume_B;
    private Button mStop_B;

    private Thread th_A;
    private Thread th_B;

    private Object mPauseLock;
    private boolean mPausedA;
    private boolean mPausedB;
    private boolean mFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        threadA = (TextView)findViewById(R.id.txt_threadA_numbers);
        threadB = (TextView)findViewById(R.id.txt_threadB_numbers);

        // Initialize A Buttons [PAUSE, RESUME, STOP]
        mPause_A = (Button)findViewById(R.id.btn_threadA_pause);
        mResume_A = (Button)findViewById(R.id.btn_threadA_resume);
        mStop_A = (Button)findViewById(R.id.btn_threadA_stop);

        // Initialize B Buttons [PAUSE, RESUME, STOP]
        mPause_B = (Button)findViewById(R.id.btn_threadB_pause);
        mResume_B = (Button)findViewById(R.id.btn_threadB_resume);
        mStop_B = (Button)findViewById(R.id.btn_threadB_stop);

        // Initialize [START ALL, RESTART ALL, STOP ALL]
        mStartAll = (Button)findViewById(R.id.btn_startAll);
        mStopAll = (Button)findViewById(R.id.btn_stopAll);
        mRestartAll = (Button)findViewById(R.id.btn_restartAll);

        // Button OnClickListener
        mPause_A.setOnClickListener(mOnClickListener);
        mResume_A.setOnClickListener(mOnClickListener);
        mStop_A.setOnClickListener(mOnClickListener);
        mPause_B.setOnClickListener(mOnClickListener);
        mResume_B.setOnClickListener(mOnClickListener);
        mStop_B.setOnClickListener(mOnClickListener);
        mStartAll.setOnClickListener(mOnClickListener);
        mStopAll.setOnClickListener(mOnClickListener);
        mRestartAll.setOnClickListener(mOnClickListener);

        th_A = new threadA();
        th_B = new threadB();

    }

    /*
    *  Call this on Pause Thread A
    * */
    public void pauseThreadA() {
        synchronized (mPauseLock) {
            mPausedA = true;
        }
    }

    public void resumeThreadA(){
        synchronized (mPauseLock) {
            mPausedA = false;
            mPauseLock.notify();
        }
    }

    /*
    *  Call this on Pause Thread B
    * */
    public void pauseThreadB() {
        synchronized (mPauseLock) {
            mPausedB = true;
        }
    }

    public void resumeThreadB(){
        synchronized (mPauseLock) {
            mPausedB = false;
            mPauseLock.notify();
        }
    }


    public void stopThreadA() {
        synchronized (mPauseLock) {
            mPausedA = false;

        }
    }
    public void stopThreadB() {

    }



    public void startAll() {
        th_A.start();
        th_B.start();
    }

    public void restartAll(){

        if (th_A!=null){
            Log.d(TAG,"th_A"+th_A);
        }
        if (th_B!=null){
            Log.d(TAG,"th_B"+th_B);
        }

//        synchronized (mPauseLock) {
//            mPausedA = false;
//            mPausedB = false;
//            mPauseLock.notifyAll();
//        }
    }





    class threadA extends Thread {

        public threadA() {
            mPauseLock = new Object();
            mPausedA = false;
            mFinished = false;
        }

        public void run() {
            while(!mFinished) {
                try {
                    synchronized (mPauseLock){
                        while (mPausedA){
                            try {
                                mPauseLock.wait();
                            }catch (InterruptedException e){
                                e.getStackTrace();
                            }
                        }
                    }

                    Thread.sleep(200);
                    numA++;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            threadA.setText(String.valueOf(numA));
                        }
                    });
                } catch(InterruptedException ex) {
                    Log.e("SampleJavaThread","Exception in thread.",ex);
                }
            }
        }
    }

    class threadB extends Thread {

        public threadB() {
            mPauseLock = new Object();
            mPausedB = false;
            mFinished = false;
        }

        public void run() {
            while(!mFinished) {
                try {

                    synchronized (mPauseLock){
                        while (mPausedB){
                            try {
                                mPauseLock.wait();
                            }catch (InterruptedException e){
                                e.getStackTrace();
                            }
                        }
                    }

                    Thread.sleep(200);
                    numB++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            threadB.setText(String.valueOf(numB));
                        }
                    });
                } catch(InterruptedException ex) {
                    Log.e("SampleJavaThread","Exception in thread.",ex);
                }

            }
        }
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_threadA_pause:

                    pauseThreadA();

                    Log.i(TAG,"Thread A PAUSE");
                    break;

                case R.id.btn_threadA_resume:

                    resumeThreadA();

                    Log.i(TAG,"Thread A RESUME");
                    break;

                case R.id.btn_threadA_stop: // STOP A

                    //stopThreadA();
                    //threadA.setText(String.valueOf(0));
                    Log.i(TAG,"Thread A STOP");
                    break;

                case R.id.btn_threadB_pause:

                    pauseThreadB();

                    Log.i(TAG,"Thread B PAUSE");
                    break;

                case R.id.btn_threadB_resume:

                    resumeThreadB();

                    Log.i(TAG,"Thread B RESUME");
                    break;

                case R.id.btn_threadB_stop: // STOP B

                    //stopThreadB();

                    Log.i(TAG,"Thread B STOP");
                    break;

                case R.id.btn_startAll:

                    startAll();

                    Log.i(TAG,"Start All Threads");
                    break;

                case R.id.btn_stopAll:



                    Log.i(TAG,"Stop All Threads");
                    break;

                case R.id.btn_restartAll:

                    restartAll();

                    Log.i(TAG,"Restart All Threads");
                    break;

                default:
                    break;
            }
        }
    };


}
