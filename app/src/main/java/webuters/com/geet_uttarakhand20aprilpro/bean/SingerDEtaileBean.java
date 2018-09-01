package webuters.com.geet_uttarakhand20aprilpro.bean;

/**
 * Created by Admin on 2/10/2016.
 */
public class SingerDEtaileBean {
    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String albumName) {
        AlbumName = albumName;
    }

    @Override
    public String toString() {
        return "SingerDEtaileBean{" +
                "AlbumName='" + AlbumName + '\'' +
                ", Id='" + Id + '\'' +
                ", AlbumCover='" + AlbumCover + '\'' +
                '}';
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAlbumCover() {
        return AlbumCover;
    }

    public void setAlbumCover(String albumCover) {
        AlbumCover = albumCover;
    }

    String AlbumName,Id,AlbumCover;
}
