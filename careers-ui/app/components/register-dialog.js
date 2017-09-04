import Ember from 'ember';

const { bind } = Ember.run;

export default Ember.Component.extend({
  regInfoFld: [
    {
      id: 'fullNameNew',
      label: 'Full Name',
      limit: 100,
    },
    {
      id: 'emailNew',
      label: 'Email',
      limit: 100,
    },
    {
      id: 'usernameSelection',
      label: 'Username',
      limit: 50,
    },
    {
      id: 'passwordMake',
      label: 'Choose password',
      limit: 200,
    },
    {
      id: 'passwordMatch',
      label: 'Confirm password',
      limit: 200,
    }
  ],

  formId: 'regForm',
  formPage: 1,
  formSelector: Ember.computed('formId', function () {
    let formId = this.get('formId');
    return `#${formId}`;
  }),
  formFld: [],

  init () {
    this._super(...arguments);
    let viewable = this.get('regInfoFld').slice(0, 2);
    this.set('formFld', viewable);
  },

  changePage (from, to) {
    let viewable = this.get('regInfoFld').slice(from, to);
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
        case 1: this.changePage(2, 5);
          this.set('formPage', page + 1);
          Ember.$('#regPrevBtn').toggleClass('disabled');
          Ember.$('#regNextBtn').toggleClass('disabled');
          break;
        default: break;
      }
    },

    prev () {
      let page = this.get('formPage');
      switch (page) {
        case 2: this.changePage(0, 2);
          this.set('formPage', page - 1);
          Ember.$('#regPrevBtn').toggleClass('disabled');
          Ember.$('#regNextBtn').toggleClass('disabled');
          break;
        default: break;
      }
    }
  }
});
