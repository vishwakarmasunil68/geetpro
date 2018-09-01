package webuters.com.geet_uttarakhand20aprilpro.bean;

/**
 * Created by Tushar on 2/24/2016.
 */
public class EventDetailesBean {
    @Override
    public String toString() {
        return "EventDetailesBean{" +
                "id='" + id + '\'' +
                ", EventName='" + EventName + '\'' +
                ", EventCover='" + EventCover + '\'' +
                ", MusicDirector='" + MusicDirector + '\'' +
                ", Lyricist='" + Lyricist + '\'' +
                ", Location='" + Location + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", StartTime='" + StartTime + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", EndTime='" + EndTime + '\'' +
                ", Details='" + Details + '\'' +
                ", OrganizerName='" + OrganizerName + '\'' +
                ", CreatedDate='" + CreatedDate + '\'' +
                ", ModifiedDate='" + ModifiedDate + '\'' +
                ", SingerId='" + SingerId + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventCover() {
        return EventCover;
    }

    public void setEventCover(String eventCover) {
        EventCover = eventCover;
    }

    public String getMusicDirector() {
        return MusicDirector;
    }

    public void setMusicDirector(String musicDirector) {
        MusicDirector = musicDirector;
    }

    public String getLyricist() {
        return Lyricist;
    }

    public void setLyricist(String lyricist) {
        Lyricist = lyricist;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getOrganizerName() {
        return OrganizerName;
    }

    public void setOrganizerName(String organizerName) {
        OrganizerName = organizerName;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
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

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    String id,EventName,EventCover,MusicDirector,Lyricist,Location,StartDate,StartTime,EndDate,EndTime,Details,OrganizerName,CreatedDate,ModifiedDate,SingerId,FirstName,LastName;
}
