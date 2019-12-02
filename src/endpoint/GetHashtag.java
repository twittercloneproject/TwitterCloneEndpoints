package endpoint;

import dummydata.Model;
import request.HashtagRequest;
import result.StatusesResult;

public class GetHashtag {
    public StatusesResult getHashtag(HashtagRequest request) {
        StatusesResult result = new StatusesResult();
        Model model = new Model();
        result.statuses = model.getHashtags(request.hashtag, request.lastDate);
        return result;
    }
}
