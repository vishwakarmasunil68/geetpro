package webuters.com.geet_uttarakhand20aprilpro.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.bean.Catagory_calass_bean;


/**
 * Created by Admin on 2/17/2016.
 */
public class CatagoryAdapter extends ArrayAdapter<Catagory_calass_bean> {

    private ArrayList<Catagory_calass_bean> items;
    private ArrayList<Catagory_calass_bean> arraylist;

    private Context mContext;


    //   ImageView image;

    public CatagoryAdapter(Context context, int textViewResourceID, ArrayList<Catagory_calass_bean> items){

        super(context,textViewResourceID,items);
        //Log.e("Adapter call  here ----->>>", "adapter call ");
        mContext = context;

        this.items = items;

//        Collections.sort(items, new Comparator<Catagory_calass_bean>() {
//            public int compare(Catagory_calass_bean s1, Catagory_calass_bean s2) {
//                // notice the cast to (Integer) to invoke compareTo
//                return ((String ) s1.getAlbumName()).compareTo(s2.getAlbumName());
//            }
//        });
        Log.d("sunil", items.toString());
        this.arraylist = new ArrayList<Catagory_calass_bean>();
        this.arraylist.addAll(items);

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
       // LatestSongBean latest_songs_bean = items.get(position);

        Catagory_calass_bean catagory_bean = items.get(position);

        if(v==null){

            LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v=inflater.inflate(R.layout.latest_song_item_layout,null);

        }

        TextView title = (TextView)v.findViewById(R.id.text_latest);
        ImageView image=(ImageView)v.findViewById(R.id.singer_list_item_image);
        ImageView shareimg=(ImageView)v.findViewById(R.id.see_all_singerssee_all_singers);



        if (title != null) {

            title.setText(catagory_bean.getAlbumName());
           Picasso.with(mContext).load(catagory_bean.getAlbumCover()).into(image);
        }

        shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody ="https://play.google.com/store/apps/details?id=webuters.com.geet_uttarakhand20april&hl=en";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        return v;

    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arraylist);
        } else {
            for (Catagory_calass_bean wp : arraylist) {
                if (wp.getAlbumName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    items.add(wp);
                }
            }
        }
        Collections.sort(items, new Comparator<Catagory_calass_bean>() {
                        public int compare(Catagory_calass_bean s1, Catagory_calass_bean s2) {
                // notice the cast to (Integer) to invoke compareTo
                return ((String ) s1.getAlbumName()).compareTo(s2.getAlbumName());
            }
        });
            notifyDataSetChanged();
        }

    }

