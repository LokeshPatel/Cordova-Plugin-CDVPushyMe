var exec = require('cordova/exec');
var pushyMe = {
  pushyMeID :function(callbacksucess,callbackfail) {
     exec(callbacksucess,callbackfail,"CDVPlushyMePlugin", "pushyMeTokenID", []);
   },
  pushyMeInstall :function(callbacksucess,callbackfail) {
    exec(callbacksucess,callbackfail,"CDVPlushyMePlugin", "installPlugin", []);
  },
  pushyMeReadAllMessages :function(callbacksucess,callbackfail) {
      exec(callbacksucess,callbackfail,"CDVPlushyMePlugin", "getAllMessages", []);
    },
  pushyMeClearAllMessages :function(callbacksucess,callbackfail) {
       exec(callbacksucess,callbackfail,"CDVPlushyMePlugin", "clearMessageFromStore", []);
     },
 pushyMeClearByMessagesId :function(callbacksucess,callbackfail,messageID) {
      if(messageID == "")
      {
        callbackfail("Insert Message Id");
        return;
        }
        exec(callbacksucess,callbackfail,"CDVPlushyMePlugin", "clearMessageByIdFromStore", [messageID]);
    }
};
module.exports = pushyMe;
