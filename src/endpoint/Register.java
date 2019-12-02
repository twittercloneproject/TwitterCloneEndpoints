package endpoint;

import dao.DataAccessException;
import dummydata.Model;
import request.RegisterRequest;
import result.RegisterResult;

public class Register {
    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        RegisterResult result = new RegisterResult();
        Model model = new Model();
        model.addUser(request.alias, request.firstName, request.lastName, request.url);
        result.message = "success";
        return result;
    }
}
