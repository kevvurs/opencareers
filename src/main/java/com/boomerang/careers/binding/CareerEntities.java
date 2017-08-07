package com.boomerang.careers.binding;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class CareerEntities extends CareerPacket {
    private List<JobEntity> data;

    public CareerEntities(){}

    public List<JobEntity> getData() {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        return this.data;
    }

    public void setData(List<JobEntity> data) {
        this.data = data;
    }

    public JobEntity peek() {
        JobEntity jobBean;
        if (this.data == null || this.data.isEmpty()) {
            jobBean = new JobEntity();
        } else {
            jobBean = this.data.get(0);
        }
        return jobBean;
    }

    public void push(JobEntity jobEntity) {
        this.getData().add(jobEntity);
    }

    public String serialize() {
        return GSON.toJson(this);
    }

    public CareerPacket deserialize(String str) {
        return GSON.fromJson(str, CareerEntities.class);
    }

    public Response wrap(Response.Status status) {
        return ResponseFactory.create(status, this, MediaType.APPLICATION_JSON);
    }
}
