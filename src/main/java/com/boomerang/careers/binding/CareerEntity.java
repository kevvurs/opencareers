package com.boomerang.careers.binding;

import com.boomerang.careers.data.JobBean;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CareerEntity extends CareerPacket {
    private JobBean jobs;

    public CareerEntity(){}

    public JobBean getJobs() {
        return jobs;
    }

    public void setJobs(JobBean jobs) {
        this.jobs = jobs;
    }

    public JobBean peek() {
        return this.jobs;
    }

    public void push(JobBean jobBean) {
        this.jobs = jobBean;
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
