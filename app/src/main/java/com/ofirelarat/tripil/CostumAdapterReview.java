package com.ofirelarat.tripil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by ofir on 08/02/2016.
 */
public class CostumAdapterReview extends BaseAdapter {
    Context context;
    String[] names;
    String[] messege;
    private static LayoutInflater inflater = null;
    public CostumAdapterReview(Context context,String[] names,String[] messege) {
        this.context = context;
        this.names = names;
        this.messege = messege;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
