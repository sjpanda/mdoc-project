* Developers : Abdoul Diallo <mrdiallo.abdoul@gmail.com> , Jing Shu <js.shujing@gmail.com>

* Frameworks/JRE/IDE/Server used with their versions : 

- JRE 1.7
- Hibernate 3
- Spring 3.0.5
- Ehcache core 2.8.0
- JSF 2.0
- Eclipse JEE Juno
- Tomcat 6

* Plugins for eclipse : 

- JBoss Hibernate Tools
- Spring IDE


* Steps to take to install the application : 

1. Make sure that you have all the frameworks/JRE/IDE/plugins above installed

2. Import our project (.zip) into your eclipse (You can also import the project from the git repository : https://github.com/sjpanda/m.git)

3. Add the libraries JSF 2.1 (Mojarra) to the project : 
	right click on the project -> Properties -> Unwrap "Project Facets" -> 
	Java server faces -> Download library -> Choose JSF 2.1 (Mojarra) -> install it by following its steps

4. Run the project on tomcat server 6

5. Open DOS and go to the folder <the project>\WebContent\WEB-INF\lib

6. Enter in dos "java -jar hibernate-jconsole-1.0.7.jar" to launch the hibernate monitor, 

   then "Connection" -> "New Connection" -> Select "org.apache.catalina.startup.Bootstrap start" in "Local Process"(1) -> "Connect" -> "Insecure" -> "Hibernate Monitor"

7. Return to eclipse, enter a name and a password identical (eg. bob, bob) to sign in

8. Click on the button "Generate contact" to create a testing database

9. To create a contact, go to "Contact -> Create" (the "Siret number" should be a number)

   To update/delete a contact or to add a book to a contact, go to "Contact -> Update"

   To list your contact and their books, go to "Contact -> List contact"

   To search a contact, go to "Contact -> Search"



(1) If nothing in "Local Process" : 

Return to eclipse, stop tomcat server, 

Right click on the project -> Run as -> Run Configurations -> Arguments, add the following options (without comma) at the end of "VM arguments" (9011 is a port not used on your machine): 
" -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=9011 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false ",

Relaunch the project on tomcat server

Relaunch the hibernate monitor -> "Connection" -> "New Connection" -> Enter "localhost:9011" in "Remote Process" -> "Connect" -> "Insecure" -> "Hibernate Monitor"

Return to the Step 7 above