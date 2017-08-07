package com.boomerang.careers.test;

import com.boomerang.careers.binding.CareerEntity;
import com.boomerang.careers.binding.CareerPacket;
import com.boomerang.careers.binding.JobEntity;
import com.boomerang.careers.data.JobBean;
import com.boomerang.careers.service.CacheService;
import com.boomerang.careers.service.CareerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

@Test
@ContextConfiguration(locations = {"classpath:application-test-context.xml"})
public class ApiTest extends AbstractTestNGSpringContextTests {
    private static final Logger LOG = Logger.getLogger(ApiTest.class);

    @Autowired
    CareerService careerService;

    @Autowired
    CacheService cacheService;

    @Value("${careers.app.secret}")
    String appSecret;

    @Test( groups = { "universal" }, priority = 0)
    public void testPing() {
        LOG.info("Testing ping endpoint");
        boolean passing;

        try {
            String response = careerService.ping();
            LOG.info(String.format("Response:\n%s",response));
            passing = true;
        } catch (Exception e) {
            LOG.error(e);
            passing = false;
        }

        Assert.assertEquals(passing, true);
    }

    @Test( groups = { "central" }, priority = 2)
    public void testFindAll() {
        LOG.info("Testing findAll()");
        boolean passing;

        try {
            Response response = careerService.findAll();
            LOG.info(String.format("Response:\n%s",
                    response.getEntity()));
            passing = true;
        } catch (Exception e) {
            LOG.error(e);
            passing = false;
        }

        Assert.assertEquals(passing, true);
    }

    @Test( groups = { "off" }, priority = 1)
    public void testFindRecord() {
        LOG.info("Testing findRecord()");
        boolean passing;

        try {
            Response response = careerService.findRecord("1");
            LOG.info(String.format("Response:\n%s",
                    response.getEntity()));
            passing = true;
        } catch (Exception e) {
            LOG.error(e);
            passing = false;
        }

        Assert.assertEquals(passing, true);
    }

    @Test( groups = { "central" }, priority = 1)
    public void testSaveNew() {
        LOG.info("Testing save() for new record");
        boolean passing;

        JobBean jobBean = new JobBean();
        jobBean.setCompany("Science Lab");
        jobBean.setTitle("Researcher");
        jobBean.setSalary("$72,000/yr.");
        jobBean.setRef("www.google.com");
        jobBean.setLocation("Boise, ID");
        jobBean.setGlass("www.glassdoor.com");
        jobBean.setLinkedin("www.linkedin.com");
        jobBean.setNotes("Research plant biology in state-of-the-art facility.");
        CareerPacket request = new CareerEntity();

        request.push(new JobEntity(jobBean));

        try {
            Response response = careerService.save(request.serialize());
            LOG.info(String.format("Response:\n%s",
                    response.getEntity()));
            // cacheService.persist("{\"secret\": \"careers-909im%1t\"}");
            passing = true;
        } catch (Exception e) {
            LOG.error(e);
            passing = false;
        }

        Assert.assertEquals(passing, true);
    }

    @Test( groups = { "off" }, priority = 1)
    public void testUpdateRecord() {
        LOG.info("Testing save() for existing record");
        boolean passing;

        JobBean jobBean = new JobBean();
        jobBean.setCompany("Bing (test)");
        jobBean.setTitle("Bing (test)");
        jobBean.setSalary("$15.00/hr.");
        jobBean.setRef("bing.com");
        jobBean.setLocation("Mountain View, CA");
        jobBean.setGlass("glassdoor.com");
        jobBean.setLinkedin("linkedin.com");
        jobBean.setNotes("This is a test");
        CareerPacket request = new CareerEntity();

        request.push(new JobEntity(jobBean));

        try {
            Response response = careerService.save("103", request.serialize());
            LOG.info(String.format("Response:\n%s",
                    response.getEntity()));
            passing = true;
        } catch (Exception e) {
            LOG.error(e);
            passing = false;
        }

        Assert.assertEquals(passing, true);
    }

    @Test( groups = { "off" }, priority = 1)
    public void testDestroyRecord() {
        LOG.info("Testing destroyRecord()");
        boolean passing;

        try {
            Response response = careerService.destroyRecord("103");
            LOG.info(String.format("Response:\n%s",
                    response.getEntity()));
            passing = true;
        } catch (Exception e) {
            LOG.error(e);
            passing = false;
        }

        Assert.assertEquals(passing, true);
    }
}
