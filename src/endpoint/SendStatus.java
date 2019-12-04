package endpoint;

import dao.DataAccessException;
import dummydata.Model;
import request.SendStatusRequest;
import result.SendStatusResult;

public class SendStatus {
    public SendStatusResult sendStatus(SendStatusRequest request) throws DataAccessException {
        SendStatusResult result = new SendStatusResult();
        Model model = new Model();
        model.sendStatus(request.alias, request.name, request.date, request.message, request.hashtags, request.profilePic, request.urlPicture, request.authToken);
        result.message = "success";
        return result;
    }
}
