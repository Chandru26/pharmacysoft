'use strict';
app.controller('SettingsController', function ($scope,$http,$timeout,$uibModal, $log, Upload, Flash,deemsoft) 
{	
	function clearAll(){
		$scope.editUser = false;
		$scope.newUser = false;
		$scope.users = [];
		$scope.user={};
		$scope.settings = {};	
		deemsoft.getHTTP('../settingsrest/listusers/').then(function(r){$scope.users = r; });
		deemsoft.getHTTP('../settingsrest/getsettings/1').then(function(r){$scope.settings = r; });		
	}
		
	var csrf = document.getElementById("csrf_token").value;	
	var userID = document.getElementById("user__id").value;
	
	$scope.userEditCancel = function(){
		$scope.user={};
		$scope.editUser = false;
	}
	
	$scope.userEditNew = function(){
		$scope.editUser = true;		
		$scope.user={};
		$scope.newUser = true;
	}
	
	$scope.userEditSave = function(){
		if($scope.newUser){
			$scope.user.id = "";
			$scope.user.password = "newpass";					
		}
		deemsoft.postHTTP('../settingsrest/saveusers/',$scope.user,csrf)
		.then(function(r){			
			 Flash.create('success', "User Data Saved Successfully!");
			 clearAll();	
		});		
	}
	
	$scope.userResetPass = function(){
		deemsoft.getHTTP('../settingsrest/resetpass/'+$scope.user.ssoId).then(function(r){ });
	}
	
	$scope.userSelect = function(indx){
		$scope.user = $scope.users[indx];
		$scope.editUser = true;
	}
	
	$scope.fileset = function(){
		var files = document.getElementById('myfile').files;
		if (files.length > 0) {
			$scope.logo = files[0].name;			
			getBase64(files[0]);
		  }
	}
	$scope.saveSettings = function(){
		deemsoft.postHTTP('../settingsrest/savesettings/', $scope.settings, csrf).then(function(r){$scope.settings = r; });	
	}
	
	function getBase64(file) {
		   var reader = new FileReader();
		   reader.readAsDataURL(file);
		   reader.onload = function () {
			 //console.log(reader.result);
			 $scope.settings.logo = reader.result;
			 $scope.$digest();
		   };
		   reader.onerror = function (error) {
			 console.log('Error: ', error);
		   };
	}
	
	clearAll();   
});
