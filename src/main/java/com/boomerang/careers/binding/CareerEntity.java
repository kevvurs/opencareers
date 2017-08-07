package com.boomerang.careers.binding;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CareerEntity extends CareerPacket {
    private JobEntity data;

    public CareerEntity(){}

    public JobEntity getData() {
        return data;
    }

    public void setData(JobEntity data) {
        this.data = data;
    }

    public JobEntity peek() {
        return this.data;
    }

    public void push(JobEntity jobEntity) {
        this.data = jobEntity;
    }

    public String serialize() {
        return GSON.toJson(this);
    }

    public CareerPacket deserialize(String str) {
        return GSON.fromJson(str, CareerEntity.class);
    }

    public Response wrap(Response.Status status) {
        return CareerPacket.ResponseFactory.create(status, this, MediaType.APPLICATION_JSON);
    }
}
