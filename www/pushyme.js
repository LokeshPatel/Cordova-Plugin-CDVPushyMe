var exec = require('cordova/exec');
var pushyMe = {
     pushyMeID :function(callbacksucess,callbackfail) {
     exec(callbacksucess,callbackfail,"CDVPlushyMePlugin", "pushyMeTokenID", []);
   },
    pushyMeInstall :function(callbacksucess,callbackfail) {
    exec(callbacksucess,callbackfail,"CDVPlushyMePlugin", "installPlugin", []);
  }
};
module.exports = pushyMe;
