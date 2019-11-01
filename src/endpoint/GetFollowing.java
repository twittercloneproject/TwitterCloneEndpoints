package endpoint;

import dummydata.Model;
import request.FollowerRequest;
import result.FollowingResult;

public class GetFollowing {
    public FollowingResult getFollowing(FollowerRequest request) {
        FollowingResult result = new FollowingResult();
        Model model = new Model();
        result.following = model.getFollowing(request.alias);
        return result;
    }
}
