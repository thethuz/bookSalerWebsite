(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('BookTagController', BookTagController);

    BookTagController.$inject = ['$scope', '$state', 'Book', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','entity','$resource'];

    function BookTagController ($scope, $state, Book, ParseLinks, AlertService, paginationConstants, pagingParams, entity,$resource) {
        var vm = this;
        console.log(entity);
        vm.books=[];
        loadAll();
        function loadAll () {
          console.log("this");
          var Books =$resource('/api/books/tag/'+entity,{},{'query': { method: 'GET', isArray: true}});
          console.log("that");
          Books.query({},function (data) {
            vm.books=data;
            console.log(data);
          });
        }


    }
})();
