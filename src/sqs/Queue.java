package sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import dummydata.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Queue implements Scale {

    @Override
    public void sendStatus(String url, String username, String name, String date, String message, List<String> hashtags, String profilePic, String attachment) {
        Model model = new Model();
        String hasht = model.listToString(hashtags);
        Map<String, MessageAttributeValue> messageMap = new HashMap<String, MessageAttributeValue>();
        messageMap.put("name", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(name));
        messageMap.put("alias", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(username));
        messageMap.put("date", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(date));
        messageMap.put("message", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(message));
        messageMap.put("profilePic", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(profilePic));
        messageMap.put("urlPicture", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(attachment));
        messageMap.put("hashtags", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(hasht));

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(url)
                .withMessageBody("empty")
                .withMessageAttributes(messageMap)
                .withDelaySeconds(5);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
        String msgId = send_msg_result.getMessageId();
    }


    @Override
    public void sendMessages(String url, List<String> followersUsername, String username, String name, String date, String message, String hashtags, String profilePic, String attachment) {
        Model model = new Model();
        Map<String, MessageAttributeValue> messageMap = new HashMap<String, MessageAttributeValue>();

        messageMap.put("name", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(name));
        messageMap.put("alias", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(username));
        messageMap.put("date", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(date));
        messageMap.put("message", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(message));
        messageMap.put("profilePic", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(profilePic));
        messageMap.put("urlPicture", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(attachment));
        messageMap.put("hashtags", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue(hashtags));
        messageMap.put("followerUsername", new MessageAttributeValue()
                .withDataType("String")
                .withStringValue("here"));


        int length = followersUsername.size();
        System.out.println(length);
        List<String> sList;
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        for(int i = 0; i < length;) {
            System.out.println(i);
            if(length < i+500) {
                sList = followersUsername.subList(i, length);
            }
            else {
                sList = followersUsername.subList(i, i+500);
            }
            String sFollowers = model.listToString(sList);
            messageMap.replace("followerUsername", new MessageAttributeValue()
                    .withDataType("String")
                    .withStringValue(sFollowers));
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(url)
                    .withMessageBody("empty")
                    .withMessageAttributes(messageMap)
                    .withDelaySeconds(5);

            SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

            i += 500;
        }
    }
}
