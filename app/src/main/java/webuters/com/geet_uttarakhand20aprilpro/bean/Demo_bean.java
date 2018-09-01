package webuters.com.geet_uttarakhand20aprilpro.bean;

/**
 * Created by Tushar on 4/7/2016.
 */
public class Demo_bean {
    @Override
    public String toString()
    {
        return "Demo_bean{" +
                "id='" + id + '\'' +
                ", AlbumName='" + AlbumName + '\'' +
                ", AlbumCover='" + AlbumCover + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
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

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    String id,AlbumName,AlbumCover,CategoryId;
}
