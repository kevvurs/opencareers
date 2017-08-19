import Ember from 'ember';

export default Ember.Component.extend({
  userActivity: Ember.inject.service(),

  signed: Ember.computed.alias('userActivity.loggedIn'),
  showSignDlg: false,

  actions: {
    signIn () {
      this.set('showSignDlg', true);
    },

    signOut () {
      this.get('userActivity').exit();
    }
  }
});
