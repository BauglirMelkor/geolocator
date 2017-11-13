angular.module('locatorApp', [])
.controller('LocatorController', function($scope, $http) {
    $scope.searchClosestStoreLocation = function(longitute,latitude,distance,size) {
        var requestUrl = 'http://localhost:8080/locator/'+longitute+"/"+latitude+"/"+distance+"/"+size;
        $http.get(requestUrl).then(function (response) {

            $scope.stores = response.data;
        });
    }
});
