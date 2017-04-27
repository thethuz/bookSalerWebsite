(function() {
    'use strict';
    angular
        .module('bookManagementApp')
        .factory('Author', Author);

    Author.$inject = ['$resource', 'DateUtils'];

    function Author ($resource, DateUtils) {
        var resourceUrl =  'api/authors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.ngaySinh = DateUtils.convertLocalDateFromServer(data.ngaySinh);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.ngaySinh = DateUtils.convertLocalDateToServer(copy.ngaySinh);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.ngaySinh = DateUtils.convertLocalDateToServer(copy.ngaySinh);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
