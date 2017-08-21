import Ember from 'ember';

export default Ember.Component.extend({
  jobInfoFld: [
    {
      id: 'jobTitleFld',
      label: 'Title',
      limit: 50,
      required: true,
    },
    {
      id: 'companyNameFld',
      label: 'Company',
      limit: 50,
      required: true,
    },
    {
      id: 'companyLocationFld',
      label: 'Location',
      limit: 25,
      required: false,
    },
    {
      id: 'jobSalaryFld',
      label: 'Salary/Wage',
      limit: 25,
      required: false,
    },
    {
      id: 'referrerFld',
      label: 'Link to Job',
      limit: 200,
      required: true,
    },
    {
      id: 'supportGlassdoorFld',
      label: 'GlassDoor Page',
      limit: 200,
      required: false,
    },
    {
      id: 'supportLinkedinFld',
      label: 'Corporate Linkedin Profile',
      limit: 200,
      required: false,
    }
  ],
  jobDescription: {
    id: 'jobInfoFld',
    label: 'Description',
    limit: 250,
    required: true,
  }
});
