(function() {
    'use strict';
    angular
        .module('bookManagementApp')
        .factory('Cart', Cart);

    Cart.$inject = ['$resource', 'DateUtils'];

    function Cart ($resource, DateUtils) {
        var resourceUrl =  'api/carts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.orderDate = DateUtils.convertDateTimeFromServer(data.orderDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
