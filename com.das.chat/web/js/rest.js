var Rest = angular.module('Rest', ['ngResource']);

Rest.factory('Profile', ['$resource', function($resource){
    return $resource('webresources/entity.profiles/:id', {id: '@id'}, {
        get: {method:'GET'},
        save: {method:'POST'},
        update: {method:'PUT'},
        delete: {method:'DELETE'},
        
        query: {method:'GET', isArray:true},
        findByLogin: {method:'GET', url:'webresources/entity.profiles/login/:login'}
    });
}]);

Rest.factory('UserLogin', ['$resource', function($resource){
    return $resource('webresources/entity.userslogins/:id', {id: '@id'}, {
        get: {method:'GET'},
        save: {method:'POST'},
        update: {method:'PUT'},
        delete: {method:'DELETE'},
        
        query: {method:'GET', isArray:true},
        findActiveByLogin: {method:'GET',url:'webresources/entity.userslogins/login/:login'},
        
        terminate: {method:'POST', params:{action:'terminate'}}
    });        
}]);

Rest.factory('UserAccess', ['$resource', function($resource){
    return $resource('webresources/entity.usersaccess/:id', {id: '@id'}, {
        get: {method:'GET'},
        save: {method:'POST'},
        update: {method:'PUT'},
        delete: {method:'DELETE'},

        search: {method:'GET', url:'webresources/entity.usersaccess/search'},
        
        query: {method:'GET', isArray:true},
        findActivesByRoom: {method:'GET', url: 'webresources/entity.usersaccess/room/:room/actives', isArray:true },
        
        terminate: {method:'POST', params:{action:'terminate'}}
    });        
}]);

Rest.factory('Room', ['$resource', function($resource){
    return $resource('webresources/entity.rooms/:id', {id: '@id'}, {
        get: {method:'GET'},
        save: {method:'POST'},
        update: {method:'PUT'},
        delete: {method:'DELETE'},
        
        query: {method:'GET', isArray:true},
        search: {method:'GET', url:'webresources/entity.rooms/search'},
        findPublics: {method:'GET', url:'webresources/entity.rooms/type/public', isArray:true},
    });        
}]);

Rest.factory('Invitation', ['$resource', function($resource){
    return $resource('webresources/entity.invitations/:id', {id: '@id'}, {
        get: {method:'GET'},
        save: {method:'POST'},
        update: {method:'PUT'},
        delete: {method:'DELETE'},
        
        query: {method:'GET', isArray:true},
        findBySender: {method:'GET', url:'webresources/entity.invitations/sender/:sender', isArray:true},
        findByReceiver: {method:'GET', url:'webresources/entity.invitations/receiver/:receiver', isArray:true},
        
        accept: {method:'POST', params:{action:'accept'}},
        reject: {method:'POST', params:{action:'reject'}}
    });        
}]);

Rest.factory('Message', ['$resource', function($resource){
    return $resource('webresources/entity.messages/:id', {id: '@id'}, {
        get: {method:'GET'},
        save: {method:'POST'},
        update: {method:'PUT'},
        delete: {method:'DELETE'},
        
        query: {method:'GET', isArray:true},
        findByRoom: {method:'GET', url:'webresources/entity.messages/room/:room', isArray:true}
    });        
}]);

Rest.factory('RoomAccessPolicy', ['$resource', function($resource){
    return $resource('webresources/entity.roomsaccesspolicy/:id', { id: '@id'}, {
        query: {method:'GET', isArray:true},
        
        get: {method:'GET'},
        save: {method:'POST'},
        update: {method:'PUT'},
        delete: {method:'DELETE'},
        
        findByRoom: {method:'GET', url:'webresources/entity.roomsaccesspolicy/room/:room', isArray:true}
    });        
}]);

Rest.factory('$rest', ['Profile','UserLogin','UserAccess','Room','Invitation','Message','RoomAccessPolicy', function(Profile,UserLogin,UserAccess,Room,Invitation,Message,RoomAccessPolicy){
    return {
        Profile: Profile,
        UserLogin: UserLogin,
        Room: Room,
        Invitation: Invitation,
        UserAccess: UserAccess,
        Message: Message,
        RoomAccessPolicy: RoomAccessPolicy
    };
}]);



