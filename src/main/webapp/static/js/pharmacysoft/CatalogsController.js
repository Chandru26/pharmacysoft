'use strict';
app.controller('CatalogsController', function ($scope,$http,$timeout,$uibModal, $log, Upload, Flash,deemsoft)
{
	var csrf = document.getElementById("csrf_token").value;
	var userID = document.getElementById("user__id").value;
	$scope.others = false;
	$scope.catprice = false;
	var $ctrl = this;
	$scope.dateFormat = "dd/MM/yyyy";
	$scope.open1 = function($event) { $scope.status1 = true; };	
	$scope.dateOptions = { showWeeks: false, showButtonBar: false  };
	$scope.Catalogs = {};
	$scope.Catalogs.quantity = "1";
	function clearAll(){	
		$scope.newRecord = false;
	
		$scope.Catalogs={};
	}
	
	
	$scope.refreshSearchProduct = function(search){
		if( search.length > 3 ){
			deemsoft.getHTTP('../catalogsrest/searchcatalogsbyname/'+search).then(function(r){$scope.catalogLists = r; });		
		}
	}
	
	$scope.getItemData = function(){
			
			if( $scope.Catalogs.barcode === "" ) return;
			deemsoft.getHTTP('../catalogsrest/searchcatalogsbybarcode/'+$scope.Catalogs.barcode)
			.then(function(r){
				$scope.getProduct(r[0].id);
			});		
	
	}	
	
	$scope.getProduct = function(id){
		deemsoft.getHTTP('../catalogsrest/getcatalogs/'+id).then(function(r){
			$scope.Catalogs = r;
			//$scope.Catalogs.expiration = new Date($scope.Catalogs.expiration);
		
			$scope.catalogLists	= [];		
			$scope.catalogLists.push($scope.Catalogs);
			$scope.catalogLists.selected = $scope.catalogLists[0];
			});		
		}
		
		$scope.barcodeprint = function(){
		
		
		
						 
			
			var htmlData = "<html><head>";
				htmlData += "<script src=\"/educationSoft/static/js/angular/jsbarcode.js\"></script>";
				htmlData += "<script> JsBarcode(\".barcode\").init();</script>";
				htmlData += "</head><body onload=\"window.print()\">";			
									
					htmlData += "<div class=\"row\">";
					htmlData +="<div class=\"col-sm-12\" style=\"float: left;\"><table cellpadding=\"20\" ><tr><td><br><svg class=\"barcode\"";
					htmlData += "jsbarcode-format=\"CODE128\"";
					htmlData += "jsbarcode-value=\""+$scope.Catalogs.barcode+"\" \n";
					htmlData += "jsbarcode-textmargin=\"0\" \n";
					htmlData += "jsbarcode-height=\"20\" ";
					htmlData += "jsbarcode-width=\"1\" \n";
					htmlData += "jsbarcode-fontoptions=\"bold\"></svg></td></tr>";
					htmlData += "</table>";								
					htmlData += "</div></div>";									
				
				htmlData += "<script> JsBarcode(\".barcode\").init();</script>";				
				htmlData +="</body></html>"; 
												 
				var x = screen.width/2 - 800/2;
				var y = screen.height/2 - 800/2;
				var myWindow = window.open("", "Books Barcode Print", 'toolbar=no,location=no,directories=no,status=no,menubar=no,width=800,height=800,left='+x+',top='+y);
				myWindow = myWindow.document.write(htmlData);
			
			
			
		}
	
	$scope.cancelEdits = function(){
		$scope.Catalogs={};		
	} 
	
	$scope.addNewRecord = function(){	
		$scope.Catalogs={};
		$scope.newRecord = true;
	}
	
	$scope.saveEdits = function(){
		$scope.Catalogs.created_by = 1;
		$scope.Catalogs.updated_by = 1;
		//$scope.Catalogs.expiration = $scope.Catalogs.expiration.toLocaleDateString();
		deemsoft.postHTTP('../catalogsrest/savecatalogs/',$scope.Catalogs,csrf)
		.then(function(r){
		//Flash.create('success', "Record Saved Successfully!");
		$scope.Catalogs = r;
				var modalInstance = $uibModal.open({
				  animation: $ctrl.animationsEnabled,         
				  templateUrl: 'savecatalogs',
				  controller: 'ModalInstanceCtrl',
				  controllerAs: '$ctrl',         
				  resolve: {
					items: function () {
					  return 0;
					}
				  }
				});

				modalInstance.result.then(function (selectedItem) {       
					
				 // console.log(selectedItem);
				  //$scope.ShowConfirm(selectedItem);
				}, function () {
				  
				  //$log.info('Modal dismissed at: ' + new Date());
				});
		 //clearAll();
	});
}
		
	//clearAll();
 });
app.controller('ModalInstanceCtrl', function ($uibModalInstance, items) {
  var $ctrl = this;
  $ctrl.items = items;

  $ctrl.ok = function () {
    $uibModalInstance.close($ctrl.items);
  };

  $ctrl.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
