package webuters.com.geet_uttarakhand20aprilpro.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.bean.CategoryGetSetUrl;


/**
 * Created by Tushar on 2/28/2016.
 */
public class See_all_catagory_Adapter extends ArrayAdapter<CategoryGetSetUrl> {

    private ArrayList<CategoryGetSetUrl> items;

    private Context mContext;


    //   ImageView image;

    public See_all_catagory_Adapter(Context context, int textViewResourceID, ArrayList<CategoryGetSetUrl> items){

        super(context,textViewResourceID,items);
        Log.e("Adapter call  here ----->>>", "adapter call ");
        mContext = context;

        this.items = items;

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        // LatestSongBean latest_songs_bean = items.get(position);

        CategoryGetSetUrl catagory_bean = items.get(position);

        if(v==null){

            LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v=inflater.inflate(R.layout.see_all_cat_item_layout,null);

        }

        TextView title = (TextView)v.findViewById(R.id.see_all_cat_txt);
        ImageView image=(ImageView)v.findViewById(R.id.singer_list_item_image);

        if (title != null) {

                title.setText(catagory_bean.getCatagoryName());
                Picasso.with(mContext).load(R.drawable.color).into(image);
        }

        return v;
    }

}


