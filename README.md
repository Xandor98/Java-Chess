# Java Chess Server

- [Java Chess Server](#java-chess-server)
  - [1. What is this Project about](#1-what-is-this-project-about)
  - [2. How do I start the Server](#2-how-do-i-start-the-server)
    - [2.1 With the Source](#21-with-the-source)
    - [2.2 With .jar](#22-with-jar)
  - [3. Configuration](#3-configuration)


## 1. What is this Project about

The goal of this project is to provide a Server that can be used to play Chess against other people. This is only the base Server Implementation and has no possibilities to play a local round of Chess. (Maybe later)

## 2. How do I start the Server

### 2.1 With the Source

If you have the Source you can simply run it with Maven. When no speciffic Configuration ios needed, you can start it with the following command.
```bash
mvn -B exec:java
```
If you have VS Code and Open the Folder I prepared some Task for you just use `CTRL+Shift+B` and select `mvn:run`

When you want to use a specified Configuration for the Server use following command.
```bash
mvn -B exec:java -Dexec.args="-c <ABSOLUTE_PATH_TO_CONFIG>"
```
For this there is no Task in VS CODE. How to create a Configuration you can read [here](#3-configuration).

### 2.2 With .jar 

To run the JAR just enter following Command line:
```bash
java -jar JavaChess.jar [<Options>]
```
Following Options are available: 
- \-h -> to show the Help
- \-c -> to load a own Config 

How to create a specified Configuration can be read [here](#3-configuration).

## 3. Configuration

To use a Specified Configuration the Server needs to get an `.property` file. How to use it in specific u can read it in the [wiki](https://de.wikipedia.org/wiki/Java-Properties-Datei). 

In this case there are Following Options:
| Option | Description| Default |
| :--------| :---------- | :-------: |
| PORT | This is the Port for **no SSL** connections. | 8910 |
| SSLPORT | This is the Port for **SSL** connections. This port only plays a role when **SSL_CERT_STORE** and **SSL_CERT_PASSWD** is set. | 8911 |
| SSL_CERT_STORE | This is the Path to the SSL Certificate. If Set the Server uses the **SSLPORT** as valid connection. | |
| SSL_CERT_STORE_PASSWD | This is the Password for the SSL Certificate. If Set the Server uses the **SSLPORT** as valid connection.| |
| CONSOLE_START | Currently not Used | |
| TIMEOUT_TIME | The time, in seconds, a Player/KI has to make a Move. | 10 |


