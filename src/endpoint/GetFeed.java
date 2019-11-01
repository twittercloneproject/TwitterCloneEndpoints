package endpoint;

import dummydata.Model;
import request.FeedRequest;
import result.FeedResult;

public class GetFeed {
    public FeedResult getFeed(FeedRequest request) {
        FeedResult result = new FeedResult();
        Model model = new Model();
        result.feed = model.getFeed(request.alias);
        return result;
    }
}
