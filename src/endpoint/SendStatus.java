package endpoint;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import dao.DataAccessException;
import dummydata.Model;
import dummydata.Status;
import request.SendStatusRequest;
import result.SendStatusResult;

import sqs.Queue;
import sqs.Scale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendStatus {
    public SendStatusResult sendStatus(SendStatusRequest request) throws DataAccessException {
        String queueUrl = "https://sqs.us-west-2.amazonaws.com/765113573878/firsttwitterQueue";
        Model model = new Model();
        model.sendStatus(request.alias, request.name, request.date, request.message, request.hashtags, request.profilePic, request.urlPicture, "token");
        Scale queue = new Queue();
        queue.sendStatus(queueUrl, request.alias, request.name, request.date, request.message, request.hashtags, request.profilePic, request.urlPicture);

        SendStatusResult result = new SendStatusResult();
        result.message = "success";
        return result;
    }
}
