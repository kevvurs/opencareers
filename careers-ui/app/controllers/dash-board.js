import Ember from 'ember';

export default Ember.Controller.extend({
  bannerList: [
    {
      heading: 'OpenCareers',
      bgcol: '#5499C7',
      description: 'Social recruiting; break the cycle.'
    },
    {
      heading: 'Featured Jobs',
      bgcol: '#c74465',
      description: 'View the top positions shared on OpenCareers this month.'
    }
  ]
});
