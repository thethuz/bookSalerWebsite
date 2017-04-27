(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state','Book','AlertService'];

    function HomeController ($scope, Principal, LoginService, $state, Book,AlertService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });


        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
        loadAll();

        function loadAll () {
            Book.query({
                page: 0,
                size: 20,
                sort: ["id,asc"]
            }, onSuccess, onError);
            // function sort() {
            //     var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            //     if (vm.predicate !== 'id') {
            //         result.push('id');
            //     }
            //     return result;
            // }
            function onSuccess(data, headers) {
                // vm.links = ParseLinks.parse(headers('link'));
                // vm.totalItems = headers('X-Total-Count');
                // vm.queryCount = vm.totalItems;
                vm.books = data;
                console.log(data);
                // vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
