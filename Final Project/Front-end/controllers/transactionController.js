
  myApp.controller('transactionController', ['$scope', '$location', '$timeout', 'transactionService', 'bankService', function($scope, $location, $timeout, transactionService, bankService) {

    var _id = bankService.accountId;

    var _getAllTransaction = function() {
      $scope.vm.loading = true;
        transactionService.getAllTransactionsForEachAccountById(_id)
          .then(function(response) {
            $timeout(function() {
              angular.forEach(response.data, function(item) {
              var date = new Date(item.modifiedDate);
              item.modifiedDate = date.toLocaleString()
              });
              $scope.vm.transactionRecords = response.data;
              $scope.vm.loading = false;
            }, 1500);
          });
    };

   


    var viewModel = {
      id: _id,
      getAllTransaction: _getAllTransaction
    }
    $scope.vm = viewModel;
    $scope.vm.getAllTransaction();


 

  }]);
