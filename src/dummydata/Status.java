package dummydata;


import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.List;

public class Status {
    public String name;
    public String message;
    public List<String> hashtags;
    public String alias;
    public String date;
    public String urlPicture;
    public Integer id;
    public String profilePic;
}
