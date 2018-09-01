package webuters.com.geet_uttarakhand20aprilpro.bean;

/**
 * Created by Tushar on 2/23/2016.
 */
public class SingerlistBean {

    @Override
    public String toString() {
        return "SingerlistBean{" +
                "Id='" + Id + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }

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

    String Id,FirstName,LastName,Image;
}
