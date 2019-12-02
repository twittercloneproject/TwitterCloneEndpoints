package endpoint;

import dummydata.Model;
import dummydata.Person;
import request.IsFollowingRequest;
import result.IsFollowingResult;

import java.util.List;

public class IsFollowingUser {
    public IsFollowingResult isFollowing(IsFollowingRequest request) {
        Model model = new Model();
        IsFollowingResult result = new IsFollowingResult();
        result.isFollowing = model.isFollowing(request.currentAlias, request.otherAlias);
        return result;
    }
}
