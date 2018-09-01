package webuters.com.geet_uttarakhand20aprilpro.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 14-03-2018.
 */

public class AlbumSongResultPOJO implements Serializable{
    @SerializedName("SongPath")
    String SongPath;
    @SerializedName("SongName")
    String SongName;
    @SerializedName("SongImg")
    String SongImg;

    public String getSongPath() {
        return SongPath;
    }

    public void setSongPath(String songPath) {
        SongPath = songPath;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    public String getSongImg() {
        return SongImg;
    }

    public void setSongImg(String songImg) {
        SongImg = songImg;
    }

    @Override
    public String toString() {
        return "AlbumSongResultPOJO{" +
                "SongPath='" + SongPath + '\'' +
                ", SongName='" + SongName + '\'' +
                ", SongImg='" + SongImg + '\'' +
                '}';
    }
}
