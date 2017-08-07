import DS from 'ember-data';

export default DS.JSONAPIAdapter.extend({
  // Data access layer
  namespace: '/api'
});
