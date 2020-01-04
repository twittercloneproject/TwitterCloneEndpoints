package sqs;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import dummydata.Model;

import java.util.List;
import java.util.Map;

public class FirstQueue implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        SQSEvent.SQSMessage sqsMessage = new SQSEvent.SQSMessage();
        String queueUrl = "https://sqs.us-west-2.amazonaws.com/765113573878/secondtwitterqueue";
        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            sqsMessage = msg;
            break;
        }
        Map<String, SQSEvent.MessageAttribute> values = sqsMessage.getMessageAttributes();
        Model model = new Model();
        List<String> followers = model.getAllFollowers(values.get("alias").getStringValue());

        Scale queue = new Queue();
        queue.sendMessages(queueUrl, followers, values.get("alias").getStringValue(), values.get("name").getStringValue(),
                values.get("date").getStringValue(),values.get("message").getStringValue(),values.get("hashtags").getStringValue(),
                values.get("profilePic").getStringValue(),values.get("urlPicture").getStringValue());

        return null;
    }

}
