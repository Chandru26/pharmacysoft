'use strict';
app.controller('AccountsController', function ($scope,$http,$timeout,$uibModal, $log, Upload, Flash,deemsoft) 
{	
	function clearAll(){
		$scope.editAccount = false;
		$scope.payAccount = false;
		$scope.newAccount = false;
		$scope.payAmount = 0;
		$scope.accounts = [];
		$scope.account={};		
		deemsoft.getHTTP('../accountsrest/listaccounts/').then(function(r){$scope.accounts = r; });		
	}
		
	var csrf = document.getElementById("csrf_token").value;	
	var userID = document.getElementById("user__id").value;
	
	$scope.accountEditCancel = function(){
			$scope.account={};
			$scope.editAccount = false;
	}
	$scope.accountEditNew = function(){
		$scope.editAccount = true;		
		$scope.account={};
		$scope.newAccount = true;
	}
	
	$scope.accountEditSave = function(){
		if($scope.newAccount){
			$scope.account.updated_by = userID;
			$scope.account.created_by = userID;
			$scope.account.addr.updated_by = userID;
			$scope.account.addr.created_by = userID;			
		}
		deemsoft.postHTTP('../addressesrest/saveaddresses/',$scope.account.addr,csrf)
		.then(function(r){
			$scope.account.addr = r;
			$scope.account.addresses_id = $scope.account.addr.id;
			//$scope.account.addr = "";
			deemsoft.postHTTP('../accountsrest/saveaccounts/',$scope.account,csrf)
			.then(function(r){
			 Flash.create('success', "Account Data Saved Successfully!");
			 clearAll();
		});
		});		
	}
	
	//grid section
	$scope.showAccount = function(row){		
		$scope.editAccount = true;
		$scope.account={};
		$scope.account.id = row.entity.id;
		$scope.account.title = row.entity.title;
		$scope.account.firstname = row.entity.firstname;
		$scope.account.description = row.entity.description;
		$scope.account.phone = row.entity.phone;
		$scope.account.email = row.entity.email;
		$scope.account.addresses_id = row.entity.addresses_id;
		$scope.account.updated_by = row.entity.updated_by;
		$scope.account.created_by = row.entity.created_by;
		if( $scope.account.addresses_id !== "" ){
			deemsoft.getHTTP('../addressesrest/getaddresses/'+$scope.account.addresses_id).then(function(r){$scope.account.addr = r;});		
		}
		
		$scope.payAccount = true;
		deemsoft.getHTTP('../accounttransactionsrest/findaccounttransactions/'+row.entity.id).then(function(r){
			$scope.accountTrxs = r;
			$scope.totalExpenses = 0;
			$scope.totalPayments = 0;
			if($scope.accountTrxs.length > 0 ){
				for(var i = 0; i < $scope.accountTrxs.length; i++){        
					$scope.totalExpenses = parseFloat( parseFloat($scope.totalExpenses) + parseFloat($scope.accountTrxs[i].expense)).toFixed(2);
					$scope.totalPayments = parseFloat( parseFloat($scope.totalPayments) + parseFloat($scope.accountTrxs[i].payment)).toFixed(2);					
				}
			 $scope.totalBalance = parseFloat($scope.accountTrxs[$scope.accountTrxs.length-1].balance).toFixed(2);
			}});		

		
	}

	$scope.applyPayment = function(amount){
		var accTx = {};
		accTx.id = "";
		accTx.accounts_id = $scope.account.id;
		accTx.payment = amount;
		accTx.balance = parseFloat( parseFloat($scope.totalBalance) + parseFloat(amount)).toFixed(2);
		accTx.created_by = userID;
		accTx.updated_by = userID;
		deemsoft.postHTTP('../accounttransactionsrest/saveaccounttransactions/',accTx,csrf)
		.then(function(d){
			deemsoft.getHTTP('../accounttransactionsrest/findaccounttransactions/'+$scope.account.id).then(function(r){
			$scope.accountTrxs = r;
			$scope.totalExpenses = 0;
			$scope.totalPayments = 0;
			$scope.payAmount = 0;
			if($scope.accountTrxs.length > 0 ){
				for(var i = 0; i < $scope.accountTrxs.length; i++){        
					$scope.totalExpenses = parseFloat( parseFloat($scope.totalExpenses) + parseFloat($scope.accountTrxs[i].expense)).toFixed(2);
					$scope.totalPayments = parseFloat( parseFloat($scope.totalPayments) + parseFloat($scope.accountTrxs[i].payment)).toFixed(2);					
				}
			 $scope.totalBalance = parseFloat($scope.accountTrxs[$scope.accountTrxs.length-1].balance).toFixed(2);
			}});			
		});	
	}
		
	var rowTemp = '<div  class="ui-grid-cell-contents" ng-click="grid.appScope.showAccount(row)" > {{ COL_FIELD }}</div>';
	$scope.gridOptions = {
			
		columnDefs: [ 
			{ field:'title', width:200, displayName: 'Account Name',cellTemplate: rowTemp },
			{ field:'firstname', width:200, displayName: 'Contact Person',cellTemplate: rowTemp },
			{ field:'description', displayName: 'Description',cellTemplate: rowTemp },
			{ field:'phone', width:200, displayName: 'Phone',cellTemplate: rowTemp },
			{ field:'email', width:200, displayName: 'Email',cellTemplate: rowTemp  }
		]	  
	};
	
		
	clearAll();
   
});
