package webuters.com.geet_uttarakhand20aprilpro.bean;

/**
 * Created by Tushar on 2/27/2016.
 */
public class AlbumListBean {
    @Override
    public String toString() {
        return "AlbumListBean{" +
                "id='" + id + '\'' +
                ", AlbumName='" + AlbumName + '\'' +
                ", AlbumCover='" + AlbumCover + '\'' +
                ", CreatedDate='" + CreatedDate + '\'' +
                '}';
    }

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