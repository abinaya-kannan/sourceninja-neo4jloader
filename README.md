# sourceninja-neo4jloader

This code base has two main functionalities.

1.  Load method call logs from Source Ninja to Neo4j. We handle most of the corner cases except when new threads are created.
We track those threads not as parallel calls but sequential. We expect to fix this in future releases. 

2.  Index java files from github or from local file system and load to MySQL tables. 

Pre-requisite: Neo4j and MySQL installed. 
