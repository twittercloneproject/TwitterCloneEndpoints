package endpoint;

import request.UnFollowRequest;
import result.UnFollowResult;

public class UnFollowUser {
    public UnFollowResult unFollowUser(UnFollowRequest request) {
        UnFollowResult result = new UnFollowResult();
        result.message = "success";
        return result;
    }
}
