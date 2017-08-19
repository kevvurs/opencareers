import Ember from 'ember';

export default Ember.Component.extend({
  userActivity: Ember.inject.service(),

  showWarning: false,
  isLoading: false,

  actions: {
    userLogin () {
      Ember.Logger.info('Logging in');
      let username = Ember.$("#username-fld").val();
      let password = Ember.$("#password-fld").val();
      this.set('isLoading', true);
      this.get('userActivity').login(username, password)
        .then(response => {
          if (response.valid) {
            this.get('userActivity').set('loggedIn', true);
            this.get('userActivity').set('currentUser', response.username);
            Ember.$("#loginModal").modal("hide");
          } else {
            this.set('showWarning', true);
          }
          this.set('isLoading', false);
        });
    }
  }
});
