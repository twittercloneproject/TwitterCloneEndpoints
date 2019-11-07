package endpoint;

import request.SignInRequest;
import result.SignInResult;

public class SignIn {
    public SignInResult signIn(SignInRequest request) {
        SignInResult result = new SignInResult();
        result.message = "success";
        return result;
    }
}
