package webuters.com.geet_uttarakhand20aprilpro.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.bean.SingerlistBean;


/**
 * Created by Tushar on 3/17/2016.
 */
public class Singer_Adapter extends ArrayAdapter<SingerlistBean> {

    private ArrayList<SingerlistBean> items;
    private ArrayList<SingerlistBean> arraylist;

    private Context mContext;

    ImageView image;
    //   ImageView image;

    public Singer_Adapter(Context context, int textViewResourceID, ArrayList<SingerlistBean> items) {

        super(context, textViewResourceID, items);
        //Log.e("Adapter call  here ----->>>", "adapter call ");
        mContext = context;

        this.items = items;
        this.arraylist = new ArrayList<SingerlistBean>();
        this.arraylist.addAll(items);

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        // LatestSongBean latest_songs_bean = items.get(position);

        final SingerlistBean catagory_bean = items.get(position);

        if (v == null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.latest_song_item_layout, null);

        }

        TextView title = (TextView) v.findViewById(R.id.text_latest);
        image = (ImageView) v.findViewById(R.id.singer_list_item_image);


        ImageView image_menu = (ImageView) v.findViewById(R.id.see_all_singerssee_all_singers);
        // image_menu.setLayoutParams(new ViewGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
        //  image_menu.setImageResource(R.drawable.sharing);
        image_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



               // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Singer_Adapter.this);

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "SongURL";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
//                System.out.println("Click menu item here " + "click");
//                Log.e("Click menu item here ::::::::::::::tushar", "Tushar clicked");
//                Intent shareIntent =new Intent(android.content.Intent.ACTION_SEND);
//
//                final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getContext());
//              //  String value=(mSharedPreference.getString("SongURL",SongPath);
////set the type
//               /* String path = Environment.getExternalStorageDirectory()
//                        .getAbsolutePath() + "/abc.mp3";*/
//                shareIntent.setType("text/plain");
//               // shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(NameOfShared));
//                mContext.startActivity(Intent.createChooser(shareIntent, "Share Sound File"));

            }
        });

        if (title != null) {
            title.setText(catagory_bean.getFirstName());



              //  Picasso.with(mContext).load(catagory_bean.getImage()).into(image);

        }
        System.out.println("fkmdkdkdkkkkkkkkkkkk::::::::::" + catagory_bean.getImage());
        try {
            Picasso.with(mContext).load(catagory_bean.getImage()).into(image);
           // Picasso.with(mContext).load(catagory_bean.getImage()).into(image);
           // Picasso.with(mContext).load(singer_detailes_array.getSongImg()).into(image);
        }catch (Exception e) {
            e.printStackTrace();
        }




        return v;

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arraylist);
        } else {
            for (SingerlistBean wp : arraylist) {
                if (wp.getFirstName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    items.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}

