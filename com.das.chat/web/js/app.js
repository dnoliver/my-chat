/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var App = angular.module('App',[
    'ngRoute',
    'Controllers',
    'Rest',
    'Session',
    'Command'
]);

App.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
        when('/home', {
            controller: 'HomeController',
            templateUrl: 'template/home.html'
        }).
        when('/profile/:profileId', {
            templateUrl: 'template/user/home.html',
            controller: 'ProfileHomeController'
        }).
        when('/room/:roomId', {
            templateUrl: 'template/user/room.html',
            controller: 'RoomHomeController'
        }).
        when('/admin/:profileId', {
            templateUrl: 'template/admin/home.html',
            controller: 'AdminHomeController'
        }). 
        when('/admin/room/:roomId', {
            templateUrl: 'template/user/room.html',
            controller: 'RoomHomeController'
        }).
        otherwise({
            redirectTo: '/home'
        });
    }
]);

