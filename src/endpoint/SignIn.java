package endpoint;

import com.fasterxml.jackson.annotation.JsonCreator;
import dummydata.Model;
import request.SignInRequest;
import result.SignInResult;

import java.util.UUID;

public class SignIn {
    public SignInResult signIn(SignInRequest request) {
        SignInResult result = new SignInResult();
        String newToken = UUID.randomUUID().toString();
        newToken = newToken.substring(0, 8);
        Model model = new Model();
        if(model.signIn(request.alias, request.password, newToken)){
            result.message = newToken;
        }
        else {
            result.message = "failure";
        }
        return result;
    }
}
