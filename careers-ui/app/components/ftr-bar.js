import Ember from 'ember';

export default Ember.Component.extend({
  tagName: 'nav',
  classNames: ['navbar', 'navbar-inverse', 'bg-inverse'],
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
