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
        List<Person> users = model.getPerson(request.currentAlias).getFollowings();
        for (Person u:users) {
            if(u.getAlias().getUsername().equals(request.otherAlias)) {
                result.isFollowing = true;
                return result;
            }
        }
        result.isFollowing = false;
        return result;
    }
}
