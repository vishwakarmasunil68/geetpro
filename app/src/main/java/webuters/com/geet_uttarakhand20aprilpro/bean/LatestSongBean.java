package webuters.com.geet_uttarakhand20aprilpro.bean;

/**
 * Created by Admin on 2/10/2016.
 */
public class LatestSongBean {

    public String getSongId() {
        return SongId;
    }

    public void setSongId(String songId) {
        SongId = songId;
    }

    @Override
    public String toString() {
        return "LatestSongBean{" +
                "SongId='" + SongId + '\'' +
                ", id='" + id + '\'' +
                ", SongName='" + SongName + '\'' +
                ", CreatedDate='" + CreatedDate + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", SongImg='" + SongImg + '\'' +
                ", SongPath='" + SongPath + '\'' +
                ",SongImg='" +SongImg+'\'' +
                '}';
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
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

    public String getSongImg() {
        return SongImg;
    }

    public void setSongImg(String songImg) {
        SongImg = songImg;
    }

    public String getSongPath() {
        return SongPath;
    }

    public void setSongPath(String songPath) {
        SongPath = songPath;
    }

    String SongId;
    String id;
    String SongName;
    String CreatedDate;
    String FirstName;
    String LastName;
String SongImg,SongPath;

}
