package endpoint;

import request.SendStatusRequest;
import result.SendStatusResult;

public class SendStatus {
    public SendStatusResult sendStatus(SendStatusRequest request) {
        SendStatusResult result = new SendStatusResult();
        result.message = "success";
        return result;
    }
}
