package com.boomerang.careers.service;

import com.boomerang.careers.data.JobBean;
import com.boomerang.careers.data.JobDao;
import com.boomerang.careers.data.JobRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/cache")
@Component
public class CacheService {
    private static final Logger LOG = Logger.getLogger(CacheService.class.getName());
    private static final Gson GSON = new GsonBuilder().create();
    private static final String RESPONSE_TEMPLATE = "{\"updated\": %s}";

    @Autowired
    JobRepo jobRepo;

    @Autowired
    JobDao jobDao;

    @Value("${careers.app.secret}")
    String appSecret;

    @POST
    @Path("/reload")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String reload(String request) {
        boolean reloaded = readSecret(request, "reload");

        if (reloaded) {
            jobRepo.init();
        } else {
            LOG.warn(String.format("Invalid reload request: %s", request));
        }

        return String.format(RESPONSE_TEMPLATE, reloaded);
    }


    @POST
    @Path("/persist")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String persist(String request) {
        boolean persisted = readSecret(request, "persist");

        if (persisted) {
            List<JobBean> jobs = new ArrayList<>();
            Map<Integer, JobBean> snapshot = jobRepo.snapshot();
            for (Map.Entry<Integer, JobBean> jobEntry : jobRepo.getCache().entrySet()) {
                if (!snapshot.containsKey(jobEntry.getKey())) {
                    jobs.add(jobEntry.getValue());
                }
            }

            if (!jobs.isEmpty()) {
               jobDao.insertJobs(jobs);
            }
        } else {
            LOG.warn(String.format("Invalid persist request: %s", request));
        }

        return String.format(RESPONSE_TEMPLATE, persisted);
    }

    @POST
    @Path("/sync")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String sync(String request) {
        boolean synced = readSecret(request, "sync");

        if (synced) {
            persist(request);
            reload(request);
        } else {
            LOG.warn(String.format("Invalid sync request: %s", request));
        }

        return String.format(RESPONSE_TEMPLATE, synced);
    }

    private boolean readSecret(String request, String endpoint) {
        String secret;
        try {
            CacheRequest cacheRequest = GSON.fromJson(request, CacheRequest.class);
            secret = cacheRequest.getSecret();
        } catch (JsonSyntaxException e) {
            LOG.warn(String.format("Bad request for %s: %s", endpoint, request));
            secret = "";
        }
        return appSecret.equals(secret);
    }

    private class CacheRequest {
        private String secret;

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getSecret() {
            return this.secret;
        }
    }
}
