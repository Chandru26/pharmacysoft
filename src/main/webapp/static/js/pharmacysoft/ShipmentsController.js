'use strict';
app.controller('reservationRoomsController', function ($scope,$http,$timeout,$uibModal, $log, Upload, Flash,deemsoft)
{
function clearAll(){
$scope.showEditBlock = false;
$scope.newRecord = false;
$scope.Shipmentss = [];
$scope.Shipments={};
deemsoft.getHTTP('../Shipmentsrest/listShipments/').then(function(r){$scope.Shipmentss = r; $scope.gridOptions.data = $scope.Shipmentss; }
}
var csrf = document.getElementById("csrf_token").value;
var userID = document.getElementById("user__id").value;
$scope.cancelEdits = function(){
$scope.Shipments={};
$scope.showEditBlock = false; 
} 
$scope.addNewRecord = function(){
$scope.showEditBlock = true;
$scope.Shipments={};
$scope.newRecord = true;
	}
$scope.saveEdits = function(){
if($scope.newRecord){
$scope.Shipments.created_by = userID; }
$scope.Shipments.updated_by = userID;
deemsoft.postHTTP('../Shipmentsrest/saveShipments/',$scope.Shipments,csrf)
.then(function(r){
Flash.create('success', "Record Saved Successfully!");
 clearAll();
});}
$scope.gridRowSelection = function(row){
$scope.showEdits = true;
$scope.Shipments={};
$scope.Shipments = row.entity;
}
var rowTemp = '<div  class="ui-grid-cell-contents" ng-click="grid.appScope.gridRowSelection(row)" > {{ COL_FIELD }}</div>';
$scope.gridOptions = { 
 columnDefs: [ 
 ]};
clearAll();
 });
