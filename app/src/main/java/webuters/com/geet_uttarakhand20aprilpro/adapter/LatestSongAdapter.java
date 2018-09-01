package webuters.com.geet_uttarakhand20aprilpro.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.activity.Singers_Details_class;
import webuters.com.geet_uttarakhand20aprilpro.pojo.LatestSongPOJO;

/**
 * Created by sunil on 05-05-2017.
 */

public class LatestSongAdapter extends RecyclerView.Adapter<LatestSongAdapter.ViewHolder> {
    private List<LatestSongPOJO> items;
    Activity activity;
    Fragment fragment;

    public LatestSongAdapter(Activity activity, Fragment fragment, List<LatestSongPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_album_adapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Picasso.with(activity)
                .load(items.get(position).getImage())
                .into(holder.iv_album);

        holder.tv_album.setText(items.get(position).getFirstName());

        holder.ll_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(activity, AlbumeSongs.class);
//                i.putExtra("Album_id", items.get(position).getId());
//                i.putExtra("SongImage123", items.get(position).getImage());
//                activity.startActivity(i);

//                Log.d(TagUtils.getTag(),"id:-"+items.get(position).getId());
//                Log.d(TagUtils.getTag(),"SongImage123:-"+items.get(position).getImage());
//                Log.d(TagUtils.getTag(),"album_name:-"+items.get(position).getFirstName());
//                Intent i = new Intent(activity, NewAlbumSongsActivity.class);
//                i.putExtra("Album_id", items.get(position).getId());
//                i.putExtra("SongImage123", items.get(position).getImage());
//                i.putExtra("album_name", items.get(position).getFirstName()+" "+items.get(position).getLastName());
//                activity.startActivity(i);
//

                Intent i = new Intent(activity, Singers_Details_class.class);
                String SongImg = items.get(position).getImage();
                String AlbumId = items.get(position).getId();
                String Singer_name = items.get(position).getFirstName()+" "+items.get(position).getLastName();

                String full_name = Singer_name;
                i.putExtra("full", full_name);
//                                    String Singer_name1=array_SingerlistBean.get(0).getLastName();
//                                    String full_name=Singer_name+Singer_name1;
//                                    i.putExtra("full",full_name);
                i.putExtra("Singer_id_put", AlbumId);

                i.putExtra("ImageIs", SongImg);
                activity.startActivity(i);
            }
        });



        holder.itemView.setTag(items.get(position));
    }


    private final String TAG = getClass().getSimpleName();

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_album;
        public TextView tv_album;
        public LinearLayout ll_album;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_album = (LinearLayout) itemView.findViewById(R.id.ll_album);
            tv_album = (TextView) itemView.findViewById(R.id.tv_album);
            iv_album = (ImageView) itemView.findViewById(R.id.iv_album);
        }
    }
}
