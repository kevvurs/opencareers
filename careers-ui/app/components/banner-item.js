import Ember from 'ember';

const BannerItemComponent = Ember.Component.extend;

BannerItemComponent.reopenClass({
  positionalParams: ['heading', 'bgcol']
});

export default BannerItemComponent({
});
