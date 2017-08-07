package com.boomerang.careers.binding;

import com.boomerang.careers.data.JobBean;

public class JobEntity {
    private static final String TYPE = "jobs";
    private String id;
    private String type;
    private JSJob attributes;

    public JobEntity(){
        this.type = TYPE;
    }

    public JobEntity(JobBean jobBean) {
        this();
        this.id = String.valueOf(jobBean.getId());
        this.attributes = new JSJob(jobBean);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSJob getAttributes() {
        return attributes;
    }

    public void setAttributes(JSJob attributes) {
        this.attributes = attributes;
    }

    public JobBean toBean() {
        JobBean jobBean = new JobBean();
        if (!(this.id == null || this.id.equalsIgnoreCase("null"))){
            jobBean.setId(Integer.parseInt(this.id));
        }
        jobBean.setCompany(this.attributes.getCompany());
        jobBean.setLocation(this.attributes.getLocation());
        jobBean.setTitle(this.attributes.getTitle());
        jobBean.setSalary(this.attributes.getSalary());
        jobBean.setRef(this.attributes.getRef());
        jobBean.setGlass(this.attributes.getGlass());
        jobBean.setLinkedin(this.attributes.getLinkedin());
        jobBean.setNotes(this.attributes.getNotes());
        return jobBean;
    }

    private class JSJob {
        private String company;
        private String location;
        private String title;
        private String salary;
        private String ref;
        private String glass;
        private String linkedin;
        private String notes;

        public JSJob(){}

        public JSJob(JobBean jobBean) {
            this.company = jobBean.getCompany();
            this.location = jobBean.getLocation();
            this.title = jobBean.getTitle();
            this.salary = jobBean.getSalary();
            this.ref = jobBean.getRef();
            this.glass = jobBean.getGlass();
            this.linkedin = jobBean.getLinkedin();
            this.notes = jobBean.getNotes();
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public String getGlass() {
            return glass;
        }

        public void setGlass(String glass) {
            this.glass = glass;
        }

        public String getLinkedin() {
            return linkedin;
        }

        public void setLinkedin(String linkedin) {
            this.linkedin = linkedin;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}
