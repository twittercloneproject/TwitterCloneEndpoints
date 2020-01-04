package sqs;

import java.util.List;

public interface Scale {
    public void sendMessages(String url, List<String> followersUsername, String username, String name, String date,
                            String message, String hashtags, String profilePic, String attachment);

    public void sendStatus(String url, String username, String name, String date,
                            String message, List<String> hashtags, String profilePic, String attachment);
}
