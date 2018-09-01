package webuters.com.geet_uttarakhand20aprilpro.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 14-03-2018.
 */

public class AlbumSongPOJO implements Serializable{
    List<AlbumSongResultPOJO> albumSongResultPOJOS;

    public List<AlbumSongResultPOJO> getAlbumSongResultPOJOS() {
        return albumSongResultPOJOS;
    }

    public void setAlbumSongResultPOJOS(List<AlbumSongResultPOJO> albumSongResultPOJOS) {
        this.albumSongResultPOJOS = albumSongResultPOJOS;
    }
}
