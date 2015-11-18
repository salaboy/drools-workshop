(function () {
    angular
            .module('items.factory', [])
            .factory('itemsFactory', itemsFactory);

    itemsFactory.$inject = ['$http', 'constantsFactory'];

    function itemsFactory($http, constants) {
        var service = {
            newItem: newItem,
            getAllItems: getAllItems,
            getItem: getItem,
            updateItem: updateItem,
            deleteItem: deleteItem
        };

        return service;

        function newItem() {
            return $http.post(constants.server + 'warehouse/items', constants.headers);
        }

        function getAllItems() {
            return $http.get(constants.server + 'warehouse/items', constants.headers);
        }

        function getItem(id) {
            return $http.get(constants.server + 'warehouse/items/' + id, constants.headers);
        }

        function updateItem(id, item) {
            return $http.put(constants.server + 'warehouse/items/' + id, item, constants.headers);
        }

        function deleteItem(id) {
            return $http.delete(constants.server + 'warehouse/items/' + id , constants.headers);
        }
    }
})();