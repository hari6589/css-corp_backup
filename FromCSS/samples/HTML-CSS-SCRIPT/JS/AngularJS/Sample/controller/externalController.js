var app = angular.module('myApp',[]);
app.controller('myCtrl', function($scope){
	$scope.firstname = "John";
	$scope.lastname = "Cena";
	$scope.fullname = function() {
		return $scope.firstname + " " + $scope.lastname;
	};
});