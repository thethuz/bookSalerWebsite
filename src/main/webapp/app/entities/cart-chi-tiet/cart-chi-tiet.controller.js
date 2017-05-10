(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('CartChiTietController', CartChiTietController);

    CartChiTietController.$inject = ['$scope', '$state', 'CartChiTiet', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','$resource'];

    function CartChiTietController ($scope, $state, CartChiTiet, ParseLinks, AlertService, paginationConstants, pagingParams,$resource) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        // loadAll();
        loadCartByUser();

        function loadAll () {
            CartChiTiet.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.cartChiTiets = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadCartByUser(){
          console.log("load Cart By User");
          var url1='/api/cart-chi-tiets/getbyuser';
          var CartCTList = $resource(url1,{},{'query': { method: 'GET', isArray: true}});
          CartCTList.query({},onSuccess, onError);
          function onSuccess(data, headers) {
            console.log(data);
              // vm.links = ParseLinks.parse(headers('link'));
              vm.totalItems = headers('X-Total-Count');
              // vm.queryCount = vm.totalItems;
              vm.cartChiTiets = data;
              // vm.page = pagingParams.page;
              // if(data!=null){
              //   for(var i=0;i<data.length;i++)
              //
              // }
          }
          function onError(error) {
              AlertService.error(error.data.message);
          }
        }
        


        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
