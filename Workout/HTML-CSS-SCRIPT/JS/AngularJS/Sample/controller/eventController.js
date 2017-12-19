var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope){
	$scope.count = 10;
	$scope.myVar = "true";
	$scope.tog = function() {
		$scope.myVar = !$scope.myVar;
	};
});