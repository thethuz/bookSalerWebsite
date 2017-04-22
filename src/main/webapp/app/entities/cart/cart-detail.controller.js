(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('CartDetailController', CartDetailController);

    CartDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cart'];

    function CartDetailController($scope, $rootScope, $stateParams, previousState, entity, Cart) {
        var vm = this;

        vm.cart = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bookManagementApp:cartUpdate', function(event, result) {
            vm.cart = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
