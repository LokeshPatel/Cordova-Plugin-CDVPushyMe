# Pushy Me Notification Service With Cordova/PhoneGap For Android.

## Master branch:
 
 ```
cordova plugin add https://github.com/LokeshPatel/Cordova-Plugin-CDVPushyMe.git
 ```
## local folder:

 ``` 
cordova plugin add Cordova-Plugin-CDVPushyMe --searchpath path

```

## 1) Install pushy me service : Call with on device ready function

 ```  
        navigator.CDVPushyMe.pushyMeInstall(function(e){
          console.log("success");
        }, function(error){
          console.log(error);
        });
     
 ``` 
  
## 2) Get pushy me service token-id 
  ```
     navigator.CDVPushyMe.pushyMeID(function(tokenID){
        // retrun token id for notification service
        console.log("Token ID = " + tokenID);
        //Token ID use for call notification form pusyme server.
     }, function(error){
        console.log(error);
     });
```

Reference: [Pushy-Me Doc](https://pushy.me/docs)