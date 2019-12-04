package dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.HashMap;
import java.util.Map;

public class AuthDao {
    private static final String TableName = "auth";
    private static final String usernameAttr = "username";
    private static final String authTokenAttr = "auth_token";
    private static final String passwordAttr = "password";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    public void insertUser(String username, String auhToken, String hashPassword) throws DataAccessException {
        Table table = dynamoDB.getTable(TableName);
        try {
            Item item = new Item()
                    .withPrimaryKey(usernameAttr, username)
                    .withString(authTokenAttr, auhToken)
                    .withString(passwordAttr, hashPassword);

            table.putItem(item);
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public boolean checkPassword(String username, String hashPssword) {
        Table table = dynamoDB.getTable(TableName);
        Item item = table.getItem(usernameAttr, username);
        if(item.getString(passwordAttr) == null) {
            return false;
        }
        if(item.getString(passwordAttr).equals(hashPssword)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkAuthToken(String username, String authToken) {
        Table table = dynamoDB.getTable(TableName);
        Item item = table.getItem(usernameAttr, username);
        if(item.getString(authTokenAttr) == null) {
            return false;
        }
        if(item.getString(authTokenAttr).toString().equals(authToken)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void update(String username, String token) {
        Table table = dynamoDB.getTable(TableName);
        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#user", authTokenAttr);

        Map<String, Object> attrValues = new HashMap<String, Object>();
        attrValues.put(":us", token);

        table.updateItem(usernameAttr, username,
                "set #user = :us", attrNames, attrValues);
    }
}
