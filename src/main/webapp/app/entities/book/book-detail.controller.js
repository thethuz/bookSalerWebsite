(function() {
    'use strict';

    angular
        .module('bookManagementApp')
        .controller('BookDetailController', BookDetailController);

    BookDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Book','$resource'];

    function BookDetailController($scope, $rootScope, $stateParams, previousState, entity, Book,$resource) {
        var vm = this;

        vm.book = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bookManagementApp:bookUpdate', function(event, result) {
            vm.book = result;
        });
        $scope.$on('$destroy', unsubscribe);
        var book=vm.book;
        vm.addToCart = function (){
          var urlx='/api/books/addtoCart/'+book.id;
          // Book
          var bookRes=$resource(urlx,{
            book: book
          },{'update': { method:'GET' }});
          bookRes.update(book,function (data) {
            console.log(data);
          });
          // Book.query()
          // console.log(vm.number);
          console.log(vm.book.id);
        }
        vm.subFromCart = function (){
          var urlx='/api/books/subfromCart/'+book.id;
          // Book
          var bookRes=$resource(urlx,{
            book: book
          },{'update': { method:'GET' }});
          bookRes.update(book,function (data) {
            console.log(data);
          });
          // Book.query()
          console.log(vm.number);
          console.log(vm.book.id);
        }
    }
})();
