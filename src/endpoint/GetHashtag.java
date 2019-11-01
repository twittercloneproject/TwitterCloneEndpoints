package endpoint;

import dummydata.Model;
import request.HashtagRequest;
import result.HashtagResult;

public class GetHashtag {
    public HashtagResult getHashtag(HashtagRequest request) {
        HashtagResult result = new HashtagResult();
        Model model = new Model();
        result.statuses = model.hastags;
        return result;
    }
}
