'use strict';
app.controller('SalesController', function ($scope,$http,$timeout, uiGridConstants, $uibModal, $log, Upload, Flash,deemsoft)
{	var csrf = document.getElementById("csrf_token").value;
	var userID = document.getElementById("user__id").value;

	function clearAll(){
		$scope.editInvoice = false;
		$scope.showPayOptions = false;
		$scope.showAccountBlock = false;
		$scope.showCheckBlock = false;
		$scope.showCreditcardBlock = false;
		$scope.popProductList = false;
		$scope.paymentSel=true;
		$scope.paymentMethod = "";
		$scope.contactsLists = [];
		$scope.contact_id = "1";
		$scope.contactName = "Guest";
		$scope.referredby = "";
		$scope.notes = "";
		$scope.paid =0;
		$scope.payment = 0;
		$scope.totqty = "";
		$scope.InvoiceTrxs = [];
		$scope.Invoice={};
		$scope.Address={};
		$scope.productcode = "";
		$scope.title = "";
		$scope.catalogLists = [];
		$scope.invoiceLists = [];
		$scope.orderDate = new Date();
		$scope.orderDate = $scope.orderDate.toLocaleDateString();
		$scope.Invoice_id = "";
		$scope.accountid = "";		
		$scope.payoptions = ['Account','Check','Cash','Credit Card'];
		$scope.accounts = [];
		deemsoft.getHTTP('../catalogsrest/listcatalogs/').then(function(r){$scope.catalogLists = r; });		
		deemsoft.getHTTP('../accountsrest/listaccounts/').then(function(r){$scope.accounts = r;});		
		$scope.getInvoicesList();
	}
	
	$scope.selectMember = function(name, contact_id){
		$scope.contact_id = contact_id;
		$scope.title = name;
	}
	
	$scope.getInvoicesList = function(){
		deemsoft.getHTTP('../invoicesrest/listinvoicesbystatusanduser/'+userID)
			.then(function(r){	$scope.invoiceLists = r; });
	}
	
	$scope.getInvoice = function(){
		$scope.Invoice={};
		deemsoft.getHTTP('../invoicesrest/getinvoices/'+$scope.Invoice_id)
		.then(function(r){	
			$scope.Invoice = r; 
			$scope.contact_id = $scope.Invoice.contacts_id;
			$scope.paymentMethod = $scope.Invoice.payment_method;
			$scope.check_number = $scope.Invoice.check_number;
			$scope.accounts_id = $scope.Invoice.payments_id;
			$scope.referredby = $scope.Invoice.referredby;
			$scope.total = parseFloat($scope.Invoice.total).toFixed(2);
			$scope.discount = parseFloat($scope.Invoice.discoun).toFixed(2);
			$scope.tax = parseFloat($scope.Invoice.tax).toFixed(2);	
			$scope.paid = parseFloat($scope.Invoice.paid).toFixed(2);
			$scope.balance = parseFloat($scope.Invoice.balance).toFixed(2);			
			$scope.notes = $scope.Invoice.notes;
			$scope.orderDate = new Date($scope.Invoice.invoice_date);
			$scope.orderDate = $scope.orderDate.toLocaleDateString();
			if( $scope.notes === null ){ $scope.notes = " "; } 						
		});	
	}
	
	$scope.getInvoiceData = function(id){
		$scope.Invoice_id = id;		
		$scope.InvoiceTrxs = [];
		deemsoft.getHTTP('../invoicesitemsrest/getinvoicesitemsbyinvoiceid/'+$scope.Invoice_id)
		.then(function(r){			
			$scope.InvoiceTrxs = r;
			$scope.gridOptions.data = $scope.InvoiceTrxs;
			calculateTotals();
		});
	}
	
	$scope.removeItem = function(row){	
		$scope.InvoiceTrxs.splice(row.entity.id-1,1);
		calculateTotals();	
	}
	
	$scope.getItemData = function(barcode){	
		if( barcode === "" ) return;
		$scope.popProductList = false;
		deemsoft.getHTTP('../catalogsrest/searchcatalogsbybarcode/'+barcode)
		.then(function(r){
			$scope.addToList(r[0].id,r[0].barcode,r[0].name,r[0].expiration,r[0].msrp,r[0].Invoice_price,r[0].max_discount,r[0].tax,r[0].quantity); 
		});		
	
	}	
	
	$scope.addToList = function(id,barcode,name,expiration,msrp,purchase_price,discount,tax,qty){
		if( barcode === "") return;
					
		if(qty<1){Flash.create('danger', "Out Of Stock! Available Quantity is"+qty);	return;}
		
		var indx = -1;
		if( $scope.InvoiceTrxs.length > 0 ){
		 indx =	$scope.InvoiceTrxs.map(function(x) {return x.barcode; }).indexOf(barcode);
	
		if( indx >= 0 ){
		  if( ($scope.InvoiceTrxs[indx].quantity+1) > qty ){ 
		  Flash.create('danger', "Out Of Stock! Available Quantity is"+qty);	
		  return;
		  }
			
		  $scope.InvoiceTrxs[indx].quantity = parseInt($scope.InvoiceTrxs[indx].quantity)+1;
		  $scope.InvoiceTrxs[indx].subtotal = parseFloat((parseFloat($scope.InvoiceTrxs[indx].price) - (parseFloat($scope.InvoiceTrxs[indx].price) * parseFloat($scope.InvoiceTrxs[indx].discount)/100))*parseInt($scope.InvoiceTrxs[indx].quantity)).toFixed(2);
		  $scope.InvoiceTrxs[indx].subtotal = parseFloat(parseFloat($scope.InvoiceTrxs[indx].subtotal) + (parseFloat($scope.InvoiceTrxs[indx].subtotal) * parseFloat($scope.InvoiceTrxs[indx].tax)/100)).toFixed(2);
		  calculateTotals();
		  return;
		 }
		}
	
		$scope.catalogItem ={id:'',catalogs_id:'',invoices_id:'',barcode:'',name:'', expiration:'',price:'',purchase_price:'',quantity:0,tax:'',discount:'',subtotal:''};
		$scope.catalogItem.catalogs_id = id;
		$scope.catalogItem.invoices_id = $scope.Invoice_id;
		$scope.catalogItem.barcode = barcode; 
		$scope.catalogItem.name = name;
		$scope.catalogItem.expiration = expiration;
		$scope.catalogItem.price = msrp;
		$scope.catalogItem.purchase_price = purchase_price;
		$scope.catalogItem.discount = discount;
		$scope.catalogItem.tax = tax;
		$scope.catalogItem.quantity = 1;
		$scope.catalogItem.subtotal = parseFloat((parseFloat($scope.catalogItem.price) - (parseFloat($scope.catalogItem.price) * parseFloat($scope.catalogItem.discount)/100))*parseInt($scope.catalogItem.quantity)).toFixed(2);
		$scope.catalogItem.subtotal = parseFloat(parseFloat($scope.catalogItem.subtotal) + (parseFloat($scope.catalogItem.subtotal) * parseFloat($scope.catalogItem.tax)/100)).toFixed(2);
		$scope.InvoiceTrxs.push($scope.catalogItem);
		calculateTotals();
	
	}
	
	
	function calculateTotals(){
		if( $scope.InvoiceTrxs.length <= 0 ){return;}
		$scope.total = 0;
		$scope.tax = 0;
		$scope.discount = 0;
		$scope.totqty = 0;
		for(var i = 0; i < $scope.InvoiceTrxs.length; i++){        
			$scope.total = parseFloat($scope.total) + parseFloat($scope.InvoiceTrxs[i].subtotal);
			var disc = parseFloat((parseFloat($scope.InvoiceTrxs[i].price) * parseFloat($scope.InvoiceTrxs[i].discount)/100)*parseInt($scope.InvoiceTrxs[i].quantity)).toFixed(2);
			var ttax = parseFloat(((parseFloat($scope.InvoiceTrxs[i].price) * parseFloat($scope.InvoiceTrxs[i].quantity)) - parseFloat(disc)) * parseFloat($scope.InvoiceTrxs[i].tax)/100).toFixed(2);
			$scope.tax = parseFloat($scope.tax) + parseFloat(ttax);			
			$scope.discount = parseFloat($scope.discount) + parseFloat(disc);
			$scope.totqty = parseFloat($scope.totqty) + parseFloat($scope.InvoiceTrxs[i].quantity);
		}
		$scope.total = parseFloat($scope.total).toFixed(2);
		$scope.tax = parseFloat($scope.tax).toFixed(2);
		$scope.discount = parseFloat($scope.discount).toFixed(2);		
		
		$scope.balance = parseFloat(parseFloat($scope.total) - parseFloat($scope.paid)).toFixed(2);
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
    { field: 'name', displayName: 'Product Description',enableCellEdit: false },
	{ field: 'price', displayName: 'Price', width: '10%', enableCellEdit: false, cellClass: 'text-right',cellFilter: 'number: 2' },
	{ field: 'quantity', displayName: 'Quantity', footerCellTemplate: '<div class="ui-grid-cell-contents text-center"> {{grid.appScope.totqty}}</div>',width: '10%', cellClass: 'text-center' },
	{ field: 'tax', displayName: 'Tax', footerCellTemplate: '<div class="ui-grid-cell-contents text-right"> {{grid.appScope.tax}}</div>', width: '10%', cellClass: 'text-right', enableCellEdit: false, cellFilter: 'number: 2' },
	{ field: 'subtotal', displayName: 'Sub Total',  footerCellTemplate: '<div class="ui-grid-cell-contents text-right"> {{grid.appScope.total}}</div>', width: '10%', enableCellEdit: false, cellClass: 'text-right',cellFilter: 'number: 2' }	
     ];
	$scope.gridOptions.data = $scope.InvoiceTrxs;

	$scope.gridOptions.onRegisterApi = function(gridApi){
          $scope.gridApi = gridApi;
          gridApi.edit.on.afterCellEdit($scope,function(rowEntity, colDef, newValue, oldValue){
			$scope.$apply();
			var indx = $scope.gridOptions.data.indexOf(rowEntity);
			if( (newValue - oldValue) > 0 ){
				
				deemsoft.getHTTP('../catalogsrest/searchcatalogsbybarcode/'+$scope.InvoiceTrxs[indx].barcode)
				.then(function(r){
					if( r.quantity < (newValue - oldValue)){
						Flash.create('danger', "Out Of Stock! Available Quantity is"+r.quantity);
					}
					else{
						$scope.InvoiceTrxs[indx].quantity = newValue;						
						$scope.InvoiceTrxs[indx].subtotal = parseFloat((parseFloat($scope.InvoiceTrxs[indx].price) - (parseFloat($scope.InvoiceTrxs[indx].price) * parseFloat($scope.InvoiceTrxs[indx].discount)/100))*parseInt($scope.InvoiceTrxs[indx].quantity)).toFixed(2);
						$scope.InvoiceTrxs[indx].subtotal = parseFloat(parseFloat($scope.InvoiceTrxs[indx].subtotal) + (parseFloat($scope.InvoiceTrxs[indx].subtotal) * parseFloat($scope.InvoiceTrxs[indx].tax)/100)).toFixed(2);
						calculateTotals();						
					}
				});	
				
			}
			else {
			$scope.InvoiceTrxs[indx].quantity = newValue;						
			$scope.InvoiceTrxs[indx].subtotal = parseFloat((parseFloat($scope.InvoiceTrxs[indx].price) - (parseFloat($scope.InvoiceTrxs[indx].price) * parseFloat($scope.InvoiceTrxs[indx].discount)/100))*parseInt($scope.InvoiceTrxs[indx].quantity)).toFixed(2);
			$scope.InvoiceTrxs[indx].subtotal = parseFloat(parseFloat($scope.InvoiceTrxs[indx].subtotal) + (parseFloat($scope.InvoiceTrxs[indx].subtotal) * parseFloat($scope.InvoiceTrxs[indx].tax)/100)).toFixed(2);

			calculateTotals();
			}
          });
    }
  
 $scope.onPaymentChange = function(){	
	$scope.balance = parseFloat(parseFloat($scope.total) - parseFloat($scope.paid)).toFixed(2);
 }
 
 $scope.onPaymentSelect = function(){
	 $scope.paid = $scope.total;
	 $scope.balance = 0;
	 $scope.paymentSel = false;	
	
 }
 
 $scope.CreateInvoice = function(){	
	$scope.Invoice={};	
	$scope.Invoice.contacts_id = $scope.contact_id;
	$scope.Invoice.title = $scope.title;
	$scope.Invoice.status = 1;
	$scope.Invoice.created_by = userID;
	$scope.Invoice.updated_by = userID;
		
	deemsoft.postHTTP('../invoicesrest/saveinvoices/', $scope.Invoice,csrf)
	.then(function(inv){
		
	});
	
 }
 
 $scope.saveInvoice = function(){	
	$scope.Invoice={};		
	$scope.Invoice.id = $scope.Invoice_id;
	$scope.Invoice.contacts_id = $scope.contact_id;
	$scope.Invoice.referredby = $scope.referredby;
	$scope.Invoice.payment_method = $scope.paymentMethod;
	$scope.Invoice.check_number = $scope.check_number;
	$scope.Invoice.payments_id = $scope.accounts_id;
	$scope.Invoice.total = parseFloat($scope.total).toFixed(2);
	$scope.Invoice.discount = parseFloat($scope.discount).toFixed(2);
	$scope.Invoice.tax = parseFloat($scope.tax).toFixed(2);	
	$scope.Invoice.paid = parseFloat($scope.paid).toFixed(2);
	$scope.Invoice.balance = parseFloat($scope.balance).toFixed(2);			
	$scope.Invoice.notes = $scope.notes;
	$scope.Invoice.created_by = userID;
	$scope.Invoice.updated_by = userID;
		
	deemsoft.postHTTP('../invoicesrest/saveinvoices/', $scope.Invoice,csrf)
	.then(function(inv){	
		
	});
 }
 
 $scope.insertItems = function(){
		deemsoft.postHTTP('../invoicesitemsrest/saveinvoicesitems/',$scope.InvoiceTrxs,csrf)
		.then(function(d){ 
			Flash.create('success', "Invoice Saved Successfully!");
			$scope.editInvoice = true;
			$scope.gridOptions.columnDefs[5].enableCellEdit = false;
			
		});	
 }		
 
 $scope.printInvoice = function(){
	 var htmlData = "<html><head><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">";  
				htmlData += "</head><body onload=\"window.print()\"><table><tr><td><table>";		
				
				htmlData += "<tr><td>To  </td><td>:<b> "+$scope.contactName+" ( Ph: "+$scope.phone+")</b></td></tr>";
				htmlData += "<tr><td>Invoice No  </td><td>:<b> "+$scope.Invoice_id+"</b></td></tr>";
				htmlData += "<tr><td>Invoice Date </td><td>:<b> "+$scope.orderDate+"</b></td></tr></table></td></tr>";
						
				htmlData += "<tr><td><br><table class=\"table table-bordered\"><thead>";
				htmlData += "<tr ><th>SL.No.</th>";
				htmlData += "<th>BatchNo.</th>";
				htmlData += "<th>ProductDescription</th>";
				htmlData += "<th>Expiration</th>";
				htmlData += "<th>MSRP</th>";
				htmlData += "<th>Quantity</th>	";			
				htmlData += "<th>Discount</th>	";
				htmlData += "<th>Tax</th>	";				
				htmlData += "<th>Sub Total</th></tr></thead><tbody>";		
			var tmpstr="";
			for(var i = 0; i < $scope.InvoiceTrxs.length; i++){
				tmpstr += "<tr><td>"+parseInt(i+1)+"</td><td>"+$scope.InvoiceTrxs[i].barcode;
				tmpstr += "</td><td>"+$scope.InvoiceTrxs[i].name;
				tmpstr += "</td><td>"+$scope.InvoiceTrxs[i].expiration;
				tmpstr += "</td><td  align=\"right\">"+parseFloat($scope.InvoiceTrxs[i].price).toFixed(2);
				tmpstr += "</td><td align=\"center\">"+$scope.InvoiceTrxs[i].quantity;				
				tmpstr += "</td><td align=\"right\">"+parseFloat($scope.InvoiceTrxs[i].discount).toFixed(2);
				tmpstr += "</td><td align=\"right\">"+parseFloat($scope.InvoiceTrxs[i].tax).toFixed(2);				
				tmpstr += "</td><td align=\"right\">"+parseFloat($scope.InvoiceTrxs[i].subtotal).toFixed(2)+"</tr>";		
			}
				htmlData += tmpstr+"<tr><td colspan=6><br>Notes : "+$scope.notes+"</td><td colspan=3><table class=\"table table-bordered\">";
				htmlData += "<tr><td>Total</td><td align=\"right\">"+parseFloat($scope.total).toFixed(2)+"</td></tr>";
				htmlData += "<tr><td>Discount</td><td align=\"right\">"+parseFloat($scope.discount).toFixed(2)+"</td></tr>";
				htmlData += "<tr><td>Tax</td><td align=\"right\">"+parseFloat($scope.tax).toFixed(2)+"</td></tr>";				
				htmlData += "<tr><td>Balance</td><td align=\"right\">"+parseFloat($scope.balance).toFixed(2)+"</td></tr>";
				htmlData += "<tr><td>Payment Method</td><td>"+$scope.paymentMethod+"</td></tr>";					
				htmlData += "</table></td></tr>";
				htmlData += "</tbody></table></td></tr></table></body></html>"
		
		var myWindow = window.open("", "", "width=400,height=200");
		myWindow.document.write(htmlData);
		//setTimeout(function(){ myWindow.close() }, 6000);
	 	 
 }
 
	 $scope.newInvoice = function(){
		 $scope.editInvoice = false;
		 $scope.gridOptions.columnDefs[5].enableCellEdit = false;
		 clearAll();
		 $scope.gridOptions.data = $scope.InvoiceTrxs;	 
	 }
	clearAll();
 });

