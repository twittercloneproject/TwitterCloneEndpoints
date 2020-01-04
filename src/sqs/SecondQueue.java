package sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import dao.DataAccessException;
import dummydata.Model;

import java.util.List;
import java.util.Map;

public class SecondQueue implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        SQSEvent.SQSMessage sqsMessage;
        Model model = new Model();
        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            sqsMessage = msg;
            Map<String, SQSEvent.MessageAttribute> values = sqsMessage.getMessageAttributes();
            List<String> followersUsernames = model.stringToList(values.get("followerUsername").getStringValue());
            List<String> hashtags = model.stringToList(values.get("hashtags").getStringValue());
            System.out.println(followersUsernames.size());
            model.feedBatchWrite(followersUsernames, values.get("alias").getStringValue(), values.get("name").getStringValue(),
                    values.get("date").getStringValue(), values.get("message").getStringValue(),
                    hashtags, values.get("profilePic").getStringValue(), values.get("urlPicture").getStringValue());

        }


        return null;
    }
}
