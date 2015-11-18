'use strict';

/**
 * @ngdoc function
 * @name codebaseFrontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the codebaseFrontendApp
 */
angular.module('items.new.controller', [])
        .controller('newItemController', ['$scope',  'itemsFactory', '$location', function ($scope, itemsFactory, $location) {

                $scope.item = {};

                $scope.newItem = function (item) {
                    console.log(item);
                    itemsFactory.newItem(item).success(function (data) {
                        $location.path("/items");
                    }).error(function (error) {
                        console.log("Error: " + error);
                    });

                };


            }]);