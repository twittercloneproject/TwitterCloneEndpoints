package endpoint;

import dummydata.Model;
import request.StatusRequest;
import result.StatusResult;

public class GetStatus {
    public StatusResult getStatus(StatusRequest request) {
        StatusResult result = new StatusResult();
        Model model = new Model();
        result.status = model.getStatus(request.alias, request.time);
        return result;
    }

}
