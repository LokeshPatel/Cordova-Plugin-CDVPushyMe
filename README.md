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
## 3) Message store in local store : Read all message  
  ```
 navigator.CDVPushyMe.pushyMeReadAllMessages(function(result){
     /* Return all message in json formate with id and message */
   console.log(result);
 },function(e){console.log(e)}
 );
 
 /* Result format : {"msg_1":"Hello World One !","msg_2":"Hello World Two!","msg_3":"Hello World Three!","totalCount":3} */
```
## 4) Remove all messages from local store  
  ```
     navigator.CDVPushyMe.pushyMeClearAllMessages(function(result){
        console.log(result);
       
     }, function(error){
        console.log(error);
     });
```

## 5) Remove messages from local store (one by one) : 
  ```
     navigator.CDVPushyMe.pushyMeClearByMessagesId(function(result){
        console.log(result);
      }, function(error){
        console.log(error);
     },"msg_1");
     
     /* Message get from local store list */
```

Reference: [Pushy-Me Doc](https://pushy.me/docs)

