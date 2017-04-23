(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('PayingDialogController', PayingDialogController);

    PayingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Paying'];

    function PayingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Paying) {
        var vm = this;

        vm.paying = entity;
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
            if (vm.paying.id !== null) {
                Paying.update(vm.paying, onSaveSuccess, onSaveError);
            } else {
                Paying.save(vm.paying, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bookManagementApp:payingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
