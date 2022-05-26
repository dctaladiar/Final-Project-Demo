myApp.controller('currentController', ['$scope', '$location', '$timeout', 'bankService', 'currentService', 'transactionService', function($scope, $location, $timeout, bankService, currentService, transactionService) {

  var _id = bankService.accountId;
  var _typeOfTransaction = bankService.typeOfTransaction;

  var _getBalance = function() {
    if (_typeOfTransaction === "Balance") {
      currentService.getCurrentBalanceById(_id)
        .then(function(response) {
          $scope.vm.balance = response.data;
        });
    }
  };

  _getBalance();

  var _makeAnotherTransaction = function(_id) {
    bankService.accountId = $scope.vm.id;
    $location.path('/account')
  };


  var _addNewTransaction = function(amount) {
    var transactionModel = {
      transactionType: $scope.vm.typeOfTransaction,
      typeOfAccount: $scope.vm.typeOfAccount,
      amount: amount,
      accountId: $scope.vm.id
    };

    transactionService.insertNewTransaction(transactionModel)
      .then(function(response) {
        // pass parameters to the next route.
          if($scope.vm.typeOfTransaction != "Transfer") {
            bankService.amountToClaim = amount;
          }
            bankService.typeOfTransaction = $scope.vm.typeOfTransaction;
            bankService.isTransactionSuccess = true;
            bankService.isTransactionHasReceipt = $scope.vm.isTransactionWithReceipt;
            bankService.accountId = $scope.vm.id;
            bankService.typeOfAccount = $scope.vm.typeOfAccount;
            $location.path('/success');
      });
  };


  var _withdraw = function(isTransactionWithReceipt) {
    $scope.vm.loading = true;
    $scope.vm.isTransactionWithReceipt = isTransactionWithReceipt;

    var amount = {
      balance: $scope.vm.amountOfMoneyToWithdraw
    };

    currentService.withdrawInCurrentBalance($scope.vm.id, amount)
      .then(function(response) {
        $scope.vm.withdrawResponse = response.data;
        $timeout(function() {


        // api returns 0 means the balance will be less than 2000(set recommended minimum).
        if (parseInt($scope.vm.withdrawResponse) === 0) {
          $scope.vm.loading = false;
          $scope.vm.isWithdrawalOverride = true;
        // api returns 2 means withdrawal limit is reached (50000)
        } else if (parseInt($scope.vm.withdrawResponse) === 2) {
          bankService.typeOfTransaction = $scope.vm.typeOfTransaction;
          bankService.isTransactionSuccess = false;
          bankService.isWithdrawalAtLimit = true;
          $location.path('/success');

        } else {
          _addNewTransaction($scope.vm.withdrawResponse);
        }

          }, 1500);
      });
  };


  var _addNewTransactionForoverrideWithdraw = function() {

    if (parseInt($scope.vm.amountOfMoneyToWithdraw) === parseInt($scope.vm.overrideWithdrawResponse)) {

      _addNewTransaction($scope.vm.overrideWithdrawResponse);

    } else {
      bankService.typeOfTransaction = $scope.vm.typeOfTransaction;
      bankService.isTransactionSuccess = false;
      $location.path('/success');
    }

  };

  var _overrideWithdraw = function(action) {
      $scope.vm.loading = true;
    if (action === 1) {
      var amount = {
        balance: $scope.vm.amountOfMoneyToWithdraw
      };

      currentService.overrideWithdrawInCurrentBalance($scope.vm.id, amount)
        .then(function(response) {
          $timeout(function() {

          $scope.vm.overrideWithdrawResponse = response.data;
          _addNewTransactionForoverrideWithdraw();
        }, 1500);
        });
    } else {
      $scope.vm.loading = false;
      $scope.vm.isWithdrawalOverride = false;
      $scope.vm.isReceiptForTransaction = false;
    }

  };

  var _addNewTransactionForDepositIfValid = function() {

    if (parseInt($scope.vm.amountOfMoneyToDeposit) != parseInt($scope.vm.depositResponse)) {

      var transactionModel = {
        transactionType: $scope.vm.typeOfTransaction,
        typeOfAccount: $scope.vm.typeOfAccount,
        amount: $scope.vm.amountOfMoneyToDeposit - $scope.vm.depositResponse,
        accountId: $scope.vm.id
      };

      transactionService.insertNewTransaction(transactionModel)
        .then(function(response) {
            bankService.amountToClaim = $scope.vm.depositResponse;
            bankService.typeOfTransaction = $scope.vm.typeOfTransaction;
            bankService.isTransactionSuccess = true;
            bankService.isTransactionHasReceipt = $scope.vm.isTransactionWithReceipt;
            bankService.accountId = $scope.vm.id;
            bankService.typeOfAccount = $scope.vm.typeOfAccount;
            $location.path('/success');
        });
    } else {
      bankService.amountToClaim = $scope.vm.depositResponse;
      bankService.typeOfTransaction = $scope.vm.typeOfTransaction;
      bankService.isTransactionSuccess = false;
      $location.path('/success');
    }

  };



  var _deposit = function(isTransactionWithReceipt) {
      $scope.vm.loading = true;
    $scope.vm.isTransactionWithReceipt = isTransactionWithReceipt;

    var amount = {
      balance: $scope.vm.amountOfMoneyToDeposit
    };

    currentService.depositInCurrentBalance($scope.vm.id, amount)
      .then(function(response) {
        $scope.vm.depositResponse = response.data;

        $timeout(function() {

        _addNewTransactionForDepositIfValid();
      }, 1500);
      });
  };



  var _transferFunds = function(isTransactionWithReceipt) {
$scope.vm.isTransactionWithReceipt = isTransactionWithReceipt;
  $scope.vm.loading = true;
    var amount = {
      balance: $scope.vm.amountToTransfer
    };

    currentService.transferFundsToOtherAccount($scope.vm.id, $scope.vm.accountNumber, amount)
      .then(function(response) {
        $scope.vm.transferFundResponse = response.data;
        $timeout(function() {

        if (parseInt($scope.vm.transferFundResponse) === 0) {
          bankService.typeOfTransaction = $scope.vm.typeOfTransaction;
          bankService.isTransactionSuccess = false;
          bankService.isInvalidAccountNumber = true;
          $location.path('/success');

        } else if (parseInt($scope.vm.transferFundResponse) === 2) {

          bankService.typeOfTransaction = $scope.vm.typeOfTransaction;
          bankService.isTransactionSuccess = false;
          bankService.isInvalidAccountNumber = false;
          bankService.isWithdrawalAtLimit = true;
          $location.path('/success');

        } else if (parseInt($scope.vm.transferFundResponse) === 1) {

          _addNewTransaction($scope.vm.amountToTransfer);

        } else {
          $scope.vm.loading = false;
          $scope.vm.isTransferFundsOverride = true;
        }
      }, 1500);
      });
  };

  var _overrideTransferOfFundsToOtherAccount = function(action) {
      $scope.vm.loading = true;
    if (action === 1) {
      var amount = {
        balance: $scope.vm.amountToTransfer
      };

      currentService.overrideTransferOfFundsToOtherAccount($scope.vm.id, $scope.vm.accountNumber, amount)
        .then(function(response) {
          $scope.vm.overrideTransfer = response.data;
          $timeout(function() {

          if (parseInt($scope.vm.overrideTransfer) === 1) {

            _addNewTransaction($scope.vm.amountToTransfer);

          } else {
            bankService.isTransactionSuccess = false;
            bankService.typeOfTransaction = $scope.vm.typeOfTransaction
            bankService.isInsufficientBalance = true;
            $location.path('/success');
          }
        }, 1500);
        });
    } else {
      $scope.vm.loading = false;
      $scope.vm.isTransferFundsOverride = false;
      $scope.vm.isReceiptForTransaction = false;
    }
  };

  var _checkIfInputIsValid = function(value) {
      if(value % 100 === 0 || value === null) {
        $scope.vm.isSubmitButtonDisable = false;
        $scope.vm.isErrorMessageShown = false;
      } else {
        $scope.vm.isSubmitButtonDisable = true;
        $scope.vm.isErrorMessageShown = true;
      }
  };

  var _toggleReceiptForTransaction = function(isReceiptForTransaction) {
    $scope.vm.isReceiptForTransaction = isReceiptForTransaction;
  };


  var viewModel = {
    isErrorMessageShown: false,
    isSubmitButtonDisable: true,
    isTransactionWithReceipt: false,
    isWithdrawalOverride: false,
    isReceiptForTransaction: false,
    id: _id,
    typeOfTransaction: _typeOfTransaction,
    typeOfAccount: "Current",
    makeAnotherTransaction: _makeAnotherTransaction,
    withdraw: _withdraw,
    deposit: _deposit,
    transferFunds: _transferFunds,
    overrideWithdraw: _overrideWithdraw,
    isTransferFundsOverride: null,
    overrideTransferOfFundsToOtherAccount: _overrideTransferOfFundsToOtherAccount,
    toggleReceiptForTransaction: _toggleReceiptForTransaction,
    checkIfInputIsValid: _checkIfInputIsValid
  };

  $scope.vm = viewModel;
}]);
