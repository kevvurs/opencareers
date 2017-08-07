import Ember from 'ember';

export default Ember.Component.extend({
  sign: false,
  actions: {
    signIn () {
      this.set('sign', true);
    },

    signOut () {
      this.set('sign', false);
    },
  }
});
