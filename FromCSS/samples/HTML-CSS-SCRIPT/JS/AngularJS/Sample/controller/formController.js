var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope){
	$scope.master = {firstname:"John", lastname:"Cena"};
	$scope.reset = function() {
		$scope.user = angular.copy($scope.master);
	};
	$scope.reset();
});