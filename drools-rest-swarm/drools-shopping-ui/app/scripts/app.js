(function () {

    /**
     * @ngdoc overview
     * @name codebaseFrontendApp
     * @description
     * # codebaseFrontendApp
     *
     * Main module of the application.
     */

    angular
        .module('shoppingCartApp', [
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
    'items.list.controller'
//    'items.new.controller',
//    'items.details.controller',
//    'items.edit.controller'
   
  ])
        .config(function ($stateProvider, $urlRouterProvider) {
            

            $urlRouterProvider.otherwise('/');
            $stateProvider
               
               
                .state('/items', {
                    url: '/',
                    templateUrl: 'scripts/Items/List/list.html',
                    controller: 'itemsListController'
//                })
//                .state('items.new', {
//                    url: '/items/new',
//                    templateUrl: 'scripts/Items/New/new.html',
//                    controller: 'newItemController'
//                })
//                .state('items.edit', {
//                    url: '/items/edit',
//                    templateUrl: 'scripts/Items/Edit/edit.html',
//                    controller: 'editItemController'
//                })
//                .state('items.details', {
//                    url: '/items/details',
//                    templateUrl: 'scripts/Items/Details/details.html',
//                    controller: 'itemDetailsController'
                });
        }).run(function () {});
})();