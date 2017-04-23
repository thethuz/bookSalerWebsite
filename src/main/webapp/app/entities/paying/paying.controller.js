(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('PayingController', PayingController);

    PayingController.$inject = ['$scope', '$state', 'Paying'];

    function PayingController ($scope, $state, Paying) {
        var vm = this;

        vm.payings = [];

        loadAll();

        function loadAll() {
            Paying.query(function(result) {
                vm.payings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
