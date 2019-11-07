package endpoint;

import dummydata.Model;
import request.RequestByAlias;
import result.StatusesResult;

public class GetFeed {
    public StatusesResult getFeed(RequestByAlias request) {
        StatusesResult result = new StatusesResult();
        Model model = new Model();
        result.statuses = model.getFeed(request.alias);
        return result;
    }
}
