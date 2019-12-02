package endpoint;

import dummydata.Model;
import request.RequestByAlias;
import result.UsersResult;

public class GetFollower {
    public UsersResult getFollower(RequestByAlias request) {
        UsersResult result = new UsersResult();
        Model model = new Model();
        result.users = model.getFollower(request.alias, request.lastUser);
        return result;
    }
}
