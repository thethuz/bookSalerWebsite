(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('PayingDialogController', PayingDialogController);

    PayingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Paying','$resource'];

    function PayingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Paying,$resource) {
        var vm = this;
        vm.paying=entity;
        var PayingX = $resource('api/payings/prefix',{},{
          'get': {
              method: 'GET',
              transformResponse: function (data) {
                  if (data) {
                      data = angular.fromJson(data);
                  }
                  return data;
              }
          }
        });
        PayingX.get({},function(data){
          console.log(data);
          // vm.paying = entity;
          if(data!=undefined || data.length==1){
            vm.paying.price=data.price;
            vm.paying.priceWithVAT=data.priceWithVAT;
            vm.paying.chiTietGiaoDich=data.chiTietGiaoDich;
            vm.paying.cartid=data.cartid;
            vm.paying.user_id=data.user_id
            console.log(vm.paying);
          } else {
            vm.paying.price=0;
            vm.paying.priceWithVAT=0;
            vm.paying.chiTietGiaoDich="";
            vm.paying.cartid="";
            // vm.user_id=
            console.log(vm.paying);
          }
        });
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
