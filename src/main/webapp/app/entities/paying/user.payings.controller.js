(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('PayingController', PayingController);

    UserPayingController.$inject = ['$scope', '$state', 'Paying','$resource'];

    function UserPayingController ($scope, $state, Paying,$resource) {
        var vm = this;

        vm.payings = [];

        loadAll();

        function loadAll() {
            var Payingx=$resource().query(function(result) {
                vm.payings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
