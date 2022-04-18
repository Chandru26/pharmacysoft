'use strict';
app.controller('reservationRoomsController', function ($scope,$http,$timeout,$uibModal, $log, Upload, Flash,deemsoft)
{
function clearAll(){
$scope.showEditBlock = false;
$scope.newRecord = false;
$scope.Invoices_Itemss = [];
$scope.Invoices_Items={};
deemsoft.getHTTP('../Invoices_Itemsrest/listInvoices_Items/').then(function(r){$scope.Invoices_Itemss = r; $scope.gridOptions.data = $scope.Invoices_Itemss; }
}
var csrf = document.getElementById("csrf_token").value;
var userID = document.getElementById("user__id").value;
$scope.cancelEdits = function(){
$scope.Invoices_Items={};
$scope.showEditBlock = false; 
} 
$scope.addNewRecord = function(){
$scope.showEditBlock = true;
$scope.Invoices_Items={};
$scope.newRecord = true;
	}
$scope.saveEdits = function(){
if($scope.newRecord){
$scope.Invoices_Items.created_by = userID; }
$scope.Invoices_Items.updated_by = userID;
deemsoft.postHTTP('../Invoices_Itemsrest/saveInvoices_Items/',$scope.Invoices_Items,csrf)
.then(function(r){
Flash.create('success', "Record Saved Successfully!");
 clearAll();
});}
$scope.gridRowSelection = function(row){
$scope.showEdits = true;
$scope.Invoices_Items={};
$scope.Invoices_Items = row.entity;
}
var rowTemp = '<div  class="ui-grid-cell-contents" ng-click="grid.appScope.gridRowSelection(row)" > {{ COL_FIELD }}</div>';
$scope.gridOptions = { 
 columnDefs: [ 
 ]};
clearAll();
 });
