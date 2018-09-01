package webuters.com.geet_uttarakhand20aprilpro.bean;

/**
 * Created by Tushar on 4/6/2016.
 */
public class Catagory_calass_bean {
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Catagory_calass_bean{" +
                "id='" + id + '\'' +
                ", AlbumName='" + AlbumName + '\'' +
                ", AlbumCover='" + AlbumCover + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
                ", Images='" + Images + '\'' +
                '}';
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

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getImage() {
        return Images;
    }

    public void setImage(String images) {
        this.Images = images;
    }

    String id,AlbumName,AlbumCover,CategoryId,Images;
}
