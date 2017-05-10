(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('paying.user', {
            parent: 'entity',
            url: '/paying',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Payings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/paying/payings.html',
                    controller: 'PayingController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('paying', {
            parent: 'entity',
            url: '/paying',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Payings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/paying/payings.html',
                    controller: 'PayingController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('paying-detail', {
            parent: 'entity',
            url: '/paying/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Paying'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/paying/paying-detail.html',
                    controller: 'PayingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Paying', function($stateParams, Paying) {
                    return Paying.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'paying',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('paying-detail.edit', {
            parent: 'paying-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paying/paying-dialog.html',
                    controller: 'PayingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Paying', function(Paying) {
                            return Paying.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('paying.new', {
            parent: 'paying',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paying/paying-dialog.html',
                    controller: 'PayingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                price: null,
                                priceWithVAT: null,
                                phuongThucThanhToan: null,
                                hoten: null,
                                email: null,
                                diaChi: null,
                                chiTietGiaoDich: null,
                                user_id: null,
                                cartid: null,
                                daGiaoTien: null,
                                daGiaoHang: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('paying', null, { reload: 'paying' });
                }, function() {
                    $state.go('paying');
                });
            }]
        })
        .state('paying.edit', {
            parent: 'paying',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paying/paying-dialog.html',
                    controller: 'PayingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Paying', function(Paying) {
                            return Paying.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('paying', null, { reload: 'paying' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('paying.delete', {
            parent: 'paying',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/paying/paying-delete-dialog.html',
                    controller: 'PayingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Paying', function(Paying) {
                            return Paying.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('paying', null, { reload: 'paying' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
