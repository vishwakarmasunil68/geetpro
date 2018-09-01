package webuters.com.geet_uttarakhand20aprilpro.bean;

/**
 * Created by Admin on 2/9/2016.
 */
public class AlbumSongsBean {
    @Override
    public String toString() {


        return "AlbumSongsBean{" +
                "id='" + id + '\'' +
                ", AlbumName='" + AlbumName + '\'' +
                ", Description='" + Description + '\'' +
                ", AlbumCover='" + AlbumCover + '\'' +
                ", SingerId='" + SingerId + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", SongPath='" + SongPath + '\'' +
                ", SongName='" + SongName + '\'' +
                "SongImg='" + SongImg + '\'' +
                "SingerId='" +SingerId + '\''
                +
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAlbumCover() {
        return AlbumCover;
    }

    public void setAlbumCover(String albumCover) {
        AlbumCover = albumCover;
    }

    public String getSingerId() {
        return SingerId;
    }

    public void setSingerId(String singerId) {
        SingerId = singerId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

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

    String id,AlbumName,Description,AlbumCover,SingerId,FirstName,SongPath,SongName;


    public String getSongImg() {
        return SongImg;
    }

    public void setSongImg(String songImg) {
        SongImg = songImg;
    }

    String SongImg;


}
