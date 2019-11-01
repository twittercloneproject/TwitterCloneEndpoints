package dummydata;

import java.util.ArrayList;
import java.util.List;

public class Feed {
    private List<Status> statuses;

    public Feed() {
        statuses = new ArrayList<Status>();
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
