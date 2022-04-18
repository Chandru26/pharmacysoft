'use strict';
app.controller('reservationRoomsController', function ($scope,$http,$timeout,$uibModal, $log, Upload, Flash,deemsoft)
{
function clearAll(){
$scope.showEditBlock = false;
$scope.newRecord = false;
$scope.Account_Transactionss = [];
$scope.Account_Transactions={};
deemsoft.getHTTP('../Account_Transactionsrest/listAccount_Transactions/').then(function(r){$scope.Account_Transactionss = r; $scope.gridOptions.data = $scope.Account_Transactionss; }
}
var csrf = document.getElementById("csrf_token").value;
var userID = document.getElementById("user__id").value;
$scope.cancelEdits = function(){
$scope.Account_Transactions={};
$scope.showEditBlock = false; 
} 
$scope.addNewRecord = function(){
$scope.showEditBlock = true;
$scope.Account_Transactions={};
$scope.newRecord = true;
	}
$scope.saveEdits = function(){
if($scope.newRecord){
$scope.Account_Transactions.created_by = userID; }
$scope.Account_Transactions.updated_by = userID;
deemsoft.postHTTP('../Account_Transactionsrest/saveAccount_Transactions/',$scope.Account_Transactions,csrf)
.then(function(r){
Flash.create('success', "Record Saved Successfully!");
 clearAll();
});}
$scope.gridRowSelection = function(row){
$scope.showEdits = true;
$scope.Account_Transactions={};
$scope.Account_Transactions = row.entity;
}
var rowTemp = '<div  class="ui-grid-cell-contents" ng-click="grid.appScope.gridRowSelection(row)" > {{ COL_FIELD }}</div>';
$scope.gridOptions = { 
 columnDefs: [ 
 ]};
clearAll();
 });
