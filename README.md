# Bully Algorithms 
[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)



A Java Socket application for Bully Algorithm for dynamically electing a coordinator or leader from a group of distributed computer processes. The process with the highest process ID number from amongst the non-failed processes is selected as the coordinator.


# Contents

- [Running the Project]()
- [Features ](#features)
- [Built With]()
	  

# Running the project

```
git clone 
```
- Open Java IDE (Eclipse is recommended)
- File => Open Project from folder directory => specify the directory => Finish 
- Click on the Project then Run as Java Application




# Features

- Socket use Shared Memory for Process communication 
- Queue for the processes and each one recieve the message from queue
- Processes act as a server and client
- Process has 2 threads for listening to requests and message others 
- UI is updated by connecting to main 
- If a process didn't get any request from Coordinator in LAUNCH seconds, it will begin an election
- You can kill the Coordinator and its server will be terminated    
- Program will be closed when all process has been killed 



## Built With

* [JDK 17]()
* [Java Socket]()
* [JPanel]()
  

## Author

 **Abdelrahman Magdy Ibrahim**

