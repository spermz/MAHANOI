package com.seniorproject.kabigonb.mahanoi.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.seniorproject.kabigonb.mahanoi.view.CloseServiceListItem;

/**
 * Created by LaFezTer on 21-Feb-18.
 */

public class CloseServiceListAdapter extends BaseAdapter {
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
        CloseServiceListItem item;

        if(convertView != null)
        {
            item = (CloseServiceListItem) convertView;
        }
        else
        {
            item = new CloseServiceListItem(parent.getContext());
        }

        return item;

    }
}
