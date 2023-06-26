package com.mongo.api.pojo;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

/**
 * @ClassName pojo
 * @Description 请描述类的业务用途
 * @Author yangqi
 * @Date 2023/4/10 12:01
 * @Version 1.0
 **/
@Document(collection = "my_db")
public class Student extends Object {
    private String _id;
    private Integer customerId;
    private Integer price;
    private Long createDate;
    private String orgId;
    private String[] sz;
    private Object object;

    public Student() {
    }

    public Student(String _id, int customerId, Integer price, Long createDate, String orgId, String[] sz, String object) {
        this._id = _id;
        this.customerId = customerId;
        this.price = price;
        this.createDate = createDate;
        this.orgId = orgId;
        this.sz = sz;
        this.object = object;
    }

    @Override
    public String toString() {
        return "Student{" +
                "_id='" + _id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", price=" + price +
                ", createDate=" + createDate +
                ", orgId='" + orgId + '\'' +
                ", sz=" + Arrays.toString(sz) +
                ", object=" + object +
                '}';
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }


    public String[] getSz() {
        return sz;
    }

    public void setSz(String[] sz) {
        this.sz = sz;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

}
