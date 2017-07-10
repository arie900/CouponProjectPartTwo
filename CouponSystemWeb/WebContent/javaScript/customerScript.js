var app = angular.module('myApp', []);
app.factory('redirectInterceptor', function($q,$location,$window){
    return  {
        'response':function(response){
        if (typeof response.data === 'string' && response.data.indexOf("login")>-1) {
            console.log("LOGIN!!");
            console.log(response.data);
            $window.location.href = "http://localhost:8080/CouponSystemWeb";
            return $q.reject(response);
        }else{
            return response;
        }
        }
    }

    });

    app.config(['$httpProvider',function($httpProvider) {
        $httpProvider.interceptors.push('redirectInterceptor');
    }]); 
app.controller('customerCtrl', function($scope, $http){
	$scope.showCoupon=false;	
	$scope.showCouponPrice=false;	
	$scope.showCouponType=false;	
	$scope.showSingleCoupon=false
	$scope.showSingleCouponPrice=false;
	$scope.showSingleCouponType=false;
	
	$scope.availbleCoupon=function(){
		$http.get("rest/myCustomer/availble")
		.success(function(response){
			$scope.coupons=response.coupon;
		});
	};
	
	$scope.purchaseCoupon=function(){
		
		var coupon=new Object();
		coupon=$scope.selectedCoupon;
		
		var couponJson=JSON.stringify(coupon);
		$http.post("rest/myCustomer/purchase",coupon)
		.then(function(response){
			$scope.purchasedCoupon=response.data;
		});
	};
	
	$scope.getAllCoupons=function(){
		$http.get("rest/myCustomer/coupons").success(function(response) {
			if(response!=null){
			$scope.getCoupons = response.coupon;
			$scope.showCoupon = $scope.showCoupon ? false : true;
			$scope.showSingleCoupon=false;
			if(!angular.isArray($scope.getCoupons)){
				$scope.showCoupon=false;
				$scope.showSingleCoupon=$scope.showSingleCoupon ? false : true;	
			}
			}
			else{
				$scope.emptyCouponList="Coupon llist is empty"
			}
			
			
			
		});
	};
	
	$scope.getCouponByPrice=function(){
		$http.get("rest/myCustomer/couponprice/" + $scope.price).success(function(response) {
			if(response!=null){
			$scope.getPriceCoupons = response.coupon;
			$scope.showCouponPrice = $scope.showCouponPrice ? false : true;
			$scope.showSingleCouponPrice=false;
			if(!angular.isArray($scope.getPriceCoupons)){
				$scope.showCouponPrice=false;
				$scope.showSingleCouponPrice=true;
			}
			}
			else{
				$scope.couponListByPrice="Coupon list by price is empty"
			}
		});
	};
	
	$scope.getCouponType=function(){
		$http.get("rest/myCustomer/coupontype/" + $scope.type).success(function(response) {
			if(response!=null){
			$scope.getCouponByType = response.coupon;
			$scope.showCouponType = $scope.showCouponType ? false : true;
			$scope.showSingleCouponType=false;
			if(!angular.isArray($scope.getCouponByType)){
				$scope.showCouponType=false;
				$scope.showSingleCouponType = $scope.showSingleCouponType ? false : true;
			}
			}
			else{
				$scope.couponListByType="Coupon list by type is empty";
				$scope.showCouponType=false;
				$scope.showSingleCouponType=false;
			}
			
		});
	};
	
	
	
});