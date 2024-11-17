# tasks-chalenge-api

- Technical task management and control challenge

# Running Project if your build

`DB_HOST`: Where database is running, example: `localhost:3306`
`DB_USER`: User to connect database
`DB_PASSWORD`: User password
`DB_NAME`: Name of database to connect

```
java -Dspring.profiles.active=prod -DDB_HOST=localhost:3306 -DDB_USER=root -DDB_PASSWORD=root -DDB_NAME=tasks_db -DCORS_ORIGINS=* -jar [java file name]
```

# Running in devmode

- Based on your IDE, check how you run a java spring boot project
