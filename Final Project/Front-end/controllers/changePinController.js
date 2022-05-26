
  myApp.controller('changePinController', ['$scope', '$location', '$timeout', 'cardService', 'bankService', function($scope, $location, $timeout, cardService, bankService) {

    var _id = bankService.accountId;
    

    var _pinCodeValidation = function() {
      if ($scope.vm.isPinCodeCorrect === "true") {
        $scope.vm.isPinCodeCorrect = true;
      } else {
        $scope.vm.isPinCodeCorrect = false;
      }
    };

    var _enterPinCode = function() {
 
      if($scope.vm.oldPinCode.length === 6) {
        $scope.vm.loading = true;
        $scope.vm.isErrorMessageShown = false;
        var cardPinCode = {
          pinCode: $scope.vm.oldPinCode
        };
        cardService.checkIfPinCodeIsCorrectForChangePinCode(_id, cardPinCode)
          .then(function(response) {
            $timeout(function() {
              $scope.vm.loading = false;
              $scope.vm.isPinCodeCorrect = response.data;
              console.log($scope.vm.oldPinCode);
              _pinCodeValidation();
            }, 2000);
          });
      } else {
        $scope.vm.isErrorMessageShown = true;
      }
     
    };

    var _changePinValidation = function() {
     
      if ($scope.vm.changePinResult === "Success") {
        bankService.isTransactionSuccess = true;
        bankService.typeOfTransaction = "Change Pin";
        $location.path('/success');

       
      } else {
        
        $location.path('/');
      } 
    };

    var _changePin = function() {
      $scope.vm.loading = true;
      var changedPinCode = {
        pinCode: $scope.vm.newPassword.toString()
      };

      if($scope.vm.newPassword === null){
        console.log($scope.vm.newPassword);
      }
      cardService.changePinCode(_id, changedPinCode)
        .then(function(response) {
          $timeout(function() {
            $scope.vm.loading = false;
            $scope.vm.changePinResult = response.data;
            _changePinValidation();
          }, 2000);

         
        })
    };


    var viewModel = {
      typeOfTransaction: "Change Pin",
      loading: false,
      newPassword: null,
      confirmPassword: null,
      isPinCodeCorrect: false,
      enterPinCode: _enterPinCode,
      changePin: _changePin
    }
    $scope.vm = viewModel;



  }]);
