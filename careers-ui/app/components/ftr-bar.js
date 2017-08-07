import Ember from 'ember';

export default Ember.Component.extend({
  destination: [
    {
      name: 'About',
      target: 'about'
    },
    {
      name: 'Contact Us',
      target: 'contact-us'
    }
  ]
});
