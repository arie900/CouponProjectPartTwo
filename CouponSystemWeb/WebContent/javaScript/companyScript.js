var app=angular.module('companyApp',[]);
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
app.controller('companyCtrl',function($scope,$http){
	 
	$scope.showCreateCoupon=false;
	$scope.openGetCouponByid=false;
	$scope.showDeleteCoupon=false;
	$scope.showAllCoupons=false;
	$scope.typeTable=false;
	$scope.showCouponById=false;
	$scope.showSingleCoupons=false;
	$scope.singleTypeTable=false;
	
	
	$scope.openCreateCoupon=function(){
		$scope.showCreateCoupon=$scope.showCreateCoupon? false : true;
	}
	
	$scope.openCouponbyId=function(){
		$scope.openGetCouponByid=$scope.openGetCouponByid? false : true;
	}
	
	$scope.openDeleteCoupon=function(){
		$scope.showDeleteCoupon=$scope.showDeleteCoupon? false : true;
	}
	
	$scope.createCoupon=function(){
	
	var coupon=new Object();
	coupon.id=$scope.id;
	coupon.title=$scope.title;
	coupon.startDate=$scope.startDate;
	coupon.endDate=$scope.endDate;
	coupon.amount=$scope.amount;
	coupon.type=$scope.type;
	coupon.message=$scope.message;
	coupon.price=$scope.price;
	coupon.image=$scope.image;
	
	
		$http.post("rest/myCompany/coupon",coupon)
		.then(function(response){
			$scope.createdCoupon=response.data;
			if($scope.createdCoupon.id==0){
			$scope.createdCoupon="Coupon not created";
			}
			else{
				$scope.createdCoupon="Coupon created"
				
			}
			
		});
	};
	
	
	$scope.getCouponId=function(){
		$http.get("rest/myCompany/getcoupon/" + $scope.couponId)
		.then(function(response){
			$scope.getCouponById=response.data
			if($scope.getCouponById.id==0){
				$scope.getCouponByIdWorng="Coupon with the id: " + $scope.couponId + " not found";
				$scope.showUpdateCoupon=false;
				$scope.showCouponById=false;
			}
			else {
				$scope.showUpdateCoupon=true;
				$scope.showCouponById=true;
				
			}
		});
	}
	
	
	
	$scope.updateCoupon=function(){
		var coupon = new Object();
		coupon.id=$scope.couponId;
		coupon.endDate = $scope.newEndDate;
		coupon.price=$scope.newPrice;

		$http.put("rest/myCompany/updatecoupon/" + $scope.couponId, coupon)
		.then(function(response) {
			$scope.updatedCoupon = "coupon updaed successfuly"
			$scope.showUpdateCoupon=false;
		});
	};
		
	
	
	
	
	
	
	
	$scope.deleteCoupon=function(){
		$http.delete("rest/myCompany/deletecoupon/" + $scope.deleteCouponId)
		.then(function(response){
			$scope.deletedCoupon=response.data
			if($scope.deletedCoupon.id==0){
				$scope.deletedCoupon="Coupon with the id: " + $scope.deleteCouponId + " not found";
			}
			else{
				$scope.deletedCoupon="Coupon successfuly deleted";
			}
		});
	}
	
	$scope.getAllCoupons=function(){
		$http.get("rest/myCompany/getall")
		.success(function(response){
			if(response!=null){
			$scope.getCoupons=response.coupon
			$scope.showAllCoupons=$scope.showAllCoupons? false : true;
			if(!angular.isArray($scope.getCoupons)){
				$scope.showAllCoupons=false;
				$scope.showSingleCoupons=$scope.showSingleCoupons ? false : true;
			}}
			else{
				$scope.emptyCouponList="Coupon list is empty"
					$scope.showAllCoupons=false;
					$scope.showSingleCoupons=false;
			}
		});
	}
	
	
	
	$scope.getCouponType=function(){
		$http.get("rest/myCompany/couponbytype/" + $scope.couponType)
		.success(function(response){
			if(response!=null){
			$scope.getCouponByType=response.coupon
			$scope.typeTable=$scope.typeTable? false : true;
			if(!angular.isArray($scope.getCouponByType)){
				$scope.typeTable=false;
				$scope.singleTypeTable=$scope.singleTypeTable ? false : true;
			}
			}
			else{
				$scope.emptyCouponListByType="Coupon lst by type is empty"
			}
		});
	}
});