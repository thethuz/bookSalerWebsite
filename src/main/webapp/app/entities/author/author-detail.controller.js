(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('AuthorDetailController', AuthorDetailController);

    AuthorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Author'];

    function AuthorDetailController($scope, $rootScope, $stateParams, previousState, entity, Author) {
        var vm = this;

        vm.author = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bookManagementApp:authorUpdate', function(event, result) {
            vm.author = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
