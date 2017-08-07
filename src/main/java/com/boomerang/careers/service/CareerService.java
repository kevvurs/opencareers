package com.boomerang.careers.service;

import com.boomerang.careers.binding.CareerPacket;
import com.boomerang.careers.binding.CareerEntity;
import com.boomerang.careers.binding.CareerEntities;
import com.boomerang.careers.binding.JobEntity;
import com.boomerang.careers.data.JobBean;
import com.boomerang.careers.data.JobDao;
import com.boomerang.careers.data.JobRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Component
public class CareerService implements EmberService{
    private static final Logger LOG = Logger.getLogger(CareerService.class);
    private static final String PING_STATUS = "OK";

    @Autowired
    JobDao jobDao;

    @Autowired
    JobRepo jobRepo;

    public CareerService() {
        LOG.info("careerService instantiated");
    }

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        LOG.info(String.format("Ping: %s", PING_STATUS));
        return PING_STATUS;
    }

    @GET
    @Path("/jobs")
    @Produces({MediaType.APPLICATION_JSON, JSON_API})
    public Response findAll() {
        LOG.info("GET => ~/jobs");
        CareerPacket response = new CareerEntities();
        Response.Status status;
        try {
            for (JobBean jobBean : jobRepo.deliverAll()) {
                response.push(new JobEntity(jobBean));
            }
            status = Response.Status.OK;
        } catch (Exception e) {
            LOG.error("Could not fetch jobs from DB", e);
            status = Response.Status.FORBIDDEN;
        }
        return response.wrap(status);
    }

    @GET
    @Path("/jobs/{id}")
    @Produces({MediaType.APPLICATION_JSON, JSON_API})
    public Response findRecord(@PathParam("id") String id) {
        LOG.info(String.format("GET => ~/jobs/%s", id));
        CareerPacket response = new CareerEntity();
        Response.Status status;
        try {
            int jobId = Integer.valueOf(id);
            response.push(new JobEntity(jobRepo.deliverJob(jobId)));
            status = Response.Status.OK;
        } catch (Exception e) {
            LOG.error(String.format("Could not fetch job for id %s", id), e);
            status = Response.Status.FORBIDDEN;
        }
        return response.wrap(status);
    }

    @POST
    @Path("/jobs")
    @Produces({MediaType.APPLICATION_JSON, JSON_API})
    @Consumes({MediaType.APPLICATION_JSON, JSON_API})
    public Response save(String request) {
        LOG.info("POST => ~/jobs");
        CareerPacket response = new CareerEntities();
        Response.Status status;
        try {
            CareerPacket careerRequest = new CareerEntity().deserialize(request);
            JobBean jobBean = jobRepo.pushJob(careerRequest.peek().toBean());
            response.push(new JobEntity(jobBean));
            status = Response.Status.CREATED;
        } catch (Exception e) {
            LOG.error("New job not added", e);
            status = Response.Status.FORBIDDEN;
        }
        return response.wrap(status);
    }

    @PATCH
    @Path("/jobs/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@PathParam("id") String id, String request) {
        LOG.info(String.format("PATCH => ~/jobs/%s", id));
        CareerPacket response = new CareerEntity();
        Response.Status status;
        try {
            int jobId = Integer.valueOf(id);
            CareerPacket careerRequest = new CareerEntity().deserialize(request);
            JobBean jobBean = careerRequest.peek().toBean();
            jobBean.setId(jobId);
            jobBean = jobDao.patchJob(jobBean);
            response.push(new JobEntity(jobBean));
            status = Response.Status.OK;
        } catch (Exception e) {
            LOG.error(String.format("Could not update job for id %s", id), e);
            status = Response.Status.FORBIDDEN;
        }
        return response.wrap(status);
    }

    @DELETE
    @Path("/jobs/{id}")
    @Consumes({MediaType.APPLICATION_JSON, JSON_API})
    public Response destroyRecord(@PathParam("id") String id) {
        LOG.info(String.format("DELETE => ~/jobs/%s", id));
        Response.Status status;
        try {
            jobDao.deleteJob(id);
            status = Response.Status.OK;
        } catch (Exception e) {
            LOG.error(String.format("Could not update job for id %s", id), e);
            status = Response.Status.FORBIDDEN;
        }
        return Response.status(status).build();
    }
}
