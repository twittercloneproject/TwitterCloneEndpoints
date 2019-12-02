package endpoint;

import dummydata.Model;
import request.RequestByAlias;
import result.StatusesResult;

public class GetStory {
    public StatusesResult getStory(RequestByAlias request) {
        StatusesResult result = new StatusesResult();
        Model model = new Model();
        result.statuses = model.getStory(request.alias, request.lastDate);
        return result;
    }
}
