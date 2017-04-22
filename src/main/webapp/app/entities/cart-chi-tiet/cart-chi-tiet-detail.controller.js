(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('CartChiTietDetailController', CartChiTietDetailController);

    CartChiTietDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CartChiTiet'];

    function CartChiTietDetailController($scope, $rootScope, $stateParams, previousState, entity, CartChiTiet) {
        var vm = this;

        vm.cartChiTiet = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bookManagementApp:cartChiTietUpdate', function(event, result) {
            vm.cartChiTiet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
