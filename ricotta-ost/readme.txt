use maven appengine plug-in instead (https://cloud.google.com/appengine/docs/java/tools/maven#development_server_goals).

To deploy to GAE server use command 

>>mvn appengine:update -P<profile>
 
ex : deploy to test environment
>> mvn appengine:update -Ptest


To run development local environment 
>> mvn appengine:devserver -U 