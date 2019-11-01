package endpoint;

import dummydata.Model;
import request.FollowerRequest;
import result.FollowerResult;

public class GetFollower {
    public FollowerResult getFollower(FollowerRequest request) {
        FollowerResult result = new FollowerResult();
        Model model = new Model();
        result.followers = model.getFollower(request.alias);
        return result;
    }
}
