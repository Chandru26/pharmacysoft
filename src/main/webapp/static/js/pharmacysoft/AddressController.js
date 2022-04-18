'use strict';
app.controller('reservationRoomsController', function ($scope,$http,$timeout,$uibModal, $log, Upload, Flash,deemsoft)
{
function clearAll(){
$scope.showEditBlock = false;
$scope.newRecord = false;
$scope.Addresss = [];
$scope.Address={};
deemsoft.getHTTP('../Addressrest/listAddress/').then(function(r){$scope.Addresss = r; $scope.gridOptions.data = $scope.Addresss; }
}
var csrf = document.getElementById("csrf_token").value;
var userID = document.getElementById("user__id").value;
$scope.cancelEdits = function(){
$scope.Address={};
$scope.showEditBlock = false; 
} 
$scope.addNewRecord = function(){
$scope.showEditBlock = true;
$scope.Address={};
$scope.newRecord = true;
	}
$scope.saveEdits = function(){
if($scope.newRecord){
$scope.Address.created_by = userID; }
$scope.Address.updated_by = userID;
deemsoft.postHTTP('../Addressrest/saveAddress/',$scope.Address,csrf)
.then(function(r){
Flash.create('success', "Record Saved Successfully!");
 clearAll();
});}
$scope.gridRowSelection = function(row){
$scope.showEdits = true;
$scope.Address={};
$scope.Address = row.entity;
}
var rowTemp = '<div  class="ui-grid-cell-contents" ng-click="grid.appScope.gridRowSelection(row)" > {{ COL_FIELD }}</div>';
$scope.gridOptions = { 
 columnDefs: [ 
 ]};
clearAll();
 });
