(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('CartDialogController', CartDialogController);

    CartDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cart'];

    function CartDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cart) {
        var vm = this;

        vm.cart = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cart.id !== null) {
                Cart.update(vm.cart, onSaveSuccess, onSaveError);
            } else {
                Cart.save(vm.cart, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bookManagementApp:cartUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.orderDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
