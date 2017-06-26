package com.boomerang.careers.binding;

import com.boomerang.careers.data.JobBean;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class CareerEntities extends CareerPacket {
    private List<JobBean> jobs;

    public CareerEntities(){}

    public List<JobBean> getJobs() {
        if (this.jobs == null) {
            this.jobs = new ArrayList<>();
        }
        return this.jobs;
    }

    public void setJobs(List<JobBean> jobs) {
        this.jobs = jobs;
    }

    public JobBean peek() {
        JobBean jobBean;
        if (this.jobs == null || this.jobs.isEmpty()) {
            jobBean = new JobBean();
        } else {
            jobBean = this.jobs.get(0);
        }
        return jobBean;
    }

    public void push(JobBean jobBean) {
        this.getJobs().add(jobBean);
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
