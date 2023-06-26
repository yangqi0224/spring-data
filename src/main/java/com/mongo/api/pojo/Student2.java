package com.mongo.api.pojo;

import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName Student2
 * @Description 请描述类的业务用途
 * @Author yangqi
 * @Date 2023/4/13 16:11
 * @Version 1.0
 **/
public class Student2 extends Object {
    private ObjectId _id;
    private String customerId;
    private Integer price;
    private Long createDate;
    private String orgId;
    private String sz;
    private String object;
    private List<Integer> priceList;

    public List<Integer> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Integer> priceList) {
        this.priceList = priceList;
    }

    public Student2() {
    }

    public Student2(ObjectId _id, String customerId, Integer price, Long createDate, String orgId, String sz, String object) {
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
                ", sz=" + sz +
                ", object=" + object +
                '}';
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }


    public String getSz() {
        return sz;
    }

    public void setSz(String sz) {
        this.sz = sz;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
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
