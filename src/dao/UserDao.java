package dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import dummydata.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserDao {
    private static final String TableName = "users";
    private static final String usernameAttr = "username";
    private static final String firstNameAttr = "first_name";
    private static final String lastNameAttr = "last_name";
    private static final String profilePicAttr = "profile_pic";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    public void update(String username, String profilePic) {
        Table table = dynamoDB.getTable(TableName);
        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#user", profilePicAttr);

        Map<String, Object> attrValues = new HashMap<String, Object>();
        attrValues.put(":us", profilePic);

        table.updateItem(usernameAttr, username,
                "set #user = :us", attrNames, attrValues);
    }

    public void insertUser(String username, String firstName, String lastName, String profilePic) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);
        try {
            Item item = new Item()
                    .withPrimaryKey(usernameAttr, username)
                    .withString(firstNameAttr, firstName)
                    .withString(lastNameAttr, lastName)
                    .withString(profilePicAttr, profilePic);


            table.putItem(item);
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public void deleteUser(String username) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(usernameAttr, username);
    }

    public User getUser(String username) {
        Table table = dynamoDB.getTable(TableName);
        Item item = table.getItem(usernameAttr, username);
        User user = new User();
        user.alias = item.get(usernameAttr).toString();
        user.firstName = item.get(firstNameAttr).toString();
        user.lastName = item.get(lastNameAttr).toString();
        user.urlPicture = item.get(profilePicAttr).toString();
        return user;
    }

    public void createLotUsers(List<String> usernames, List<String> firstNames,List<String> lastNames) {
        List<Item> newItems = new ArrayList<Item>();
        int length = usernames.size();
        System.out.println(length);
        for(int i = 0; i < length; i++) {
            Item item = new Item()
                    .withPrimaryKey(usernameAttr, usernames.get(i))
                    .withString(firstNameAttr, firstNames.get(i))
                    .withString(lastNameAttr, lastNames.get(i))
                    .withString(profilePicAttr, "a");
            newItems.add(item);
            if(newItems.size() == 25) {
                TableWriteItems tableWriteItems = new TableWriteItems(TableName)
                        .withItemsToPut(newItems);
                BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);
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



}
