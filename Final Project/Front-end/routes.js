// ROUTES
myApp.config(function ($routeProvider) {

    $routeProvider

    .when('/account', {
        templateUrl: 'views/account.html',
        controller: 'accountController'
    })

    .when('/transaction', {
        templateUrl: 'views/transaction.html',
        controller: 'transactionController'
    })

    .when('/', {
      templateUrl: 'views/card.html',
      controller: 'cardController'
    })

    .when('/pin', {
      templateUrl: 'views/changepin.html',
      controller: 'changePinController'
    })
    .when('/success', {
      templateUrl: 'views/success.html',
      controller: 'successController'
    })
    .when('/refund', {
      templateUrl: 'views/refund.html',
      controller: 'refundController'
    })
    .when('/savings', {
      templateUrl: 'views/savings.html',
      controller: 'savingsController'
    })

    .when('/current', {
      templateUrl: 'views/current.html',
      controller: 'currentController'
    })

    .when('/checking', {
      templateUrl: 'views/checking.html',
      controller: 'checkingController'
    })

});
