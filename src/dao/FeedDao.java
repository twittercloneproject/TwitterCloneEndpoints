package dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;
import dummydata.Model;
import dummydata.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedDao {
    private static final String TableName = "feeds";
    private static final String followerUsernameAttr = "follower_username";
    private static final String timeAttr = "time";
    private static final String nameAttr = "name";
    private static final String messageAttr = "message";
    private static final String hashtagsAttr = "hashtags";
    private static final String usernameAttr = "username";
    private static final String attachmentAttr = "attachment";
    private static final String profilePicAttr = "profile_pic";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    public void insertStatus(String username, String time, String message, String name,
                             String profilePic, String attachment, String hashtags, List<String> followers) throws DataAccessException {
        List<Item> newItems = new ArrayList<Item>();
        for(int i = 0; i < followers.size(); i++) {
            Item item = new Item()
                    .withPrimaryKey(followerUsernameAttr, followers.get(i), timeAttr, time)
                    .withString(usernameAttr, username)
                    .withString(nameAttr, name)
                    .withString(messageAttr, message)
                    .withString(profilePicAttr, profilePic)
                    .withString(attachmentAttr, attachment)
                    .withString(hashtagsAttr, hashtags);
            newItems.add(item);
            if(newItems.size() == 25) {
                TableWriteItems tableWriteItems = new TableWriteItems(TableName)
                        .withItemsToPut(newItems);
                BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);
                while(outcome.getUnprocessedItems().size() > 0) {
                    try {
                        Thread.sleep(2);
                    }
                    catch (InterruptedException e) {
                        System.out.println("error");
                        System.out.println(e);
                    }

                    outcome = dynamoDB.batchWriteItemUnprocessed(outcome.getUnprocessedItems());
                    System.out.println(outcome);

                }
                newItems.clear();
            }
        }

        if(newItems.size() > 0) {
            TableWriteItems tableWriteItems = new TableWriteItems(TableName)
                    .withItemsToPut(newItems);
            BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);
            System.out.println(outcome);
        }

    }

    public List<Status> getFeed(String username, String dateKey) {
        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#us", followerUsernameAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":user", new AttributeValue().withS(username));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#us = :user")
                .withScanIndexForward(false)
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(2);

        if (!dateKey.equals("")) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(followerUsernameAttr, new AttributeValue().withS(username));
            startKey.put(timeAttr, new AttributeValue().withS(dateKey));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        List<Status> feed = new ArrayList<Status>();
        if (items != null) {
            for (Map<String, AttributeValue> item : items){
                Status status = this.convertItemToStatus(item);
                feed.add(status);
            }
        }
        return feed;
    }

    private Status convertItemToStatus(Map<String, AttributeValue> item) {
        Model model = new Model();
        Status status = new Status();
        status.alias = item.get(usernameAttr).getS();
        status.date = item.get(timeAttr).getS();
        status.hashtags = model.stringToList(item.get(hashtagsAttr).getS());
        status.message = item.get(messageAttr).getS();
        status.name = item.get(nameAttr).getS();
        status.urlPicture = item.get(attachmentAttr).getS();
        status.profilePic = item.get(profilePicAttr).getS();
        return status;
    }
}
