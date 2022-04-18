'use strict';
app.controller('reservationRoomsController', function ($scope,$http,$timeout,$uibModal, $log, Upload, Flash,deemsoft)
{
function clearAll(){
$scope.showEditBlock = false;
$scope.newRecord = false;
$scope.Returnstock_Itemss = [];
$scope.Returnstock_Items={};
deemsoft.getHTTP('../Returnstock_Itemsrest/listReturnstock_Items/').then(function(r){$scope.Returnstock_Itemss = r; $scope.gridOptions.data = $scope.Returnstock_Itemss; }
}
var csrf = document.getElementById("csrf_token").value;
var userID = document.getElementById("user__id").value;
$scope.cancelEdits = function(){
$scope.Returnstock_Items={};
$scope.showEditBlock = false; 
} 
$scope.addNewRecord = function(){
$scope.showEditBlock = true;
$scope.Returnstock_Items={};
$scope.newRecord = true;
	}
$scope.saveEdits = function(){
if($scope.newRecord){
$scope.Returnstock_Items.created_by = userID; }
$scope.Returnstock_Items.updated_by = userID;
deemsoft.postHTTP('../Returnstock_Itemsrest/saveReturnstock_Items/',$scope.Returnstock_Items,csrf)
.then(function(r){
Flash.create('success', "Record Saved Successfully!");
 clearAll();
});}
$scope.gridRowSelection = function(row){
$scope.showEdits = true;
$scope.Returnstock_Items={};
$scope.Returnstock_Items = row.entity;
}
var rowTemp = '<div  class="ui-grid-cell-contents" ng-click="grid.appScope.gridRowSelection(row)" > {{ COL_FIELD }}</div>';
$scope.gridOptions = { 
 columnDefs: [ 
 ]};
clearAll();
 });
