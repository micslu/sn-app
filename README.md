# Building and running
To build and run the application requires [Maven](https://maven.apache.org/) installed.

Use the below command to start the application. By default it will be started at localhost:8080.
```
mvn spring-boot:run
```

# API
Below you can find the API documentation.

Showing all posts created by a user:
```
GET /api/users/:user_id/posts
```
Creating a new post (and user if not created yet):
```
POST /api/users/:user_id/posts?text=:post_text
```
Showing all posts created by people a user follows:
```
GET /api/users/:user_id/follow
```
Adding a new user to the list of followed users:
```
POST /api/users/:user_id/follow?userToFollowId=:user_to_follow_id
```
