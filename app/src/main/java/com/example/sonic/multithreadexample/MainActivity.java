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
    private Button mStopAll;
    private Button mResumeAll;
    private Button mPauseAll;

    // Thread A Buttons [PAUSE, RESUME, STOP]
    private Button mPause_A;
    private Button mResume_A;
    private Button mStop_A;

    // Thread B Buttons [PAUSE, RESUME, STOP]
    private Button mPause_B;
    private Button mResume_B;
    private Button mStop_B;

    // Thread A and B
    private Thread th_A;
    private Thread th_B;

    // Thread Control Variables
    private Object mPauseLockA;
    private Object mPauseLockB;
    private boolean mPausedA;
    private boolean mPausedB;
    private boolean mFinishedA;
    private boolean mFinishedB;

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
        mPauseAll = (Button)findViewById(R.id.btn_pauseAll);
        mResumeAll = (Button)findViewById(R.id.btn_resumeAll);

        // Button OnClickListener
        mPause_A.setOnClickListener(mOnClickListener);
        mResume_A.setOnClickListener(mOnClickListener);
        mStop_A.setOnClickListener(mOnClickListener);
        mPause_B.setOnClickListener(mOnClickListener);
        mResume_B.setOnClickListener(mOnClickListener);
        mStop_B.setOnClickListener(mOnClickListener);
        mStartAll.setOnClickListener(mOnClickListener);
        mStopAll.setOnClickListener(mOnClickListener);
        mPauseAll.setOnClickListener(mOnClickListener);
        mResumeAll.setOnClickListener(mOnClickListener);

        th_A = new threadA();
        th_B = new threadB();

    }

    /*
    *  Call this on Pause Thread A
    * */
    public void pauseThreadA() {
        synchronized (mPauseLockA) {
            mPausedA = true;
        }
    }

    /*
    *  Call this on Resume Thread A
    * */
    public void resumeThreadA(){
        synchronized (mPauseLockA) {
            mPausedA = false;
            mPauseLockA.notify();
        }
    }

    /*
    *  Call this on Stop Thread A
    * */
    public void stopThreadA() {
        synchronized (mPauseLockA) {
            mFinishedA = true;
        }
    }

    /*
    *  Call this on Pause Thread B
    * */
    public void pauseThreadB() {
        synchronized (mPauseLockB) {
            mPausedB = true;
        }
    }

    /*
    *  Call this on Resume Thread B
    * */
    public void resumeThreadB(){
        synchronized (mPauseLockB) {
            mPausedB = false;
            mPauseLockB.notify();
        }
    }

    /*
    *  Call this on Stop Thread B
    * */
    public void stopThreadB() {
        synchronized (mPauseLockB) {
            mFinishedB = true;
        }
    }

    /*
    * Call this on Start All Threads
    * */
    public void startAll() {
        th_A.start();
        th_B.start();
    }


    /*
    *  Call this on Pause Thread A
    * */
    public void stopAll() {
        synchronized (mPauseLockA) {
            mFinishedA = true;
        }
        synchronized (mPauseLockB) {
            mFinishedB = true;
        }
    }

    /*
    * Call this on Pause All Threads
    * */
    public void pauseAll(){
        synchronized (mPauseLockA) {
            mPausedA = true;
        }
        synchronized (mPauseLockB) {
            mPausedB = true;
        }
    }

    /*
    * Call this on Resume All Threads
    * */
    public void resumeAll(){
        synchronized (mPauseLockA) {
            mPausedA = false;
            mPauseLockA.notifyAll();
        }
        synchronized (mPauseLockB) {
            mPausedB = false;
            mPauseLockB.notifyAll();
        }
    }

    /**
    * Thread A
    * */
    class threadA extends Thread {

        public threadA() {
            mPauseLockA = new Object();
            mPausedA = false;
            mFinishedA = false;
        }

        public void run() {
            while(!mFinishedA) {
                try {
                    synchronized (mPauseLockA){
                        while (mPausedA){
                            try {
                                mPauseLockA.wait();
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

            //  Stop Thread A reset to 0
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    threadA.setText(String.valueOf(0));
                }
            });

        }
    }

    /**
    * Thread B
    * */
    class threadB extends Thread {

        public threadB() {
            mPauseLockB = new Object();
            mPausedB = false;
            mFinishedB = false;
        }

        public void run() {
            while(!mFinishedB) {
                try {

                    synchronized (mPauseLockB){
                        while (mPausedB){
                            try {
                                mPauseLockB.wait();
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

            //  Stop Thread B reset to 0
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    threadB.setText(String.valueOf(0));
                }
            });

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

                    stopThreadA();

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

                    stopThreadB();

                    Log.i(TAG,"Thread B STOP");
                    break;

                case R.id.btn_startAll:

                    startAll();

                    Log.i(TAG,"Start All Threads");
                    break;

                case R.id.btn_stopAll:

                    stopAll();

                    Log.i(TAG,"Stop All Threads");
                    break;

                case R.id.btn_pauseAll:

                    pauseAll();

                    Log.i(TAG,"Stop All Threads");
                    break;

                case R.id.btn_resumeAll:

                    resumeAll();

                    Log.i(TAG,"Restart All Threads");
                    break;

                default:
                    break;
            }
        }
    };
}
