angular.module('locatorApp', [])
.controller('LocatorController', function($scope, $http) {
    $scope.searchClosestStoreLocation = function(longitute,latitude,distance,size) {
        if(longitute===undefined || longitute==null || longitute==""){
            longitute=-181;
        }
        if(latitude===undefined || latitude==null || latitude==""){
            latitude=-91;
        }
        if(distance===undefined || distance==null || distance==""){
            distance=-1;
        }
        if(size===undefined || size==null || size==""){
            size=-1;
        }
        var requestUrl = 'http://localhost:8080/locator/'+longitute+"/"+latitude+"/"+distance+"/"+size;
        $http.get(requestUrl).then(function (response) {

            $scope.stores = response.data;
        });
    }
});
