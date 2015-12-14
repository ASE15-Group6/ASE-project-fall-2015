// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
var app=angular.module('starter', ['ionic','ngCordova','services', 'starter.controllers','services.UserServices'])

var userLoginMode;
app.config(function($httpProvider,$stateProvider,$urlRouterProvider) {
    delete $httpProvider.defaults.headers.common["X-Requested-With"]    
  $stateProvider
    
  .state('main',{
  url:'/main',
      abstract:true,
      template:'<ion-nav-view name="Dashboard"></ion-nav-view>'
  })
  .state('login',{
  url:'/login',
          templateUrl: 'templates/login.html',
          controller: 'loginCtrl'
    
  })
  .state('register',{
url:'/register',
       cache:false,
        templateUrl:'templates/register.html',
            controller:'registrationCtrlr'
       
})
  
  .state('main.dashboard',
         {
      url:'/dahsboard',
      abstract:true,
      views:{
       'Dashboard':{   
      templateUrl:'templates/tabs-dashboard.html',
      }
      }
  })
  
  .state('main.dashboard.home',{
  url:'/home',
      views:{
      'Home':{
      templateUrl:'templates/home.html',
          controller:'homeCtrlr'
      }
      }
  })
    .state('main.dashboard.task',{
        url:'/task',
          views: {
        'Task': {
          templateUrl: 'templates/task.html',
          controller: 'taskCtrlr'
        }
      }
    })



.state('main.dashboard.translator', {
    url: '/translator',
    views: {
      'Translator': {
        templateUrl: 'templates/tab-translator.html',
        controller: 'LanguageTranslatorController'
      }
    }
  })
  .state('main.dashboard.Currency', {
    url: '/Currency',
    views: {
      'Currency': {
        templateUrl: 'templates/CurrencyConverter.html',
        controller: 'CurrencyConverter'
      }
    }
  })
  .state('main.dashboard.Places', {
    url: '/Places',
    views: {
      'Places': {
        templateUrl: 'templates/Places.html',
        controller: 'Places'
      }
    }
  })
  .state('main.dashboard.Accomdation', {
    url: '/Accomdation',
    views: {
      'Accomdation': {
        templateUrl: 'templates/Accomdation.html',
        controller: 'Accomdation'
      }
    }
  })
  
  .state('main.dashboard.weather', {
    url: '/weather',
    views: {
      'Weather': {
        templateUrl: 'templates/tab-weather.html',
        controller: 'googlemapoutput'
      }
    }
  });
    

  // if none of the above states are matched, use this as the fallback
  
  $urlRouterProvider.otherwise('/login');
  

});

