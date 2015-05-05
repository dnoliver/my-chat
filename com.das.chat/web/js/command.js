var Command = angular.module('Command', ['Rest','Session']);

Command.factory('Command',[function(){
    function Command(action){
        this.action = action;
    }
    
    Command.prototype.execute = function(options){
      this.action(options);  
    };
    
    return Command;
}]);

Command.factory('VisitantCommand', ['Command', 'Session', '$location',  function(Command, Session, $location){
    function VisitantCommand(CommandManager){
        this.ACTIONS = {};
        
        this.ACTIONS.startSession = new Command(function(options){
            
            if(options === null){
                $location.path() !== '/home'? $location.path('/home') : true;
                return;
            }
            
            if(options.userlogin && options.userlogin.profile ){
                switch(options.userlogin.profile.type){
                    case 'user': CommandManager.changeImplementation('User');
                        break;
                    case 'admin': CommandManager.changeImplementation('Administrator');
                        break;
                }
        
                CommandManager.execute('startSession',options.userlogin);
                return;
            }
            
            if(options.login) {
                // could check if can start from localstorage
                Session.createUserLogin(options,function(userlogin){
                    switch(userlogin.profile.type){
                        case 'user': CommandManager.changeImplementation('User');
                            break;
                        case 'admin': CommandManager.changeImplementation('Administrator');
                            break;
                    }
                    CommandManager.execute("startSession",userlogin);
                },function(error){
                    alert(error);
                });
            }
        });
        
        this.ACTIONS.endSession = new Command(function(options){
            $location.path('/home');
        });
    
        this.ACTIONS.register = new Command(function(profile){
            Session.createProfile(profile,function(profile){
                CommandManager.execute("startSession",profile);
            },function(){
                // TODO add registration fail here
                alert('registration fail');
            });
        });
    
        this.ACTIONS.goToHome = new Command(function(options){
            $location.path() !== '/home'? $location.path('/home') : true;
        });
    };
    
    return VisitantCommand;
}]);

Command.factory('UserCommand', ['Command', 'Session', 'RoomSession','$location', function(Command, Session, RoomSession, $location){
    function UserCommand(CommandManager){
        this.ACTIONS = {};
        
        this.ACTIONS.startSession = new Command(function(userlogin){
            Session.startSession(userlogin,function(){
                var useraccess = RoomSession.getFromLocalStorage();
                
                if(useraccess && useraccess.room){
                    CommandManager.changeImplementation('RoomParticipant');
                    CommandManager.execute('startRoomSession',useraccess);
                }
                else {
                    CommandManager.execute('goToHome');
                }
            },function(){
                alert('fail to start user session');
            });
        });
        
        this.ACTIONS.endSession = new Command(function(options){
            Session.endSession(function(){
                CommandManager.changeImplementation('Visitant');
                CommandManager.execute("endSession");
            },function(){
                // TODO handle logout error here
                alert('error in end session');
            });
        });
        
        this.ACTIONS.goToHome = new Command(function(options){
            $location.path('/profile/' + Session.profile.id);
        });
        
        this.ACTIONS.startRoomSession = new Command(function(room){
            Session.createUserAccess(room,function(useraccess){
                CommandManager.changeImplementation('RoomParticipant');
                CommandManager.execute('startRoomSession',useraccess);
            },function(){
                alert('cannot start room session');
            });
        });
        
        this.ACTIONS.startChatSession = new Command(function(invitation){
            Session.createUserAccess(invitation.room,function(useraccess){
                CommandManager.changeImplementation('RoomParticipant');
                CommandManager.execute('startRoomSession',useraccess);
            },function(){
                alert('cannot start chat session');
            });
        });
        
        this.ACTIONS.acceptInvitation = new Command(function(invitation){
            Session.acceptInvitation(invitation,function(){
                alert('invitation accepted');
            },function(){
                alert('error invitation accepted');
            });
        });
        
        this.ACTIONS.rejectInvitation = new Command(function(invitation){
            Session.rejectInvitation(invitation,function(){
                alert('invitation rejected');
            },function(){
                alert('error invitation rejected');
            });
        });
    };
    
    return UserCommand;
}]);

Command.factory('AdministratorCommand', ['Command', 'UserCommand', 'Session', 'AdminSession', 'RoomSession', '$location', function(Command, UserCommand, Session, AdminSession, RoomSession, $location){
    function AdministratorCommand(CommandManager){
        UserCommand.apply(this,[CommandManager]);
        
        this.ACTIONS.startSession = new Command(function(userlogin){
            Session.startSession(userlogin,function(){
                var useraccess = RoomSession.getFromLocalStorage();
                
                if(useraccess && useraccess.room){
                    CommandManager.changeImplementation('RoomAdministrator');
                    CommandManager.execute('startRoomSession',useraccess);
                }
                else {
                    CommandManager.execute('goToHome');
                }
            },function(){
                alert('fail to start user session');
            });
        });
        
        this.ACTIONS.endSession = new Command(function(options){
            AdminSession.endSession(function(){
                Session.endSession(function(){
                    CommandManager.changeImplementation('Visitant');
                    CommandManager.execute("endSession");
                },function(){
                    // TODO handle logout error here
                    alert('error in end session');
                });
            },function(){
                alert('error in end session');
            });
        });
        
        this.ACTIONS.goToHome = new Command(function(options){
            AdminSession.startSession(Session.profile,function(){
                $location.path('/admin/' + Session.profile.id);
            },function(){
                // TODO handle error
                alert('error');
            });
        });
        
        this.ACTIONS.startRoomSession = new Command(function(room){
            Session.createUserAccess(room,function(useraccess){
                CommandManager.changeImplementation('RoomAdministrator');
                CommandManager.execute('startRoomSession',useraccess);
            },function(){
                alert('cannot start room session');
            });
       });
    };
    
    return AdministratorCommand;
}]);

Command.factory('RoomParticipantCommand', ['Command', 'UserCommand', 'Session', 'RoomSession', '$location', function(Command, UserCommand, Session, RoomSession ,$location){
    function RoomParticipantCommand(CommandManager){
        UserCommand.apply(this,[CommandManager]);
        
        this.ACTIONS.endSession = new Command(function(options){
            RoomSession.endSession(function(){
                CommandManager.changeImplementation('User');
                CommandManager.execute('endSession');
            },function(){
                alert('error in endSession');
            });
        });
        
        this.ACTIONS.startRoomSession = new Command(function(useraccess){
            RoomSession.startSession(useraccess,function(){
                $location.path('/room/' + RoomSession.room.id);
            },function(){
                // TODO handle startRoomSession error
                alert('error in startRoomSession');
            });
        });
        
        this.ACTIONS.endRoomSession = new Command(function(options){
            RoomSession.endSession(function(){
                CommandManager.changeImplementation('User');
                CommandManager.execute('goToHome');
            },function(){
                alert('error in endRoomSession');
            });
        });
        
        this.ACTIONS.switchRoomSession = new Command(function(room){
            RoomSession.endSession(function(){
                Session.createUserAccess(room,function(useraccess){
                    CommandManager.execute('startRoomSession',useraccess);
                },function(){
                    alert('error');
                });
            },function(){
                alert('error');
            });
        });
        
        this.ACTIONS.startChatSession = new Command(function(profile){
            // search if there is a chat
            var chatInvitation = null;
            
            Session.chats.forEach(function(c){
                if(c.receiver.id === profile.id){
                    chatInvitation = c;
                }
            });
            
            Session.invitations.forEach(function(i){
                if(i.sender.id === profile.id){
                    chatInvitation = i;
                }
            });
            
            if(chatInvitation !== null){
                CommandManager.execute('switchRoomSession',chatInvitation.room);
                return;
            }
            
            // create the chat and send invitation
            if(chatInvitation === null){
                Session.createChat({},function(room){
                    Session.createInvitation({
                        receiver: profile,
                        room: room
                    }, function(invitation){
                        CommandManager.execute('switchRoomSession',invitation.room);
                    }, function(){
                        alert('error in invitation creation');
                    });
                },function(){
                    alert('error in chat creation');
                });
            }
        });
        
        this.ACTIONS.postMessage = new Command(function(message){
            RoomSession.postMessage(message,function(){
                message.body = ""; // to cleanup the input
                console.log('success in message post');
            },function(){
                alert('error in message post');
            });
        });  
    };
    
    return RoomParticipantCommand;
}]);

Command.factory('RoomAdministratorCommand', ['Command', 'AdministratorCommand', 'RoomSession', '$location', function(Command, AdministratorCommand, RoomSession ,$location){
    function RoomAdministratorCommand(CommandManager){
        AdministratorCommand.apply(this,[CommandManager]);
        
        this.ACTIONS.endSession = new Command(function(options){
            RoomSession.endSession(function(){
                CommandManager.changeImplementation('Administrator');
                CommandManager.execute('endSession');
            },function(){
                alert('error in end session');
            });
        });
        
        this.ACTIONS.startRoomSession = new Command(function(useraccess){
            RoomSession.startSession(useraccess,function(){
                $location.path('/room/' + RoomSession.room.id);
            },function(){
                // TODO handle startRoomSession error
                alert('error start room session');
            });
        });
        
        this.ACTIONS.endRoomSession = new Command(function(options){
            RoomSession.endSession(function(){
                CommandManager.changeImplementation('Administrator');
                CommandManager.execute('goToHome');
            },function(){
                alert('error');
            });
        });

        this.ACTIONS.addUserToBlackList = new Command(function(useraccess){
            RoomSession.addUserToBlackList(useraccess.profile,function(){
                CommandManager.execute('postMessage',{body:useraccess.profile.login + ' has been removed from this room'});
            },function(){
                alert('error in add user to blacklist');
            });
        });
        
        this.ACTIONS.removeMessage = new Command(function(message){
            RoomSession.removeMessage(message,function(){
                console.log('success in remove message');
            },function(){
                alert('fail in remove message');
            });
        });
        
        this.ACTIONS.postMessage = new Command(function(message){
            RoomSession.postMessage(message,function(){
                console.log('success in message post');
            },function(){
                alert('error in message post');
            });
        });
    };
    
    return RoomAdministratorCommand;
}]);

Command.factory('CommandImplementation',[
    'VisitantCommand', 
    'UserCommand', 
    'AdministratorCommand',
    'RoomParticipantCommand',
    'RoomAdministratorCommand',
    function(
        VisitantCommand,
        UserCommand,
        AdministratorCommand,
        RoomParticipantCommand,
        RoomAdministratorCommand
    ){
    return {
        Visitant: VisitantCommand,
        User: UserCommand,
        Administrator: AdministratorCommand,
        RoomParticipant: RoomParticipantCommand,
        RoomAdministrator: RoomAdministratorCommand
    };
}]);

Command.factory('CommandManager', [ 'CommandImplementation', function(CommandImplementation){
    
    var CommandManager = {};
    
    var UserImplementation = new CommandImplementation.User(CommandManager);
    var AdministratorImplementation = new CommandImplementation.Administrator(CommandManager);
    var VisitantImplementation = new CommandImplementation.Visitant(CommandManager);
    var RoomParticipantImplementation = new CommandImplementation.RoomParticipant(CommandManager);
    var RoomAdministratorImplementation = new CommandImplementation.RoomAdministrator(CommandManager);
    
    CommandManager.implementationName = null;
    CommandManager.implementation = null;
    
    CommandManager.changeImplementation = function(implementation){
        console.log('CommandManager changeImplementation',implementation);
        switch(implementation){
            case 'Visitant':
                CommandManager.implementation = VisitantImplementation;
                CommandManager.implementationName = implementation;
                break;
            case 'User':
                CommandManager.implementation = UserImplementation;
                CommandManager.implementationName = implementation;
                break;
            case 'Administrator':
                CommandManager.implementation = AdministratorImplementation;
                CommandManager.implementationName = implementation;
                break;
            case 'RoomParticipant':
                CommandManager.implementation = RoomParticipantImplementation;
                CommandManager.implementationName = implementation;
                break;
            case 'RoomAdministrator':
                CommandManager.implementation = RoomAdministratorImplementation;
                CommandManager.implementationName = implementation;
                break;  
            default:
                console.error('CommandManager bad implementation', implementation);      
        }
    };
    
    CommandManager.execute = function(method,options){
        if(CommandManager.implementation.ACTIONS[method]){
            console.log('CommandManager execute',method,'as',CommandManager.implementationName);
            CommandManager.implementation.ACTIONS[method].execute(options);
        }
        else {
            console.log('CommandManager cannot execute',method,'as',CommandManager.implementationName);
            alert('invalid command');
        }
    };
    
    return CommandManager;
}]);