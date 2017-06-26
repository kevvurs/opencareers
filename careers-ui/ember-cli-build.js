/* eslint-env node */
const EmberApp = require('ember-cli/lib/broccoli/ember-app');

module.exports = function(defaults) {
  var app = new EmberApp(defaults, {
  });

  app.import('bower_components/material-design-lite/material.css');
  app.import('bower_components/material-design-lite/material.js');
  app.import('bower_components/font-awesome/css/font-awesome.css');
  app.import('bower_components/bootstrap/dist/css/bootstrap.css');
  return app.toTree();
};
