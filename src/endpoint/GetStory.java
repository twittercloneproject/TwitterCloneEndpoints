package endpoint;

import dummydata.Model;
import request.StoryRequest;
import result.StoryResult;

public class GetStory {
    public StoryResult getStory(StoryRequest request) {
        StoryResult result = new StoryResult();
        Model model = new Model();
        result.story = model.getStory(request.alias);
        return result;
    }
}
