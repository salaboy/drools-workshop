'use strict';

/**
 * @ngdoc function
 * @name codebaseFrontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the codebaseFrontendApp
 */
angular.module('items.details.controller', [])
        .controller('itemDetailsController', ['$scope',  'itemsFactory', '$location', function ($scope,  itemsFactory, $location) {
                $scope.item = {};
        
                $scope.go = function (path, data) {
                    $location.path(path).search(data);
                };
                
                var queryString = $location.search();
                $scope.getItem = function (id) {
                    itemsFactory.getItem(id).success(function (item) {
                        $scope.item = item;
                    }).error(function (error) {
                        console.log("Error: " + error);
                    });

                };

                $scope.getItem(queryString.id);

            }]);