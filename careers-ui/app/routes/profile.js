import Ember from 'ember';

export default Ember.Route.extend({
  model () {
    return {
      followers: 201,
      shared: 67,
      applied: 15
    }
  }
});
