'use strict';
app.controller('PurchasesController', function ($scope,$http,$timeout, uiGridConstants, $uibModal, $log, Upload, Flash,deemsoft)
{	var csrf = document.getElementById("csrf_token").value;
	var userID = document.getElementById("user__id").value;
	var $ctrl = this;
	$scope.cgst = "";
	$scope.sgst = "";
	$scope.cgst_perc = "";
	$scope.sgst_perc = "";
	var ttax ;
	var purchasename = "" ;
	$scope.print = true;
	$scope.savepurchase = true;
	function clearAll(){
		$scope.editPurchase = false;
		$scope.showPayOptions = false;
		$scope.showAccountBlock = false;
		$scope.showCheckBlock = false;
		$scope.showCreditcardBlock = false;
		$scope.paymentSel=true;
		$scope.paymentMethod = "";
		$scope.contactsLists = [];
		$scope.contact_id = "1";
		$scope.contactName = "Guest";
		$scope.notes = "";
		$scope.paid = 0;
		$scope.billno = "";
		$scope.balance = 0;
		$scope.billamount = 0;
		$scope.payment = 0;
		$scope.grandtotal = 0;
		$scope.discount = 0;
		$scope.discount = 0;
		$scope.cgst = 0;
		$scope.sgst = 0;
		$scope.netbillamount = 0;		
		$scope.grassamount = 0;
		$scope.totqty = "";
		$scope.PurchaseTrxs = [];
		$scope.Purchase={};
		$scope.Address={};
		$scope.productcode = "";
		$scope.catalogLists = [];
		$scope.orderDate = deemsoft.IndianDate(new Date());
		var Purchase_id = "";
		$scope.accountid = "";		
		$scope.payoptions = ['Account','Check','Cash','Credit Card'];
		$scope.accounts = [];		
		deemsoft.getHTTP('../accountsrest/listaccounts/').then(function(r){$scope.accounts = r;});		
	
	}
	clearAll();
	$scope.getMaxPurchaseID = function(){
		deemsoft.getHTTP('../purchasesrest/getmaxid/')
			.then(function(r){	$scope.max_Purchase_id = parseInt(r[0].max_id)+1; });
	}
	
	$scope.getPurchase = function(){
		$scope.savepurchase = true;
		
		$scope.Purchase={};
		deemsoft.getHTTP('../purchasesrest/getpurchases/'+$scope.Purchase_id)
		.then(function(r){	
			$scope.Purchase = r;
			
			$scope.contact_id = $scope.Purchase.contacts_id;
			$scope.purchasename = $scope.Purchase.title;
			$scope.paymentMethod = $scope.Purchase.payment_method;
			$scope.check_number = $scope.Purchase.check_number;
			$scope.accounts_id = $scope.Purchase.payments_id;
			$scope.total = parseFloat($scope.Purchase.total).toFixed(2);
			$scope.discount = parseFloat($scope.Purchase.discount).toFixed(2);
			$scope.tax = parseFloat($scope.Purchase.tax).toFixed(2);	
			$scope.paid = parseFloat($scope.Purchase.paid).toFixed(2);
			$scope.balance = parseFloat($scope.Purchase.balance).toFixed(2);			
			$scope.billamount = parseFloat($scope.Purchase.billamount).toFixed(2);			
			$scope.billno = $scope.Purchase.billno;
			$scope.notes = $scope.Purchase.notes;
			$scope.orderDate = deemsoft.IndianDate($scope.Purchase.purchase_date);
			//$scope.orderDate = $scope.orderDate.toLocaleDateString();
			if( $scope.notes === null ){ $scope.notes = " "; } 
			if( $scope.contact_id  !== "" ){
				$scope.getContact($scope.contact_id)	
			}	
			$scope.editPurchase = true;
			$scope.gridOptions.columnDefs[6].enableCellEdit = false;			
		});	
		$scope.PurchaseTrxs = [];
		deemsoft.getHTTP('../purchasesitemsrest/getpurchasesitemsbypurchaseid/'+$scope.Purchase_id)
		.then(function(r){	
			
			$scope.PurchaseTrxs = r;
			
			$scope.gridOptions.data = $scope.PurchaseTrxs;
			calculateTotals();
		});
	}
	$scope.getContact = function(contactid){
		if( contactid != "" ){			
			deemsoft.getHTTP('../contactsrest/getcontacts/'+contactid)
			.then(function(r){				
					$scope.contactsLists.push(r);
					$scope.contactsLists.selected = $scope.contactsLists[0];									
					$scope.getAddress(r.firstname,r.address_id,r.id,r.mobile_phone,r.email);
				});
		}
	}
	
	$scope.getAddress = function(name,addressid,id,phone,email){	
		$scope.contactName = name;		
		if( addressid != "" ){			
			deemsoft.getHTTP('../addressrest/getaddress/'+addressid)
			.then(function(r){
					$scope.Address = r;
					$scope.phone = phone;
					$scope.email = email;
					$scope.contact_id = id;
				});
		}
	}
	
	$scope.AddContact = function(){
		$scope.Address.created_by = userID
		$scope.Address.updated_by = userID;
		deemsoft.postHTTP('../addressrest/saveaddress/',$scope.Address,csrf)
		.then(function(r){
			//Flash.create('success', "Address Saved Successfully!");
			$scope.Contacts = {id:'',firstname:'',address_id:'',mobile_phone:'',email:'',created_by:'',updated_by:''};
			$scope.Contacts.firstname = $scope.contactName;
			$scope.Contacts.address_id = r.id;
			$scope.Contacts.mobile_phone = $scope.phone;
			$scope.Contacts.email = $scope.email;
			$scope.Contacts.created_by = userID;
			$scope.Contacts.updated_by = userID;
			deemsoft.postHTTP('../contactsrest/savecontacts/',$scope.Contacts,csrf)
			.then(function(r){
				//Flash.create('success', "Contact Info Saved Successfully!");	
				var modalInstance = $uibModal.open({
				  animation: $ctrl.animationsEnabled,         
				  templateUrl: 'savepurchase',
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
			});
		});
		
	}
	
	$scope.removeItem = function(row){	
		//$scope.PurchaseTrxs.splice(row.entity.id-1,1);
		//calculateTotals();
		var index = $scope.gridOptions.data.indexOf(row.entity);
		$scope.gridOptions.data.splice(index, 1);				
		calculateTotals();			
	}
	
	$scope.getItemData = function(){	
		if( $scope.productcode === "" ) return;
		deemsoft.getHTTP('../catalogsrest/searchcatalogsbybarcode/'+$scope.productcode)
		.then(function(r){
			$scope.addToList(r[0].id,r[0].barcode,r[0].name,r[0].expiration,r[0].msrp,r[0].purchase_price,r[0].max_discount,r[0].tax,r[0].gst,r[0].quantity); 
		});		
	
	}	
	
	$scope.addToList = function(id,barcode,name,expiration,msrp,purchase_price,discount,tax,gst,qty){
		if( barcode === "") return;
					
		/*if(qty<1){Flash.create('danger', "Out Of Stock! Available Quantity is"+r[0].quantity);	return;}*/
		$scope.purchasename = name;
		
		
		var indx = -1;
		if( $scope.PurchaseTrxs.length > 0 ){
		 indx =	$scope.PurchaseTrxs.map(function(x) {return x.barcode; }).indexOf(barcode);
	
		if( indx >= 0 ){
		  if( ($scope.PurchaseTrxs[indx].quantity+1) > qty ){ Flash.create('danger', "Out Of Stock! Available Quantity is"+r[0].quantity);	return;}
			
		  $scope.PurchaseTrxs[indx].quantity = parseInt($scope.PurchaseTrxs[indx].quantity)+1;
		  $scope.PurchaseTrxs[indx].subtotal = parseFloat((parseFloat($scope.PurchaseTrxs[indx].purchase_price) - (parseFloat($scope.PurchaseTrxs[indx].purchase_price) * parseFloat($scope.PurchaseTrxs[indx].discount)/100))*parseInt($scope.PurchaseTrxs[indx].quantity)).toFixed(2);
		 // $scope.PurchaseTrxs[indx].subtotal = parseFloat(parseFloat($scope.PurchaseTrxs[indx].subtotal) + (parseFloat($scope.PurchaseTrxs[indx].subtotal) * parseFloat($scope.PurchaseTrxs[indx].tax)/100)).toFixed(2);
		  calculateTotals();
		  return;
		 }
		}
	
		$scope.catalogItem ={id:'',catalogs_id:'',Purchases_id:'',barcode:'',name:'', expiration:'',price:'',purchase_price:'',quantity:0,tax:'',gst:'',discount:'',subtotal:''};
		$scope.catalogItem.catalogs_id = id;
		$scope.catalogItem.Purchases_id = $scope.Purchase_id;
		$scope.catalogItem.barcode = barcode; 
		$scope.catalogItem.name = name;
		$scope.catalogItem.expiration = expiration;
		$scope.catalogItem.price = msrp;
		$scope.catalogItem.purchase_price = purchase_price;
		$scope.catalogItem.discount = discount;
		$scope.catalogItem.tax = tax;
		
		$scope.catalogItem.gst = gst;
		$scope.cgst_perc = $scope.catalogItem.tax/2;
		$scope.sgst_perc = $scope.catalogItem.tax/2;
		
		$scope.catalogItem.quantity = 1;
		$scope.catalogItem.subtotal = parseFloat((parseFloat($scope.catalogItem.purchase_price) - (parseFloat($scope.catalogItem.purchase_price) * parseFloat($scope.catalogItem.discount)/100))*parseInt($scope.catalogItem.quantity)).toFixed(2);
		//$scope.catalogItem.subtotal = parseFloat(parseFloat($scope.catalogItem.subtotal) + (parseFloat($scope.catalogItem.subtotal) * parseFloat($scope.catalogItem.tax)/100)).toFixed(2);
		$scope.PurchaseTrxs.push($scope.catalogItem);
		calculateTotals();
	
	}
	
	
	function calculateTotals(){
		if( $scope.PurchaseTrxs.length <= 0 ){return;}
		$scope.grandtotal = 0;
		$scope.total = 0;
		$scope.tax = 0;
		$scope.discount = 0;
		$scope.totqty = 0;
		for(var i = 0; i < $scope.PurchaseTrxs.length; i++){        
			$scope.total = parseFloat($scope.total) + parseFloat($scope.PurchaseTrxs[i].subtotal);
			//$scope.PurchaseTrxs[i].expiration = new Date($scope.PurchaseTrxs[i].expiration);
			
			var disc = parseFloat((parseFloat($scope.PurchaseTrxs[i].purchase_price) * parseFloat($scope.PurchaseTrxs[i].discount)/100)*parseInt($scope.PurchaseTrxs[i].quantity)).toFixed(2);
		
			
			//alert(disc);
			//var ttax = parseFloat(((parseFloat($scope.PurchaseTrxs[i].purchase_price) * parseFloat($scope.PurchaseTrxs[i].quantity)) - parseFloat(disc)) * parseFloat($scope.PurchaseTrxs[i].tax)/100).toFixed(2);
			//alert($scope.PurchaseTrxs[i].subtotal);
			ttax = parseFloat(((parseFloat($scope.PurchaseTrxs[i].subtotal))) * parseFloat($scope.PurchaseTrxs[i].tax)/100).toFixed(2);
			//alert(ttax);
			$scope.tax = parseFloat($scope.tax) + parseFloat(ttax);	
			console.log($scope.tax);
			$scope.discount = parseFloat($scope.discount) + parseFloat(disc);
			$scope.totqty = parseFloat($scope.totqty) + parseFloat($scope.PurchaseTrxs[i].quantity);
		}
		$scope.total = parseFloat($scope.total).toFixed(2);
		
		
		
		$scope.tax = parseFloat($scope.tax).toFixed(2);
		$scope.discount = parseFloat($scope.discount).toFixed(2);
			
		//$scope.grandtotal = parseFloat(parseFloat($scope.total)+parseFloat($scope.tax)).toFixed(2);
		$scope.grandtotal = parseFloat(parseFloat($scope.total)).toFixed(2);		
		$scope.netbillamount = parseFloat(parseFloat($scope.total) + parseFloat($scope.tax)).toFixed(2);	
		$scope.grassamount = parseFloat(parseFloat($scope.total) + parseFloat($scope.discount)).toFixed(2);		
		$scope.balance = parseFloat(parseFloat($scope.netbillamount) - parseFloat($scope.paid)).toFixed(2);
		$scope.cgst = parseFloat($scope.tax)/2 ;
		$scope.sgst = parseFloat($scope.tax)/2 ;
	}
	
	$scope.refreshSearchProduct = function(search){
		if( search.length > 3 ){
			deemsoft.getHTTP('../catalogsrest/searchcatalogsbyname/'+search).then(function(r){$scope.catalogLists = r; });		
		}
	}
	
	$scope.refreshSearchContacts = function(search){
		if( search.length > 3 ){
			deemsoft.getHTTP('../contactsrest/searchcontactsbyname/'+search).then(function(r){$scope.contactsLists = r; });		
		}
	}	
	
	$scope.gridOptions = { 
		enableCellEditOnFocus: true,
		enableFiltering: false,
		showGridFooter: false,
		enableColumnMenus: false,
		showColumnFooter: true,
		enableHorizontalScrollbar : uiGridConstants.scrollbars.NEVER 		
	};
	var cellTempl = '<div class="ui-grid-cell-contents"><button class="btn btn-default btn-xs" ng-click="grid.appScope.removeItem(row)">x</button>  {{grid.renderContainers.body.visibleRowCache.indexOf(row)+1}}</div>';
	
	$scope.gridOptions.columnDefs = [
    { field: 'id', displayName: 'SL NO', width: '5%', enableCellEdit: false, cellTemplate:cellTempl },    
    { field: 'barcode', displayName: 'Batch No', enableCellEdit: false, width: '10%' },
	{ field: 'name', displayName: 'Product Description',enableCellEdit: false },
	{ field: 'expiration', displayName: 'Expiration', width: '5%', enableCellEdit: false, type: 'date', cellFilter: 'date:\'MM-yy\'' },
	{ field: 'price', displayName: 'MRP', width: '5%', enableCellEdit: false, cellClass: 'text-right',cellFilter: 'number: 2' },
	{ field: 'purchase_price', displayName: 'Pur Price', width: '5%', enableCellEdit: false, cellClass: 'text-right',cellFilter: 'number: 2' },
	{ field: 'freequantity', displayName: 'FreeQty', footerCellTemplate: '<div class="ui-grid-cell-contents text-center"> {{grid.appScope.freeqty}}</div>',width: '5%', cellClass: 'text-center' },
	{ field: 'quantity', displayName: 'Quantity', footerCellTemplate: '<div class="ui-grid-cell-contents text-center"> {{grid.appScope.totqty}}</div>',width: '5%', cellClass: 'text-center' },
	{ field: 'discount', displayName: 'Discount(%)', footerCellTemplate: '<div class="ui-grid-cell-contents text-right"> {{grid.appScope.discount}}</div>', width: '5%', enableCellEdit: false, cellClass: 'text-right',cellFilter: 'number: 2' },
	{ field: 'tax', displayName: 'GST(%)', footerCellTemplate: '<div class="ui-grid-cell-contents text-right"> {{grid.appScope.tax}}</div>', width: '5%', cellClass: 'text-right', enableCellEdit: false, cellFilter: 'number: 2' },
	{ field: 'subtotal', displayName: 'Sub Total',  footerCellTemplate: '<div class="ui-grid-cell-contents text-right"> {{grid.appScope.total}}</div>', width: '10%', enableCellEdit: false, cellClass: 'text-right',cellFilter: 'number: 2' }	
     ];
	$scope.gridOptions.data = $scope.PurchaseTrxs;
	$scope.msg = {};
 
	$scope.gridOptions.onRegisterApi = function(gridApi){
          $scope.gridApi = gridApi;
          gridApi.edit.on.afterCellEdit($scope,function(rowEntity, colDef, newValue, oldValue){
			$scope.$apply();
			var indx = $scope.gridOptions.data.indexOf(rowEntity);
			if( (newValue - oldValue) > 0 ){
				
				deemsoft.getHTTP('../catalogsrest/searchcatalogsbybarcode/'+$scope.PurchaseTrxs[indx].barcode)
				.then(function(r){
					if( r.quantity < (newValue - oldValue)){
						Flash.create('danger', "Out Of Stock! Available Quantity is"+r.quantity);
					}
					else{
						$scope.PurchaseTrxs[indx].quantity = newValue;						
						$scope.PurchaseTrxs[indx].subtotal = parseFloat((parseFloat($scope.PurchaseTrxs[indx].purchase_price) - (parseFloat($scope.PurchaseTrxs[indx].purchase_price) * parseFloat($scope.PurchaseTrxs[indx].discount)/100))*parseInt($scope.PurchaseTrxs[indx].quantity)).toFixed(2);
						//$scope.PurchaseTrxs[indx].subtotal = parseFloat(parseFloat($scope.PurchaseTrxs[indx].subtotal) + (parseFloat($scope.PurchaseTrxs[indx].subtotal) * parseFloat($scope.PurchaseTrxs[indx].tax)/100)).toFixed(2);
						calculateTotals();						
					}
				});	
				
			}
			else {
			$scope.PurchaseTrxs[indx].quantity = newValue;						
			$scope.PurchaseTrxs[indx].subtotal = parseFloat((parseFloat($scope.PurchaseTrxs[indx].purchase_price) - (parseFloat($scope.PurchaseTrxs[indx].purchase_price) * parseFloat($scope.PurchaseTrxs[indx].discount)/100))*parseInt($scope.PurchaseTrxs[indx].quantity)).toFixed(2);
			//$scope.PurchaseTrxs[indx].subtotal = parseFloat(parseFloat($scope.PurchaseTrxs[indx].subtotal) + (parseFloat($scope.PurchaseTrxs[indx].subtotal) * parseFloat($scope.PurchaseTrxs[indx].tax)/100)).toFixed(2);

			calculateTotals();
			}
          });
    }
  
 $scope.onPaymentChange = function(){	
	//$scope.balance = parseFloat(parseFloat($scope.total) - parseFloat($scope.paid)).toFixed(2);
	$scope.balance = parseFloat(parseFloat($scope.netbillamount) - parseFloat($scope.paid)).toFixed(2);
 }
 
 $scope.onPaymentSelect = function(){
	 //$scope.paid = $scope.total;
	 $scope.paid = $scope.netbillamount;
	 $scope.balance = 0;
	 $scope.paymentSel = false;	
	 $scope.savepurchase = false;
	
 }
 
 $scope.savePurchase = function(){	
	$scope.billamount = Math.round($scope.billamount);
	$scope.netbillamount = Math.round($scope.netbillamount);
	//alert($scope.grandtotal);	
	if( $scope.billamount !== $scope.netbillamount ) { 
		//alert("Bill Amount and Grand Total is not equal");
		
		var modalInstance = $uibModal.open({
		  animation: $ctrl.animationsEnabled,         
		  templateUrl: 'matchbillamount',
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
		
		return;
	}	
	$scope.Purchase={};		
	$scope.Purchase.id = $scope.Purchase_id;
	$scope.Purchase.contacts_id = $scope.contact_id;
	$scope.Purchase.title = $scope.purchasename;
	$scope.Purchase.payment_method = $scope.paymentMethod;
	$scope.Purchase.check_number = $scope.check_number;
	$scope.Purchase.payments_id = $scope.accounts_id;
	//$scope.Purchase.total = parseFloat($scope.total).toFixed(2);
	$scope.Purchase.total = parseFloat($scope.grassamount).toFixed(2);
	//alert($scope.Purchase.total);
	$scope.Purchase.discount = parseFloat($scope.discount).toFixed(2);
	//alert($scope.Purchase.discount);
	$scope.Purchase.tax = parseFloat($scope.tax).toFixed(2);	
	$scope.Purchase.paid = parseFloat($scope.paid).toFixed(2);
	$scope.Purchase.balance = parseFloat($scope.balance).toFixed(2);			
	$scope.Purchase.billamount = parseFloat($scope.netbillamount).toFixed(2);			
	$scope.Purchase.billno = $scope.billno;
	$scope.Purchase.notes = $scope.notes;
	$scope.Purchase.created_by = userID;
	$scope.Purchase.updated_by = userID;
		
	$scope.Purchase.sgst_amount = $scope.sgst;			
	$scope.Purchase.cgst_amount = $scope.cgst;

	deemsoft.postHTTP('../purchasesrest/savepurchases/', $scope.Purchase,csrf)
	.then(function(pur){	
		$scope.Purchase_id = pur.id;
		
		$scope.print = false;
				
		for(var i = 0; i < $scope.PurchaseTrxs.length; i++){
			$scope.PurchaseTrxs[i].purchases_id = pur.id;
			$scope.PurchaseTrxs[i].purchase_id = pur.id;
			//$scope.PurchaseTrxs[i].expiration = $scope.PurchaseTrxs[i].expiration.toLocaleDateString();

		}
		
		deemsoft.postHTTP('../purchasesitemsrest/savepurchasesitems/',$scope.PurchaseTrxs,csrf)
		.then(function(d){ 
			//Flash.create('success', "Invoice Saved Successfully!");
					
						var modalInstance = $uibModal.open({
						  animation: $ctrl.animationsEnabled,         
						  templateUrl: 'savepurchase',
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
			$scope.editPurchase = true;
			$scope.gridOptions.columnDefs[6].enableCellEdit = false;
			
		});
		
	});
	
 }
 
 $scope.printPurchase = function(){
	 $scope.cgst = $scope.tax /2 ;
	 $scope.sgst = $scope.tax /2 ;
	 //alert($scope.cgst);
	 //alert($scope.sgst);
	 
	 var htmlData = "<html><head><link rel=\"stylesheet\" href=\"http://localhost:8080/pharmacysoft/static/css/bootstrap/bootstrap.min.css\">";  
				
				htmlData += "</head><body onload=\"window.print()\" style=\"background: -webkit-linear-gradient(rgba(230, 22, 23, 0), rgba(230, 22, 23, 0)), url(/pharmacysoft/static/images/deemsoft/amchlohonew.jpg) no-repeat;background-position:center; background-size:contain; text-align:center\"  >"+
				
				
				"<table style=\"padding-left=15px ; font-family:calibri; font-size:13px;\" width=100%>";		
				htmlData += "<tr><td colspan='2' align='center' style=\" font-family:  Times New Roman, Times, serif;\"><b>|| Jai Sri Gurudev ||</b><br>Sri Adichunchanagiri Shikshana Trust (R)<br><span style=\" font-family: Friz quadrata BT !important;\">ADICHUNCHANAGIRI AYURVEDIC MEDICAL COLLEGE, HOSPITAL AND RESEARCH CENTER</span>(<span style=\"font-family:Colonna MT;font-size:18px !important;\">A</span><span style=\" font-family:Bookman Old Style !important;\">MC</span>)<br>Nagarur , Bengaluru - 562 123<br>Ph.No : +91 9481861519, Email : principal@amc.org.in</br> www.amc.org.in<br><br><hr><br><b>Cash Bill/Receipt</b><br></td></tr>";


				htmlData += "<tr><td style=\"text-align:left;\">Patient Name:<b> "+$scope.contactName+"</b></td><td style=\"text-align:right;\">Purchase No:<b> "+$scope.Purchase_id+"</b></td></tr>";
				htmlData += "<tr><td style=\"text-align:left;\">Doctor:<b> "+$scope.referredby+"</b></td><td style=\"text-align:right;\">Date:<b> "+$scope.orderDate+"</b></td></tr></table><br>";
				/*
				htmlData += "<table width=100%><tr><td><b> "+$scope.contactName+"<br>"+$scope.Address.address1+"<br>"+$scope.Address.city+" , "+$scope.Address.province+" , "+$scope.Address.zipcode+" <br> "+$scope.email+" , "+$scope.phone+"</b> </td><td align='right'>Bill No : <b> "+$scope.billno+"</b><br>Bill Date : <b>"+$scope.orderDate+"</b><br>Purchase No : <b>"+$scope.Purchase_id+"</b></td></tr></table>";*/
						
				htmlData += "<tr><td><br><table class=\"table table-bordered\"style=\"padding-left=15px ; font-family:calibri; font-size:13px; text-align:center;\"><thead>";
				htmlData += "<tr ><th style=\"border: 1px solid black;text-align:center\">SL</th>";
				htmlData += "<th style=\"border: 1px solid black; text-align:center;text-align:center\">BatchNo</th>";
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">Name</th>";
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">Exp</th>";
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">MSRP</th>";
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">Qty</th>";			
				
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">CGST</th>";	
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">SGST</th>";				
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">Total</th></tr></thead><tbody>";		
			var tmpstr="";
			for(var i = 0; i < $scope.PurchaseTrxs.length; i++){
				//$scope.PurchaseTrxs[i].expiration = $scope.PurchaseTrxs[i].expiration.toLocaleDateString();
				tmpstr += "<tr><td style=\"border: 1px solid black;\" align=\"center\">"+parseInt(i+1)+"</td><td style=\"border: 1px solid black;\">"+$scope.PurchaseTrxs[i].barcode;
				tmpstr += "</td><td style=\"border: 1px solid black;\">"+$scope.PurchaseTrxs[i].name;
				tmpstr += "</td><td style=\"border: 1px solid black;\">"+$scope.PurchaseTrxs[i].expiration;
				tmpstr += "</td><td style=\"border: 1px solid black;\" align=\"center\">"+parseFloat($scope.PurchaseTrxs[i].price).toFixed(2);
				tmpstr += "</td><td  style=\"border: 1px solid black;\" align=\"center\">"+$scope.PurchaseTrxs[i].quantity;				
							
				tmpstr += "</td><td style=\"border: 1px solid black;\" align=\"center\">"+parseFloat((parseFloat($scope.PurchaseTrxs[i].subtotal) * parseFloat($scope.PurchaseTrxs[i].tax)/100)/2).toFixed(2);					
				tmpstr += "</td><td style=\"border: 1px solid black;\" align=\"center\">"+parseFloat((parseFloat($scope.PurchaseTrxs[i].subtotal) * parseFloat($scope.PurchaseTrxs[i].tax)/100)/2).toFixed(2);
								
				tmpstr += "</td><td style=\"border: 1px solid black;\" align=\"right\" >"+parseFloat($scope.PurchaseTrxs[i].subtotal).toFixed(2)+"</tr>";			
			}
			
				htmlData += tmpstr+"<tr style=\"border: 1px solid black;\"><tr><tr style=\"border: 1px solid black;\"><td style=\"border: 1px solid black; text-align:left;\" rowspan=10 colspan=6><b>Notes :</b> "+$scope.notes+"</td></tr><tr></tr><tr></tr><tr><td style=\"border: 1px solid black;\" align=\"left\" colspan=2>BILL AMOUNT</td><td style=\"border: 1px solid black;\"text-align:center;\">"+parseFloat($scope.netbillamount).toFixed(2)+"</td></tr>";
				
				//htmlData += tmpstr+"<tr style=\"border: 1px solid black;\"><tr><tr style=\"border: 1px solid black;\"><td style=\"border: 1px solid black; text-align:left;\" rowspan=10 colspan=6><br><b>Notes :</b> "+$scope.notes+"</td></tr><tr></tr><tr></tr><tr><td style=\"border: 1px solid black;\" align=\"left\">GROSS AMOUNT</td><td style=\"border: 1px solid black;\"text-align:center;\">"+parseFloat($scope.grassamount).toFixed(2)+"</td></tr><tr><td style=\"border: 1px solid black;\" align=\"left\">CGST</td><td style=\"border: 1px solid black;\"text-align:center;\">"+parseFloat($scope.cgst).toFixed(2)+"</td></tr><tr><td style=\"border: 1px solid black;\" align=\"left\">SGST</td><td style=\"border: 1px solid black;\"text-align:center;\">"+parseFloat($scope.sgst).toFixed(2)+"</td></tr><tr><td style=\"border: 1px solid black;\" align=\"left\">BILL AMOUNT</td><td style=\"border: 1px solid black;\"text-align:center;\">"+parseFloat($scope.netbillamount).toFixed(2)+"</td></tr><tr><td style=\"border: 1px solid black;\" align=\"left\">PAID</td><td style=\"border: 1px solid black;\" text-align:center;\">"+parseFloat($scope.paid).toFixed(2)+"</td></tr><tr><td style=\"border: 1px solid black;\" align=\"left\">BALANCE</td><td style=\"border: 1px solid black;\" text-align:center;\">"+parseFloat($scope.balance).toFixed(2)+"</td></tr>";
							
				htmlData += "</table></td></tr>";
				htmlData += "</tbody></table></td></tr></table></body></html>"
		
		var myWindow = window.open("", "", "width=800,height=450");
		myWindow.document.write(htmlData);
		//setTimeout(function(){ myWindow.close() }, 6000);
	 	 
 }
 
 $scope.newPurchase = function(){
	 $scope.editPurchase = false;
	 $scope.gridOptions.columnDefs[6].enableCellEdit = false;
	 clearAll();
	 $scope.gridOptions.data = $scope.PurchaseTrxs;
	$scope.Purchase_id = "";
	$scope.print = true;	 
 }

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
