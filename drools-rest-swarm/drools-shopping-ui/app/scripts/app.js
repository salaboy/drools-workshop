(function () {

    /**
     * @ngdoc overview
     * @name codebaseFrontendApp
     * @description
     * # codebaseFrontendApp
     *
     * Main module of the application.
     */

    var module = angular.module('shoppingCartApp', [
        'ngAnimate',
        'ngCookies',
        'ngResource',
        'ngRoute',
        'ngSanitize',
        'ngTouch',
        'ui.router',
        'constants.factory',
        'nav.controller',
        'items.factory',
        'items.list.controller',
        'items.new.controller',
        'items.details.controller',
        'items.edit.controller',
        'carts.factory',
        'carts.list.controller',
        'dashboard.controller'
    ]);

    var auth = {};
    var logout = function () {
        console.log('*** LOGOUT');
        auth.loggedIn = false;
        auth.authz = null;
        window.location = auth.logoutUrl;
    };

    angular.element(document).ready(function ($http) {
        
        var keycloakAuth = new Keycloak();
        auth.loggedIn = false;

        keycloakAuth.init({onLoad: 'login-required'}).success(function () {
            auth.loggedIn = true;
            auth.authz = keycloakAuth;
            auth.logoutUrl = keycloakAuth.authServerUrl + "/realms/shop/tokens/logout?redirect_uri=/";

            angular.bootstrap(document, ["shoppingCartApp"]);
        }).error(function () {
            console.log("ERROR -> MUST RELOAD!!!");
            //window.location.reload();
        });

    });

    module.factory('Auth', function () {
        return auth;
    });

    module.factory('authInterceptor', function ($q, Auth) {
        return {
            request: function (config) {
                
                var deferred = $q.defer();
                if (Auth.authz.token) {
                    
                    Auth.authz.updateToken(5).success(function () {
                        config.headers = config.headers || {};
                        config.headers.Authorization = 'Bearer ' + Auth.authz.token;

                        deferred.resolve(config);
                    }).error(function () {
                        deferred.reject('Failed to refresh token');
                    });
                }
                return deferred.promise;
            },
            response: function (response) {
                if (response.status === 401) {
                    // handle the case where the user is not authenticated
                    console.log("HERE!!!!!!");
                    console.log(response);
                }
                return response || $q.when(response);
            }

        };
    });


    module.config(function ($httpProvider, $stateProvider, $urlRouterProvider) {

        $httpProvider.interceptors.push('authInterceptor');
        $urlRouterProvider.otherwise('/');
        $stateProvider
                .state('items', {
                    url: '/',
                    templateUrl: 'scripts/Items/List/list.html',
                    controller: 'itemsListController'
                })
                .state('items/new', {
                    url: '/items/new',
                    templateUrl: 'scripts/Items/New/new.html',
                    controller: 'newItemController'
                })
                .state('items/edit', {
                    url: '/items/edit',
                    templateUrl: 'scripts/Items/Edit/edit.html',
                    controller: 'editItemController'
                })
                .state('items/details', {
                    url: '/items/details',
                    templateUrl: 'scripts/Items/Details/details.html',
                    controller: 'itemDetailsController'
                })
                .state('carts', {
                    url: '/carts',
                    templateUrl: 'scripts/Carts/List/list.html',
                    controller: 'cartsListController'
                })
                .state('dashboard', {
                    url: '/dashboard',
                    templateUrl: 'scripts/Dashboard/dashboard.html',
                    controller: 'dashboardController'
                });
    });



})();