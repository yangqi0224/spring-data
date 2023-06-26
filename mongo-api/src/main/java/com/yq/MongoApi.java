package com.yq;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MongoApi {
    public static void main(String[] args) {
        que();
    }

    public static void que(){
        /*
        MongoClientSettings build = MongoClientSettings.builder()
                .readPreference(ReadPreference.secondaryPreferred())
                .applyConnectionString(new ConnectionString("mongodb://120.26.166.153:11817"))
                .build();
        MongoClient mongoClient = MongoClients.create(build);

         */
        MongoClientOptions build = MongoClientOptions.builder().readPreference(ReadPreference.secondaryPreferred()).build();
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient("120.26.166.153:11817", build);

        //MongoClient mongoClient = MongoClients.create();
        MongoDatabase my_db = mongoClient.getDatabase("my_db");
        MongoCollection<Document> cl = my_db.getCollection("comployee");
        //cl.withReadPreference(ReadPreference.secondaryPreferred());

        BasicDBObject bson = new BasicDBObject();
        BasicDBObject bson1 = new BasicDBObject();
        BasicDBObject bson2 = new BasicDBObject();
        bson.put("$eq",1000);

        // 错误日志：{ "$query": { "find": "ours_mq_log", "filter": { "currentMQState": "send4Success" } }, "$readPreference": { "mode": "secondaryPreferred" } }
        bson1.put("customerId","2");
        List<Bson> list = new ArrayList<>();
        list.add(bson);
        list.add(bson1);
        Bson and = Filters.and(list);
        bson2.put("$query",bson);
        int[] li = new int[3];
        li[0] = 1000;
        li[1] = 2000;
        li[2] = 3000;


        FindIterable<Document> documents = cl.find(Filters.elemMatch("object",Filters.eq("var1","value11"))).skip(3).limit(1);
        //documents.skip(1);
        //documents.filter(bson);
        //documents.projection(Projections.elemMatch("price",bson));
        MongoCursor<Document> iterator = documents.iterator();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            System.out.println(next.toJson());
        }
        iterator.close();

    }
}
