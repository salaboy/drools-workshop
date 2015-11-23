'use strict';

/**
 * @ngdoc function
 * @name codebaseFrontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the codebaseFrontendApp
 */
angular.module('nav.controller', [])
        .controller('navController', ['$scope', '$location', function ($scope,  $location) {

                $scope.status = "Home";
                $scope.sidebarOpen = false;
                $scope.mainNavTopOpen = false;
             
//            
//                $scope.isActive = function (route) {
//                    if($location.path().indexOf(route) >= 0){
//                        return true;
//                    }else {
//                        return false;
//                    }
//                };
//                
//                $scope.openSidebar = function(){
//                    $scope.sidebarOpen = !$scope.sidebarOpen;
//                    
//                };
//                $scope.openMainNavTop = function(){
//                    $scope.mainNavTopOpen = !$scope.mainNavTopOpen; 
//                };
//                $scope.closeMainNavTop = function(){
//                    $scope.mainNavTopOpen = false; 
//                };
//            
                $scope.go = function (path, data) {
                    $scope.status = path;
                    $location.path(path).search(data);
                    $scope.sidebarOpen = false;
                    $scope.mainNavTopOpen = false;
                };
//                
//            
//                $( window ).resize(function() {
//                    if($( window ).width() > 992) {
//                         $scope.sidebarOpen = false;
//                        $scope.$apply();
//                    }
//                });


            }]);