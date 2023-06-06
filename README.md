# change-data-capture-CDC
Change Data Capture (CDC) is a technique used in data integration and event-driven architectures to capture and propagate changes from a source database to downstream systems in real-time. It enables applications to react to and process data changes as they occur, rather than relying on periodic batch updates.

### Kafka and debezium setup procedure

**CDC with Kafka Connect and Postgres Process**

1. **Requirements**

    1.     Apache Kafka
    2.     Postgres
    3.     Kafka Connect Libraries
2. Installation Process
    1. Copy Kafka connects **`kafka-connect-jdbc-10.7.1.jar, postgresql-42.4.3.jar` and** paste it on Kafka lib directory
    2. Zookeeper, Kafka and distributed connect started
        1. ``` sudo sh bin/zookeeper-server-start.sh config/zookeeper.properties ```
        2. `````` sudo sh bin/kafka-server-start.sh config/server.properties ``````
        3. ```` sudo bin/connect-distributed.sh  config/connect-distributed.properties````
    3. Create Kafka topic where dB events will publish
        1. ```` sudo bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --topic topic-name --partitions 3 --replication-factor 1 ````
        2.  ```` sudo bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic debezium_topic --from-beginning ````(console consumer for topic data) 
    4. Source Connector configuration
        1. Url:  ````POST localhost:8083/connectors/ ````
        2. Request body:
````
{

"name": "source-postgress-jdbc-connector-bulk",

"config": {

"connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",

"tasks.max": 1,

"connection.url": "jdbc:postgresql://localhost:5432/source_db?currentSchema=source?user=user&password=password",

"connection.user": "user",

"connection.password": "password",

"query": "select * from source.customers",

"mode": "bulk",

// "incrementing.column.name": "id",

"topic.prefix": "debezium_topic",

"validate.non.null": **false**

}

}
````

1. Sink Connector configuration
    1. URL: **`POST localhost:8083/connectors/`**
    2. Request Body:

````
{

"name": "postgres-sink-connector-local",

"config": {

"connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",

"tasks.max": "1",

"topics": "debezium_topic",

"connection.url": "jdbc:postgresql://localhost:5432/postgres",

"connection.user": "postgres",

"connection.password": "password",

"auto.create": "true",

"auto.evolve": "true",

"insert.mode": "upsert",

"pk.mode": "record_value",

"pk.fields": "id",

"column.mapping": "name:name, age:age, email:email"

}

}
````