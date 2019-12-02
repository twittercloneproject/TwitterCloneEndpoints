package endpoint;

import dao.DataAccessException;
import dummydata.Model;
import request.FollowRequest;
import result.FollowResult;

public class FollowUser {
    public FollowResult followUser(FollowRequest request) throws DataAccessException {
        FollowResult result = new FollowResult();
        Model model = new Model();
        model.follow(request.currentAlias, request.otherAlias);
        result.message = "success";
        return result;
    }


}
