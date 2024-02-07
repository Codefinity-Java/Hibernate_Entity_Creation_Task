# Hibernate_Entity_Creation_Task

This task will consist of 2 parts.

## Setting up the connection:

Your task is to **create the necessary database in MySQL** using regular SQL queries. I will provide you with the codes for the table that we will **use and develop** in this course; you just need to insert these codes into your table.

Then, you need to **connect your database to the project**. I've left a template for you in `hibernate.cfg.xml`; you just need to insert the **link to your database** (I recommend using the name `testDatabase`, as this database will be used to **test your solution**. When we have a finished project, we will create a new database).

You also need to fill in the `username` in MySQL (most likely, it will be `root`) as well as your `password`.

*Don't worry, I won't see your password :).*

## Creating the entity class:

Next, you need to go to the `Department` class and **make this class an entity class**. Use all the necessary **annotations** learned in previous chapters. You can also use the **Project Lombok** library, or you can manually write getters, setters, and constructors. The choice is yours.

After you complete the task, **run the integration tests I've written**, and they will verify the correctness of your task.