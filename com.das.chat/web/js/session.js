/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var Session = angular.module('Session', ['Rest']);

Session.constant('fetch_interval', 10000);

Session.factory('AdminSession', ['fetch_interval', '$rest', function(fetch_interval,$rest){
    var AdminSession = {};
    
    AdminSession.profiles = null;
    AdminSession.rooms = null;
    AdminSession.messages = null;
    AdminSession.invitations = null;
    AdminSession.userslogins = null;
    AdminSession.usersaccess = null;
    AdminSession.roomsaccesspolicy = null;
    
    AdminSession.fetchInterval = null;
    
    AdminSession.startSession = function(profile,callback,error){
        AdminSession.startFetching();
        AdminSession.updateSession(callback,error);
    };
    
    AdminSession.endSession = function(callback,error){
        AdminSession.stopFetching();
        callback && callback();
    };
    
    AdminSession.startFetching = function(){
        if(AdminSession.fetchInterval !== null){
            return;
        }
        
        AdminSession.fetchInterval = setInterval(function(){
            AdminSession.updateSession(function(){
                console.log('AdminSession','updated');
            },function(){
                console.log('AdminSession','error in update');
                AdminSession.stopFetching();
            });
        },fetch_interval);
    };
    
    AdminSession.stopFetching = function(){
        if(AdminSession.fetchInterval === null){
            return;
        }
        
        clearInterval(AdminSession.fetchInterval);
        AdminSession.fetchInterval = null;
    };
    
    AdminSession.updateProfiles = function(callback,error){
        $rest.Profile.query(function(profiles){
            AdminSession.profiles = profiles;
            callback && callback();
        },error);
    };
    
    AdminSession.updateRooms = function(callback,error){
        $rest.Room.query(function(rooms){
            AdminSession.rooms = rooms;
            callback && callback(); 
        },error);
    };
    
    AdminSession.updateInvitations = function(callback,error){
        $rest.Invitation.query(function(invitations){
            AdminSession.invitations = invitations;
            callback && callback();
        },error);
    };
    
    AdminSession.updateUsersLogins = function(callback,error){
        $rest.UserLogin.query(function(userslogins){
            AdminSession.userslogins = userslogins;
            callback && callback();
        },error);
    };
    
    AdminSession.updateUsersAccess = function(callback,error){
        $rest.UserAccess.query(function(usersaccess){
            AdminSession.usersaccess = usersaccess;
            callback && callback();
        },error);
    };
    
    AdminSession.updateRoomsAccessPolicy = function(callback,error){
        $rest.RoomAccessPolicy.query(function(roomsaccesspolicy){
            AdminSession.roomsaccesspolicy = roomsaccesspolicy;
            callback && callback();
        },error);
    };
    
    AdminSession.updateMessages = function(callback,error){
        $rest.Message.query(function(messages){
            AdminSession.messages = messages;
            callback && callback();
        },error);
    };
    
    AdminSession.updateSession = function(callback,error){
        AdminSession.updateProfiles(function(){
            AdminSession.updateRooms(function(){
                AdminSession.updateInvitations(function(){
                    AdminSession.updateMessages(function(){
                        AdminSession.updateUsersLogins(function(){
                            AdminSession.updateUsersAccess(function(){
                                AdminSession.updateRoomsAccessPolicy(callback,error);
                            },error);
                        },error);
                    },error);
                },error);
            },error);
        },error);
    };
    
    return AdminSession;
}]);

Session.factory('Session', ['$rest','fetch_interval', function($rest,fetch_interval){
    var Session = {};
    
    Session.profile = null;
    Session.userlogin = null;
    Session.rooms = null;
    Session.chats = null;
    Session.invitations = null;
    
    Session.fetchInterval = null;
    
    Session.createProfile = function(person,callback,error){
        var profile = new $rest.Profile(person);
        profile.type = 'user';
        profile.$save(callback,error);
    };
    
    Session.createUserLogin = function(user,callback,error){
        $rest.Profile.findByLogin(user,function(profile){
            if(!profile.id ) {
                error('invalid profile');
                return;
            }
            
            if(user.password !== profile.password){
                error('invalid password');
                return;
            }
            
            $rest.UserLogin.save({
                profile: profile
            },callback,error);
        },error);
    };
    
    Session.createUserAccess = function(room,callback,error){
        $rest.UserAccess.save({
            room: room,
            profile: Session.profile
        }, callback,error);
    };
    
    Session.createChat = function(options,callback,error){
        $rest.Room.save({
            name: (new Date()).getTime().toString(),
            owner: Session.profile,
            type: 'private'
        },callback,error);
    };
    
    Session.createInvitation = function(invitation,callback,error){
        $rest.Invitation.save({
            sender: Session.profile,
            receiver: invitation.receiver,
            room: invitation.room,
            state: 'pending'
        },callback,error);
    };
    
    Session.createRoomAccessPolicy = function(accesspolicy,callback,error){
        $rest.RoomAccessPolicy.save({
            room: accesspolicy.room,
            profile: accesspolicy.profile,
            policy: 'reject'
        },callback,error);
    };
    
    Session.acceptInvitation = function(invitation,callback,error){
        $rest.Invitation.get(invitation,function(invitation){
            invitation.$accept(function(){
                Session.updateInvitations(callback,error);
            });
        },error);
    };
    
    Session.rejectInvitation = function(invitation,callback,error){
        $rest.Invitation.get(invitation,function(invitation){
            invitation.$reject(function(){
                Session.updateInvitations(callback,error);
            });
        },error);
    };
    
    Session.getFromLocalStorage = function(){
        var userlogin = localStorage['mychat_session'];
        
        if(userlogin){
            return JSON.parse(userlogin);
        }
        else {
            null;
        }
    };
    
    Session.startSession = function(userlogin,callback,error){
        $rest.UserLogin.get(userlogin,function(userlogin){
            Session.userlogin = userlogin;
            
            $rest.Profile.get(userlogin.profile,function(profile){
                Session.profile = profile;
                Session.startFetching();
                Session.updateSession(callback,error);
            });
        },error);
    };
    
    Session.saveSession = function(callback,error){
        localStorage['mychat_session'] = JSON.stringify(Session.userlogin.toJSON());
        callback();
    };
    
    Session.endSession = function(callback,error){
        Session.stopFetching();
        Session.userlogin.$terminate(function(){
            localStorage.removeItem('mychat_session');
            Session.profile = null;
            Session.userlogin = null;
            Session.rooms = null;
            Session.chats = null;
            Session.invitations = null;
            callback && callback();
        },error);
    };
    
    Session.startFetching = function(){
        if(Session.fetchInterval !== null){
            return;
        }
        
        Session.fetchInterval = setInterval(function(){
            Session.updateSession(function(){
                console.log('Session','updated');
            },function(){
                console.error('Session','error in update');
                Session.stopFetching();
            });
        },fetch_interval);
    };
    
    Session.stopFetching = function(){
        if(Session.fetchInterval === null){
            return;
        }
        
        clearInterval(Session.fetchInterval);
        Session.fetchInterval = null;
    };
    
    Session.updateProfile = function(callback,error){
       Session.profile.$get(callback,error);
    };
    
    Session.updateUserLogin = function(callback,error){
       Session.userlogin.$get(callback,error); 
    };
    
    Session.updateRooms = function(callback,error){
        $rest.Room.findPublics({type:'public'},function(rooms){
            Session.rooms = rooms;
            callback && callback();
        },error);
    };
    
    Session.updateChats = function(callback,error){
        $rest.Invitation.findBySender({sender:Session.profile.id},function(chats){
            Session.chats = chats;
            callback && callback();
        },error);
    };
    
    Session.updateInvitations = function(callback,error){
        $rest.Invitation.findByReceiver({receiver:Session.profile.id},function(invitations){
            Session.invitations = invitations;
            callback && callback();
        },error);
    };
    
    Session.updateUser = function(callback,error){
        Session.updateProfile(function(){
            Session.updateUserLogin(function(){
                Session.saveSession(callback,error);
            },error);
        },error);
    };
    
    Session.updateCollections = function(callback,error){
        Session.updateRooms(function(){
            Session.updateChats(function(){
                Session.updateInvitations(function(){
                    callback();
                },error);
            },error);
        },error);
    };
    
    Session.updateSession = function(callback,error){
        Session.updateUser(function(){
            Session.updateCollections(callback,error);
        },error);
    };

    return Session;
}]);

Session.factory('RoomSession', ['$rest','Session','fetch_interval', function($rest, Session, fetch_interval){
    var RoomSession = {};
    
    RoomSession.room = null;
    RoomSession.useraccess = null;
    RoomSession.participants = null;
    RoomSession.messages = null;
    RoomSession.roomaccesspolicy = null;
    
    RoomSession.fetchInterval = null;
    
    RoomSession.getFromLocalStorage = function(){
        var useraccess = localStorage['mychat_room_session'];
        
        if(useraccess){
            return JSON.parse(useraccess);
        }
        else {
            null;
        }
    };
    
    RoomSession.startSession = function(useraccess,callback,error){
        // TODO check room access policy
        $rest.UserAccess.get(useraccess,function(useraccess){
            RoomSession.useraccess = useraccess;
            
            $rest.Room.get(useraccess.room,function(room){
                RoomSession.room = room;
                
                RoomSession.saveSession(function(){
                    RoomSession.startFetching();
                    RoomSession.updateSession(callback,error);
                },error);
            },error);
        },error);
    };
    
    RoomSession.saveSession = function(callback,error){
        localStorage['mychat_room_session'] = JSON.stringify(RoomSession.useraccess.toJSON());
        callback && callback();
    };
    
    RoomSession.endSession = function(callback,error){
        RoomSession.stopFetching();
        RoomSession.useraccess.$terminate(function(){
            localStorage.removeItem('mychat_room_session');
            RoomSession.room = null;
            RoomSession.useraccess = null;
            RoomSession.participants = null;
            RoomSession.messages = null;
            RoomSession.roomaccesspolicy = null;
            callback && callback();
        },error);
    };
    
    RoomSession.startFetching = function(){
        if(RoomSession.fetchInterval !== null){
            return;
        }
        
        RoomSession.fetchInterval = setInterval(function(){
            RoomSession.updateSession(function(){
                console.log('RoomSession','updated');
            },function(){
                console.error('RoomSession','error in update');
                RoomSession.stopFetching();
            });
        },fetch_interval);
    };
    
    RoomSession.stopFetching = function(){
        if(RoomSession.fetchInterval === null){
            return;
        }
        
        clearInterval(RoomSession.fetchInterval);
        RoomSession.fetchInterval = null;
    };
    
    RoomSession.updateParticipants = function(callback,error){
        $rest.UserAccess.findActivesByRoom({room:RoomSession.room.id},function(participants){
            RoomSession.participants = participants;
            callback && callback();
        },error);
    };
    
    RoomSession.updateMessages = function(callback,error){
        $rest.Message.findByRoom({room:RoomSession.room.id},function(messages){
            RoomSession.messages = messages;
            callback && callback();
        },error);
    };
    
    RoomSession.updateRoomAccessPolicy = function(callback,error){
        $rest.RoomAccessPolicy.findByRoom({room:RoomSession.room.id},function(roomaccesspolicy){
            RoomSession.roomaccesspolicy = roomaccesspolicy;
            callback && callback();
        },error);
    };
    
    RoomSession.updateSession = function(callback,error){
        RoomSession.updateParticipants(function(){
            RoomSession.updateMessages(function(){
                RoomSession.updateRoomAccessPolicy(callback,error);
            },error);
        },error);
    };
    
    RoomSession.postMessage = function(message,callback,error){
        $rest.Message.save({
            body: message.body,
            owner: Session.profile,
            state: 'active',
            room: RoomSession.room
        }, function(){
             RoomSession.updateMessages(callback,error);
        }, error);
    };
    
    RoomSession.removeMessage = function(message,callback,error){
        $rest.Message.get(message,function(message){
            message.state = 'deleted';
            message.$update(function(){
                RoomSession.updateMessages(callback,error);
            },error);
        },error);
    };
    
    RoomSession.removeUserAccess = function(useraccess,callback,error){
        $rest.UserAccess.get(useraccess,function(useraccess){
            useraccess.$terminate(function(){
                RoomSession.updateParticipants(callback,error);
            },error);
        },error);
    };
    
    RoomSession.addUserToBlackList = function(profile,callback,error){
        Session.createRoomAccessPolicy({
            profile: profile,
            room: RoomSession.room
        },function(){
            RoomSession.updateParticipants(callback,error);
        },error);
    };
    
    return RoomSession;
}]);

