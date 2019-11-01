package endpoint;

import dummydata.Model;
import request.UserRequest;
import result.UserResult;

public class GetUser {
    public UserResult getUser(UserRequest request) {
        UserResult result = new UserResult();
        Model model = new Model();
        result.user = model.getUser(request.alias);
        return result;
    }
}
