package request;

import java.util.List;

public class SendStatusRequest {
    public String authToken;
    public String name;
    public String alias;
    public String message;
    public String date;
    public List<String> hashtags;
    public String urlPicture;
    public String profilePic;
}
