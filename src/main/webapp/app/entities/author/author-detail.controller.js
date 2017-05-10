(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('AuthorDetailController', AuthorDetailController);

    AuthorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Author','$resource'];

    function AuthorDetailController($scope, $rootScope, $stateParams, previousState, entity, Author,$resource) {
        var vm = this;

        vm.author = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bookManagementApp:authorUpdate', function(event, result) {
            vm.author = result;
        });
        vm.books=[];
        var Books=$resource('/api/books/tacgia/'+vm.author.id,{},{'query': { method: 'GET', isArray: true}});
        Books.query({},function (data) {
          vm.books=data;
          console.log(data);
        })
        $scope.$on('$destroy', unsubscribe);

    }
})();
