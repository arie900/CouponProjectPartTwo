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
    
    
app.controller('companyCtrl', function($scope, $http) { 
	
	$scope.showCompanyCreate=false;
	$scope.companyList=false;
	$scope.showCompanyId=false;
	$scope.showCompanyDelete=false;
	$scope.showCompanyIncome=false;
	$scope.showAllcompanies=false
	$scope.showCmpanId=false;
	$scope.showCompanyIncome=false;
	$scope.allIncomeList=false;
	$scope.companySingle=false;
	$scope.singleIncomeCompany=false
	$scope.companyIncomeList=false
	$scope.incomeList=false;
	$scope.incomeSingle=false;

	$scope.postdata = function() {

		var company = new Object();
		company.id = $scope.id;
		company.compName = $scope.compName;
		company.password = $scope.password;
		company.email = $scope.email;
		
		
		$http.post("rest/myAdmin/company", company).then(function(response) {
			$scope.createCompany=response.data;
			if($scope.createCompany.id==0){
				$scope.createCompany="Company not created";
				
			}
			else{
				$scope.createCompany="Company created";
			}
		})

	};
	$scope.OpenCreate=function(){
		$scope.showCompanyCreate = $scope.showCompanyCreate ? false : true;
	}

	$scope.getCompany = function() {
		$scope.showMe = false;
		$http.get("rest/myAdmin/companyid/" + $scope.companyId + "/").then(
				function(response) {
					$scope.getCompanyId = response.data
					$scope.showMe = !$scope.showMe;
					if($scope.getCompanyId.id==0){
						$scope.getCompanyIdWrong="Company with the id: " + $scope.companyId + " not found";
						$scope.showMe = false;
						$scope.showCmpanId=false;
					}
					else{
						$scope.showCmpanId=true;
					}
				});

	};

	$scope.updateCompany = function() {
		var company=new Object();
		company.password=$scope.newPass;
		company.email=$scope.newMail;
		company.compName=$scope.getCompanyId.compName;
		company.id=$scope.getCompanyId.id;
		$http.put("rest/myAdmin/updatecompany/" + $scope.getCompanyId.compName + "/",company).then(
				function(response) {
					$scope.getUpdatedCompany = "Company with the id: " + $scope.companyId + " updated";
					$scope.showMe=false;
				});
	};

	$scope.getAllCompanies = function() {
		$http.get("rest/myAdmin/companies").success(function(response) {
			if(response!=null){
			$scope.getCompanies = response.company;
			$scope.companyList = $scope.companyList ? false : true;
			$scope.showAllcompanies=true;
			if(!angular.isArray($scope.getCompanies)){
				$scope.companyList=false;
				$scope.companySingle = $scope.companySingle ? false : true;
			}
			}
			
			else{
				$scope.emptyAllCompanies="Company list is empty";
				$scope.companyList=false
			}
		});

	};
	$scope.openCompanyId=function(){
		$scope.showCompanyId = $scope.showCompanyId ? false : true;
	}

	$scope.deleteCompany = function() {
		$http.delete("rest/myAdmin/deletecompany/" + $scope.deleteCompanyId).then(
				function(response) {
					$scope.deletedCompany = response.data;
					if($scope.deletedCompany.id==0){
						$scope.deletedCompany="Company with the name: " + $scope.deleteCompanyId + " not found";
					}
					else{
						$scope.deletedCompany="Company successfuly deleted"
					}
				});
	};

	$scope.openDeleteCompany=function(){
		$scope.showCompanyDelete = $scope.showCompanyDelete ? false : true;
	}
	
	$scope.incomeCompany=function(){
		$http.get("http://localhost:8080/CouponSystemWeb/rest/myAdmin/incomecompany/" + $scope.incomeCompanyId)
		.success(function(response){
			if(response!=null){
			$scope.getIncomeCompany=response.income;
			$scope.companyIncomeList=$scope.companyIncomeList ? false : true;
			if(!angular.isArray($scope.getIncomeCompany)){
				$scope.singleIncomeCompany=$scope.singleIncomeCompany ? false : true;
				$scope.companyIncomeList=false;
				
			}
			}
			else{
				$scope.emptyCompanyincome="The income of the company is empty";
				$scope.showCompanyIncome = false;
			}
		})
	}
	 
	$scope.openIncomeCompany=function(){
		$scope.showCompanyIncome = $scope.showCompanyIncome ? false : true;
	}
	
	$scope.AllIncome=function(){
		$http.get("http://localhost:8080/CouponSystemWeb/rest/myAdmin/allincome")
		.success(function(response){
			if(response!=null){
				$scope.getAllIncomes=response.income;
				$scope.allIncomeList=$scope.allIncomeList? false : true;
				$scope.incomeList=true;
				if(!angular.isArray($scope.getAllIncomes)){
					$scope.incomeList=false;
					$scope.incomeSingle=$scope.incomeSingle ? false : true;
				}
			}
			else{
				$scope.emptyAllIncome="The income list is empty";
				$scope.allIncomeList=false;
			}

		})
	}

	});
	
		



app.controller('customerCtrl', function($scope, $http) {
	$scope.showCreateCustomer=false;
	$scope.customerList=false;
	$scope.showcustomerId=false;
	$scope.showDeleteCustomer=false;
	$scope.showCustomerIncome=false;
	$scope.showCustomerTable=false;
	$scope.showCustomerList=false;
	$scope.customerSingleList=false;
	$scope.customerIncomeList=false;
	$scope.customerSingleIncome=false;
	
	$scope.createCustomer = function() {

		var customer = new Object();
		customer.id = $scope.id;
		customer.custName = $scope.custName;
		customer.password = $scope.password;
		

		var customerJson=JSON.stringify(customer);
		
		$http.post("rest/myAdmin/customer", customerJson).then(
				function(response) {
					$scope.createdCustomer = response.data;
					if($scope.createdCustomer.id==0){
						$scope.createdCustomer="Customer not created";
					}
					else{
					$scope.createdCustomer="Customer created";
					}
				})

	};
	$scope.openCreateCustomer=function(){
		$scope.showCreateCustomer=$scope.showCreateCustomer ? false : true;
	}

	$scope.getAllCustomers = function() {
		$http.get("rest/myAdmin/customers").success(function(response) {
			if(response!=null){
			$scope.getCustomers = response.customer;
			$scope.customerList = $scope.customerList ? false: true;
			if(!angular.isArray($scope.getCustomers)){
				$scope.customerList=false;
				$scope.customerSingleList=$scope.customerSingleList ? false : true;
				
			}
			}
			else{
				$scope.emptyCustomerList="Customer list is empty";
				$scope.customerList=false;
			}
		});

	};

	$scope.getCustomer = function() {
		$scope.showUpdateCustomer = $scope.showUpdateCustomer = false;
		$http.get("rest/myAdmin/customerid/" + $scope.customerId + "/").then(
				function(response) {
					$scope.getCustomerId = response.data;
					$scope.showUpdateCustomer = !$scope.showUpdateCustomer;
					if($scope.getCustomerId.id==0){
						$scope.getCustomerIdWorng="Customer with the id: " + $scope.customerId + " not found";
						$scope.showUpdateCustomer = false;
						$scope.showCustomerTable=false;
					}
					else{
						$scope.showCustomerTable=true;
					}
					

				});

	};

	$scope.deleteCustomer = function() {
		$http.delete('rest/myAdmin/deletecustomer/' + $scope.deleteCustomerId)
				.then(function(response) {
					$scope.deletedCustomer = response.data;
					if($scope.deletedCustomer.id==0){
						$scope.deletedCustomer="Customer with the id: " + $scope.deleteCustomerId + " not found";
					}
					else{
						$scope.deletedCustomer="Customer successfuly deleted";
						
					}
				});
	};

	$scope.updateCustomer = function() {

		var customer = new Object();
		customer=$scope.getCustomerId;
		customer.password=$scope.newCustPassword
			
		$http.put('rest/myAdmin/updatecustomer/' + customer.custName + '/',
				customer).then(function(response) {
			$scope.updatedCustomer =response.data;
			
			if($scope.updatedCustomer.id==0){
				$scope.updatedCustomer = "Customer not found";
			}
			else{
				$scope.updatedCustomer = "Customer successfuly updated";
				$scope.showUpdateCustomer = false;
			}
		});
	};
	
	$scope.openCustomerId=function(){
		$scope.showCustomerId=$scope.showCustomerId? false : true ;
	}
	
	$scope.openDeleteCustomer=function(){
		$scope.showDeleteCustomer=$scope.showDeleteCustomer? false: true;
	}
	
	$scope.CustomerIncome=function(){
		$http.get("rest/myAdmin/incomecustomer/" + $scope.incomeCustomerId)
			.success(function(response){
				if(response!=null){
				$scope.getCustomerIncomes=response.income;	
				$scope.customerIncomeList=$scope.customerIncomeList ? false : true;
				if(!angular.isArray($scope.getCustomerIncomes)){
					$scope.customerIncomeList=false;
					$scope.customerSingleIncome=$scope.customerSingleIncome ? false : true;
				}
		}
			else{
				$scope.emptyCustomerIncome="Customer income is empty";
				
			}

		});
	}
	
	$scope.openCustomerIncome=function(){
		$scope.showCustomerIncome=$scope.showCustomerIncome? false : true;
	}

});
