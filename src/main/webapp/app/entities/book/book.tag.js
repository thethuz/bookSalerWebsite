(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('BookTagController', BookTagController);

    BookTagController.$inject = ['$scope', '$state', 'Book', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function BookTagController ($scope, $state, Book, ParseLinks, AlertService, paginationConstants, pagingParams) {
        var vm = this;

        loadAll();

        function loadAll () {

        }

        
    }
})();
