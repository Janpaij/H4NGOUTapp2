package jr.h4ngout;

/**
 * Created by jrfif on 22/02/2018.
 */

public class MyEventsGetSet {
    private String title, date, location, EventID;

    public MyEventsGetSet(String title, String date, String location, String EventID)
    {
        this.setTitle(title);
        this.setDate(date);
        this.setLocation(location);
        this.setEventID(EventID);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String EventID) {
        this.EventID = EventID;
    }
}
