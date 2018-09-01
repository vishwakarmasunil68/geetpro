package webuters.com.geet_uttarakhand20aprilpro.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 16-02-2018.
 */

public class AlbumPOJO {
    @SerializedName("id")
    String id;
    @SerializedName("AlbumName")
    String AlbumName;
    @SerializedName("AlbumCover")
    String AlbumCover;
    @SerializedName("CreatedDate")
    String CreatedDate;
    @SerializedName("CategoryId")
    String CategoryId;

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

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
