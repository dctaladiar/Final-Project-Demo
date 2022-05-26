myApp.controller('cardController', ['$scope', '$location', '$timeout', 'cardService', 'bankService', function($scope, $location, $timeout, cardService, bankService) {

  var _id = null;
  bankService.accountId = 0;

  var _insert = function() {
    var vm = $scope.vm;
    vm.isCardReady = true;
  }

  var _idValidation = function(response) {
    if (response != 0) {
      bankService.accountId = response;
      $scope.vm.id= response;
      $scope.vm.isCardIdAvailable = true;
      $scope.vm.isCardReady = false;
    } else {
      $scope.vm.isCardIdAvailable = false;
      $scope.vm.isCardReady = false;
      $scope.vm.cardNumber = null;
    }
  };

  var _enterCardId = function() {
    _id = $scope.vm.cardNumber;

    cardService.checkCardById(_id)
      .then(function(response) {
        _idValidation(response.data);
      });
  };

  var _resetValues = function () {
    $scope.vm.isPinCodeCorrect = false;
    $scope.vm.loading = false;
    bankService.accountId = 0;
    $scope.vm.pinCode = null;
    $scope.vm.cardNumber = null;
    $scope.vm.isCardReady = false;
    $scope.vm.isCardIdAvailable = false;
    bankService.cardId = 0
  };

  var _pinCodeValidation = function(response) {
    if (response === "true") {
      $timeout(function() {
        bankService.accountId = $scope.vm.id;
        $location.path('/account');
      }, 1500);
    } else {
      $scope.vm.isPinCodeCorrect = true;
      $timeout(function() {
        _resetValues();
      }, 1500);
    }
  };

  var _proceed = function() {
    if ($scope.vm.pinCode.length === 6) {
      $scope.vm.isErrorMessageShown = false;
      $scope.vm.isCardIdAvailable = false;
      $scope.vm.loading = true;

      var cardPinCode = {
        pinCode: $scope.vm.pinCode
      };

      cardService.checkIfPinCodeIsCorrect(_id, cardPinCode)
        .then(function(response) {
          _pinCodeValidation(response.data);
        });
    } else {
      $scope.vm.isErrorMessageShown = true;
    }
  };


  var viewModel = {
    isErrorMessageShown: false,
    isPinCodeCorrect: false,
    loading: false,
    isCardIdAvailable: false,
    isCardReady: false,
    id: _id,
    insert: _insert,
    enterCardId: _enterCardId,
    proceed: _proceed
  };

  $scope.vm = viewModel;


}]);
