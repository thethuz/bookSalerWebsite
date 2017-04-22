(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('CartChiTietDeleteController',CartChiTietDeleteController);

    CartChiTietDeleteController.$inject = ['$uibModalInstance', 'entity', 'CartChiTiet'];

    function CartChiTietDeleteController($uibModalInstance, entity, CartChiTiet) {
        var vm = this;

        vm.cartChiTiet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CartChiTiet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
