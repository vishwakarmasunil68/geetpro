package webuters.com.geet_uttarakhand20aprilpro.activity;

/**
 * Created by Tushar on 2/24/2016.
 */
public class EventClassBean {
    @Override
    public String toString() {
        return "EventClassBean{" +
                "id='" + id + '\'' +
                ", EventName='" + EventName + '\'' +
                ", EventCover='" + EventCover + '\'' +
                ", StartDate='" + StartDate + '\'' +
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

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    String id,EventName,EventCover,StartDate;


}
