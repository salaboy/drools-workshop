'use strict';

/**
 * @ngdoc function
 * @name codebaseFrontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the codebaseFrontendApp
 */
angular.module('items.list.controller', [])
        .controller('itemsListController', ['$scope',  'itemsFactory', '$location', function ($scope, itemsFactory, $location) {
                $scope.items = [];

                $scope.go = function (path, data) {
                    $location.path(path).search(data);
                };


                $scope.loadItems = function () {

                    itemsFactory.getAllItems().success(function (items) {
                        console.log(" Start - Items Get All : ");
                        for(var i = 0; i < items.length; i++){
                            console.log(items[i]);
                            
                        }
                        console.log(" End - Items Get All : ");
                        $scope.items = items;
                    }).error(function (error) {
                        console.log("Error: " + error);
                    });
                };

                $scope.loadItems();


            }]);