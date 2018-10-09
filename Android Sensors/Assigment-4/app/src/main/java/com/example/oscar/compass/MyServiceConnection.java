package com.example.oscar.compass;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class MyServiceConnection implements ServiceConnection{
    private final StepActivity mStepActivity;

    public MyServiceConnection(StepActivity stepActivity){
        mStepActivity = stepActivity;
    }


    /**
     * Method that connects the StepActivity with the Service class
     * @param name
     * @param service
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MyService.MyLocalBinder myLocalBinder = (MyService.MyLocalBinder)service;
        mStepActivity.mService = myLocalBinder.getService();
        mStepActivity.mBound = true;
        mStepActivity.mService.setListenerActivity(mStepActivity);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mStepActivity.mBound = false;
    }
}
