# How to build

1- Install IDE: Eclipse or Visual Studio Code

2- In case you chose Visual Studio Code, follow this guide to [get started with Java in VS Code](https://code.visualstudio.com/docs/java/java-tutorial)

3- Install and configure MySQL: 
- Make sure to use the "root" user for the rest of the building process
- Make sure to include MySQL Java Connector during the installation process or download it independently from [this link](https://dev.mysql.com/downloads/connector/j/)
- [From this link](https://dev.mysql.com/downloads/connector/j/), download the connector mysql-connector-java-<version>.jar file (Can be found under "Platform Independent" option) and put it in ./mySQL folder 
- Include the mysql-connector-java-<version>.jar file into the classpath of Eclipse or Visual Studio Code
- Open & connect to MySQL service from the workbench and import ./mySQL/casino_app.sql and run the portion of the code above the ### line to build the database schema

4- Create a file called 'casino.password' in the root directory of the project (same level as .gitignore) and write in it the password to your "root" user of MySQL

5- Compile the code and enjoy the game!
