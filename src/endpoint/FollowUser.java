package endpoint;

import request.FollowRequest;
import result.FollowResult;

public class FollowUser {
    public FollowResult followUser(FollowRequest request) {
        FollowResult result = new FollowResult();
        result.message = "success";
        return result;
    }


}
