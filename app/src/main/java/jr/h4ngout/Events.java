package jr.h4ngout;

/**
 * Created by jrfif on 05/02/2018.
 */

public class Events {
    private String title, start, end;

    public Events(String title, String start, String end)
    {
        this.setTitle(title);
        this.setStart(start);
        this.setEnd(end);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
