package dummydata;

import java.util.ArrayList;
import java.util.List;

public class Story {
    private List<Status> myStatuses;

    public Story() {
        myStatuses = new ArrayList<Status>();
    }

    public List<Status> getMyStatuses() {
        return myStatuses;
    }

    public void setMyStatuses(List<Status> myStatuses) {
        this.myStatuses = myStatuses;
    }
}
