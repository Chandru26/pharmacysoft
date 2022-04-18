'use strict';
app.controller('reservationRoomsController', function ($scope,$http,$timeout,$uibModal, $log, Upload, Flash,deemsoft)
{
function clearAll(){
$scope.showEditBlock = false;
$scope.newRecord = false;
$scope.Purchases_Itemss = [];
$scope.Purchases_Items={};
deemsoft.getHTTP('../Purchases_Itemsrest/listPurchases_Items/').then(function(r){$scope.Purchases_Itemss = r; $scope.gridOptions.data = $scope.Purchases_Itemss; }
}
var csrf = document.getElementById("csrf_token").value;
var userID = document.getElementById("user__id").value;
$scope.cancelEdits = function(){
$scope.Purchases_Items={};
$scope.showEditBlock = false; 
} 
$scope.addNewRecord = function(){
$scope.showEditBlock = true;
$scope.Purchases_Items={};
$scope.newRecord = true;
	}
$scope.saveEdits = function(){
if($scope.newRecord){
$scope.Purchases_Items.created_by = userID; }
$scope.Purchases_Items.updated_by = userID;
deemsoft.postHTTP('../Purchases_Itemsrest/savePurchases_Items/',$scope.Purchases_Items,csrf)
.then(function(r){
Flash.create('success', "Record Saved Successfully!");
 clearAll();
});}
$scope.gridRowSelection = function(row){
$scope.showEdits = true;
$scope.Purchases_Items={};
$scope.Purchases_Items = row.entity;
}
var rowTemp = '<div  class="ui-grid-cell-contents" ng-click="grid.appScope.gridRowSelection(row)" > {{ COL_FIELD }}</div>';
$scope.gridOptions = { 
 columnDefs: [ 
 ]};
clearAll();
 });
