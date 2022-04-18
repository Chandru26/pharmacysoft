'use strict';
var app = angular.module('pharmacysoft', [
'ngSanitize', 
'ngAnimate',
'ui.bootstrap', 
'ngTouch', 
'ui.select', 
'ui.grid',
'ui.grid.selection', 
'ui.grid.exporter',
'ui.grid.edit', 
'ui.grid.cellNav',
'ngFileUpload',
'ngFlash'

]);

app.filter('twoDecimals', function () {
  return function (value) {
    return value.toFixed(2);
  };
})

app.factory('deemsoft', function($http, $log,Flash){
return {
    getHTTP : function(gUrl){
		return $http( { method: 'GET', url: gUrl } ).
		then( 	
			function(response) { return response.data; }, 
			function(response) { return response.data; }
		);
	},
	
	postHTTP : function(pUrl,data,csrf){		
		return $http( { method: 'POST',
				 url: pUrl,
				 data: data,
				 headers: {'X-CSRF-TOKEN': csrf } 
				 } ).
		then( function(response) {					
					return response.data;					
				}, function(response) {
					return response.data; 
				}
			);
			
	},
	IndianDate : function(dstr){
		var timeStr = new Date(dstr);
		var mon = timeStr.getMonth()+1;
		return timeStr.getDate()+"/"+mon+"/"+timeStr.getFullYear();
	},
	IndianDateTime : function(dstr){
		var timeStr = new Date(dstr);
		var mon = timeStr.getMonth()+1;
		return timeStr.getDate()+"/"+mon+"/"+timeStr.getFullYear()+" "+timeStr.getHours()+":"+timeStr.getMinutes()+":"+timeStr.getSeconds();
	},
    getBatchOptions : function(){	
			var batchOptions = [];
			var currentTime = new Date();
			var year = currentTime.getFullYear()+1;
			var minBatchYear = year - 10;
			while( year > minBatchYear ){
				var twodigits= year.toString().substr(2,2);
				year--;	
				batchOptions.push(year.toString()+"-"+twodigits);			
			}	
			return batchOptions;		
	},
	
	getBloodGroups : function(){		
			var bloodGroups = ['O+','O-','A+','A-','B+','B-', 'AB+','AB-'];
			return bloodGroups;		
	},
	
	findObjectByID : function(arry,ID){
		var elementPos = arry.map(function(x) {return x.id; }).indexOf(ID);
		return arry[elementPos];		
	},
	
	findObjectByName : function(arry,Name){
		var elementPos = arry.map(function(x) {return x.name; }).indexOf(Name);
		return arry[elementPos];		
	}
	
  }
});

app.controller('ModalInstanceCtrl', function ($uibModalInstance, items) {
  var $ctrl = this;
  $ctrl.items = items;
  $ctrl.selected = {
    item: $ctrl.items[0]
  };

  $ctrl.ok = function () {
    $uibModalInstance.close($ctrl.selected.item);
  };

  $ctrl.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
