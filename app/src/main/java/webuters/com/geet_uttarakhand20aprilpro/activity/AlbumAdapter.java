package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.bean.AlbumSongsBean;

/**
 * Created by Admin on 2/9/2016.
 */
class AlbumAdapter extends ArrayAdapter<AlbumSongsBean> {


    private ArrayList<AlbumSongsBean> items;

    //public static Set<String>listItem;
    public static Activity mContext;
    ImageView menu_singer_download_image;

    public static String songpath;
    public static String S1, S2, song_image;

    //   ImageView image;
    ImageView favrate;
    Boolean flag = true;
    List<ImageView> lst = new ArrayList<>();
//    boolean[] flags;
    List<Boolean> flags;
    public static String SongImage123 = "", Sengaer_song_path = "", Index = "", song_name = "";
    List<String> lst_album_id = new ArrayList<String>();
    List<String> lst_song_name = new ArrayList<String>();
    List<String> lst_song_url = new ArrayList<String>();
    List<String> lst_song_album = new ArrayList<String>();

    public AlbumAdapter(Activity context, int textViewResourceID, ArrayList<AlbumSongsBean> items) {

        super(context, textViewResourceID, items);
        mContext = context;
        this.items = items;

//    listItem = new HashSet<String>();
//        flags = new boolean[items.size()];
        flags=new ArrayList<Boolean>();
        for (AlbumSongsBean albumSongsBean:items) {
            flags.add(false);
        }
        getList();
    }

    public void getList() {

        SharedPreferences sp = mContext.getSharedPreferences("geet.txt", Context.MODE_PRIVATE);
        String song_list_url = sp.getString("fav_song_url", "");
        String song_list_name = sp.getString("fav_song_name", "");
        String song_list_album = sp.getString("fav_song_album", "");
        String song_album_id = sp.getString("fav_song_album_id", "");
        if (song_list_name.equals("")) {

        } else {
            String[] song_name = song_list_name.split("::");
            String[] song_url = song_list_url.split("::");
            String[] song_album = song_list_album.split("::");
            String[] song_id_album = song_album_id.split("::");
            for (int i = 0; i < song_name.length; i++) {
                if (!song_name[i].equals("")) {
                    lst_song_name.add(song_name[i]);
                    lst_song_album.add(song_album[i]);
                    lst_song_url.add(song_url[i]);
                    lst_album_id.add(song_id_album[i]);
                    Log.d("sunil", lst_song_name.toString());
                }
            }
        }
    }

    @Override

    public View getView(final int position, final View convertView, ViewGroup parent) {

        View v = convertView;

        final AlbumSongsBean albumSongsBean = items.get(position);

        if (v == null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.album_detailes_layout, null);
        }

        final TextView title = (TextView) v.findViewById(R.id.txt_album_detailes_layout);

        int songIndex = position;

        final Intent i = new Intent(v.getContext(), BlurMediaPlayer1.class);
        i.putExtra("backup2", true);
        SharedPreferences sp = mContext.getSharedPreferences("geet.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("album_back_id", AlbumeSongs.Album_ID + "");
        editor.putString("album_back_cover", AlbumeSongs.Album_Cover_Image + "");
        editor.commit();


        final String albumID12 = AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongImg();
        songpath = AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongPath();
        final String IndexNo = String.valueOf(AlbumeSongs.AlBUM_LIST_ARRAY.get(songIndex).getSongPath());


        ImageView play = (ImageView) v.findViewById(R.id.play_image);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.img_flag = true;
//                AlbumeSongs.pdd.show();
                AlbumeSongs.songListBean.setSongname(songpath);
                SharedPreferences khogen = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                SharedPreferences.Editor editr = khogen.edit();
                editr.putString("SongImage123", albumID12);
                Log.d("sun", "SongImage123:-" + albumID12);
                Log.d("sunil", "position:-" + position);
                editr.putString("Sengaer_song_path", AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongPath());
                Log.d("sun", "Sengaer_song_path:-" + AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongPath());
                editr.putString("Index", IndexNo);
                Log.d("sun", "Index:-" + IndexNo);
                editr.putString("song_name", AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongName());
                editr.commit();


                AppConstant.setsavedmusicplaying(mContext, false);
                AppConstant.setmainmusicplaying(mContext, false);
                AppConstant.setfavmusicplaying(mContext, false);

                SongImage123 = albumID12;
                Sengaer_song_path = AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongPath();
                Index = IndexNo;
                song_name = AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongName();

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
                    NotificationManager nMgr = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
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
                mContext.startActivityForResult(i, 4);

            }
        });
        ImageView download = (ImageView) v.findViewById(R.id.oitem_singer_download_image);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("List Item Click", "Clicked b");
                String url = "url you want to download";
                String root_sd = Environment.getExternalStorageDirectory().toString();
                //file = new File(root_sd + "/Download");
                File file = new File(root_sd + File.separator + "Android" + File.separator + "data" + File.separator + "webuters.com.geet_uttarakhand20aprilpro" + File.separator + "files");
                file.mkdirs();

                File file1 = new File(root_sd + File.separator + "Android" + File.separator + "data" + File.separator + "webuters.com.geet_uttarakhand20aprilpro" + File.separator + "images");
                file1.mkdirs();
                Log.e("downloaddddddd", songpath);

                S1 = AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongName();
                song_image = AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongImg();
                Log.d("sunil", "position:-" + position);
                Log.d("sunil", "song_path:-" + AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongPath());
                Log.d("sunil", "song_image1:-" + song_image);
                Log.d("sunil", "song_name:-" + S1);
//
                activitylistener listener = (AlbumeSongs) mContext;
                listener.yourDesiredMethod(AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongPath());


            }
        });

        favrate = (ImageView) v.findViewById(R.id.favrate);
        favrate.setTag(position + "");
        lst.add(favrate);
        favrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sun", position + "");
                if (flags.get(position)) {
//                    flags[position] = false;
                    flags.set(position,false);
//                    Log.d("sun", flags[position] + "");
                    List<String> lst_album_id = new ArrayList<String>();
                    List<String> lst_song_name = new ArrayList<String>();
                    List<String> lst_song_url = new ArrayList<String>();
                    List<String> lst_song_album = new ArrayList<String>();
                    SharedPreferences sp = mContext.getSharedPreferences("geet.txt", Context.MODE_PRIVATE);
                    String song_list_url = sp.getString("fav_song_url", "");
                    String song_list_name = sp.getString("fav_song_name", "");
                    String song_list_album = sp.getString("fav_song_album", "");
                    String song_album_id = sp.getString("fav_song_album_id", "");
                    if (song_list_name.equals("")) {

                    } else {
                        String[] song_name = song_list_name.split("::");
                        String[] song_url = song_list_url.split("::");
                        String[] song_album = song_list_album.split("::");
                        String[] song_id_album = song_album_id.split("::");
                        for (int i = 0; i < song_name.length; i++) {
                            if (!song_name[i].equals("")) {
                                lst_song_name.add(song_name[i]);
                                lst_song_album.add(song_album[i]);
                                lst_song_url.add(song_url[i]);
                                lst_album_id.add(song_id_album[i]);
                            }
                        }
                    }


                    int pos = -1;

                    for (int j = 0; j < lst_song_name.size(); j++) {
                        if (title.getText().toString().equals(lst_song_name.get(j))) {
                            pos = j;
                            Log.d("sunil", "pos:-" + pos + "");
                        }
                    }
                    if (pos != -1) {

                        lst.get(position).setImageResource(R.drawable.fav_white);
                        lst_song_name.remove(pos);
                        lst_song_url.remove(pos);
                        lst_song_album.remove(pos);
                        lst_album_id.remove(pos);


                        SharedPreferences.Editor editor = sp.edit();
                        String shared_song_name = "";
                        String shared_song_url = "";
                        String shared_song_album = "";
                        String shared_song_album_id = "";
                        for (int i = 0; i < lst_song_name.size(); i++) {
                            shared_song_name += lst_song_name.get(i) + "::";
                            shared_song_url += lst_song_url.get(i) + "::";
                            shared_song_album += lst_song_album.get(i) + "::";
                            shared_song_album_id += lst_album_id.get(i) + "::";
                        }
                        editor.putString("fav_song_name", shared_song_name);
                        editor.putString("fav_song_url", shared_song_url);
                        editor.putString("fav_song_album", shared_song_album);
                        editor.putString("fav_song_album_id", shared_song_album_id);
                        editor.commit();
                    }

                } else {
                    flags.set(position,true);
//                    Log.d("sun", flags[position] + "");
//                favrate.setBackgroundColor(Color.parseColor("#000000"));
//                lst.get(position).setBackgroundColor(Color.parseColor("#00bfff"));
                    lst.get(position).setImageResource(R.drawable.fav_yellow);
                    S2 = AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongName();
                    MainActivity.listItem.add(S2);

                    List<String> lst_album_id = new ArrayList<String>();
                    List<String> lst_song_name = new ArrayList<String>();
                    List<String> lst_song_url = new ArrayList<String>();
                    List<String> lst_song_album = new ArrayList<String>();
                    SharedPreferences sp = mContext.getSharedPreferences("geet.txt", Context.MODE_PRIVATE);
                    String song_list_url = sp.getString("fav_song_url", "");
                    String song_list_name = sp.getString("fav_song_name", "");
                    String song_list_album = sp.getString("fav_song_album", "");
                    String song_album_id = sp.getString("fav_song_album_id", "");
                    if (song_list_name.equals("")) {

                    } else {
                        String[] song_name = song_list_name.split("::");
                        String[] song_url = song_list_url.split("::");
                        String[] song_album = song_list_album.split("::");
                        String[] song_id_album = song_album_id.split("::");
                        for (int i = 0; i < song_name.length; i++) {
                            if (!song_name[i].equals("")) {
                                lst_song_name.add(song_name[i]);
                                lst_song_album.add(song_album[i]);
                                lst_song_url.add(song_url[i]);
                                lst_album_id.add(song_id_album[i]);
                            }
                        }
                    }


                    lst_song_name.add(AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongName());
                    lst_song_url.add(AlbumeSongs.AlBUM_LIST_ARRAY.get(position).getSongPath());
                    lst_song_album.add(AlbumeSongs.album_url);
                    lst_album_id.add(albumID12);

                    SharedPreferences.Editor editor = sp.edit();
                    String shared_song_name = "";
                    String shared_song_url = "";
                    String shared_song_album = "";
                    String shared_song_album_id = "";
                    for (int i = 0; i < lst_song_name.size(); i++) {
                        shared_song_name += lst_song_name.get(i) + "::";
                        shared_song_url += lst_song_url.get(i) + "::";
                        shared_song_album += lst_song_album.get(i) + "::";
                        shared_song_album_id += lst_album_id.get(i) + "::";
                    }
                    editor.putString("fav_song_name", shared_song_name);
                    editor.putString("fav_song_url", shared_song_url);
                    editor.putString("fav_song_album", shared_song_album);
                    editor.putString("fav_song_album_id", shared_song_album_id);
                    editor.commit();

                }
            }
        });

        ImageView imageView = (ImageView) v.findViewById(R.id.singer_list_item_image);
        menu_singer_download_image = (ImageView) v.findViewById(R.id.see_all_singerssee_all_singers);

        menu_singer_download_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Clicked share item ", "tushar clicked");

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                // share.addFlags(Integer.parseInt(Intent.EXTRA_SUBJECT));
                Log.e("text share is :", "text");
                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, albumSongsBean.getSongPath());

                mContext.startActivity(Intent.createChooser(share, "Share link!"));


            }
        });


        System.out.println("song image :: " + albumSongsBean.getSongName());
        if (title != null) {
            title.setText(albumSongsBean.getSongName());

            for (String s : lst_song_name) {
                Log.d("sunil", s + "\t\t" + title.getText().toString());
                if (title.getText().toString().equals(s)) {
                    Log.d("sunil", "infav");
                    favrate.setImageResource(R.drawable.fav_yellow);
//                    flags[position] = true;
                    flags.set(position,true);
                }
            }

            Picasso.with(mContext).load(albumSongsBean.getSongImg()).into(imageView);
//        title2.setText(album_song.getSongPath());
            //   title3.setText(album_song.getMusicDirector());
            //   textView44.setText(a);
//                Picasso.with(this)
//                        .load("YOUR IMAGE URL HERE")
//                        .into(image);
        }

        return v;

    }

}
