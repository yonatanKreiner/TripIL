package com.ofirelarat.tripil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by ofir elarat on 21-Jan-16.
 */
public class CostumAdapter  extends BaseAdapter {

    Context context;
    Trip[] trips;
    private static LayoutInflater inflater = null;

    public CostumAdapter(Context context, Trip[] trips) {
    this.context = context;
    this.trips=trips;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return trips.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return trips[position].getId();
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
            vi = inflater.inflate(R.layout.custom_row, null);
        TextView name = (TextView) vi.findViewById(R.id.user_id);
        name.setText(trips[position].getUsername());
        TextView date = (TextView) vi.findViewById(R.id.date_id);
        date.setText(trips[position].getArrivalDate() + " - " + trips[position].getReturnDate());
       loadImageFromStorage(trips[position].getPictures()[0],vi);
        RatingBar ratingBar=(RatingBar)vi.findViewById(R.id.ratingBar);
        ratingBar.setRating(trips[position].getStars());
        return vi;
    }

    private void loadImageFromStorage(String path,View vi)
    {
        try {
            File f=new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img=(ImageView)vi.findViewById(R.id.img_id);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
