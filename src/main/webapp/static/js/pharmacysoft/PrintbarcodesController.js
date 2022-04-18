'use strict';
app.controller('PrintbarcodesController', function ($scope,$http,$timeout,$uibModal, $log, Upload, Flash,deemsoft)
{	

var csrf = document.getElementById("csrf_token").value;
	var userID = document.getElementById("user__id").value;	
	
	$scope.librarybooksCatalog = [];
	$scope.libraryjournalsCatalog = [];
	$scope.libraryCDCatalog = [];
	
	//$scope.reports =  [ 'BOOKSCATALOG','JOURNALS LIST','CD/DVD LIST','' ];
	
	$scope.barcodeprint = function(){ 	
		
		
			
			deemsoft.postHTTP('../catalogsrest/periodcatalogsbar/',{beginDate:$scope.accnumfrom,endDate:$scope.accnumto},csrf).then(function(d){
					$scope.librarybooksCatalog = d; 
					console.log(d);	
			
			var htmlData = "<html><head>";
				htmlData += "<script src=\"/educationSoft/static/js/angular/jsbarcode.js\"></script>";
				htmlData += "<script> JsBarcode(\".barcode\").init();</script>";
				htmlData += "</head><body onload=\"window.print()\">";			
				for(var i = 0; i < $scope.librarybooksCatalog.length; i++){						
					htmlData += "<div class=\"row\">";
					htmlData +="<div class=\"col-sm-12\" style=\"float: left;\"><table cellpadding=\"20\" ><tr><td><br><svg class=\"barcode\"";
					htmlData += "jsbarcode-format=\"CODE128\"";
					htmlData += "jsbarcode-value=\""+$scope.librarybooksCatalog[i].barcode+"\" \n";
					htmlData += "jsbarcode-textmargin=\"0\" \n";
					htmlData += "jsbarcode-height=\"20\" ";
					htmlData += "jsbarcode-width=\"1\" \n";
					
					htmlData += "jsbarcode-fontoptions=\"bold\"></svg></td></tr>";
					htmlData += "</table>";								
					htmlData += "</div></div>";									
				}
				htmlData += "<script> JsBarcode(\".barcode\").init();</script>";				
				htmlData +="</body></html>"; 
												 
				var x = screen.width/2 - 800/2;
				var y = screen.height/2 - 800/2;
				var myWindow = window.open("", "Books Barcode Print", 'toolbar=no,location=no,directories=no,status=no,menubar=no,width=800,height=800,left='+x+',top='+y);
				myWindow = myWindow.document.write(htmlData);
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
