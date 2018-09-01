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

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.activity.Singers_Details_class;
import webuters.com.geet_uttarakhand20aprilpro.bean.SingerDEtaileBean;


/**
 * Created by Admin on 2/10/2016.
 */
public class SingerAdapter extends ArrayAdapter<SingerDEtaileBean> {

    private ArrayList<SingerDEtaileBean> items;

    private Context mContext;


    ImageView menu_singer_download_image;
    static String SongURL;
    Singers_Details_class cls2 = new Singers_Details_class();

    public SingerAdapter(Context context, int textViewResourceID, ArrayList<SingerDEtaileBean> items) {

        super(context, textViewResourceID, items);
        Log.e("Adapter call  here ----->>>", "adapter call ");
        mContext = context;

        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        final SingerDEtaileBean singer_detailes_array = items.get(position);

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.latest_song_item_layout, null);
        }

        TextView title = (TextView) v.findViewById(R.id.text_latest);
        ImageView image = (ImageView) v.findViewById(R.id.singer_list_item_image);
        menu_singer_download_image = (ImageView) v.findViewById(R.id.see_all_singerssee_all_singers);

        menu_singer_download_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Clicked menu item by tushar here:::::::::::::::::::::::::::::::::::: ", "tushar clicked");


                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, singer_detailes_array.getId());

                mContext.startActivity(Intent.createChooser(share, "Share link!"));


//                Intent shareIntent =
//                        new Intent(android.content.Intent.ACTION_SEND);
//
//                Log.e("Adapter share call here ","call share data");
//                Log.e("Recive share data here ",singer_detailes_array.getSongPath());
////set the type
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("audio/*");
//
//                // Make sure you put example png image named myImage.png in your
//                // directory
//                String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()
//                        + "/.mp3";
//
//                File imageFileToShare = new File(singer_detailes_array.getSongPath());
//
//                Uri uri = Uri.parse(String.valueOf(imageFileToShare));
//                share.putExtra(Intent.EXTRA_STREAM, uri);
//
//                mContext.startActivity(Intent.createChooser(share, "Share Image!"));


//                String path = Environment.getExternalStorageDirectory()
//                        .getAbsolutePath() + "/.mp3";
//             shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(singer_detailes_array.getSongPath() + path));
//
//
//                mContext.startActivity(Intent.createChooser(shareIntent, "Share Sound path File"));
//                shareIntent.setType("text/plain");
            }
        });
//        TextView title2 = (TextView)v.findViewById(R.id.album_director);
//        TextView title3 = (TextView)v.findViewById(R.id.album_created_by);

        // TextView textView44=(TextView)v.findViewById(R.id.textView44);
        // ImageView image=(ImageView)v.findViewById(R.id.image);
        if (title != null) {

            title.setText(singer_detailes_array.getAlbumName());
            Log.e("Album name is :", singer_detailes_array.getAlbumName());
            Picasso.with(mContext).load(singer_detailes_array.getAlbumCover()).into(image);
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
