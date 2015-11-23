'use strict';

/**
 * @ngdoc function
 * @name codebaseFrontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the codebaseFrontendApp
 */
angular.module('carts.list.controller', [])
        .controller('cartsListController', ['$scope',  'cartsFactory', '$location', function ($scope,  cartsFactory, $location) {

                $scope.carts = [];

                $scope.go = function (path, data) {
                    $location.path(path).search(data);
                };


                $scope.loadCarts = function () {

                    cartsFactory.getAllCarts().success(function (carts) {
                        console.log(" Start - Cart Get All : ");
                        for (var i = 0; i < carts.length; i++) {
                            console.log(carts[i]);

                        }
                        console.log(" End - Cart Get All : ");
                        $scope.items = carts;
                    }).error(function (data) {

                        console.log("Error Data: ");
                        console.log(data);
                        
                    });
                };

                $scope.loadCarts();


            }]);