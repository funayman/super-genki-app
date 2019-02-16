# SuperGenki

## Super Genki Database
The database for Super Genki is based on the [JMDict Project](http://edrdg.org/jmdict/j_jmdict.html), please click on the link for more information about the project.
The actual database is built using the [super-genki-db](https://github.com/Xsixteen/super-genki-db) project.
Build the database and place `jisho-main.db` in `app/src/main/assets`.

### Buliding Without the Database
It is possible to use the app without using the external database.
Super Genki can use a SQLite in-memory database with a select few entries to test functionality.

### Rebuilding
In rare cases, the data base schema will change and the current implementation could cause issues.
Please rebuild the database from [super-genki-db](https://github.com/Xsixteen/super-genki-db) or delete `app/src/main/assets/jisho-main.db` and use the application's in-memory database.

## Installation
Clone the project and run in Android Studio.
