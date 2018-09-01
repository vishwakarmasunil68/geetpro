package webuters.com.geet_uttarakhand20aprilpro.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.bean.SingerlistBean;


/**
 * Created by Tushar on 2/26/2016.
 */
public class Singer_list_Adapter extends ArrayAdapter<SingerlistBean> {

    private ArrayList<SingerlistBean> items;
    private String[] imageUrls;

    private Context mContext;
    ImageView menu_singer_download_image;
    String shareBody = "Here is the share content body";

        public Singer_list_Adapter(Context context, int textViewResourceID, ArrayList<SingerlistBean> items){

        super(context,textViewResourceID,items);
        Log.e("Adapter call  here ----->>>", "adapter call ");
        mContext = context;
        this.items = items;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent)
    {

        View v = convertView;
        SingerlistBean singerlistBean = items.get(position);
        if(v==null)
        {
            LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.latest_song_item_layout,null);
        }
        TextView title = (TextView)v.findViewById(R.id.text_latest);
        ImageView image=(ImageView)v.findViewById(R.id.singer_list_item_image);

        ImageView image_menu=(ImageView)v.findViewById(R.id.see_all_singerssee_all_singers);
       // image_menu.setLayoutParams(new ViewGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
      //  image_menu.setImageResource(R.drawable.sharing);
        image_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Click menu item here " + "click");
                Log.e("Click menu item here ::::::::::::::tushar", "Tushar clicked");
                Intent shareIntent =
                        new Intent(Intent.ACTION_SEND);

                String path = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/abc.mp3";

                shareIntent.setType("audio/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///" + path));
               mContext.startActivity(Intent.createChooser(shareIntent, "Share Sound File"));
                shareIntent.setType("text/plain");


            }
        });
        if (title != null) {

            title.setText(singerlistBean.getFirstName());

         // Picasso.with(mContext).load(singerlistBean.getImage()).into(image);
            //Picasso.with(mContext).load(catagory_bean.getSongImg()).into(image);

        }
        return v;
    }


}

