'use strict';
app.controller('ReturnstockController', function ($scope,$http,$timeout, uiGridConstants, $uibModal, $log, Upload, Flash,deemsoft)
{	var csrf = document.getElementById("csrf_token").value;
	var userID = document.getElementById("user__id").value;
	var $ctrl = this;
	$scope.cgst = "";
	$scope.sgst = "";
	$scope.cgst_perc = "";
	$scope.sgst_perc = "";
	$scope.grassamount = "";
	$scope.discount = "";
	$scope.paid = "";
	$scope.balance = "";
	$scope.checkavailableQty = "";
	$scope.checkNewavailableQty = "";
	$scope.print = true;
	$scope.save = true;
	$scope.returnstock = {};
	$scope.catalogdata = {};
		
	function clearAll(){
		$scope.editInvoice = false;
		$scope.showPayOptions = false;
		$scope.showAccountBlock = false;
		$scope.showCheckBlock = false;
		$scope.showCreditcardBlock = false;
		$scope.paymentSel=true;
		$scope.paymentMethod = "";
		$scope.contactsLists = [];
		var contact_id = "";
		var contactName = "";
		//$scope.contactName = "Guest";
		$scope.referredby = "";
		$scope.notes = "";
		$scope.paid =0;
		$scope.payment = 0;
		$scope.grandTotal = 0;
		$scope.grassamount = 0;
		$scope.balance = 0;
		$scope.cgst = 0;
		$scope.sgst = 0;
		$scope.totqty = "";
		$scope.InvoiceTrxs = [];
		$scope.Returnstock={};
		$scope.Address={};
		$scope.productcode = "";
		$scope.catalogLists = [];
		$scope.catalogdata = {};
		$scope.orderDate = deemsoft.IndianDate(new Date());
		var Returnstock_id = "";
		$scope.accountid = "";		
		$scope.payoptions = ['Account','Check','Cash','Credit Card', 'Free'];
		$scope.accounts = [];		
		deemsoft.getHTTP('../accountsrest/listaccounts/').then(function(r){$scope.accounts = r;});	
	}
	clearAll();
	$scope.getMaxInvoiceID = function(){
		deemsoft.getHTTP('../returnstockrest/getmaxid/')
			.then(function(r){	$scope.max_Invoice_id = parseInt(r[0].max_id)+1; });
	}

	$scope.getInvoice = function(){
		$scope.save = true;
		$scope.print = false;
		//$scope.myForm.balance.$error.required = true;
		$scope.Returnstock={};
		deemsoft.getHTTP('../returnstockrest/getreturnstock/'+$scope.Returnstock_id)
		.then(function(r){	
			$scope.Returnstock = r; 
			$scope.Returnstock_id = $scope.Returnstock.id;
			
			$scope.contact_id = $scope.Returnstock.contacts_id;
			$scope.paymentMethod = $scope.Returnstock.payment_method;
			$scope.check_number = $scope.Returnstock.check_number;
			$scope.accounts_id = $scope.Returnstock.payments_id;
			$scope.referredby = $scope.Returnstock.referredby;
			$scope.total = parseFloat($scope.Returnstock.total).toFixed(2);
			$scope.discount = parseFloat($scope.Returnstock.discount).toFixed(2);
			$scope.tax = parseFloat($scope.Returnstock.tax).toFixed(2);	
			$scope.paid = parseFloat($scope.Returnstock.paid).toFixed(2);
			$scope.balance = parseFloat($scope.Returnstock.balance).toFixed(2);			
			$scope.notes = $scope.Returnstock.notes;			
			$scope.orderDate = deemsoft.IndianDate($scope.Returnstock.return_date);
			if( $scope.notes === null ){ $scope.notes = " "; } 
			if( $scope.contact_id  !== "" ){
				$scope.getContact($scope.contact_id)	
			}	
			$scope.editInvoice = true;
			$scope.gridOptions.columnDefs[5].enableCellEdit = false;			
		});	

		$scope.InvoiceTrxs = {};
		
		deemsoft.getHTTP('../returnstockitemsrest/getreturnstockitemsbyreturnid/'+$scope.Returnstock_id)
		.then(function(r){	
			
			$scope.InvoiceTrxs = r;
			
			$scope.gridOptions.data = $scope.InvoiceTrxs;
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
			Flash.create('success', "Address Saved Successfully!");
			$scope.Contacts = {id:'',firstname:'',address_id:'',mobile_phone:'',email:'',created_by:'',updated_by:''};
			$scope.Contacts.firstname = $scope.contactName;
			$scope.Contacts.address_id = r.id;
			$scope.Contacts.mobile_phone = $scope.phone;
			$scope.Contacts.email = $scope.email;
			$scope.Contacts.created_by = userID;
			$scope.Contacts.updated_by = userID;
			deemsoft.postHTTP('../contactsrest/savecontacts/',$scope.Contacts,csrf)
			.then(function(r){
				$scope.contact_id = r.id;
				$scope.contactName = r.firstname;
				Flash.create('success', "Contact Info Saved Successfully!");		
			});
		});
		
	}
	
	$scope.removeItem = function(row){
		var index = $scope.gridOptions.data.indexOf(row.entity);
		$scope.gridOptions.data.splice(index, 1);				
		calculateTotals();	
	}
	
	
	$scope.getItemData = function(){		
		$scope.catalogdata = {};
		if( $scope.productcode === "" ) return;
		deemsoft.getHTTP('../catalogsrest/getcatalogs/'+$scope.productcode)
		.then(function(r){	
		
			alert(r.id);
			$scope.addToList(r.id,r.barcode,r.gst,r.name,r.expiration,r.msrp,r.purchase_price,r.max_discount,r.tax,r.quantity);
			
			
		});		
	
	}	
	
	$scope.addToList = function(id,barcode,gst,name,expiration,msrp,purchase_price,discount,tax,qty){
		
		$scope.checkavailableQty = qty;
		if( barcode === "") return;
					
		if(qty<1){
			$scope.checkavailableQty = "1";
			$scope.checkNewavailableQty = "0";
			//Flash.create('danger', "Out Of Stock! Available Quantity is"+qty);	return;
			var modalInstance = $uibModal.open({
			  animation: $ctrl.animationsEnabled,         
			  templateUrl: 'stockzeroalert',
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
		
		var indx = -1;
		if( $scope.InvoiceTrxs.length > 0 ){
		 indx =	$scope.InvoiceTrxs.map(function(x) {return x.barcode; }).indexOf(barcode);
	
		if( indx >= 0 ){
		  if( ($scope.InvoiceTrxs[indx].quantity+1) > qty ){ 
		  //Flash.create('danger', "Out Of Stock! Available Quantity is"+qty);	
		  return;
		  }
			
		  $scope.InvoiceTrxs[indx].quantity = parseInt($scope.InvoiceTrxs[indx].quantity)+1;
		  // comented by santhrupthi$scope.InvoiceTrxs[indx].subtotal = parseFloat((parseFloat($scope.InvoiceTrxs[indx].price) - (parseFloat($scope.InvoiceTrxs[indx].price) * parseFloat($scope.InvoiceTrxs[indx].discount)/100))*parseInt($scope.InvoiceTrxs[indx].quantity)).toFixed(2); 
		  
		 
		  $scope.InvoiceTrxs[indx].subtotal = parseFloat((parseFloat($scope.InvoiceTrxs[indx].price))*parseInt($scope.InvoiceTrxs[indx].quantity)).toFixed(2);
		  
		
		 // $scope.InvoiceTrxs[indx].subtotal = parseFloat(parseFloat($scope.InvoiceTrxs[indx].subtotal) + (parseFloat($scope.InvoiceTrxs[indx].subtotal) * parseFloat($scope.InvoiceTrxs[indx].tax)/100)).toFixed(2);
		  //$scope.totalTax += parseFloat((parseFloat($scope.InvoiceTrxs[indx].subtotal) * parseFloat($scope.InvoiceTrxs[indx].tax)/100)).toFixed(2);;
		  calculateTotals();
		  return;
		 }
		}
	
		$scope.catalogItem ={id:'',catalogs_id:'',return_id:'',barcode:'',name:'', expiration:'',price:'',purchase_price:'',quantity:0,gst:'',tax:'',discount:'',subtotal:''};
		
		$scope.catalogItem.catalogs_id = id;
		$scope.catalogItem.return_id = $scope.Returnstock_id;
		$scope.catalogItem.barcode = barcode; 
		$scope.catalogItem.name = name;
		//$scope.catalogItem.expiration = new Date(expiration);
		$scope.catalogItem.expiration = expiration;
		$scope.catalogItem.price = msrp;
		$scope.catalogItem.purchase_price = purchase_price;
		$scope.catalogItem.discount = discount;
		
		$scope.catalogItem.gst = gst;
		
		$scope.catalogItem.tax = tax;
		$scope.cgst_perc = $scope.catalogItem.tax/2;
		$scope.sgst_perc = $scope.catalogItem.tax/2;
		
		$scope.catalogItem.quantity = 1;
		
		// comented by santhrupthi  $scope.catalogItem.subtotal = parseFloat((parseFloat($scope.catalogItem.price) - (parseFloat($scope.catalogItem.price) * parseFloat($scope.catalogItem.discount)/100))*parseInt($scope.catalogItem.quantity)).toFixed(2);  
		
		
		$scope.catalogItem.subtotal = parseFloat((parseFloat($scope.catalogItem.price)*parseInt($scope.catalogItem.quantity)).toFixed(2))
		
		//$scope.catalogItem.subtotal = parseFloat(parseFloat($scope.catalogItem.subtotal) + (parseFloat($scope.catalogItem.subtotal) * parseFloat($scope.catalogItem.tax)/100)).toFixed(2);
		//$scope.totalTax = parseFloat((parseFloat($scope.catalogItem.subtotal) * parseFloat($scope.catalogItem.tax)/100)).toFixed(2);
		 
		$scope.InvoiceTrxs.push($scope.catalogItem);
		calculateTotals();
	
	}
	
	
	function calculateTotals(){
		
		if( $scope.InvoiceTrxs.length <= 0 ){return;}
		$scope.total = 0;
		$scope.grandTotal = 0;
		$scope.tax = 0;
		$scope.discount = 0;
		$scope.totqty = 0;
		for(var i = 0; i < $scope.InvoiceTrxs.length; i++){
			//$scope.InvoiceTrxs[i].expiration = new Date($scope.InvoiceTrxs[i].expiration);
			$scope.total = parseFloat($scope.total) + parseFloat($scope.InvoiceTrxs[i].subtotal);
			var disc = parseFloat((parseFloat($scope.InvoiceTrxs[i].price) * parseFloat($scope.InvoiceTrxs[i].discount)/100)*parseInt($scope.InvoiceTrxs[i].quantity)).toFixed(2);
			//var ttax = parseFloat(((parseFloat($scope.InvoiceTrxs[i].price) * parseFloat($scope.InvoiceTrxs[i].quantity)) - parseFloat(disc)) * parseFloat($scope.InvoiceTrxs[i].tax)/100).toFixed(2);
			//var ttax = parseFloat(((parseFloat($scope.InvoiceTrxs[i].price) * parseFloat($scope.InvoiceTrxs[i].quantity))) * parseFloat($scope.InvoiceTrxs[i].tax)/100).toFixed(2);
			
			var ttax = parseFloat(((parseFloat($scope.InvoiceTrxs[i].subtotal))) * parseFloat($scope.InvoiceTrxs[i].tax)/100).toFixed(2);
			
			$scope.tax = parseFloat($scope.tax) + parseFloat(ttax);			
			$scope.discount = parseFloat($scope.discount) + parseFloat(disc);
			$scope.totqty = parseFloat($scope.totqty) + parseFloat($scope.InvoiceTrxs[i].quantity);
			$scope.checkNewavailableQty = $scope.InvoiceTrxs[i].quantity;
			

		}
		$scope.total = parseFloat($scope.total).toFixed(2);
		$scope.tax = parseFloat($scope.tax).toFixed(2);
		$scope.discount = parseFloat($scope.discount).toFixed(2);		
		$scope.grandTotal = parseFloat($scope.total).toFixed(0);		
		//$scope.grassamount = parseFloat(parseFloat($scope.total) + parseFloat($scope.discount)).toFixed(2);
		//$scope.grandTotal = parseFloat(parseFloat($scope.total) + parseFloat($scope.tax)).toFixed(2);
		$scope.grassamount = parseFloat(parseFloat($scope.total)).toFixed(0);
		$scope.balance = parseFloat(parseFloat($scope.grandTotal) - parseFloat($scope.paid)).toFixed(2);
		$scope.cgst = parseFloat($scope.tax)/2 ;
		$scope.sgst = parseFloat($scope.tax)/2 ;
		
		
	}
	
	$scope.refreshSearchProduct = function(search){
		if( search.length > 3 ){
			deemsoft.getHTTP('../catalogsrest/searchcatalogsbyname/'+search).then(function(r){
				$scope.catalogLists = r; 
			
			//$scope.checkavailableQty = $scope.catalogLists[0].quantity;
			
			
			});		
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
    { field: 'id', displayName: 'SL NO', width: '5%', enableCellEdit: false, cellClass: 'text-center',cellTemplate:cellTempl },    
    { field: 'barcode', displayName: 'Batch No', enableCellEdit: false,cellClass: 'text-center', width: '10%' },
	{ field: 'name', displayName: 'Product Description',enableCellEdit: false, width: '20%',cellClass: 'text-center',width: '20%' },
	{ field: 'expiration', displayName: 'Expiration', width: '15%',cellClass: 'text-center', enableCellEdit: false},
	{ field: 'price', displayName: 'MRP', width: '10%', enableCellEdit: false, cellClass: 'text-center',cellFilter: 'number: 2' },
	{ field: 'purchase_price', displayName: 'Purchase Price', width: '10%', enableCellEdit: false, cellClass: 'text-center',cellFilter: 'number: 2' },
	{ field: 'quantity', displayName: 'Quantity', footerCellTemplate: '<div class="ui-grid-cell-contents text-center"> {{grid.appScope.totqty}}</div>', cellClass: 'text-center',width: '7%' },
	/*{ field: 'discount', displayName: 'Discount(%)', footerCellTemplate: '<div class="ui-grid-cell-contents text-center"> {{grid.appScope.discount}}</div>', cellClass: 'text-center', width: '8%', enableCellEdit:false,cellFilter: 'number: 2' },*/
	{ field: 'tax', displayName: 'GST(%)', footerCellTemplate: '<div class="ui-grid-cell-contents text-center"> {{grid.appScope.tax}}</div>',  cellClass: 'text-center',width: '10%', enableCellEdit:false, cellFilter: 'number: 2' },
	{ field: 'subtotal', displayName: 'Sub Total',  footerCellTemplate: '<div class="ui-grid-cell-contents text-right"> {{grid.appScope.total}}</div>', cellClass: 'text-right', width: '10%', enableCellEdit:false, cellFilter: 'number: 2' }	
     ];
	$scope.gridOptions.data = $scope.InvoiceTrxs;
	$scope.msg = {};
 
	$scope.gridOptions.onRegisterApi = function(gridApi){
		
          $scope.gridApi = gridApi;
          gridApi.edit.on.afterCellEdit($scope,function(rowEntity, colDef, newValue, oldValue){
			$scope.$apply();
			
			var indx = $scope.gridOptions.data.indexOf(rowEntity);
			//if( (newValue - oldValue) > 0 ){
				
				deemsoft.getHTTP('../catalogsrest/searchcatalogsbybarcode/'+$scope.InvoiceTrxs[indx].barcode)
				.then(function(r){	
					console.log(r);											
					$scope.checkavailableQty = r[0].quantity;
									
					$scope.checkNewavailableQty = newValue;
				
					if(r[0].quantity < newValue) {
					
						
						console.log("testing modal");
						var modalInstance = $uibModal.open({
						  animation: $ctrl.animationsEnabled,         
						  templateUrl: 'stockalert',
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
						  newValue = 1;
						
						  $scope.InvoiceTrxs[indx].quantity = newValue;
						// comented by santhrupthi  $scope.InvoiceTrxs[indx].subtotal = parseFloat((parseFloat($scope.InvoiceTrxs[indx].price) - (parseFloat($scope.InvoiceTrxs[indx].price) * parseFloat($scope.InvoiceTrxs[indx].discount)/100))*parseInt($scope.InvoiceTrxs[indx].quantity)).toFixed(2);
						  calculateTotals();
						 $scope.InvoiceTrxs[indx].subtotal = parseFloat((parseFloat($scope.InvoiceTrxs[indx].price))*parseInt($scope.InvoiceTrxs[indx].quantity)).toFixed(2)
						 //$log.info('Modal dismissed at: ' + new Date());
						});
		
						//Flash.create('danger', "Out Of Stock! Available Quantity is"+" "+r[0].quantity);
					}
					
					/*if( r.quantity < (newValue - oldValue)){
						Flash.create('danger', "Out Of Stock! Available Quantity is"+r.quantity);
					}*/
					else{
						$scope.InvoiceTrxs[indx].quantity = newValue;						
						// comented by santhrupthi  $scope.InvoiceTrxs[indx].subtotal = parseFloat((parseFloat($scope.InvoiceTrxs[indx].price) - (parseFloat($scope.InvoiceTrxs[indx].price) * parseFloat($scope.InvoiceTrxs[indx].discount)/100))*parseInt($scope.InvoiceTrxs[indx].quantity)).toFixed(2);
						 $scope.InvoiceTrxs[indx].subtotal = parseFloat((parseFloat($scope.InvoiceTrxs[indx].price))*parseInt($scope.InvoiceTrxs[indx].quantity)).toFixed(2)
						//$scope.InvoiceTrxs[indx].subtotal = parseFloat(parseFloat($scope.InvoiceTrxs[indx].subtotal) + (parseFloat($scope.InvoiceTrxs[indx].subtotal) * parseFloat($scope.InvoiceTrxs[indx].tax)/100)).toFixed(2);
						calculateTotals();						
					}
				});	
				
			//}
			/*else {
			$scope.InvoiceTrxs[indx].quantity = newValue;						
			$scope.InvoiceTrxs[indx].subtotal = parseFloat((parseFloat($scope.InvoiceTrxs[indx].price) - (parseFloat($scope.InvoiceTrxs[indx].price) * parseFloat($scope.InvoiceTrxs[indx].discount)/100))*parseInt($scope.InvoiceTrxs[indx].quantity)).toFixed(2);
			//$scope.InvoiceTrxs[indx].subtotal = parseFloat(parseFloat($scope.InvoiceTrxs[indx].subtotal) + (parseFloat($scope.InvoiceTrxs[indx].subtotal) * parseFloat($scope.InvoiceTrxs[indx].tax)/100)).toFixed(2);

			calculateTotals();
			}*/
          });
    }
  
 $scope.onPaymentChange = function(){
      	 
	$scope.balance = parseFloat(parseFloat($scope.grandTotal) - parseFloat($scope.paid)).toFixed(2);
 }
 
 $scope.onPaymentSelect = function(){
	 if($scope.paymentMethod != 'Free'){
		 $scope.paid = $scope.grandTotal;
		 $scope.balance = 0;		 
	 }else{
		$scope.paid = 0;
		 $scope.balance = 0;
	 }
		$scope.paymentSel = false;
		 $scope.save = false;
 }
 
 if($scope.contactName == "" || $scope.contactName == undefined){
	 
	 $scope.contactName = "Guest";
	
 } 
 if($scope.contactName == "Guest"){
	 
	 $scope.contact_id = 1;
	
 }
 $scope.saveInvoice = function(){
	
	
	
	if($scope.checkavailableQty < $scope.checkNewavailableQty) {
		alert( "Out Of Stock! Available Quantity is" + " " +$scope.checkavailableQty );
		
	}else
	{
		
		$scope.Returnstock={};		
		$scope.Returnstock.id = $scope.Returnstock_id;
		$scope.Returnstock.contacts_id = $scope.contact_id;
		$scope.Returnstock.title = $scope.contactName;
		$scope.Returnstock.referredby = $scope.referredby;
		$scope.Returnstock.payment_method = $scope.paymentMethod;
		$scope.Returnstock.check_number = $scope.check_number;
		$scope.Returnstock.payments_id = $scope.accounts_id;
		$scope.Returnstock.total = parseFloat($scope.grandTotal).toFixed(0);
		$scope.Returnstock.discount = parseFloat($scope.discount).toFixed(2);
		$scope.Returnstock.tax = parseFloat($scope.tax).toFixed(2);	
		$scope.Returnstock.paid = parseFloat($scope.paid).toFixed(2);
		$scope.Returnstock.balance = parseFloat($scope.balance).toFixed(2);			
		$scope.Returnstock.notes = $scope.notes;
		$scope.Returnstock.created_by = userID;
		$scope.Returnstock.updated_by = userID;
		
		$scope.Returnstock.sgst_amount = $scope.sgst;			
		$scope.Returnstock.cgst_amount = $scope.cgst;
		
		deemsoft.postHTTP('../returnstockrest/savereturnstock/', $scope.Returnstock,csrf)
		.then(function(inv){	
			$scope.Returnstock_id = inv.id;	
			$scope.print = false;		
			for(var i = 0; i < $scope.InvoiceTrxs.length; i++){
				$scope.Returnstock = inv.id;
				$scope.InvoiceTrxs[i].return_id = inv.id;
				
				//$scope.InvoiceTrxs[i].expiration = $scope.InvoiceTrxs[i].expiration.toLocaleDateString();
			}
			
		
			deemsoft.postHTTP('../returnstockitemsrest/savereturnstockitems/',$scope.InvoiceTrxs,csrf)
			.then(function(d){ 
				//Flash.create('success', "Returnstock Saved Successfully!");
					console.log("testing modal");
						var modalInstance = $uibModal.open({
						  animation: $ctrl.animationsEnabled,         
						  templateUrl: 'saveinvoice',
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
				$scope.editInvoice = true;
				$scope.gridOptions.columnDefs[5].enableCellEdit = false;
				
			});		
		});
	}
 
 }
 
 $scope.printInvoice = function(){
			 $scope.cgst = $scope.tax/2;
			 $scope.sgst = $scope.tax/2;
			 var htmlData = "<html><head><link rel=\"stylesheet\" href=\"http://localhost:8080/pharmacysoft/static/css/bootstrap/bootstrap.min.css\">";  
				htmlData += "</head><body onload=\"window.print()\"  ><table  width=100%><tr style=\"background: -webkit-linear-gradient(rgba(230, 22, 23, 0), rgba(230, 22, 23, 0)), url(/pharmacysoft/static/images/deemsoft/amchlohonew.jpg) no-repeat;background-position:center; background-size:contain; text-align:center\">"+

			
			"<td width=0px></td>"+
			
			"<td style=\"morgin-left:15px\">"+
				
				
				"<table style=\"padding-left=15px ; font-family:calibri; font-size:13px;\" width=100%>";		
				htmlData += "<tr><td colspan='2' align='center' style=\" font-family:  Times New Roman, Times, serif;\"><b>|| Jai Sri Gurudev ||</b><br>Sri Adichunchanagiri Shikshana Trust (R)<br><span style=\" font-family: Friz quadrata BT !important;\">ADICHUNCHANAGIRI AYURVEDIC MEDICAL COLLEGE, HOSPITAL AND RESEARCH CENTER</span>(<span style=\"font-family:Colonna MT;font-size:18px !important;\">A</span><span style=\" font-family:Bookman Old Style !important;\">MC</span>)<br>Nagarur , Bengaluru - 562 123<br>Ph.No : +91 9481861519, Email : principal@amc.org.in</br> www.amc.org.in<br><hr><b>Cash Bill/Receipt</b><br></td></tr>";

				htmlData += "<tr><td>Patient Name:<b> "+$scope.contactName+"</b></td><td style=\"text-align:right;\">Returnstock No:<b> "+$scope.Returnstock_id+"</b></td></tr>";
				htmlData += "<tr><td>Doctor:<b> "+$scope.referredby+"</b></td><td style=\"text-align:right;\">Date:<b> "+$scope.orderDate+"</b></td></tr></table><br>";				
				htmlData += "<br><table class=\"table table-bordered \"  style=\"font-family:calibri; font-size:13px;  border: 1px solid black;\" width=50%><thead>";
				htmlData += "<tr style=\"border: 1px solid black;\"><th style=\"border: 1px solid black;text-align:center\">SL</th>";
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">Name</th>";
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">BatchNo</th>";				
				//htmlData += "<th style=\"border: 1px solid black;\">Exp</th>";
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">Price</th>";
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">Qty</th>";			
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">CGST</th>";	
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">SGST</th>";
				
				htmlData += "<th style=\"border: 1px solid black;text-align:center\">Total</th></tr></thead><tbody>";		
			var tmpstr="";
			for(var i = 0; i < $scope.InvoiceTrxs.length; i++){
				tmpstr += "<tr style=\"border: 1px solid black;\"><td style=\"border: 1px solid black;\" align=\"center\">"+parseInt(i+1);
				tmpstr += "</td><td style=\"border: 1px solid black;\">"+$scope.InvoiceTrxs[i].name;			
				tmpstr += "</td><td style=\"border: 1px solid black;\">"+$scope.InvoiceTrxs[i].barcode;				
				//tmpstr += "</td><td style=\"border: 1px solid black;\">"+$scope.InvoiceTrxs[i].expiration;	
				tmpstr += "</td><td style=\"border: 1px solid black;\" align=\"center\">"+parseFloat($scope.InvoiceTrxs[i].price).toFixed(2);
				tmpstr += "</td><td style=\"border: 1px solid black;\" align=\"center\">"+$scope.InvoiceTrxs[i].quantity;							
				tmpstr += "</td><td style=\"border: 1px solid black;\" align=\"center\">"+parseFloat((parseFloat($scope.InvoiceTrxs[i].subtotal) * parseFloat($scope.InvoiceTrxs[i].tax)/100)/2).toFixed(2);					
				tmpstr += "</td><td style=\"border: 1px solid black;\" align=\"center\">"+parseFloat((parseFloat($scope.InvoiceTrxs[i].subtotal) * parseFloat($scope.InvoiceTrxs[i].tax)/100)/2).toFixed(2);					
				tmpstr += "</td><td style=\"border: 1px solid black;\" align=\"right\">"+parseFloat($scope.InvoiceTrxs[i].subtotal).toFixed(2)+"</tr>";		
			}	
			
				htmlData += tmpstr+"<tr style=\"border: 1px solid black;\"><tr><tr style=\"border: 1px solid black;\"><td style=\"border: 1px solid black;\" rowspan=3 colspan=5><b>Notes :</b> "+$scope.notes+"</td></tr><tr><td style=\"border: 1px solid black;\" align=\"right\" colspan=2><b>BILL AMOUNT</b></td><td style=\"border: 1px solid black;\" align=\"right\"><b>"+parseFloat($scope.grandTotal).toFixed(0)+"</b></td></tr>";
				
				//htmlData += tmpstr+"<tr style=\"border: 1px solid black;\"><tr><tr style=\"border: 1px solid black;\"><td style=\"border: 1px solid black;\" rowspan=10 colspan=6><br><b>Notes :</b> "+$scope.notes+"</td></tr><tr></tr><tr><td style=\"border: 1px solid black;\">GROSS AMOUNT</td><td style=\"border: 1px solid black;\"text-align:center;\">"+parseFloat($scope.grassamount).toFixed(2)+"</td></tr><tr></tr><tr><td style=\"border: 1px solid black;\">CGST</td><td style=\"border: 1px solid black;\"text-align:center;\">"+parseFloat($scope.cgst).toFixed(2)+"</td></tr><tr></tr><tr><td style=\"border: 1px solid black;\">SGST</td><td style=\"border: 1px solid black;\"text-align:center;\">"+parseFloat($scope.sgst).toFixed(2)+"</td></tr><tr><td style=\"border: 1px solid black;\">BILL AMOUNT</td><td style=\"border: 1px solid black;\"text-align:center;\">"+parseFloat($scope.grandTotal).toFixed(2)+"</td></tr><tr><td style=\"border: 1px solid black;\">PAID</td><td style=\"border: 1px solid black;\" text-align:center;\">"+parseFloat($scope.paid).toFixed(2)+"</td></tr><tr><td style=\"border: 1px solid black;\">BALANCE</td><td style=\"border: 1px solid black;\" text-align:center;\">"+parseFloat($scope.balance).toFixed(2)+"</td></tr>";
				
				//htmlData += tmpstr+"<tr><td colspan=6><br><br><br> "+$scope.notes+"</td><td colspan=3><table class=\"table\">";				
				//htmlData += "<tr><td>Discount</td><td align=\"right\">"+parseFloat($scope.discount).toFixed(2)+"</td></tr>";
				//htmlData += "<br>"+"<b>"+"CGST Amt"+"</b>"+"("+$scope.cgst_perc+"%)" + " " +":" + " " + " " +""+parseFloat($scope.cgst).toFixed(2)+"<hr //style=\"margin-left:0px\">";
				//htmlData += "<b>"+"SGST Amt"+"</b>"+"("+$scope.sgst_perc+"%)"+ " " +":" + " " + " " +""+parseFloat($scope.sgst).toFixed(2)+"<hr>";
				
				//htmlData += "<b>"+"Total"+"</b>"+ " " +":" + " " + " " +parseFloat($scope.grandTotal).toFixed(2)+"";				
				//htmlData += "<tr><td>Balance</td><td align=\"right\">"+parseFloat($scope.balance).toFixed(2)+"</td></tr>";
				//htmlData += "<tr><td>Payment Method</td><td>"+$scope.paymentMethod+"</td></tr>";					
							
				htmlData += "</table></td></tr>";
				
				htmlData += "<tr><td rowspan=4 colspan='9' style=\"border: none;\" align='right' style=\" font-family:  Times New Roman, Times, serif;font-size:15px;\"><br><br><br><b>Cashier</b></td></tr>";
				
				htmlData += "</tbody></table></td></tr></table>"+	
			"</td>"+"</tr></table>" + "</body></html>"		
		var myWindow = window.open("", "", "width=820,height=500");
		myWindow.document.write(htmlData);
		//setTimeout(function(){ myWindow.close() }, 6000);
	 	 
 }
 
 $scope.newInvoice = function(){
	 $scope.editInvoice = false;
	 $scope.gridOptions.columnDefs[5].enableCellEdit = false;
	 clearAll();
	 //sessionStorage.removeItem("Returnstock_id");
	 $scope.gridOptions.data = $scope.InvoiceTrxs;	
	$scope.Returnstock = {};
	$scope.Returnstock_id = "";

	
	
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

