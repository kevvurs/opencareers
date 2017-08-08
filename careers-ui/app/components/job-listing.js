import Ember from 'ember';

const JobListingComponent = Ember.Component.extend({});

JobListingComponent.reopenClass({
  positionalParams: ['job']
});

export default JobListingComponent;
