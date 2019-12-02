package endpoint;

import dummydata.Model;
import request.UnFollowRequest;
import result.UnFollowResult;

public class UnFollowUser {
    public UnFollowResult unFollowUser(UnFollowRequest request) {
        UnFollowResult result = new UnFollowResult();
        Model model = new Model();
        model.unfollow(request.currentAlias, request.otherAlias);

        result.message = "success";
        return result;
    }
}
