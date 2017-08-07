package com.boomerang.careers.data;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JobRepo {
    private static final Logger LOG = Logger.getLogger(JobRepo.class.getName());
    private Map<Integer, JobBean> cache;

    @Autowired
    JobDao jobDao;

    public JobRepo() {
        this.cache = new ConcurrentHashMap<>();
        LOG.info("jobRepo Instantiated");
    }

    @PostConstruct
    public void init() {
        if (!this.cache.isEmpty()) {
            this.cache.clear();
        }

        this.cache.putAll(snapshot());
        LOG.info("Repository synchronized");
    }

    public Map<Integer, JobBean> getCache() {
        return this.cache;
    }

    public Map<Integer, JobBean> snapshot() {
        Map<Integer, JobBean> map = new ConcurrentHashMap<>();
        List<JobBean> jobs = jobDao.fetchAllJobs();
        for (JobBean job : jobs) {
            map.put(job.getId(), job);
        }
        return map;
    }

    public List<JobBean> deliverAll() {
        List<JobBean> jobs = new ArrayList<>();
        for (Map.Entry<Integer, JobBean> jobEntry : this.cache.entrySet()) {
            jobs.add(jobEntry.getValue());
        }
        return jobs;
    }

    public JobBean deliverJob(int id) {
        JobBean job;
        if (this.cache.containsKey(id)) {
            job = this.cache.get(id);
        } else {
            job = jobDao.fetchJob(id);
            if (job.getConfirmed()) {
                this.cache.put(id, job);
            }
        }
        return job;
    }

    public JobBean pushJob(JobBean job) {
        int id = reserveId();
        job.setId(id);
        this.cache.put(id, job);
        return job;
    }

    private int reserveId() {
        Set<Integer> idSet = this.cache.keySet();
        Integer[] ids = new Integer[idSet.size()];
        idSet.toArray(ids);
        Arrays.sort(ids);
        return (ids[idSet.size() -1] + 1);
    }

}
