package com.seniorproject.kabigonb.mahanoi;

import android.app.Application;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by LaFezTer on 22-Oct-17.
 */

public class MahanoiApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
