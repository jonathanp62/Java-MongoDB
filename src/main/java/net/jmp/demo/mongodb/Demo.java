package net.jmp.demo.mongodb;

import org.bson.BsonType;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/*
 * As a BSON creator this class must be public
 * and have an empty, no argument, public
 * constructor.
 */

public final class Demo {
    @BsonId()
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String objectId;

    int prodId;

    int price;

    int quantity;

    @BsonCreator
    public Demo() {
        super();
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }

    public int getProdId() {
        return this.prodId;
    }

    public void setProdId(final int prodId) {
        this.prodId = prodId;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "objectId=" + this.objectId +
                ", prodId=" + this.prodId +
                ", price=" + this.price +
                ", quantity=" + this.quantity +
                '}';
    }
}
