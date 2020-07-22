![Harold holding a cup](https://imgur.com/wovweEt.png)

# Harold bot
Hiding the pain of managing Discord servers

---
![Kotlin](https://img.shields.io/badge/Written%20in-Kotlin-0095D5?style=for-the-badge&logo=kotlin)
![Travis build status](https://img.shields.io/travis/jirkavrba/harold?style=for-the-badge)
![GitHub contributors](https://img.shields.io/github/contributors/jirkavrba/harold?style=for-the-badge&color=green)
![GitHub issues](https://img.shields.io/github/issues/jirkavrba/harold?style=for-the-badge&color=green)
![GitHub stars](https://img.shields.io/github/stars/jirkavrba/harold?style=for-the-badge&color=green)
![GitHub last commit](https://img.shields.io/github/last-commit/jirkavrba/harold?style=for-the-badge)

## Setup

1. Install 
[Java Development Kit](https://openjdk.java.net/install/),
[Apache Maven](https://maven.apache.org/install.html) and 
[MySQL Server](https://dev.mysql.com/doc/mysql-installation-excerpt/8.0/en/)
1. Clone this repository with either \
`git clone https://github.com/jirkavrba/harold.git` or \
`git clone git@github.com:jirkavrba/Harold`
1. Install dependencies and compile the code with `mvn clean package`
1. Setup your environmental variables:
    1. `DISCORD_TOKEN` - Your app's discord bot token from [Discord Developer Portal](https://discord.com/developers)
    1. `DATABASE_URL` - Your database URL (eg. `localhost:3360/dbname`):w
    1. `DATABASE_USER` - Your database user
    1. `DATABASE_PASSWORD` - Respective password for the user provided in `DATABASE_USER`
    <!-- More variables will need to be setup probably -->
1. Run the application with `mvn spring-boot:run`
1. Add the bot to your Discord servers using [this link](https://discord.com/api/oauth2/authorize?client_id=716026411554439208&permissions=8&scope=bot)
