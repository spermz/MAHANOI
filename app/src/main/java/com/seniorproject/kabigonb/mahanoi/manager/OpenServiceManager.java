package com.seniorproject.kabigonb.mahanoi.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.seniorproject.kabigonb.mahanoi.dao.OpenListDao;


public class OpenServiceManager {

    private static OpenServiceManager instance;

    public static OpenServiceManager getInstance() {
        if (instance == null)
            instance = new OpenServiceManager();
        return instance;
    }

    private Context mContext;
    private OpenListDao dao;

    private OpenServiceManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public OpenListDao getDao() {
        return dao;
    }

    public void setDao(OpenListDao dao) {
        this.dao = dao;
    }
}
