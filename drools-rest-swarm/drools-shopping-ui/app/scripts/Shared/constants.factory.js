(function () {
    'use strict';

    angular
        .module('constants.factory', [])
        .factory('constantsFactory', constantsFactory);

    constantsFactory.$inject = ['$cookies'];

    function constantsFactory($cookies) {
        var service = {
            port: '8080',
            server: 'http://localhost:8080/api/',
            authHeader: {
                headers: {
                    service_key: 'webkey:' + $cookies.get('user_email'),
                    auth_token: $cookies.get('user_token')
                }
            },
            header: {
                headers: {
                    'Access-Control-Allow-Headers': '*'
                }
            }
        };

        return service;
    }
})();