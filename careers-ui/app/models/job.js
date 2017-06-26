import DS from 'ember-data';

export default DS.Model.extend({
  company:  DS.attr('string'),
  location: DS.attr('string'),
  title:    DS.attr('string'),
  salary:   DS.attr('string'),
  ref:      DS.attr('string'),
  glass:    DS.attr('string'),
  linkedin: DS.attr('string'),
  notes:    DS.attr('string')
});
