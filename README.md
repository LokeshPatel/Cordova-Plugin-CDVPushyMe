# Pushy Me Notification Service With Cordova/PhoneGap

## Master branch:
 
 ```
cordova plugin add https://github.com/LokeshPatel/Cordova-Plugin-CDVPushyMe.git
 ```
## local folder:

 ``` 
cordova plugin add Cordova-Plugin-CDVPushyMe --searchpath path

```

## 1) Install pushy me service : on device ready function

 ```  
     navigator.CDVPushyMe.pushyMeInstall(function(){
      // result in base64 image format 
       console.log("Success");
     }, function(error){
       console.log("fail");
    });
     
 ``` 
  
## 2) Get pushy me service token-id 
  ```
     navigator.CDVPushyMe.pushyMeID(function(tokenID){
        // retrun token id for notification service
        console.log("Success" + tokenID);
     }, function(error){
        console.log("fail");
     });
```

Reference: [Pushy-Me Doc](https://pushy.me/docs)