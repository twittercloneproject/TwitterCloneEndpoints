package endpoint;

import dao.DataAccessException;
import dummydata.Model;
import request.RegisterRequest;
import result.RegisterResult;

import java.util.UUID;

public class Register {
    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        RegisterResult result = new RegisterResult();
        Model model = new Model();
        if(request.firstName.equals("a")) {
            model.updateUser(request.alias, request.url);
            result.message = "a";
            return result;
        }
        String newToken = UUID.randomUUID().toString();
        newToken = newToken.substring(0, 8);
        model.addUser(request.alias, request.firstName, request.lastName, request.url, newToken, request.password);
        result.message = newToken;
        return result;
    }
}
