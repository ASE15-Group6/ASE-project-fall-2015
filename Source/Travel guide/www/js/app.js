// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
var app=angular.module('starter', ['ionic','ngCordova','services', 'starter.controllers'])

//app.run(function($ionicPlatform) {
//  $ionicPlatform.ready(function() {
//    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
//    // for form inputs)
//    if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
//      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
//      cordova.plugins.Keyboard.disableScroll(true);
//
//    }
//
//    if (window.StatusBar) {
//      // org.apache.cordova.statusbar required
//      StatusBar.styleLightContent();
//    }
//   
//  })
//   
//})
var userLoginMode;
app.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider
    
    .state('tabs', {
      url: '/tab',
      abstract: true,
      templateUrl: 'templates/tabs.html'
    })
   .state('tabs.home',{
  url:'/home',
      views:{
      'Home':{
      templateUrl:'templates/home.html',
          controller:'homeCtrlr'
      }
      }
  })
  .state('tabs.login',{
  url:'/login',
        views: {
        'Login': {
          templateUrl: 'templates/login.html',
          controller: 'loginCtrl'
        }
      }
     
  })
 
    .state('tabs.task',{
        url:'/task',
          views: {
        'Task': {
          templateUrl: 'templates/task.html',
          controller: 'taskCtrlr'
        }
      }
    })

.state('tabs.register',{
url:'/register',
    views:{
        'Register':{
        templateUrl:'templates/register.html',
            controller:'registrationCtrlr'
        }
    }
})

.state('tabs.translator', {
    url: '/translator',
    views: {
      'Translator': {
        templateUrl: 'templates/tab-translator.html',
        controller: 'LanguageTranslatorController'
      }
    }
  })
  .state('tabs.weather', {
    url: '/weather',
    views: {
      'Weather': {
        templateUrl: 'templates/tab-weather.html',
        controller: 'googlemapoutput'
      }
    }
  });
    

  // if none of the above states are matched, use this as the fallback
  
  $urlRouterProvider.otherwise('/tab/login');
  

});

