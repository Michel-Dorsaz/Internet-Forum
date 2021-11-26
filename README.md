# Internet-Forum

## General information
Internet-Forum is a student project for the class of server programming using Spring boot and thymealeaf.
The project idea was free so I decided to implement a web-forum. 
Although my project could be used for any kind of web-forum, I decided to do a forum for helping exchange student in Finland (as per myself).

### Project overview

#### Project goal
The goal of this project is to provide a web-forum for exhange student coming to Finland. The idea is that anyone can see the "Topics" (A.K.A. a subject) and anyone can see the responses of any topic.

However the creation of topics and responses are restricted to logged users only.

##### Restriction rules
- **Object / Anyone / Logged user / Admin**
- Login / creation / ✔ / ✔         
- See topic / ✔ / ✔ / ✔      
- Create topic / ❌ / ✔ / ✔  
- Edit topic / ❌ / ✔ / ✔    
- Delete topic / ❌ / ❌ / ✔  
- See response / ✔ / ✔ / ✔   
- Create response / ❌ / ✔ / ✔
- Edit response / ❌ / self-only / ✔  
- Delete response / ❌ / ❌ / ✔


#### Website architectur
##### Topics
The primary page of the forum is the list of all topics ordred by the most recent. Users can see the topic title, the author, the date and the number of participants to the responses.

The creation of a topic is restricted to logged users.

##### Reponses
The second main page of the forum is the list of all the responses to a topic. The users can see the content of each responses, the author, the date and the likes/dislikes.

The creation of a response and liking/disliking is restricted to logged users.

#### Website features

##### Search algorithm
The website as a search bar that offer the possibility to filter topics and response based on text fitlers.
The Boyer-Moore-Horspool algorithm is used to find the relevant posts to be displayed to the user.

##### Web security
The website prevents malicious usage of the endpoints by checking authorisations on server-side before most requests needing credentials.
In addition, the text inputs are validated for malicious scripts injection and only safe texts are displayed.
