
myApp.controller('refundController', ['$scope', '$location', '$timeout', 'bankService', function($scope, $location, $timeout, bankService) {

  $scope.amountToClaim = bankService.amountToClaim;

  $scope.claim = function () {

    $scope.amountToClaim = "Claiming . . ."

    $timeout(function() {
      $location.path('/');
    }, 2000);

  };

}]);
