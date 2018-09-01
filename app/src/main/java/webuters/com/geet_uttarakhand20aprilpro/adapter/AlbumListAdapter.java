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
import webuters.com.geet_uttarakhand20aprilpro.bean.SongListBean;


/**
 * Created by Tushar Sharma on 2/27/2016.
 */
public class AlbumListAdapter extends ArrayAdapter<SongListBean> {

private ArrayList<SongListBean> items;

private Context mContext;


//   ImageView image;

public AlbumListAdapter(Context context, int textViewResourceID, ArrayList<SongListBean> items){

        super(context,textViewResourceID,items);
    Log.e("Adapter call  here ----->>>", "adapter call ");
        mContext = context;

        this.items = items;

        }

@Override

public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;



        if(v==null){

        LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v=inflater.inflate(R.layout.album_item_song_layout,null);

        }
    SongListBean songListBean = items.get(position);
        TextView title = (TextView)v.findViewById(R.id.album_title);
    ImageView image=(ImageView)v.findViewById(R.id.image_album_item_list);
//
        // ImageView image=(ImageView)v.findViewById(R.id.image);
        if (title != null) {

           title.setText(songListBean.getAlbumName());


            Picasso.with(mContext).load(songListBean.getAlbumCover()).into(image);
        }

        return v;

        }

        }
