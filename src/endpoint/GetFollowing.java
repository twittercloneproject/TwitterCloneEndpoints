package endpoint;

import dummydata.Model;
import request.RequestByAlias;
import result.UsersResult;

public class GetFollowing {
    public UsersResult getFollowing(RequestByAlias request) {
        UsersResult result = new UsersResult();
        Model model = new Model();
        result.users = model.getFollowing(request.alias, request.lastUser);
        return result;
    }
}
