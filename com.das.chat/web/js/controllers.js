/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var Controllers = angular.module('Controllers',[]);

Controllers.constant('max_idle_time',60);

Controllers.controller('MainController', ['$scope','$window','max_idle_time','CommandManager','Session','AdminSession','RoomSession', function($scope,$window,max_idle_time,CommandManager,Session,AdminSession,RoomSession){
    app = {
        CommandManager: CommandManager,
        Session: Session,
        AdminSession: AdminSession,
        RoomSession: RoomSession,
        scope: $scope
    };
    
    $scope.CommandManager = CommandManager;
    $scope.AdminSession = AdminSession;
    $scope.Session = Session;
    $scope.RoomSession = RoomSession;
    
    $scope.$watch(function(){
        return Session.profile;
    },function(){
        $scope.isVisitant = Session.profile === null;
        $scope.isUser = !$scope.isVisitant && Session.profile.type === 'user';
        $scope.isAdmin = !$scope.isVisitant && Session.profile.type === 'admin';
    });
    
    $scope.$watch(function(){
        return Session.userlogin;
    },function(){
        $scope.isLoggedIn = Session.userlogin !== null && !Session.userlogin.datetimeOfAccessEnd;
    });
    
    $scope.$watch(function(){
        return RoomSession.useraccess;
    },function(){
        $scope.isLoggedInRoom = RoomSession.useraccess !== null && !RoomSession.useraccess.datetimeOfAccessEnd;
    });
    
    $scope.$watch(function(){
        return RoomSession.room;
    },function(){
        $scope.isPublicRoom = RoomSession.room !== null && RoomSession.room.type === 'public';
        $scope.isPrivateRoom = RoomSession.room !== null && RoomSession.room.type === 'private';
        
        $scope.isLoadingRoom = RoomSession.room === null;
        
        if($scope.isLoadingRoom){
            $scope.roomTitle = '';
        }
        else {
            $scope.roomTitle = RoomSession.room.type === 'public'? $scope.RoomSession.room.name + ' room': 'Private Room';
        }
    });
    
    var _idleSecondsCounter = 0;
    
    $window.document.onclick = function() {
        _idleSecondsCounter = 0;
    };
    $window.document.onmousemove = function() {
        _idleSecondsCounter = 0;
    };
    $window.document.onkeypress = function() {
        _idleSecondsCounter = 0;
    };
    $window.window.setInterval(function(){
        _idleSecondsCounter++;
        
        if($scope.isLoggedIn && _idleSecondsCounter >= max_idle_time){
            $('#sessionExpireModal').modal({
                backdrop: 'static',
                keyboard: 'false'
            });
        }
    }, 1000); 
    
    CommandManager.changeImplementation('Visitant');
    CommandManager.execute('startSession',{userlogin:Session.getFromLocalStorage()});
}]);

Controllers.controller('HomeController', ['$scope', function($scope){
    
}]);

Controllers.controller('ProfileHomeController', ['$scope', function($scope){
    
}]);

Controllers.controller('AdminHomeController', ['$scope', function($scope){
    
}]);

Controllers.controller('RoomHomeController',['$scope', function($scope){
    
    $scope.$watch(function(){ 
        return $scope.RoomSession.roomaccesspolicy; 
    },function(){
        var policies = $scope.RoomSession.roomaccesspolicy;
        
        if(policies === null || policies.length === 0){
            return;
        }
        
        for(var i = 0; i < policies.length; i++){
            if(policies[i].profile.id === $scope.Session.profile.id && policies[i].policy === 'reject'){
                $('#roomModal').modal({
                    backdrop: 'static',
                    keyboard: 'false'
                });
                return;
            }
        }
    });
}]);

Controllers.controller('InvitationController', ['$scope', function($scope){
    $scope.isPending = $scope.invitation.state === "pending";
    $scope.isAccepted =  $scope.invitation.state === "accepted";
    $scope.isRejected = $scope.invitation.state === "rejected";
    
    $scope.$watch('invitation.state', function(newVal,oldVal){
        $scope.isPending = newVal === "pending";
        $scope.isAccepted =  newVal === "accepted";
        $scope.isRejected = newVal === "rejected";
    });
}]);

Controllers.controller('MessageListController', ['$scope', function($scope){
    // if messages is empty
    $scope.$watch(function(){
        return $scope.RoomSession.messages;
    },function(){
        $scope.isLoading = $scope.RoomSession.messages === null;
        $scope.isEmpty = $scope.isLoading || $scope.RoomSession.messages.length === 0;
    });
}]);

Controllers.controller('MessageController', ['$scope', function($scope){
    // if messages is empty
    $scope.$watch(function(){
        return $scope.message;
    },function(){
        $scope.isActive = $scope.message.state === 'active';
        $scope.isDeleted = $scope.message.state === 'deleted';
    });
}]);