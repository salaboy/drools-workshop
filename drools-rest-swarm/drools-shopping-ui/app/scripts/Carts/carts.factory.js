(function () {
    angular
            .module('carts.factory', [])
            .factory('cartsFactory', cartsFactory);

    cartsFactory.$inject = ['$http', 'constantsFactory'];

    function cartsFactory($http, constants) {
        var service = {
            newCart: newCart,
            getAllCarts: getAllCarts,
            getCart: getCart,
        };

        return service;


        function newCart(user) {
            return $http.post(constants.shoppingCartService + 'new', user);
        }

        function getAllCarts() {
            return $http.get(constants.shoppingCartService + 'carts');
        }

        function getCart(id) {
            return $http.get(constants.shoppingCartService + id);
        }


    }
})();