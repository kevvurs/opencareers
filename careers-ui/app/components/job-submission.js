import Ember from 'ember';

const { bind } = Ember.run;

export default Ember.Component.extend({
  jobInfoFld: [
    {
      id: 'jobTitleFld',
      label: 'Title',
      limit: 50,
      required: true,
      multi: false,
    },
    {
      id: 'companyNameFld',
      label: 'Company',
      limit: 50,
      required: true,
      multi: false,
    },
    {
      id: 'companyLocationFld',
      label: 'Location',
      limit: 25,
      required: false,
      multi: false,
    },
    {
      id: 'jobSalaryFld',
      label: 'Salary/Wage',
      limit: 25,
      required: false,
      multi: false,
    },
    {
      id: 'referrerFld',
      label: 'Link to Job',
      limit: 200,
      required: true,
      multi: false,
    },
    {
      id: 'supportGlassdoorFld',
      label: 'GlassDoor Page',
      limit: 200,
      required: false,
      multi: false,
    },
    {
      id: 'supportLinkedinFld',
      label: 'Corporate Linkedin Profile',
      limit: 200,
      required: false,
      multi: false,
    },
    {
      id: 'jobInfoFld',
      label: 'Description',
      limit: 250,
      required: true,
      multi: true,
    }
  ],

  formId: 'jobForm',
  formPage: 1,
  formSelector: Ember.computed('formId', function () {
    let formId = this.get('formId');
    return `#${formId}`;
  }),
  formFld: [],

  init () {
    this._super(...arguments);
    let viewable = this.get('jobInfoFld').slice(0, 4);
    this.set('formFld', viewable);
  },
  changePage (from, to) {
    let viewable = this.get('jobInfoFld').slice(from, to);
    let selector = this.get('formSelector');
    Ember.$(selector).fadeOut("fast", bind(this, function () {
      this.set('formFld', viewable);
      Ember.$(selector).fadeIn("fast");
    }));
  },

  actions: {
    next () {
      let page = this.get('formPage');
      switch (page) {
        case 1: this.changePage(4, 7);
          this.set('formPage', page + 1);
          Ember.$('#prevBtn').toggleClass('disabled');
          break;
        case 2: this.changePage(7, 8);
          this.set('formPage', page + 1);
          Ember.$('#nextBtn').toggleClass('disabled');
          break;
        default: break;
      }
    },

    prev () {
      let page = this.get('formPage');
      switch (page) {
        case 1: break;
        case 2: this.changePage(0, 4);
          this.set('formPage', page - 1);
          Ember.$('#prevBtn').toggleClass('disabled');
          break;
        case 3: this.changePage(4, 7);
          this.set('formPage', page - 1);
          Ember.$('#nextBtn').toggleClass('disabled');
          break;
        default: break;
      }
    }
  }
});
