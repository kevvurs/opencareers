import Ember from 'ember';

export default Ember.Component.extend({
  userActivity: Ember.inject.service(),

  tagName: 'nav',
  classNames: ['navbar', 'fixed-top', 'navbar-expand', 'op-nav'],
  signed: Ember.computed.alias('userActivity.loggedIn'),

  actions: {
    signOut () {
      this.get('userActivity').exit();
    }
  }
});
