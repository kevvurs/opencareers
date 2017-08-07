package com.boomerang.careers.data;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JobDao {
    private static final Logger LOG = Logger.getLogger(JobDao.class.getName());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${careers.sql.insert}")
    String dataInsert;

    @Value("${careers.sql.update}")
    String dataUpdate;

    @Value("${careers.sql.select}")
    String dataSelect;

    @Value("${careers.sql.query}")
    String dataQuery;

    @Value("${careers.sql.find}")
    String dataFind;

    @Value("${careers.sql.delete}")
    String dataDelete;

    public JobDao() {
        LOG.info("jobDao instantiated");
    }

    public List<JobBean> fetchAllJobs() {
        LOG.info("Fetching all jobs data");
        List<JobBean> jobBeans = new ArrayList<>();
        try {
            jobBeans = jdbcTemplate.query(dataSelect,
                    new BeanPropertyRowMapper<>(JobBean.class));
        } catch (Exception e) {
            LOG.error("Selecting jobs failed", e);
        }
        return jobBeans;
    }

    public JobBean fetchJob(int id) {
        LOG.info("Fetching job data");
        JobBean jobBean = new JobBean();
        try {
            jobBean = jdbcTemplate.queryForObject(dataQuery, new Object[]{id},
                    new BeanPropertyRowMapper<>(JobBean.class));
        } catch (Exception e) {
            LOG.error(String.format("Querying job for id %s failed", id), e);
        }
        return jobBean;
    }

    private JobBean fetchJob(String ref) {
        JobBean jobBean = new JobBean();
        try {
            jobBean = jdbcTemplate.queryForObject(dataFind, new Object[]{ref},
                    new BeanPropertyRowMapper<>(JobBean.class));
        } catch (Exception e) {
            LOG.error("Upserted lookup failed", e);
        }
        return jobBean;
    }

    public JobBean insertJob(final JobBean jobBean) {
        LOG.info("Inserting new job");
        try {
            jdbcTemplate.update(dataInsert, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(1, jobBean.getCompany());
                    preparedStatement.setString(2, jobBean.getLocation());
                    preparedStatement.setString(3, jobBean.getTitle());
                    preparedStatement.setString(4, jobBean.getSalary());
                    preparedStatement.setString(5, jobBean.getRef());
                    preparedStatement.setString(6, jobBean.getGlass());
                    preparedStatement.setString(7, jobBean.getLinkedin());
                    preparedStatement.setString(8, jobBean.getNotes());
                }
            });
        } catch (Exception e) {
            LOG.error("Inserting new job failed", e);
        }
        return fetchJob(jobBean.getRef());
    }

    public void insertJobs(final List<JobBean> jobs) {
        final int batchSize = jobs.size();
        LOG.info(String.format("Inserting batch of %s jobs", batchSize));
        try {
            jdbcTemplate.batchUpdate(dataInsert, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    JobBean jobBean = jobs.get(i);
                    preparedStatement.setString(1, jobBean.getCompany());
                    preparedStatement.setString(2, jobBean.getLocation());
                    preparedStatement.setString(3, jobBean.getTitle());
                    preparedStatement.setString(4, jobBean.getSalary());
                    preparedStatement.setString(5, jobBean.getRef());
                    preparedStatement.setString(6, jobBean.getGlass());
                    preparedStatement.setString(7, jobBean.getLinkedin());
                    preparedStatement.setString(8, jobBean.getNotes());
                }

                @Override
                public int getBatchSize() {
                    return batchSize;
                }
            });
        } catch (Exception e) {
            LOG.error("Batch insert failed during execution");
        }
    }

    public JobBean patchJob(final JobBean jobBean) {
        LOG.info("Updating existing job");
        try {
            jdbcTemplate.update(dataUpdate, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(1, jobBean.getCompany());
                    preparedStatement.setString(2, jobBean.getLocation());
                    preparedStatement.setString(3, jobBean.getTitle());
                    preparedStatement.setString(4, jobBean.getSalary());
                    preparedStatement.setString(5, jobBean.getRef());
                    preparedStatement.setString(6, jobBean.getGlass());
                    preparedStatement.setString(7, jobBean.getLinkedin());
                    preparedStatement.setString(8, jobBean.getNotes());
                    preparedStatement.setString(9, jobBean.getId().toString());
                }
            });
        } catch (Exception e) {
            LOG.error("Updating job failed", e);
        }
        return fetchJob(jobBean.getRef());
    }

    public void deleteJob(String id) {
        LOG.info("Removing job");
        try {
            jdbcTemplate.update(dataDelete, new Object[]{id});
        } catch (Exception e) {
            LOG.error(String.format("Deleting job for id %s failed", id), e);
        }
    }
}
