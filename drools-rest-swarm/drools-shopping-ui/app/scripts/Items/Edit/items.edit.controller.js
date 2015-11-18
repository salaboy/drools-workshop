'use strict';

/**
 * @ngdoc function
 * @name codebaseFrontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the codebaseFrontendApp
 */
angular.module('items.edit.controller', [])
        .controller('editItemController', ['$scope', 'itemsFactory', '$location', function ($scope, itemsFactory, $location) {

                $scope.item = {};

                var queryString = $location.search();
                $scope.getItem = function (id) {
                    itemsFactory.getItem(id).success(function (item) {
                        $scope.item = item;
                    }).error(function (error) {
                        console.log("Error: " + error);
                    });

                };

                 $scope.deleteItem = function (id) {
                    itemsFactory.deleteItem(id).success(function (data) {
                        $location.path("/items");
                    }).error(function (error) {
                        console.log("Error: " + error);
                    });

                };
                $scope.updateItem = function (item) {
                    console.log(item);
                    itemsFactory.updateItem(item).success(function (data) {
                        $location.path("/items");
                    }).error(function (error) {
                        console.log("Error: " + error);
                    });

                };
                
                $scope.getItem(queryString.id);

            }]);