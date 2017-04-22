(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('BookDetailController', BookDetailController);

    BookDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Book'];

    function BookDetailController($scope, $rootScope, $stateParams, previousState, entity, Book) {
        var vm = this;

        vm.book = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bookManagementApp:bookUpdate', function(event, result) {
            vm.book = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
