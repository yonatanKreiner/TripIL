package com.ofirelarat.tripil;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.review_row, null);
        TextView name = (TextView) vi.findViewById(R.id.user_id);
        name.setText(names[position]);
        TextView message = (TextView) vi.findViewById(R.id.messegeId);
        message.setText(messege[position]);
        return vi;
    }
}
