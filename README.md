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

If You Like Plugin ,[Please Buy a Cup of Coffee!] (https://www.paypal.com/in/cgi-bin/webscr?cmd=_flow&SESSION=hcFKg478oCEkvVkK14GmI47cXa2NKqLzT2m1JkLalaQU8jYXO1gKJ7zwt8y&dispatch=5885d80a13c0db1f8e263663d3faee8d6625bf1e8bd269586d425cc639e26c6a)
