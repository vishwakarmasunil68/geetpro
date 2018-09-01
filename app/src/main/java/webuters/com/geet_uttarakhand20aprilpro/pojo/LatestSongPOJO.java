package webuters.com.geet_uttarakhand20aprilpro.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 16-02-2018.
 */

public class LatestSongPOJO {
    @SerializedName("Id")
    String Id;
    @SerializedName("FirstName")
    String FirstName;
    @SerializedName("LastName")
    String LastName;
    @SerializedName("Image")
    String Image;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
