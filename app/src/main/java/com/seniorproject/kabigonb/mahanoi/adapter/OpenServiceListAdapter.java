package com.seniorproject.kabigonb.mahanoi.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.seniorproject.kabigonb.mahanoi.view.OpenServiceListItem;

/**
 * Created by LaFezTer on 21-Feb-18.
 */

public class OpenServiceListAdapter extends BaseAdapter{
    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
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

        return item;

    }
}
