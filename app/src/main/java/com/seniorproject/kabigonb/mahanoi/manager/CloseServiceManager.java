package com.seniorproject.kabigonb.mahanoi.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.seniorproject.kabigonb.mahanoi.dao.CloseListDao;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class CloseServiceManager {

    private static CloseServiceManager instance;

    public static CloseServiceManager getInstance() {
        if (instance == null)
            instance = new CloseServiceManager();
        return instance;
    }

    private Context mContext;
    private CloseListDao dao;

    private CloseServiceManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public CloseListDao getDao() {
        return dao;
    }

    public void setDao(CloseListDao dao) {
        this.dao = dao;
    }
}
