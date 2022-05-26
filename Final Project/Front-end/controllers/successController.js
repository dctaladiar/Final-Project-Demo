myApp.controller('successController', ['$scope', '$location', '$timeout', 'bankService', 'transactionService', function($scope, $location, $timeout, bankService, transactionService) {

  $scope.isTransactionSuccess = bankService.isTransactionSuccess;
  $scope.typeOfTransaction = bankService.typeOfTransaction;
  $scope.isTransactionHasReceipt = bankService.isTransactionHasReceipt;
  $scope.id = bankService.accountId;
  $scope.typeOfAccount = bankService.typeOfAccount;
  $scope.isReceiptShown = false;

  var _checkTypeOfTransaction = function () {
    if($scope.typeOfTransaction != "Change Pin") {
      if ($scope.typeOfAccount === "Savings") {
        $scope.id_2 = 1;
      } else if ($scope.typeOfAccount === "Current") {
        $scope.id_2 = 2;
      } else if ($scope.typeOfAccount === "Checking") {
        $scope.id_2 = 3;
      }
    } else {
      $scope.id_2 = null;
    }
  };
  _checkTypeOfTransaction();
  


  var doc = new jsPDF();

  function saveDiv() {
    doc.fromHTML(`<html><head><title></title></head><body>` + document.getElementById('receiptContainer').innerHTML + `</body></html>`);
    doc.save('receipt.pdf');
  }


  var _getReceiptDetails = function() {
    transactionService.getTransactionForReceipt($scope.id, $scope.id_2)
      .then(function(response) {
        angular.forEach(response.data, function(item) {
          var date = new Date(item.modifiedDate);
          item.modifiedDate = date.toLocaleString()
        });
        $scope.receipt = response.data[0];
      });
  };


  var _validate = function() {
    if ($scope.isTransactionSuccess) {
      if($scope.typeOfTransaction != "Change Pin") {
        _getReceiptDetails();
      }
    
      $scope.message = "Transaction is completed.";

      if ($scope.typeOfTransaction === "Change Pin") {
        $timeout(function() {
          $location.path('/');
        }, 2000);
      } else if ($scope.typeOfTransaction != "Transfer" && parseInt(bankService.amountToClaim) != 0) {
        if ($scope.isTransactionHasReceipt) {
          $timeout(function() {
            $scope.message = "Printing receipt . . .";
          }, 2000);

          $timeout(function() {
            saveDiv();
            $location.path('/refund');
          }, 2000);
        } else {
          $timeout(function() {
            $location.path('/refund');
          }, 2000);
        }

      } else if($scope.isTransactionHasReceipt) {
        $timeout(function() {
          $scope.isReceiptShown = true;
          $scope.message = "Printing receipt . . .";
        }, 2000);

        $timeout(function() {
          $scope.isReceiptShown = false;
          saveDiv();
          $location.path('/');
        }, 2000);
      } else {
        $timeout(function() {
          $location.path('/');
        }, 2000);
      }

    } else {
      if ($scope.typeOfTransaction === "Withdraw") {
        if (bankService.isWithdrawalAtLimit) {
          $scope.message = "Failed to complete the transaction. Withdrawal/Fund Transfer exceeds daily limit.";
        } else {
          $scope.message = "Failed to complete the transaction. Insufficient balance.";
        }
        return $timeout(function() {
          $location.path('/');
        }, 2000);
      } else if ($scope.typeOfTransaction === "Deposit") {
        $scope.message = "Failed to complete the transaction. Amount to deposit should be atleast 300 or more.";

        $timeout(function() {
          $location.path('/refund');
        }, 2000);
      } else if ($scope.typeOfTransaction === "Transfer") {
        if (bankService.isInvalidAccountNumber) {
          $scope.message = "Failed to complete the transaction. Account doesn't exist";
        }

        if (bankService.isWithdrawalAtLimit) {
          $scope.message = "Failed to complete the transaction. Withdrawal/Fund Transfer exceeds daily limit.";
        }

        if (bankService.isInsufficientBalance) {
          $scope.message = "Failed to complete the transaction. Insufficient balance.";
        }

        $timeout(function() {
          $location.path('/');
        }, 2000);
      }
    }


  };

  _validate();

}]);
