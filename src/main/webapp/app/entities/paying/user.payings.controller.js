(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('UserPayingController', UserPayingController);

    UserPayingController.$inject = ['$scope', '$state', 'Paying','$resource'];

    function UserPayingController ($scope, $state, Paying,$resource) {
        var vm = this;

        vm.payings = [];

        loadAll();

        function loadAll() {
            var Payingx=$resource('/api/payings/user',{},{'query': { method: 'GET', isArray: true}});
            Payingx.query({},function (data) {
              console.log(data);
              vm.payings=data;
            });
        }
    }
})();
