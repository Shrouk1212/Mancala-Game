# Mancala Game

## Description
Mancala or Kalaha is a game in the mancala family invented in the United States by

### Rules :
1-At the beginning of the game, four seeds are placed in each pit. This is the traditional method.
2-Each player controls the six pits and their stones on the player’s side of the board. The player’s score is the number of seeds in the store to their right (treasury).
3-Players take turns moving their stones. On a turn, the player removes all stones from one of the pits under their control. Moving counter-clockwise,
 the player drops one stone in each pit in turn, including the player’s own treasury but not their opponent’s.
4-If the last moved stone lands in an empty pit owned by the player, and the opposite pit contains seeds, both the last seed and the opposite seeds are captured and placed into the player’s treasury.
5-If the last moving stone lands in the player’s treasury, the player gets an additional move. 
When one player no longer has any stones in any of their pits,the game ends. The other player moves all remaining stones to their pits,and the player with the most stones in their treasurey wins.

## Technologies
Springboot
Spring Security : for security 
Spring MVC : for creating RESTful API
h2 database : to presist the Game information
spring data JPA : to presist the Game information
Swagger : Swagger-UI, for API documentation
thymeleaf : for the Ui
Actuator : Monitoring the app, gathering metrics, understanding traffic
devtools :  to trigger a browser refresh when a resource is changed

## Architecture
The solution uses Microservices architecture.
The implementation consists of one microservice implemented in Java using Spring Boot :
mancala :
1- getStart endpoint : To create a new Game
2- getMovement endpoint : to make movements of the stones.
You can find several Tests created 

## How to make it work:
Just download and run the project. 
use the following credentials
Username: user
Password: 123