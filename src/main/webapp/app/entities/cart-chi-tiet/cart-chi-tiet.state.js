(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cart-chi-tiet', {
            parent: 'entity',
            url: '/cart-chi-tiet?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CartChiTiets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cart-chi-tiet/cart-chi-tiets.html',
                    controller: 'CartChiTietController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('cart-chi-tiet-detail', {
            parent: 'entity',
            url: '/cart-chi-tiet/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CartChiTiet'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cart-chi-tiet/cart-chi-tiet-detail.html',
                    controller: 'CartChiTietDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CartChiTiet', function($stateParams, CartChiTiet) {
                    return CartChiTiet.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cart-chi-tiet',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cart-chi-tiet-detail.edit', {
            parent: 'cart-chi-tiet-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cart-chi-tiet/cart-chi-tiet-dialog.html',
                    controller: 'CartChiTietDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CartChiTiet', function(CartChiTiet) {
                            return CartChiTiet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cart-chi-tiet.new', {
            parent: 'cart-chi-tiet',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cart-chi-tiet/cart-chi-tiet-dialog.html',
                    controller: 'CartChiTietDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                bookId: null,
                                numberOfBook: null,
                                thanhTien: null,
                                cartId: null,
                                bookName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cart-chi-tiet', null, { reload: 'cart-chi-tiet' });
                }, function() {
                    $state.go('cart-chi-tiet');
                });
            }]
        })
        .state('cart-chi-tiet.edit', {
            parent: 'cart-chi-tiet',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cart-chi-tiet/cart-chi-tiet-dialog.html',
                    controller: 'CartChiTietDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CartChiTiet', function(CartChiTiet) {
                            return CartChiTiet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cart-chi-tiet', null, { reload: 'cart-chi-tiet' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cart-chi-tiet.delete', {
            parent: 'cart-chi-tiet',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cart-chi-tiet/cart-chi-tiet-delete-dialog.html',
                    controller: 'CartChiTietDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CartChiTiet', function(CartChiTiet) {
                            return CartChiTiet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cart-chi-tiet', null, { reload: 'cart-chi-tiet' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
