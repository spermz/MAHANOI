package com.seniorproject.kabigonb.mahanoi.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.seniorproject.kabigonb.mahanoi.dao.CloseListDao;
import com.seniorproject.kabigonb.mahanoi.dao.CloseListDataDao;
import com.seniorproject.kabigonb.mahanoi.view.CloseServiceListItem;

/**
 * Created by LaFezTer on 21-Feb-18.
 */

public class CloseServiceListAdapter extends BaseAdapter {

    private CloseListDao dao;

    public CloseListDao getDao() {
        return dao;
    }

    public void setDao(CloseListDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if(dao == null)
        {
            return 0;
        }
        if(dao.getResult() == null)
        {
            return 0;
        }

        return dao.getResult().size();
    }

    @Override
    public Object getItem(int position) {
        return dao.getResult().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CloseServiceListItem item;

        if(convertView != null)
        {
            item = (CloseServiceListItem) convertView;
        }
        else
        {
            item = new CloseServiceListItem(parent.getContext());
        }

        CloseListDataDao dao = (CloseListDataDao) getItem(position);

       // item.setList_service_location_close();
        item.setList_service_name_close(dao.getProviderName());
        item.setList_service_serviceName_close(selectType(dao.getTypeService()));
        return item;

    }

    private String selectType(int typeService) {
        switch(typeService)
        {
            case 1:
                return "Food Service";
            case 2:
                return "Electric Service";
            case 3:
                return "Plumbing Service";
            case 4:
                return "Cleaning Service";
            default: return "";
        }
    }

}
