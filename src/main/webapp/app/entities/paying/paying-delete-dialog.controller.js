(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('PayingDeleteController',PayingDeleteController);

    PayingDeleteController.$inject = ['$uibModalInstance', 'entity', 'Paying'];

    function PayingDeleteController($uibModalInstance, entity, Paying) {
        var vm = this;

        vm.paying = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Paying.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
