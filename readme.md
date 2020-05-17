### Restaurant Ordering System (ROSS)
Enterprise application for optymyzing backend operations for a private restaurant, such as placing orders, managing stocks, communication, tracking employees activity.
The application improves the productivity of the restaurant by automating order flows and administrative tasks, which will increase reveneu and customer satisfaction.

**ross-web** web application designed for administering the restaurant's database, where adminstrators can manage employees, customers, products and orders. Additionally it exposes 3 Rest endpoints for interacting with the mobile application:
- /login, accepts 2 form parameters "name" and "password" representing the employee username and password. Authentificates the employee in the system and produces an active session.
- /data/{table}, accepts a table name and an active session id. Produces a csv message containing on the first line the table columns and on the following lines the table rows.
- /order, accepts a json payload and an active session id. Inserts the order in the database and produces 200.
- /order/status accepts a json payload and an active session id. Produces a json message containing the status of the requested orders.
- /order/status accepts a json payload and an active session id. Updates an order status in the database and produces 200.

**ross-mobile** android application designed for placing orders and uploading them to the Restaurant's database. The employees can select a table, add food items from the menu in a new order, view order summary and send the order right away to the backend to be processed. When an order is ready, the employee receives a push notification on his device.

### Server instalation
- install any Java application server (e.g. Tomcat 8.5+, Glassfish 5+, Wildfly 12+, Weblogic 10+, etc).
- deploy **ross-web.war** from "restaurant-management/ross-web/target" into your server's deployment directory or domain.
- go to http://localhost:8080/ross-web and login using adminstrator "rusuandrei" and "rusua".
- optionally, setup your relational database (e.g. MySql, Oracle, PostgreSQL).
    - update hibernate configuration (e.g. JDBC connection). 
    - run "z00_create.sql"" to create database schema.

### Android instalation
- copy **ross-mobile.apk** from "restaurant-management/ross-mobile/bin" into your device internal memory or sdcard.
- tap the file to install the application (requires Android 4.4 or greater).
- open the application and login using employee "ursubogdan" and "ursub".

### Usage
Login in the web application using "rusuandrei" and "rusua".  
Click on the "Employees" tab, on the left side you have some filters and the employee list and on the right side the selected employee description. Click on a field to change it, such as "Work experience", and press ok.  
Click on the "Contacts" tab to manage the tables in restaurant, select a table from the left list, then edit the name, priority or number of seats available.  
Click on the "Products" tab to manage your menu. On the left side you can filter items by category, then click on a item to edit its fields.
Click on the "Orders" tab to view orders. You can filter the list to see only orders in a specific status, such as "New", or to see orders from previous days, or to see orders created by an employee. Click on an order from the list to view its details, such as items, quantities, prices.  

### Usage
Login in the android application using "ursubogdan" and "ursub".
After login you will receive an authentification key, which is used to download the menu data from the server, and also to upload orders. This key ensures that only authentificated employees can use the application.  
In the home page you will see the following buttons:
- "New", select a table and create a new order.
- "Drafts", list all orders created in the current session, that have not been sent to the server. 
- "Sent", list all orders created in the current session, that have been sent to the server.
- "Ready", list all orders created in the current session, that have been sent to the server and also prepared for delivery.
- "Delete", clear all orders from the device, but does not affect orders on the server.
- "Logout", terminates the current session.  
Click "New" to start an order, select a table, click on "Category" to filter items, click on "Search" to quickly find an item, or click on list headers to change the sorting order. To add an item to the order simply tap on it.  
Swipe right to view the order summary, long tap on an item to remove it from the order, or single tap to adjust quantity. When finished tap "Cancel" to discard the order, "Pend" to save it as draft, or "Send" to save it on the server.  
To view your order status, go to the home page and press "Ready", then press "Refresh". If your order appears in the list that means is ready for delivery.  
After delivery go to the home page and press "Ready", select and order and press "Close".   

### External APIs
- Google Cloud Messaging, enables push notifications on mobile devices.

### Libraries
- Android SDK 19
- Android Support v4
- ORMLite Framework 4.48
- JavaServer Faces 2.0
- JavaServer Pages Standard Tag Library 1.2
- PrimeFaces 4.0
- Hibernate 4.1
- Jersey 1.18
- Apache Commons 1.8
- Jackson 1.9
- H2 1.4, in memory database