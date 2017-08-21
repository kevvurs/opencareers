import Ember from 'ember';

const FormInputComponent = Ember.Component.extend({
  inputLength: 0,
  selector: Ember.computed('inputId', function () {
    return '#' + this.get('inputId');
  }),
  helperId: Ember.computed('inputId', function () {
    return this.get('inputId') + 'Helper';
  }),
  characterCount: Ember.computed('inputLength', 'maxLength', function () {
    let inputLength = this.get('inputLength');
    let maxLength = this.get('maxLength');
    return `${inputLength}/${maxLength}`;
  }),

  actions: {
    formChanged () {
      let selector = this.get('selector');
      let inputValue = Ember.$(selector).val();
      let len = inputValue.length;
      this.set('inputLength', len);
    }
  }
});

FormInputComponent.reopenClass({
  positionalParams: ['inputId', 'inputLabel', 'maxLength', 'required', 'multiLine']
});

export default FormInputComponent;
