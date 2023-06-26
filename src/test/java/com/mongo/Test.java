package com.mongo;

import com.mongo.api.pojo.Student;
import com.mongo.api.pojo.Student2;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClientFactory;
import com.mongodb.client.result.UpdateResult;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName Test
 * @Description 请描述类的业务用途
 * @Author yangqi
 * @Date 2023/4/10 12:40
 * @Version 1.0
 **/
@SpringBootTest(classes = ApplicationBoot.class)
public class Test {

    //自动注入，spring框架完成
    @Resource
    private MongoTemplate mongoTemplate;


    //String cl = "single";
    //String cl = "newCl";
    //String cl = "comployee";
    String cl = "my_db";

    @org.junit.jupiter.api.Test
    public void insert(){
        for (int i = 1;i<=3;i++){
            for (int j=1;j<=20;j++){
                Student student1 = buildStudent(j);
                System.out.println(student1.toString());
                mongoTemplate.insert(student1,cl);
            }
        }
    }

    //支持
    @org.junit.jupiter.api.Test
    public void insertAll(){
        List<Student> list = new ArrayList<>();
        list.add(buildStudent(1));
        list.add(buildStudent(1));
        list.add(buildStudent(2));
        list.add(buildStudent(3));
        list.add(buildStudent(3));
        list.add(buildStudent(3));
        list.add(buildStudent(3));
        //in
        Collection<Student> students = mongoTemplate.insertAll(list);
        students.forEach(s->{
            System.out.println(s.toString());
        });
    }


    public Student buildStudent(int i){
        Student student = new Student();
        student.setCustomerId(i);
        student.setPrice(1000*i);
        student.setCreateDate(System.currentTimeMillis());
        student.setOrgId("sfsd"+i);
        String[] strings = new String[3];
        strings[0] = "BeiJing"+i;
        strings[1] = "ShangHai"+i;
        strings[2] = "GuangZhou"+i;
        student.setSz(strings);
        BSONObject bsonObject = new BasicBSONObject();
        bsonObject.put("var1","value1"+i);
        bsonObject.put("var2","value2"+i);
        bsonObject.put("var3","value3"+i);
        student.setObject(bsonObject);
        return student;
    }


    //不支持、报错
    @org.junit.jupiter.api.Test
    public void update(){
        //findAll();
        Query customerId = new Query(Criteria.where("customerId").is(1));
        Update set = new Update().set("price", "2020");
        //不支持
        mongoTemplate.updateFirst(customerId, set,cl);
        List<Student> customerId1 = mongoTemplate.find(Query.query(Criteria.where("customerId").is(1)), Student.class, cl);
        printStudent(customerId1);
        //findAll();
    }


    //支持
    @org.junit.jupiter.api.Test
    public void updateMulti(){
        //findAll();
        Query customerId = new Query(Criteria.where("customerId").is(1));
        Update set = new Update().set("price", "1020");
        //支持
        mongoTemplate.updateMulti(customerId,set,cl);
        List<Student> customerId1 = mongoTemplate.find(Query.query(Criteria.where("customerId").is(1)), Student.class, cl);
        printStudent(customerId1);
        //findAll();
    }

    @org.junit.jupiter.api.Test
    public void save(){
        Student student = buildStudent(12);
        mongoTemplate.save(student,cl);
    }


    @org.junit.jupiter.api.Test
    public void find(){
        Query customerId = new Query(Criteria.where("customerId").is("2"));
        List<Student> all1 = mongoTemplate.findAll(Student.class,cl);
        List<Student> students = mongoTemplate.find(customerId, Student.class,cl);
        Student andRemove = mongoTemplate.findAndRemove(customerId, Student.class,cl);
        List<Student> allAndRemove = mongoTemplate.findAllAndRemove(customerId,Student.class,cl);
        List<Student> all = mongoTemplate.findAll(Student.class,cl);
        System.out.println("======findall====");
        printStudent(all1);
        System.out.println("=====find=====");
        printStudent(students);
        System.out.println("======findremove=====");
        System.out.println(andRemove.toString());
        System.out.println("=====findallremove====");
        printStudent(allAndRemove);
        System.out.println("===========");
        //printStudent(all);

    }


    @BeforeEach
    @AfterEach
    public void findAll(){
        System.out.println("===========");
        List<Student> all = mongoTemplate.findAll(Student.class,cl);
        printStudent(all);
        System.out.println("===========");
    }


    public void fin(){

    }


    @org.junit.jupiter.api.Test
    public void findAndModify(){
        Query customerId = new Query(Criteria.where("customerId").is("3"));
        Update price = new Update().set("price", 3333);
        Student andModify = mongoTemplate.findAndModify(customerId, price, Student.class,cl);
        System.out.println(andModify.toString());
    }


    @org.junit.jupiter.api.Test
    public void findAndReplace(){
        Query customerId = new Query(Criteria.where("customerId").is("1"));
        //mongoTemplate.findAndReplace(customerId,buildStudent(6),cl);
        mongoTemplate.findAndReplace(customerId,buildStudent(7), FindAndReplaceOptions.options().upsert(),cl);
        System.out.println("ok");
    }

    @org.junit.jupiter.api.Test
    public void upsert(){
        mongoTemplate.upsert(Query.query(Criteria.where("customerId").is("9")),Update.update("price",8000),cl);
    }

    @org.junit.jupiter.api.Test
    public void other(){
        /*
        System.out.println(mongoTemplate.collectionExists("my_table"));
        System.out.println(mongoTemplate.collectionExists("my_table2"));
        System.out.println(mongoTemplate.collectionExists(cl));
        System.out.println(mongoTemplate.collectionExists("my_db"));
        System.out.println(mongoTemplate.collectionExists("my_db.my_db"));
        System.out.println(mongoTemplate.collectionExists("comployee"));
        //mongoTemplate.dropCollection("comployee");
        //mongoTemplate.dropCollection(Student.class);
        //mongoTemplate.createCollection(Student.class);
        //mongoTemplate.createCollection("my_table2");
        //mongoTemplate.createCollection("my_table3");
        System.out.println(mongoTemplate.collectionExists("comployee"));
        System.out.println(mongoTemplate.collectionExists("my_db"));
        System.out.println(mongoTemplate.collectionExists("my_table3"));

         */
        mongoTemplate.createCollection("new_cl");
        System.out.println(mongoTemplate.collectionExists("new_cl"));//true
        mongoTemplate.dropCollection("new_cl");
        System.out.println(mongoTemplate.collectionExists("new_cl"));//false
    }

    @org.junit.jupiter.api.Test
    public void query(){
        //mongoTemplate.find(Query.query(Criteria.where("customerId"))
        //MongoClientFactory mongoClientFactory = new MongoClientFactory();
        System.out.println(mongoTemplate.count(Query.query(Criteria.where("customerId").is("1")), cl));
    }

    @org.junit.jupiter.api.Test
    public void stat(){
        //支持
        IndexOperations indexOperations = mongoTemplate.indexOps(cl);
        List<IndexInfo> indexInfo = indexOperations.getIndexInfo();
        indexInfo.forEach(i->{
            System.out.println(i.getName());
        });

        /*
        //不支持
        GeoResults<Student> price = mongoTemplate.geoNear(NearQuery.near(2000, 3000).query(Query.query(Criteria.where("price").is(3000))),
                Student.class, cl);
        Iterator<GeoResult<Student>> iterator = price.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }

         */
    }

    @org.junit.jupiter.api.Test
    public void updateOne(){
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("customerId").is(3)),
                Update.update("price", 10000), cl);
    }



    @org.junit.jupiter.api.Test
    public void aggregate(){
        Aggregation aggregation = Aggregation.newAggregation(
                //筛选
                Aggregation.match(Criteria.where("customerId").is("3").and("price").gt(10))
                //.orOperator(Criteria.where("customerId").is(1))
                //排序
                ,Aggregation.sort(Sort.Direction.DESC, "price")
                //分组
                //,Aggregation.group("orgId").sum("price").as("price")
                //新增自定义字段
                //,Aggregation.addFields().addField("orgId").withValue("total_price").build().and().build()
                //,Aggregation.project("orgId").andInclude("price")
                //数组拆分 -- 支持
                ,Aggregation.unwind("sz")
                //,Aggregation.group("sz").count().as("price")
                //按照范围分组  -- 不支持 Command failed with error -6: 'Invalid Argument'
                //,Aggregation.bucket("price").withBoundaries(1000,2500).withDefaultBucket("other")
                //insert into tb select * from 不支持
                //,Aggregation.out("newCl")
                // 关联查询  --不支持
                //,Aggregation.lookup("single","price","price","priceList")
                // 展示指定字段
                //,Aggregation.project("price").andInclude("orgId")
                // limit
                ,Aggregation.skip(new Long(7))
                //,Aggregation.limit(3)
        );
        AggregationResults<Student2> aggregate = mongoTemplate.aggregate(aggregation, cl, Student2.class);
        printStudent2(aggregate.getMappedResults());
    }

    @org.junit.jupiter.api.Test
    public void count(){
        Query price = new Query(Criteria.where("customerId").is(2));
        System.out.println(mongoTemplate.count(price, cl));
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, cl);
        //支持
        bulkOperations.insert(buildStudent(10));
        //单分区，多分区支持，mongo不支持多分区
        bulkOperations.upsert(
                //Query.query(Criteria.where("_id").is(new ObjectId("6436214bc37a262e7d013821"))),
                Query.query(Criteria.where("customerId").is(10)),
                Update.update("price",6666));
        //与mongo相同，不支持多分区
        bulkOperations.updateOne(Query.query(Criteria.where("customerId").is(3)),
                Update.update("price",7777));
        //bulkOperations.remove(Query.query(Criteria.where("customerId").in("1", "2", "3")));
        //与mongo相同，不支持多分区
        bulkOperations.replaceOne(Query.query(Criteria.where("customerId").is(1)),buildStudent(11));
        bulkOperations.execute();
    }





    public void printStudent(Collection<Student> s){
        System.out.println("size: "+s.size());
        s.forEach(l->{
            System.out.println(l.toString());
        });
    }

    public void printStudent2(Collection<Student2> s){
        System.out.println("size: "+s.size());
        s.forEach(l->{
            System.out.println(l.toString());
        });
    }
}
