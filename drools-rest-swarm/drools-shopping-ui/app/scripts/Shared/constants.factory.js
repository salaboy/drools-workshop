(function () {
    'use strict';

    angular
        .module('constants.factory', [])
        .factory('constantsFactory', constantsFactory);

    constantsFactory.$inject = ['$rootScope'];

    function constantsFactory($rootScope) {
        var service = {
            itemsService: 'http://localhost:8083/api/warehouse/items/',
            shoppingCartService: 'http://localhost:8084/api/shopping/',
            header: {
                headers: {
                    'Access-Control-Allow-Headers': '*'
                }
            }
        };

        return service;
    }
})();