package webuters.com.geet_uttarakhand20aprilpro.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.activity.AlbumeSongs;
import webuters.com.geet_uttarakhand20aprilpro.bean.Catagory_calass_bean;



/**
 * Created by Tushar on 3/31/2016.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Catagory_calass_bean> itemInfoList;
   // private ArrayList<Catagory_Deatiles_bean> items;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public ViewPagerAdapter(Context context, List<Catagory_calass_bean> itemInfoList) {
        this.context = context;
        this.itemInfoList = itemInfoList;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.imageLoader = ImageLoader.getInstance();
        this.options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.mipmap.hindi)
                .showImageOnFail(R.mipmap.hindi)
                .showImageOnLoading(R.mipmap.hindi)
                .build();
    }

    @Override
    public int getCount() {
        return itemInfoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = inflater.inflate(R.layout.row_item_for_view_pager_layout, null);


        Catagory_calass_bean itemInfo = itemInfoList.get(position);

       final String url = itemInfo.getAlbumCover();
         final String Song_url = itemInfo.getAlbumName();
final String id=itemInfo.getId();
        Log.d("Android: ", "images::  " + url);
System.out.println("Adapter recive url song is --" + Song_url);


        ImageView imageView = (ImageView) view.findViewById(R.id.pager_image_iv);



        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(url, imageView, options);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("clicked image view pager","view pager");
                Intent i = new Intent(context,AlbumeSongs.class);
                i.putExtra("Album_id", id);
                i.putExtra("SongImage123",url);
              //  System.out.println("post data as it is ---- >" + Song_url);
                context.startActivity(i);
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
