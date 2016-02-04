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
import android.widget.TextView;

/**
 * Created by ofir elarat on 21-Jan-16.
 */
public class CostumAdapter  extends BaseAdapter {

    Context context;
    String[] names;
    String[] stars;
    String[] images;
    private static LayoutInflater inflater = null;

    public CostumAdapter(Context context, String[] names,String[] stars,String[] images) {
    this.context = context;
    this.names = names;
    this.stars=stars;
    this.images=images;
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
            vi = inflater.inflate(R.layout.custom_row, null);
        TextView name = (TextView) vi.findViewById(R.id.user_id);
        name.setText(names[position]);
        TextView star = (TextView) vi.findViewById(R.id.star_id);
        star.setText(stars[position]);
        ImageView img=(ImageView)vi.findViewById(R.id.img_id);
        Resources res =context.getResources();
        int id = res.getIdentifier(images[position],"drawable",context.getPackageName());
        img.setImageResource(id);

        return vi;
    }
}
