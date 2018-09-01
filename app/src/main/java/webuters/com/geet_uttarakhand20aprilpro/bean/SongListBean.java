package webuters.com.geet_uttarakhand20aprilpro.bean;

import android.graphics.Bitmap;

/**
 * Created by Tushar on 2/22/2016.
 */
public class SongListBean  {

    @Override
    public String toString() {
        return "SongListBean{" +
                "id='" + id + '\'' +
                ", AlbumName='" + AlbumName + '\'' +
                ", AlbumCover='" + AlbumCover + '\'' +
                ", CreatedDate='" + CreatedDate + '\'' +
                '}';
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String songname;
    public Bitmap getBitmapsongnam() {
        return bitmapsongnam;
    }

    public void setBitmapsongnam(Bitmap bitmapsongnam) {
        this.bitmapsongnam = bitmapsongnam;
    }

    public Bitmap bitmapsongnam;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String albumName) {
        AlbumName = albumName;
    }

    public String getAlbumCover() {
        return AlbumCover;
    }

    public void setAlbumCover(String albumCover) {
        AlbumCover = albumCover;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    String id,AlbumName,AlbumCover,CreatedDate;
}
