(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('CartChiTietDialogController', CartChiTietDialogController);

    CartChiTietDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CartChiTiet'];

    function CartChiTietDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CartChiTiet) {
        var vm = this;

        vm.cartChiTiet = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cartChiTiet.id !== null) {
                CartChiTiet.update(vm.cartChiTiet, onSaveSuccess, onSaveError);
            } else {
                CartChiTiet.save(vm.cartChiTiet, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bookManagementApp:cartChiTietUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
