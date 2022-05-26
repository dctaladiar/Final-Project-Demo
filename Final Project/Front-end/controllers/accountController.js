myApp.controller('accountController', ['$scope', '$location', 'accountService', 'bankService', function($scope, $location, accountService, bankService) {

  var _id = bankService.accountId;

  if (_id === 0 || _id === null) {
    $location.path('/');
  }

  accountService.getAccountById(_id)
    .then(function(response) {
      $scope.vm.account = response.data;
    });

  var _changePin = function() {
    bankService.accountId = $scope.vm.account.id;
    $location.path('/pin');
  };

  var _actionType = function(type) {
    console.log(type);
  };

  var _open = function (typeOfAccount) {
    bankService.accountId = $scope.vm.account.id;
      if(typeOfAccount === "Savings") {
        $location.path('/savings')
      } else if(typeOfAccount === "Current") {
        $location.path('/current')
      } else if(typeOfAccount === "Checking") {
        $location.path('/checking')
      }
  };

  var _transactionHistory = function() {
      bankService.accountId = $scope.vm.account.id;
      $location.path('/transaction')
  };

  var viewModel = {
    isMoneyTransactionInvolved: false,

    toggleTransactionView: function(isMoneyTransactionInvolved, type) {
      bankService.typeOfTransaction = type;
      $scope.vm.isMoneyTransactionInvolved = isMoneyTransactionInvolved;
    },

    changePin: _changePin,
    actionType: _actionType,
    id: _id,
    open: _open,
    transactionHistory: _transactionHistory
  };
  $scope.vm = viewModel;

}]);
