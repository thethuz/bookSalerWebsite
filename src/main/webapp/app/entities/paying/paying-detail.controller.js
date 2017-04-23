(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('PayingDetailController', PayingDetailController);

    PayingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Paying'];

    function PayingDetailController($scope, $rootScope, $stateParams, previousState, entity, Paying) {
        var vm = this;

        vm.paying = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bookManagementApp:payingUpdate', function(event, result) {
            vm.paying = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
