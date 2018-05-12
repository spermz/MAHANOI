package com.seniorproject.kabigonb.mahanoi.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.seniorproject.kabigonb.mahanoi.dao.OpenListDao;
import com.seniorproject.kabigonb.mahanoi.dao.OpnListDataDao;
import com.seniorproject.kabigonb.mahanoi.view.OpenServiceListItem;

/**
 * Created by LaFezTer on 21-Feb-18.
 */

public class OpenServiceListAdapter extends BaseAdapter{

    OpenListDao dao;

    public OpenListDao getDao() {
        return dao;
    }

    public void setDao(OpenListDao dao) {
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

        OpenServiceListItem item ;

        if(convertView != null)
        {
            item = (OpenServiceListItem) convertView;
        }

        else
        {
            item =  new OpenServiceListItem(parent.getContext());
        }

        OpnListDataDao dao = (OpnListDataDao) getItem(position);

        item.setList_service_name(dao.getProviderUsername());
        item.setList_service_serviceName(selectType(dao.getTypeService()));
        item.setList_service_location("ID : " + dao.getResponseId());

        return item;

    }

    private String selectType(int typeService) {

        String result;

        switch(typeService){
            case 1:
                result = "Food Service";
                break;
            case 2:
                result = "Electric Service";
                break;
            case 3:
                result = "Pumbling Service";
                break;
            case 4:
                result = "Cleaning Service";
                break;
         default: result = " ";
        }

        return result;

    }
}
