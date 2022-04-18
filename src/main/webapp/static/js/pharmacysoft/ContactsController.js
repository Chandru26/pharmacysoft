'use strict';
app.controller('ContactsController', function ($scope,$http,$timeout,$uibModal, $log, Upload, Flash,deemsoft)
{
	var csrf = document.getElementById("csrf_token").value;
	var userID = document.getElementById("user__id").value;
	//cantactform.email.$error.required
	function clearAll(){
		$scope.newRecord = false;
		$scope.Contacts={};
		$scope.ContactsList=[];		
		$scope.titles=['Mr.','Mrs.','Dr.','Prof','HOD'];
	}
	$scope.showdisabledcontact = true;
	$scope.shownewdisabledcontact = false;
	$scope.disabledcontact = true;
	
	$scope.refreshSearchContacts = function(search){
		if( search.length > 3 ){
			deemsoft.getHTTP('../contactsrest/searchcontactsbyname/'+search)
			.then(function(r){$scope.ContactsList = r; });		
		}
	}
	
	$scope.getContactData = function(){
			
			if( $scope.Contacts.name === "" ) return;
			deemsoft.getHTTP('../contactsrest/searchcontactsbyname/'+$scope.Contacts.name)
			.then(function(r){
				$scope.getContact(r[0].id);
			});		
	
	}	
	
	$scope.getContact = function(id){
		deemsoft.getHTTP('../contactsrest/getcontacts/'+id).then(function(r){
			$scope.disabledcontact = false;
			$scope.Contacts = r; });		
		}
	
	$scope.cancelEdits = function(){
		$scope.Contacts={};
	
	} 
	$scope.addNewRecord = function(){	
		$scope.Contacts={};
		$scope.newRecord = true;
		$scope.showdisabledcontact = false;
		$scope.shownewdisabledcontact = true;
		
	}
	$scope.saveEdits = function(){
		if($scope.newRecord){
		$scope.Contacts.created_by = userID; }
		$scope.Contacts.updated_by = userID;
		deemsoft.postHTTP('../contactsrest/savecontacts/',$scope.Contacts,csrf)
		.then(function(r){
			Flash.create('success', "Record Saved Successfully!");
			
		});
	}
	
	clearAll();
 });
