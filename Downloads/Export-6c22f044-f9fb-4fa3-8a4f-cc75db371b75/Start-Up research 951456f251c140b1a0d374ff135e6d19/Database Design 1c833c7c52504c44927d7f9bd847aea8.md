# Database Design

Since there are several different types of data, including user data, images, image metadata, followers information, to be stored, we need a scalable database schema that allows fast scanning and retrieval of information. Since the types of data in this application are inherently relational, for example images have a relationship with User IDs (each image belongs to an owner — who uploaded it), we can use a relational database to store information.

The most straightforward approach is to store data in the form of tables in a relational database such as MySQL. 

![Databasedesign.png](Database%20Design%201c833c7c52504c44927d7f9bd847aea8/Databasedesign.png)

**User Table**

For the user table, our primary key will be the UserID, which can increment serially with the creation of new profiles. Against each UserID, we’ll have a table that holds the complete information for that particular user, including name, email, location, time zone etc.

**Photo Table**

The photo table will carry the PhotoID as the primary key which can increment serially with each new photo that uploads on the system. There can be a foreign key that holds the UserID to reference the user who uploaded the picture. This foreign key also represents the relationship between the photo table and the user table. Users will have a one-to-many relationship with the photos because each user can upload multiple photos but every photo will belong to a single user.

Other than the UserID, the photo table may also carry additional information like caption, location from where the photo was uploaded, date and time it was uploaded and more. Besides all the metadata related to the photo, we also need to store the actual image somewhere.

Owing to the large size of the images, it’s not recommended to store the images in the database itself. We will store the path for the image in the photo table which points to its actual storage location. Any distributed file storage, such as GFS or HDFS may be used to store images.

**Follower Table**

The Follower table contains the UserIDs of the follower (let’s say, A) and the followee (let’s say, B). Both are foreign keys that refer to the UserID from the user table. The follower table stores a single direction of user following. From the information in the above diagram, we can interpret that A follows B, where B may or may not follow A (note that this is different from the ‘Friends’ on Facebook, which is a bidirectional relationship).

The user table will have a many-to-many relationship with the follower table, as opposed to the photo table, since a user can follow multiple people and a followee can have multiple users following him/her.

# **Extending The Database Design To NoSQL**

The same data can also be stored in a scalable, high-performance NoSQL database such as Cassandra to achieve high availability and minimize latency. In a key-value store such as this one, we can maintain the photo table with the PhotoID as the ‘key’ and an object containing all the metadata as the ‘value’. The user table will be maintained in a similar way.

 

![NoSQL.png](Database%20Design%201c833c7c52504c44927d7f9bd847aea8/NoSQL.png)

### Photo Sharing Architecture

There are multiple features that our app will handle, including uploading images, viewing images and News Feeds and following other users. When designing an app to serve millions of users and handle several features, the best option is to split them into microservices, operating independently of one another to optimize server usage and app performance.

Also keep in mind that there are going to be much more requests for viewing News Feeds than the requests for uploading images. 

To accommodate for the high number of reads, we will want to replicate our database across multiple servers to retrieve data efficiently and display it to the user in the smallest possible time.

Replicating the database (and cache) also creates redundancy to eliminate any possibility of data loss. When multiple copies of each information is stored on multiple storage servers, it’s possible to retrieve the file from a different server if one storage server is not available, thus saving the service from returning an error to the client.

![Architectural design.png](Database%20Design%201c833c7c52504c44927d7f9bd847aea8/Architectural_design.png)

## **Separate Reads And Writes**

Uploading photos is a slow process and if the same server was to serve both read and write requests, it’s possible that uploads will consume more resources and the users who want to view photos will often find the system busy.

Also, since read and write requests may have different requirements, it makes sense to have different servers handle them independently. All read requests will be directed to the Read Server, while all write requests will go to the Write Server, as you can see in the diagram.

The Write Server is not only responsible for storing the metadata for the fresh image into the database and cache, but also for uploading the image to the external storage, such as Hadoop Distributed File System (HDFS). The image metadata will carry the path from where to retrieve the image when there’s a request for it.

**Load Balancer**

Separate operations are handled on different servers and there will yet be multiple servers to handle each operation. All the read requests, for example, cannot be handled on a single server when running applications at the scale of millions of users. There will be several servers to handle read and write requests to scale the system horizontally and minimize latency.

Introducing a load balancer is essential since it will redirect the request of the client to the server that’s available and has the resources to handle the type of request made by the user. So the client’s request will first hit the load balancer and from there, it will be redirected to a suitable server to handle the request.

## **Cache For Faster Reads**

For ‘read’ applications, we can store cache for frequently used data in an in-memory data store, such as Redis, which the server can query for the requests instead of scanning the entire database. So every new ‘write’ needs to be stored to the database as well as the cache so that it is quickly available to any relevant read request and is consistent with the information in the database.

Each ‘read’ request will be queried at Redis by the Read Server and the metadata from the cache for that particular image is returned to the client by the server. This metadata information will also carry the path/link for the image so the client can directly download and view it on his/her device from that link.

## **News Feed Generation**

An important component of Photo sharing design is the generation of News Feed. 

Since it’s an independent microservice, there can be a dedicated server (or servers) for managing News Feed service. which will be named (News Feed Server).

A user’s News Feed carries a list of all the latest and high ranking photos from the users they follow. For this, the service needs to pick the UserFollow table from the cache to get the list of all the people that the user follows. Using the UserIDs of the followees, the News Feed Server will pull the metadata information for all the latest photos from the followees. The photos from all the followees are ranked based on certain attributes such as their freshness, likes, and comments before displaying a selected number of top photos to the client. (The Use of proper Algorithms is important).

![cacheStorage.png](Database%20Design%201c833c7c52504c44927d7f9bd847aea8/cacheStorage.png)

Since there are multiple steps involved before the News Feed Service can actually display the News Feed to the users, we can expect a high latency with the above approach. Yet, whenever a user logs into his/her account, his/her News Feed is there for them within milliseconds. 

Note that all feeds includes pictures , customized videos from travels etc.. 

The News Feed Service counters this latency by generating the News Feeds for the user in advance and storing it in either the same cache that stores metadata or a dedicated News Feed cache. We can build the system to update the pre-generated News Feed on an hourly basis or every few minutes, depending on what’s required. Each time the user makes a request to display their News Feed, the News Feed Server simply queries the News Feed Cache to fetch the pre-prepared feed for the user and display it on their homepage.

# **Conclusion**

This is how to design a simple version of Photo Sharing features to be implemented into our travel system. We have covered the basics of  photo-sharing app, including uploading, viewing/downloading photos and creating News Feeds . 

Based on the system to be designed, an extended and revised edition of this Interim is required.

Adding to the same design, we need append some additional microservices, 

including one that handles comments and likes of photos and another one that caters personal messaging between users. We  also need to build a service that creates Instagram-like stories, a service that stores heavier files such as Videos , a service that stores histories of user's view videos(short stories should be included or not?). and a  lot more services to be added in the future.