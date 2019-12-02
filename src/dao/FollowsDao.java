package dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;
import dummydata.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowsDao {
    private static final String TableName = "follows";
    private static final String IndexName = "followee_username-follower_username-index";
    private static final String followerUsernameAttr = "follower_username";
    private static final String followerFirstNameAttr = "follower_first_name";
    private static final String followerLastNameAttr = "follower_last_name";
    private static final String followerProfilePicAttr = "follower_profile_pic";
    private static final String followeeUsernameAttr = "followee_username";
    private static final String followeeFirstNameAttr = "followee_first_name";
    private static final String followeeLastNameAttr = "followee_last_name";
    private static final String followeeProfilePicAttr = "followee_profile_pic";


    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    public boolean isFollowing(String followerUsername, String followeeUsername) {
        Table table = dynamoDB.getTable(TableName);
        Item item = table.getItem(followerUsernameAttr, followerUsername, followeeUsernameAttr, followeeUsername);
        if(item == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public void follow(String followerUsername, String followerFirstName, String followerLastName, String followerProfilePic,
                       String followeeUsername, String followeeFirstName, String followeeLastName, String followeeProfilePic) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);
        try {
            Item item = new Item()
                    .withPrimaryKey(followerUsernameAttr, followerUsername, followeeUsernameAttr, followeeUsername)
                    .withString(followerFirstNameAttr, followerFirstName)
                    .withString(followerLastNameAttr, followerLastName)
                    .withString(followerProfilePicAttr, followerProfilePic)
                    .withString(followeeFirstNameAttr, followeeFirstName)
                    .withString(followeeLastNameAttr, followeeLastName)
                    .withString(followeeProfilePicAttr, followeeProfilePic);


            table.putItem(item);
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public void unFollow(String followerUsername, String followeeUsername) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(followerUsernameAttr, followerUsername, followeeUsernameAttr, followeeUsername);
    }

    public List<User> getFollowing(String username, String lastFollowee) {
        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#us", followerUsernameAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":user", new AttributeValue().withS(username));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#us = :user")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(25);

        if (!lastFollowee.equals("")) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(followerUsernameAttr, new AttributeValue().withS(username));
            startKey.put(followeeUsernameAttr, new AttributeValue().withS(lastFollowee));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        List<User> following = new ArrayList<User>();
        if (items != null) {
            for (Map<String, AttributeValue> item : items){
                User user = this.convertItemToUser(item, "following");
                following.add(user);
            }
        }
        return following;
    }

    public List<User> getFollowers(String username, String lastFollower) {
        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#us", followeeUsernameAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":user", new AttributeValue().withS(username));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withIndexName(IndexName)
                .withKeyConditionExpression("#us = :user")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(25);

        if (!lastFollower.equals("")) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(followeeUsernameAttr, new AttributeValue().withS(username));
            startKey.put(followerUsernameAttr, new AttributeValue().withS(lastFollower));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        List<User> followers = new ArrayList<User>();
        if (items != null) {
            for (Map<String, AttributeValue> item : items){
                User user = this.convertItemToUser(item, "followers");
                followers.add(user);
            }
        }
        return followers;
    }

    private User convertItemToUser(Map<String, AttributeValue> item, String type) {
        User user = new User();
        if(type.equals("followers")) {
            user.alias = item.get(followerUsernameAttr).getS();
            user.firstName = item.get(followerFirstNameAttr).getS();
            user.lastName = item.get(followerLastNameAttr).getS();
            user.urlPicture = item.get(followerProfilePicAttr).getS();
        }
        else {
            user.alias = item.get(followeeUsernameAttr).getS();
            user.firstName = item.get(followeeFirstNameAttr).getS();
            user.lastName = item.get(followeeLastNameAttr).getS();
            user.urlPicture = item.get(followeeProfilePicAttr).getS();
        }
        return user;
    }


}
