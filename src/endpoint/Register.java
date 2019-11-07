package endpoint;

import request.RegisterRequest;
import result.RegisterResult;

public class Register {
    public RegisterResult register(RegisterRequest request) {
        RegisterResult result = new RegisterResult();
        result.message = "success";
        return result;
    }
}
