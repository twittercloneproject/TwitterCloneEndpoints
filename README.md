# TwitterCloneEndpoints
Backend code for my TwitterCone project.
There are 13 different endpoints, each one is connected to a lambda function on aws. These provide different functionality for the program 
such as registering user, geting statuses or sending a status.
There are also two additional lambda functions that are triggered by a aws simple queue service in order to allow for scalibilty when sending 
a status that has thousands of followers.
As a database, aws dynamodb was used and the code for interacting with it is in the dao package
