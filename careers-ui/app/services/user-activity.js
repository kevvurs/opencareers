import Ember from 'ember';

export default Ember.Service.extend({
  ajax: Ember.inject.service(),

  loggedIn: false,
  currentUser: null,

  login (username, password) {
    return this.get('ajax').post('/auth/login',
      {data: {username: username, password: password}});
  },

  exit () {
    this.set('currentUser', null);
    this.set('loggedIn', false);
  }
});
