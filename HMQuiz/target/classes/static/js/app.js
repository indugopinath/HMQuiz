var app = angular.module('app', ['ngRoute','ngResource']);


app.controller('RNController', ['$scope', 'RNServiceJS', function($scope, RNServiceJS) {
	
//	$scope.homeSettings = [];
	$scope.newRandomNumber;
	$scope.randomList;
	$scope.randomHistory;
	
 	$scope.generateRandomNumber = function() {
 		RNServiceJS.generateRandomNumber().then(
				function success(response) {
					$scope.newRandomNumber = response.data;
					$scope.loadAllRNumbers();
					////alert('Generated'+$scope.newRandomNumber);
			    }, function error(response) {
			    	//alert('Someting is wrong! Please check your input.')
			    }
		)
 		
 	};
 	
 	$scope.displaySortHistory = function() {
 		//alert("In the displaySortHistory method");
 		RNServiceJS.displaySortHistory().then(
				function success(response) {
					//alert("successfully got the response");
					$scope.randomHistory = response.data;
					//$scope.loadAllRNumbers();
					//alert('Random number histry'+$scope.randomHistory);
			    }, function error(response) {
			    	//alert('Someting is wrong! Please check your input.')
			    }
		)
 		
 	};
 	
 	
 	$scope.loadAllRNumbers = function() {
 		RNServiceJS.loadAllRNumbers().then(
				function success(response) {
					$scope.randomList = response.data;
				//	//alert("The total duration is:"+$scope.randomList.sortDuration);
					////alert("The total swap  is:"+$scope.randomList.swapNumber);
					////alert("The total numbers in the list is:"+$scope.randomList.sortedList);
				    }, 
				    function error(response) {
			    	//alert('Someting is wrong while loading Randomnumbers! Please check your input.')
			    }
		)
 	
 	};
 	
 	
 
}]);


app.service('RNServiceJS', function($http){

	var self = this;
    self.generateRandomNumber = function () {
    	return $http.get('/getNewRandomNumber');
    };
    
    self.loadAllRNumbers = function () {
    	return $http.get('/loadAllRandomNumbers');
    };
    
    self.displaySortHistory = function () {
    	return $http.get('/sortingHistory');
    };
    
    

})


