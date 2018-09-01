package webuters.com.geet_uttarakhand20aprilpro.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.activity.EventClassBean;


/**
 * Created by Tushar on 2/24/2016.
 */
public class EventClassAdapter extends ArrayAdapter<EventClassBean> {

    private ArrayList<EventClassBean> items;

    private Context mContext;


    //   ImageView image;

    public EventClassAdapter(Context context, int textViewResourceID, ArrayList<EventClassBean> items){

        super(context,textViewResourceID,items);
        Log.e("Adapter call  here ----->>>", "adapter call ");
        mContext = context;

        this.items = items;

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        EventClassBean latest_songs_bean = items.get(position);

        if(v==null){

            LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v=inflater.inflate(R.layout.event_list_item,null);

        }

        TextView title = (TextView)v.findViewById(R.id.text_event_name);

//        TextView title2 = (TextView)v.findViewById(R.id.album_director);
//        TextView title3 = (TextView)v.findViewById(R.id.album_created_by);

        // TextView textView44=(TextView)v.findViewById(R.id.textView44);
        // ImageView image=(ImageView)v.findViewById(R.id.image);
        if (title != null) {

            title.setText(latest_songs_bean.getEventName());
            // title2.setText(latest_songs_bean.getSongPath());
            // title3.setText(latest_songs_bean.getMusicDirector());
            //   textView44.setText(a);
//                Picasso.with(this)
//                        .load("YOUR IMAGE URL HERE")
//                        .into(image);
        }

        return v;

    }

}

