package dummydata;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.Base64;
import dao.*;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {
    private static String BUCKET_NAME = "ddrowleytwittermedia";
    private UserDao userDao;
    private StatusDao statusDao;
    private FollowsDao followsDao;
    private HashtagDao hashtagDao;
    private FeedDao feedDao;
    private AuthDao authDao;


    public Model() {
        userDao = new UserDao();
        statusDao = new StatusDao();
        followsDao = new FollowsDao();
        hashtagDao = new HashtagDao();
        feedDao = new FeedDao();
        authDao = new AuthDao();
    }

    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    private static String uploadMediaToS3(String media) {
        if(media ==  null) {
            return null;
        }
        AmazonS3 client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_WEST_2)
                .build();
        byte[] imageBytes = Base64.decode(media);
        InputStream is = new ByteArrayInputStream(imageBytes);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageBytes.length);
        metadata.setContentType("image/png");

        client.putObject(BUCKET_NAME, "key", is, metadata);
        client.setObjectAcl(BUCKET_NAME, "key", CannedAccessControlList.PublicReadWrite);
        return client.getUrl(BUCKET_NAME, "key").toString();
    }

    public boolean signIn(String username, String password, String token) {
        if(authDao.checkPassword(username, hashPassword(password))) {
            authDao.update(username, token);
            return true;
        }
        return false;
    }

    public void updateUser(String username, String profilePic) {
        userDao.update(username, profilePic);
    }

    public void addUser(String alias, String firstName, String lastName, String profilePic, String token, String password) throws DataAccessException {
        String pic = profilePic;
        userDao.insertUser(alias, firstName, lastName, pic);
        authDao.insertUser(alias, token, hashPassword(password));
    }

    public User getUser(String alias) {
        User user = this.userDao.getUser(alias);
        return user;
    }

    public Status getStatus(String username, String time) {
        Status status = this.statusDao.getStatus(username, time);
        return status;
    }

    public List<User> getFollowing(String alias, String userKey) {
        return followsDao.getFollowing(alias, userKey);
    }

    public List<User> getFollower(String alias, String userKey) {
        return followsDao.getFollowers(alias, userKey);
    }

    public List<Status> getFeed(String alias, String dateKey) {
        return feedDao.getFeed(alias, dateKey);
    }

    public List<Status> getStory(String alias, String dateKey) {
        return this.statusDao.getStory(alias, dateKey);
    }

    public void follow(String followerUsername, String followeeUsername, String authToken) throws DataAccessException {
        authDao.checkAuthToken(followerUsername, authToken);
        User followerUser = userDao.getUser(followerUsername);
        User followeeUser = userDao.getUser(followeeUsername);
        followsDao.follow(followerUser.alias, followerUser.firstName, followerUser.lastName, followerUser.urlPicture,
                followeeUser.alias, followeeUser.firstName, followeeUser.lastName, followeeUser.urlPicture);
    }

    public void unfollow(String followerUsername, String followeeUsername, String authToken) {
        authDao.checkAuthToken(followerUsername, authToken);
        followsDao.unFollow(followerUsername, followeeUsername);
    }

    public boolean isFollowing(String followerUsername, String followeeUsername) {
        return followsDao.isFollowing(followerUsername, followeeUsername);
    }

    public List<Status> getHashtags(String hashtag, String dateKey) {
        return this.hashtagDao.getHashtags(hashtag, dateKey);
    }

    public void sendStatus(String username, String name, String date, String message, List<String> hashtags,
                           String profilePic, String attachment, String authToken) throws DataAccessException {
        authDao.checkAuthToken(username, authToken);

        statusDao.insertStatus(username, date, message, name, profilePic, attachment, hashtags);
        for(int i = 0; i < hashtags.size(); i++) {
            hashtagDao.insertStatus(hashtags.get(i), username, date, name, message, hashtags, profilePic, attachment);
        }
    }

    public void feedBatchWrite(List<String> followersUsername, String username, String name, String date,
                               String message, List<String> hashtags, String profilePic, String attachment) {
        try {
            feedDao.insertStatus(username, date, message, name, profilePic, attachment, listToString(hashtags), followersUsername);
        }
        catch (DataAccessException e) {
            System.out.println("Data Access Exception Model");
            System.out.println(e);
        }
    }

    public List<String> stringToList(String value) {
        List<String> nList = new ArrayList<String>(Arrays.asList(value.split(",")));
        return nList;
    }

    public List<String> getAllFollowers(String username) {
        List<User> fs = followsDao.getFollowers(username, "");
        List<User> followers = new ArrayList<User>(fs);
        while(fs.size() == 25) {
            fs = followsDao.getFollowers(username, fs.get(fs.size()-1).alias);
            followers.addAll(fs);
        }
        System.out.println("get all followers");
        System.out.println(followers.size());
        List<String> usernames = new ArrayList<String>();
        for(int i = 0; i < followers.size(); i++) {
            usernames.add(followers.get(i).alias);
        }
        return usernames;
    }

    public String listToString(List<String> values) {
        if(values.size() == 0) {
            return "none";
        }
        StringBuilder sb = new StringBuilder();
        String delim = ",";
        int i;
        for(i = 0; i < values.size()-1; i++) {
            sb.append(values.get(i));
            sb.append(delim);
        }
        sb.append(values.get(i));
        return sb.toString();
    }

    public void createThousandUsersFollowers() {
        List<String> usernames = new ArrayList<>();
        List<String> firstNames = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();
        for(int i = 0; i < 500; i++) {
            usernames.add("@" + getRandomString(5));
            firstNames.add(getRandomString(4));
            lastNames.add(getRandomString(4));
        }
        userDao.createLotUsers(usernames, firstNames, lastNames);
        User user = userDao.getUser("@test1");
        followsDao.batchFollow(usernames, firstNames, lastNames, user.alias, user.firstName, user.lastName, "a");
        usernames.clear();
        firstNames.clear();
        lastNames.clear();
        for(int i = 0; i < 500; i++) {
            usernames.add("@" + getRandomString(8));
            firstNames.add(getRandomString(4));
            lastNames.add(getRandomString(4));
        }
        userDao.createLotUsers(usernames, firstNames, lastNames);
        followsDao.batchFollow(usernames, firstNames, lastNames, user.alias, user.firstName, user.lastName, "a");

    }

    public String getRandomString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }


}
