// SERVICES

// Common Service
myApp.service('bankService', [function() {
  this.accountId = 0;
  this.typeOfTransaction = null;
  this.amountToClaim = null;
  this.isTransactionSuccess = null;
  this.isWithdrawalAtLimit = false;
  this.isInvalidAccountNumber = false;
  this.isInsufficientBalance = false;
  this.isTransactionHasReceipt = false;
  this.typeOfAccount = null;
}]);


// Account Service
myApp.service('accountService', ['$http', function($http) {
  this.getAccountById = function(id) {
    return $http.post(apiUrl + "/account/getAccountById/"+ id);
  }
}]);

// Card Service
myApp.service('cardService', ['$http', function($http) {

  this.checkCardById = function (id) {
    return $http.post(apiUrl + "/card/checkCardById/" + id);
  };

  this.checkIfPinCodeIsCorrect = function (id, card) {
    return $http.post(apiUrl +"/card/checkIfPinCodeIsCorrect/" + id, card)
  };

  this.checkIfPinCodeIsCorrectForChangePinCode = function (id, card) {
    return $http.post(apiUrl +"/card/checkIfPinCodeIsCorrectForChangePinCode/" + id, card)
  };

  this.changePinCode = function (id, card) {
    return $http.put(apiUrl + "/card/changePinCode/" + id, card)
  };


}]);

// Saving Service
myApp.service('savingService', ['$http', function($http) {

  this.getSavingsBalanceById = function (id) {
    return $http.post(apiUrl + "/saving/getSavingsBalanceById/" + id);
  };

  this.withdrawInSavingBalance = function (id, saving) {
    return $http.put(apiUrl + "/saving/withdrawInSavingBalance/" + id, saving);
  };

  this.overrideWithdrawInSavingBalance = function (id, saving) {
    return $http.put(apiUrl + "/saving/overrideWithdrawInSavingBalance/" + id, saving);
  };

  this.depositInSavingBalance = function (id, saving) {
    return $http.put(apiUrl + "/saving/depositInSavingBalance/" + id, saving);
  };

  this.transferFundsToOtherAccount = function(id, id_2, saving) {
    return $http.put(apiUrl + "/saving/transferFundsToOtherAccount/" + id + "/" + id_2, saving);
  };

  this.overrideTransferOfFundsToOtherAccount = function(id, id_2, saving) {
    return $http.put(apiUrl + "/saving/overrideTransferOfFundsToOtherAccount/" + id + "/" + id_2, saving);
  };

}]);

myApp.service('currentService', ['$http', function($http) {

  this.getCurrentBalanceById = function (id) {
    return $http.post(apiUrl + "/current/getCurrentBalanceById/" + id);
  };

  this.withdrawInCurrentBalance = function (id, current) {
    return $http.put(apiUrl + "/current/withdrawInCurrentBalance/" + id, current);
  };

  this.overrideWithdrawInCurrentBalance = function (id, current) {
    return $http.put(apiUrl + "/current/overrideWithdrawInCurrentBalance/" + id, current);
  };

  this.depositInCurrentBalance = function (id, current) {
    return $http.put(apiUrl + "/current/depositInCurrentBalance/" + id, current);
  };

  this.transferFundsToOtherAccount = function(id, id_2, current) {
    return $http.put(apiUrl + "/current/transferFundsToOtherAccount/" + id + "/" + id_2, current);
  };

  this.overrideTransferOfFundsToOtherAccount = function(id, id_2, current) {
    return $http.put(apiUrl + "/current/overrideTransferOfFundsToOtherAccount/" + id + "/" + id_2, current);
  };

}]);

myApp.service('checkingService', ['$http', function($http) {

  this.getCheckingBalanceById = function (id) {
    return $http.post(apiUrl + "/checking/getCheckingBalanceById/" + id);
  };

  this.withdrawInCheckingBalance = function (id, checking) {
    return $http.put(apiUrl + "/checking/withdrawInCheckingBalance/" + id, checking);
  };

  this.overrideWithdrawInCheckingBalance = function (id, checking) {
    return $http.put(apiUrl + "/checking/overrideWithdrawInCheckingBalance/" + id, checking);
  };

  this.depositInCheckingBalance = function (id, checking) {
    return $http.put(apiUrl + "/checking/depositInCheckingBalance/" + id, checking);
  };

  this.transferFundsToOtherAccount = function(id, id_2, checking) {
    return $http.put(apiUrl + "/checking/transferFundsToOtherAccount/" + id + "/" + id_2, checking);
  };

  this.overrideTransferOfFundsToOtherAccount = function(id, id_2, checking) {
    return $http.put(apiUrl + "/checking/overrideTransferOfFundsToOtherAccount/" + id + "/" + id_2, checking);
  };

}]);

myApp.service('transactionService', ['$http', function($http) {

  this.insertNewTransaction = function (transaction) {
    return $http.post(apiUrl + "/transaction/insertNewTransaction", transaction);
  };


  this.getAllTransactionsForEachAccountById = function (id) {
    return $http.post(apiUrl + "/transaction/getAllTransactionsForEachAccountById/" + id);
  };

  this.getTransactionForReceipt = function (id, id_2) {
    return $http.post(apiUrl + "/transaction/getTransactionForReceipt/" + id + "/" + id_2);
  };

}]);
