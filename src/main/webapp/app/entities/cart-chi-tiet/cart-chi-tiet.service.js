(function() {
    'use strict';
    angular
        .module('bookManagementApp')
        .factory('CartChiTiet', CartChiTiet);

    CartChiTiet.$inject = ['$resource'];

    function CartChiTiet ($resource) {
        var resourceUrl =  'api/cart-chi-tiets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
