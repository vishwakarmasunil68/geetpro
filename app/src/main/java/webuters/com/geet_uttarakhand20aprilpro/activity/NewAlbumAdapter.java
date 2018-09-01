package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.Utils.Pref;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;
import webuters.com.geet_uttarakhand20aprilpro.pojo.AlbumSongPOJO;
import webuters.com.geet_uttarakhand20aprilpro.pojo.AlbumSongResultPOJO;

/**
 * Created by Admin on 2/9/2016.
 */
class NewAlbumAdapter extends RecyclerView.Adapter<NewAlbumAdapter.ViewHolder> {
    private List<AlbumSongResultPOJO> items;
    Activity activity;
    AlbumSongPOJO album;
    Fragment fragment;
    String cover_image;
    List<String> savedSongs = new ArrayList<String>();

    public NewAlbumAdapter(Activity activity, Fragment fragment, AlbumSongPOJO album,String cover_image) {
        this.cover_image=cover_image;
        this.album = album;
        this.items = album.getAlbumSongResultPOJOS();
        this.activity = activity;
        this.fragment = fragment;
        this.savedSongs.addAll(Pref.getsaveSongSTRINGS(activity));
    }

    @Override
    public NewAlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_new_album_songs, parent, false);
        return new NewAlbumAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NewAlbumAdapter.ViewHolder holder, final int position) {

        holder.tv_song_name.setText(items.get(position).getSongName());

        holder.iv_play_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(activity, BlurMediaPlayer1.class);
                i.putExtra("backup2", true);
                i.putExtra("album", album);
                i.putExtra("playing_position", position);
                i.putExtra("cover_image", cover_image);
                Log.d(TagUtils.getTag(),"song cover adapter:-"+cover_image);
                MainActivity.img_flag = true;


                AppConstant.setsavedmusicplaying(activity, false);
                AppConstant.setmainmusicplaying(activity, false);
                AppConstant.setfavmusicplaying(activity, false);

                MainActivity.blured_mediaplaying = false;
                try {
                    BlurMediaPlayer1.bmp1.finish();
                } catch (Exception e) {
                    Log.d("sunil", e.toString());
                }
                try {
                    NotificationPanel.notificationCancel();
                } catch (Exception e) {
                    Log.d("sunil", e.toString());
                }
                try {
                    NotificationManager nMgr = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                    nMgr.cancelAll();
                    BlurMediaPlayer1.mediaPlayer.stop();
                } catch (Exception e) {
                    Log.d("sunil", e.toString());
                }
                try {
//                if (ContactUs.mediaPlayer != null) {
                    ContactUs.mediaPlayer.stop();
                    ContactUs.imgplay.setVisibility(View.GONE);
                    ContactUs.imgpause.setVisibility(View.VISIBLE);
                    ContactUs.music_flag = true;
                    ContactUs.reset_player = true;
//                }
                } catch (Exception e) {
                    Log.d("sunil", e.toString());
                }
                try {
                    BluredMediaPlayer2.mediaPlayer.stop();
                } catch (Exception e) {
                    Log.d("sunil", e.toString());
                }
                activity.startActivity(i);
            }
        });

        if (savedSongs.contains((new Gson().toJson(items.get(position))))) {
            holder.iv_set_favourite.setImageResource(R.drawable.fav_yellow);
        } else {
            holder.iv_set_favourite.setImageResource(R.drawable.fav_white);
        }

        holder.iv_set_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Pref.saveSongPOJOS(activity.getApplicationContext(), items.get(position))) {
                    holder.iv_set_favourite.setImageResource(R.drawable.fav_yellow);
                } else {
                    holder.iv_set_favourite.setImageResource(R.drawable.fav_white);
                }
            }
        });

        holder.iv_download_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity instanceof NewAlbumSongsActivity){
                    NewAlbumSongsActivity newAlbumSongsActivity= (NewAlbumSongsActivity) activity;
                    newAlbumSongsActivity.yourDesiredMethod(items.get(position).getSongPath(),items.get(position).getSongName());
                }
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
        public TextView tv_song_name;
        public ImageView iv_play_song;
        public ImageView iv_download_song;
        public ImageView iv_set_favourite;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_song_name = itemView.findViewById(R.id.tv_song_name);
            iv_play_song = itemView.findViewById(R.id.iv_play_song);
            iv_download_song = itemView.findViewById(R.id.iv_download_song);
            iv_set_favourite = itemView.findViewById(R.id.iv_set_favourite);
        }
    }
}
