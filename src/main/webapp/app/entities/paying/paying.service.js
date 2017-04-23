(function() {
    'use strict';
    angular
        .module('bookManagementApp')
        .factory('Paying', Paying);

    Paying.$inject = ['$resource'];

    function Paying ($resource) {
        var resourceUrl =  'api/payings/:id';

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
