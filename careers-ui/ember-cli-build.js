/* eslint-env node */
const EmberApp = require('ember-cli/lib/broccoli/ember-app');

module.exports = function(defaults) {
  var app = new EmberApp(defaults, {
  });

  app.import('bower_components/font-awesome/css/font-awesome.css');
  app.import('bower_components/tether/dist/js/tether.js');
  app.import('bower_components/tether/dist/css/tether.css');
  app.import('vendor/bootstrap-4.0.0-alpha.6-dist/js/bootstrap.js');
  app.import('vendor/bootstrap-4.0.0-alpha.6-dist/css/bootstrap.css');
  return app.toTree();
};
