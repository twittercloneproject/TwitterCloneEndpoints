package dummydata;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String firstName;
    private String lastName;
    private Alias alias;
    private Feed feed;
    private Story story;
    private List<Person> followers;
    private List<Person> followings;
    private int imageID;

    public Person(String firstName, String lastName, Alias alias, int imageID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.imageID = imageID;
        this.feed = new Feed();
        this.story = new Story();
        this.followings = new ArrayList<Person>();
        this.followers = new ArrayList<Person>();
    }

    public void addFollower(Person u) {
        followers.add(u);
    }

    public void addFollowing(Person u) {
        followings.add(u);
    }

    public void removeFollower(Person u) {
        followers.remove(u);
    }

    public void removeFollowing(Person u) {
        followings.remove(u);
    }

    public List<Person> getFollowers() {
        return followers;
    }

    public List<Person> getFollowings() {
        return followings;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Alias getAlias() {
        return alias;
    }

    public void setFollowings(List<Person> followings) {
        this.followings = followings;
    }

    public void setFollowers(List<Person> followers) {
        this.followers = followers;
    }

    public Feed getFeed() {
        return feed;
    }

    public Story getStory() {
        return story;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
