(function () {
    angular
            .module('items.factory', [])
            .factory('itemsFactory', itemsFactory);

    itemsFactory.$inject = ['$http', 'constantsFactory'];

    function itemsFactory($http, constants) {
        var service = {
            initItems: initItems,
            newItem: newItem,
            getAllItems: getAllItems,
            getItem: getItem,
            updateItem: updateItem,
            deleteItem: deleteItem
        };

        return service;

        function initItems() {
            return $http.post(constants.itemsService + 'init');
        }

        function newItem() {
            return $http.post(constants.itemsService);
        }

        function getAllItems() {
            return $http.get(constants.itemsService);
        }

        function getItem(id) {
            return $http.get(constants.itemsService + id);
        }

        function updateItem(id, item) {
            return $http.put(constants.itemsService + id, item);
        }

        function deleteItem(id) {
            return $http.delete(constants.itemsService + id);
        }
    }
})();