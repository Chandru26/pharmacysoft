app.controller('ReportsController', function ($scope, $rootScope,uiGridConstants, $http, $log,Flash,deemsoft) 
{ 
	function resetForm(){		
		$scope.data=[];
	}
	var invoicetotal = "";
	var invbalance = "";
	var invpaid = "";
	var nvoicesgst_amount = "";
	var invoiccecgst_amount = "";
	var purtotal = "";
	var purdiscount = "";
	var purcgst_amount = "";
	var pursgst_amount = "";
	var purbillamount = "";
	var purpaid = "";
	var purbalance = "";
	var stockpurchased_quantity = "";
	var stockavailable_quantity = "";
	var stocksold_quantity = "";
	var cat_quantity = "";
	var catmsrp = "";	
	var catpurprice = "";
	 $scope.beginDate = "";
	 $scope.endDate = "";
	 
	$scope.repors = [];
	$scope.contactinfo = [];
	$scope.print = true;
	$scope.reportinfo = [];
	$scope.reportpurchaseinfo = [];
	$scope.catloginfo = [];
	resetForm();
	var csrf = document.getElementById("csrf_token").value;
	var userID = document.getElementById("user__id").value;
	
	
	$scope.dateFormat = "dd-MM-yyyy";
	
	$scope.open1 = function($event) { $scope.status1 = true; };	  
	$scope.open2 = function($event) { $scope.status2 = true; }; 
	
	$scope.dateOptions = { showWeeks: false, showButtonBar: false  };	
	$scope.reportTypes = [
			{'id':1, 'name':'Invoices Report'},
			{'id':2, 'name':'Purchases Report'},
			{'id':3, 'name':'Stock Verification Report'},
			{'id':4, 'name':'Inventory Report'},
			{'id':5, 'name':'Contacts List'},
			{'id':6, 'name':'Accounts List'},
			{'id':7, 'name':'Sales Report'},
			{'id':8, 'name':'Low Stock Report'},
			{'id':9, 'name':'Fast Moving Stock'},
			{'id':10, 'name':'Returnstock Report'},
			
		];

		

	$scope.getReports = function(){
		
		if($scope.report === 1){			
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			$scope.gridOptions.columnDefs = [ 
					{ field:'id', width:'10%', displayName: 'INVOICE NUMBER',cellClass:'text-center' },
					{ field:'title', width:'10%',cellClass:'text-center', displayName: 'INVOICE NAME' },
					{ field:'invoice_date', width:'10%',cellClass:'text-center',cellFilter: 'date:"dd-MM-yyyy"', displayName: 'INVOICE DATE' },
					{ field:'total',aggregationType: uiGridConstants.aggregationTypes.sum, width:'18%', displayName: 'INVOICE AMOUNT',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'discount',aggregationType: uiGridConstants.aggregationTypes.sum , width:'15%', displayName: 'DISCOUNT',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'cgst_amount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'CGST',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'sgst_amount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'SGST',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'paid',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'PAID',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'balance',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'BALANCE',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' }
				];
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			$scope.gridOptions.exporterPdfFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.pdf";
			deemsoft.postHTTP('../invoicesrest/periodinvoices/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
				
					$scope.repors = r; 
					$scope.reportinvoiceinfo = r; 
					$scope.showinvoice = true; 
					$scope.showstock = false; 
					$scope.showpurchase = false;  
					$scope.print = false; 
					$scope.showcatlog = false;
					$scope.showcontact = false; 				
					$scope.showaccount = false;
					$scope.showsales = false;
					$scope.showlowstock = false;
					$scope.showfastmovingstock = false;
					$scope.showreturnstock = false;
					
					$scope.gridOptions.data = $scope.repors;
					
					$scope.invoicetotal =0;
					$scope.invbalance =0;
					$scope.invpaid =0;
					$scope.invoiccecgst_amount =0;
					$scope.nvoicesgst_amount =0;
					
					for(var i =0;i<= $scope.reportinvoiceinfo.length;i++){
						
						
					$scope.invoicetotal = $scope.invoicetotal + $scope.reportinvoiceinfo[i].total; 
					$scope.invoiccecgst_amount = $scope.invoiccecgst_amount + $scope.reportinvoiceinfo[i].cgst_amount; 
					$scope.nvoicesgst_amount = $scope.nvoicesgst_amount + $scope.reportinvoiceinfo[i].sgst_amount; 
					$scope.invpaid = $scope.invpaid + $scope.reportinvoiceinfo[i].paid; 
					$scope.invbalance = $scope.invbalance + $scope.reportinvoiceinfo[i].balance; 
					
					$scope.calculateInvoices();
					}	
				});
				
					deemsoft.postHTTP('../returnstockrest/periodreturnstock/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
									
					$scope.reportreturnstockinfo = r;					
					
					$scope.returntotal =0;
					$scope.retbalance =0;
					$scope.retpaid =0;
					$scope.returncgst_amount =0;
					$scope.returnsgst_amount =0;
					
					for(var i =0;i<= $scope.reportreturnstockinfo.length;i++){
						
						
						$scope.returntotal = $scope.returntotal + $scope.reportreturnstockinfo[i].total; 
						$scope.returncgst_amount = $scope.returncgst_amount + $scope.reportreturnstockinfo[i].cgst_amount; 
						$scope.returnsgst_amount = $scope.returnsgst_amount + $scope.reportreturnstockinfo[i].sgst_amount; 
						$scope.retpaid = $scope.retpaid + $scope.reportreturnstockinfo[i].paid; 
						$scope.retbalance = $scope.retbalance + $scope.reportreturnstockinfo[i].balance; 
						}
					});
					
				
		}
		/*if($scope.report === 2){
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			$scope.gridOptions.columnDefs = [ 
					{ field:'invoices_id', width:'10%', displayName: 'INVOICE ID' },
					{ field:'barcode', width:'10%', displayName: 'Batch No'},
					{ field:'name',width:'*', displayName: 'Product Name'},
					{ field:'price',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'Price',cellClass:'text-right', cellFilter:'number: 2' },
					{ field:'quantity',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'Quantity',cellClass:'text-right' },
					{ field:'discount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'Discount',cellClass:'text-right', cellFilter:'number: 2' },
					{ field:'tax',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'Tax',cellClass:'text-right', cellFilter:'number: 2' },
					{ field:'subtotal',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'SubTotal',cellClass:'text-right', cellFilter:'number: 2' }
				];
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			deemsoft.postHTTP('../invoicesitemsrest/periodinvoicesitems/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
					$scope.gridOptions.data = r; 
				});		
		}*/
		
		
		if($scope.report === 2){
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			$scope.gridOptions.columnDefs = [ 
					{ field:'id', width:'5%', displayName: 'ID' },
					{ field:'title', width:'10%',cellClass:'text-center', displayName: ' NAME' },
					{ field:'total',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'GROSS AMOUNT',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'discount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'DISCOUNT',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'cgst_amount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'CGST',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },					
					{ field:'sgst_amount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'SGST',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'billamount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'17%', displayName: 'PURCHASE AMOUNT',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'paid',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'PAID',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'balance',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'BALANCE',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' }
				];
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			deemsoft.postHTTP('../purchasesrest/periodpurchases/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
					$scope.gridOptions.data = r; 
					$scope.reportpurchaseinfo = r; 
					$scope.showinvoice = false;  
					$scope.showpurchase = true; 
					$scope.print = false;
					$scope.showstock = false;
					$scope.showcatlog = false;
					$scope.showcontact = false; 				
					$scope.showaccount = false;
					$scope.showsales = false;
					$scope.showlowstock = false;
					$scope.showfastmovingstock = false;
					$scope.showreturnstock = false;
					
					$scope.purtotal =0;
					$scope.purdiscount =0;
					$scope.purcgst_amount =0;
					$scope.pursgst_amount =0;
					$scope.purbillamount =0;
					$scope.purpaid =0;
					$scope.purbalance =0;
					
					for(var i =0;i<= $scope.reportpurchaseinfo.length;i++){
						
						
					$scope.purtotal = $scope.purtotal + $scope.reportpurchaseinfo[i].total; 
					$scope.purdiscount = $scope.purdiscount + $scope.reportpurchaseinfo[i].discount; 
					$scope.purcgst_amount = $scope.purcgst_amount + $scope.reportpurchaseinfo[i].cgst_amount;
					$scope.pursgst_amount = $scope.pursgst_amount + $scope.reportpurchaseinfo[i].sgst_amount; 
					$scope.purbillamount = $scope.pursgst_amount + $scope.reportpurchaseinfo[i].billamount; 
					$scope.purpaid = $scope.purpaid + $scope.reportpurchaseinfo[i].paid; 
					$scope.purbalance = $scope.purbalance + $scope.reportpurchaseinfo[i].balance; 
					}

				});
		}
		/*if($scope.report === 4){
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			$scope.gridOptions.columnDefs = [ 
					{ field:'purchases_id', width:'10%', displayName: 'Purchase ID' },
					{ field:'barcode', width:'10%', displayName: 'Batch NO' },
					{ field:'name', width:'*', displayName: 'Product Name' },
					{ field:'quantity',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'Quantity',cellClass:'text-right' },
					{ field:'price',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'MSRP',cellClass:'text-right', cellFilter:'number: 2' },
					{ field:'purchase_price',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'Purchase Price',cellClass:'text-right', cellFilter:'number: 2' },
					{ field:'discount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'Discount',cellClass:'text-right', cellFilter:'number: 2' },
					{ field:'tax',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'Tax',cellClass:'text-right', cellFilter:'number: 2' },
					{ field:'subtotal',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'SubTotal',cellClass:'text-right', cellFilter:'number: 2' }
				];
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			deemsoft.postHTTP('../purchasesitemsrest/periodpurchasesitems/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
					$scope.gridOptions.data = r; 
				});
		}*/
		
		if($scope.report === 3){
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			$scope.gridOptions.columnDefs = [ 
					{ field:'batchno', width:'10%', displayName: 'BARCODE' },
					{ field:'name',width:'*', displayName: 'PRODUCT NAME' },
					
					{ field:'sold_quantity',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'SOLD QUANTITY',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'purchased_quantity',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'PURCHASED QUANTITY',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'available_quantity',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'AVAILABLE QUANTITY',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' }
					
					
				];
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			deemsoft.postHTTP('../purchasesitemsrest/periodstockreport/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
				
				$scope.print = false;
				$scope.showstock = true;
				$scope.showinvoice = false;  
				$scope.showpurchase = false;
				$scope.showcatlog = false;
				$scope.showsales = false;
				$scope.showcontact = false; 				
				$scope.showaccount = false;
				$scope.showlowstock = false;
				$scope.showfastmovingstock = false;
				$scope.showreturnstock = false;
				$scope.l =[];
				$scope.reportinfo = r;
				
				console.log(r.purchased_quantity);
				$scope.repors = r;
				$scope.gridOptions.data = $scope.repors;
				$scope.stockpurchased_quantity = 0;
				$scope.stockavailable_quantity = 0;
				$scope.stocksold_quantity = 0;
				
			for(var i =0;i<= $scope.reportinfo.length;i++) {
				$scope.stocksold_quantity = $scope.stocksold_quantity + $scope.reportinfo[i].sold_quantity; 
					 
				
				$scope.j =[];
				$scope.j[i] = r[i].available_quantity;
				//alert($scope.j[i]);
				$scope.k =[];
				$scope.k[i] = r[i].sold_quantity;
				//alert($scope.k[i]);
				if($scope.k[i] == null || $scope.k[i] == "undefined"){
					$scope.k[i] = 0;
					
				}
				
				$scope.l[i] = $scope.j[i] + $scope.k[i];
				
				$scope.reportinfo[i].purchased_quantity = $scope.l[i];
				$scope.reportinfo[i].sold_quantity = $scope.k[i];
				
					$scope.stockpurchased_quantity = $scope.stockpurchased_quantity + $scope.reportinfo[i].purchased_quantity;
				
					$scope.stockavailable_quantity = $scope.stockavailable_quantity + $scope.reportinfo[i].available_quantity; 
				
			}
					
			/*for(var i =0;i<= $scope.repors.length;i++) {
				
				$scope.j =[];
				$scope.j[i] = r[i].available_quantity;
				//alert($scope.j[i]);
				$scope.k =[];
				$scope.k[i] = r[i].sold_quantity;
				//alert($scope.k[i]);
				if($scope.k[i] == null || $scope.k[i] == "undefined"){
					$scope.k[i] = 0;
					
				}
				
				$scope.l[i] = $scope.j[i] + $scope.k[i];
				
				$scope.repors[i].purchased_quantity = $scope.l[i];
				$scope.repors[i].sold_quantity = $scope.k[i];
				
			}
				*/
					
					/*for(var i=0;i<=r.length;i++) {
						//alert(r[i].available_quantity);
					if(r[i].available_quantity<=0)
						{
							//r = r[i].shift(); 
							alert(r[i].available_quantity);
							r.splice(i,6);
							console.log(r);
							$scope.gridOptions.data = r;
							
						}else {
							$scope.gridOptions.data = r;
							alert("No Zero");
						}
					}*/
				});
		}
		if($scope.report === 4){
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			
			$scope.gridOptions.columnDefs = [ 
					{ field:'barcode', width:"10%", displayName: 'BARCODE' },	
					{ field:'name',width:'20%', displayName: 'PRODUCT NAME' },					
					{ field:'description', width:"*", displayName: 'DESCRIPTION' },
					{ field:'expiration', width:'10%', displayName: 'EXPIRATION', type:'Date', cellFilter: 'date:\'dd-MM-yyyy\'' },
					{ field:'quantity', width:"10%", displayName: 'QUANTITY' },
					{ field:'msrp', width:"10%",displayName: 'MSRP',cellClass:'text-right', cellFilter:'twoDecimals' },
					{ field:'purchase_price', width:"10%",displayName: 'PURCHASE PRICE',cellClass:'text-right', cellFilter:'twoDecimals' }
				];
				$scope.catloginfo = {};
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			deemsoft.postHTTP('../catalogsrest/periodcatalogs/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
			
					$scope.catloginfo = r; 
					$scope.showcatlog = true; 
					$scope.print = false; 
					$scope.print = false;
					$scope.showstock = false;
					$scope.showinvoice = false;  
					$scope.showpurchase = false;
					$scope.showsales = false;
					$scope.showcontact = false; 				
					$scope.showaccount = false;
					$scope.showlowstock = false;
					$scope.showfastmovingstock = false;
					$scope.showreturnstock = false;
					$scope.gridOptions.data = $scope.catloginfo; 
					
					$scope.cat_quantity =0;
					$scope.catmsrp =0;
					$scope.catpurprice =0;
					
					for(var i =0;i<= $scope.catloginfo.length;i++){
						
						
					$scope.cat_quantity = $scope.cat_quantity + $scope.catloginfo[i].quantity; 
					$scope.catmsrp = $scope.catmsrp + $scope.catloginfo[i].msrp; 
					$scope.catpurprice = $scope.catpurprice + $scope.catloginfo[i].purchase_price;
					
					}
			});		
		}
		
		if($scope.report === 5){
			
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			
			$scope.gridOptions.columnDefs = [ 
					{ field:'title', width:"5%", displayName: 'Title' },	
					{ field:'name',width:'*',  displayName: 'Name' },
					{ field:'company', width:'20%', displayName: 'Company' },
					{ field:'mobile_phone', width:"10%", displayName: 'Phone' },
					{ field:'email', width:"10%", displayName: 'Email' },
					];
		
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			deemsoft.postHTTP('../contactsrest/periodcontacts/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
				
					$scope.gridOptions.data = r; 
					
					$scope.contactinfo = r;
					console.log(r);
					
					
					$scope.showcontact = true; 
					$scope.print = false; 
					$scope.showstock = false;
					$scope.showinvoice = false;  
					$scope.showpurchase = false;
					$scope.showcatlog = false;
					$scope.showsales = false;
					$scope.showaccount = false; 
					$scope.showaccount = false;
					$scope.showlowstock = false;
					$scope.showfastmovingstock = false;
					$scope.showreturnstock = false;
			});		
		}
		if($scope.report === 6){
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			$scope.gridOptions.columnDefs = [ 
					{ field:'title', width:"10%", displayName: 'Title' },
					{ field:'firstname',width:'*',  displayName: 'Name' },
					{ field:'company',width:'20%',  displayName: 'Company' },
					{ field:'phone', width:"10%", displayName: 'Phone' },
					{ field:'email', width:"*", displayName: 'Email'},
				];
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			deemsoft.postHTTP('../accountsrest/periodaccounts/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){		
			
					$scope.showstock = false;
					$scope.showinvoice = false;  
					$scope.showpurchase = false;
					$scope.showcatlog = false;
					$scope.showsales = false;
					$scope.showcontact = false;
					$scope.gridOptions.data = r; 
					$scope.accountinfo = r;
					$scope.showaccount = true; 
					$scope.print = false;
					$scope.showlowstock = false;
					$scope.showfastmovingstock = false;
					$scope.showreturnstock = false;
			});									
				
		}
		if($scope.report === 7){
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			$scope.gridOptions.columnDefs = [ 
					{ field:'id', width:'10%', displayName: 'INVOICE NUMBER',cellClass:'text-center' },
					{ field:'title', width:'10%',cellClass:'text-center', displayName: 'INVOICE NAME' },
					{ field:'invoice_date', width:'10%',cellClass:'text-center',cellFilter: 'date:"dd-MM-yyyy"', displayName: 'INVOICE DATE' },
					{ field:'total',aggregationType: uiGridConstants.aggregationTypes.sum, width:'18%', displayName: 'INVOICE AMOUNT',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'discount',aggregationType: uiGridConstants.aggregationTypes.sum , width:'15%', displayName: 'DISCOUNT',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'cgst_amount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'CGST',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'sgst_amount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'SGST',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'paid',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'PAID',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'balance',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'BALANCE',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' }
				];
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			$scope.gridOptions.exporterPdfFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.pdf";
			deemsoft.postHTTP('../invoicesrest/periodsales/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
				console.log(r);
					$scope.repors = r; 
					$scope.reportinvoiceinfo = r;
					$scope.salesreport.data = r;
					$scope.showinvoice = false; 
					$scope.showstock = false; 
					$scope.showpurchase = false;  
					$scope.print = false; 
					$scope.showcatlog = false;
					$scope.showcontact = false; 				
					$scope.showaccount = false; 
					$scope.showsales = true;
					$scope.showlowstock = false;
					$scope.showfastmovingstock = false;
					$scope.showreturnstock = false;
					
					$scope.gridOptions.data = $scope.repors;
					
					$scope.invoicetotal =0;
					$scope.invbalance =0;
					$scope.invpaid =0;
					$scope.invoiccecgst_amount =0;
					$scope.nvoicesgst_amount =0;
					
					for(var i =0;i<= $scope.reportinvoiceinfo.length;i++){
						
						
					$scope.invoicetotal = $scope.invoicetotal + $scope.reportinvoiceinfo[i].total; 
					$scope.invoiccecgst_amount = $scope.invoiccecgst_amount + $scope.reportinvoiceinfo[i].cgst_amount; 
					$scope.nvoicesgst_amount = $scope.nvoicesgst_amount + $scope.reportinvoiceinfo[i].sgst_amount; 
					$scope.invpaid = $scope.invpaid + $scope.reportinvoiceinfo[i].paid; 
					$scope.invbalance = $scope.invbalance + $scope.reportinvoiceinfo[i].balance; 
					}
										
				});

				
				var rowTemp = '<div  ng-click="grid.appScope.ShowDetails(row)" class="ui-grid-cell-contents">{{row.entity[col.field]}}</div>';
				$scope.salesreport = {
					enableSorting: true,
					enableGridMenu:true,
					enablePinning: true,
					enableFiltering: true,
					showFilter: true,
					showGridFooter: true,
					showColumnFooter: true,
					enableSelectAll: true,
					exporterCsvFilename: 'pharmacysoftReports.csv',
					exporterPdfFilename: 'pharmacysoftReports.pdf',
					exporterPdfDefaultStyle: {fontSize: 8},
					exporterPdfTableStyle: {margin: [10, 10, 10, 10]},
					exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: false, color: 'red'},
					exporterPdfHeader: { text: "Report", style: 'headerStyle' },
					exporterPdfFooter: function ( currentPage, pageCount ) {
						return { text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
					},
					exporterPdfCustomFormatter: function ( docDefinition ) {
						docDefinition.styles.headerStyle = { fontSize: 20, bold: true, alignment:'center', decoration: 'underline' };
						docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
						return docDefinition;
						},
					exporterPdfOrientation: 'portrait',
					exporterPdfPageSize: 'LETTER',
					exporterPdfMaxGridWidth: 500,
					exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
					onRegisterApi: function(gridApi){	$scope.gridApi = gridApi; 	},
					columnDefs: [
											
						{ field:'barcode', width:'30%', displayName: 'BARCODE',cellTemplate: rowTemp},
						{ field:'name',width:'30%', displayName: 'PRODUCT NAME',cellTemplate: rowTemp},
						{ field:'quantity', width:'45%', displayName: 'SOLD QUANTITY',cellTemplate: rowTemp},
						
					]	 
				}
				
		}
		if($scope.report === 8){
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			$scope.gridOptions.columnDefs = [ 
					{ field:'batchno', width:'10%', displayName: 'BARCODE' },
					{ field:'name',width:'*', displayName: 'PRODUCT NAME' },
					
					{ field:'sold_quantity',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'SOLD QUANTITY',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'purchased_quantity',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'PURCHASED QUANTITY',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'available_quantity',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'AVAILABLE QUANTITY',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' }
					
					
				];
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			deemsoft.postHTTP('../purchasesitemsrest/lowstockreport/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
				
				$scope.print = false;
				$scope.showstock = false;
				$scope.showinvoice = false;  
				$scope.showpurchase = false;
				$scope.showcatlog = false;
				$scope.showcontact = false;
				$scope.showsales = false; 				
				$scope.showaccount = false; 				
				$scope.showlowstock = true;
				$scope.showfastmovingstock = false;
				$scope.showreturnstock = false;
				$scope.l =[];
				$scope.reportinfo = r;
				
				console.log(r.purchased_quantity);
				$scope.repors = r;
				$scope.gridOptions.data = $scope.repors;
				$scope.stockpurchased_quantity = 0;
				$scope.stockavailable_quantity = 0;
				$scope.stocksold_quantity = 0;
				
				
			for(var i =0;i<= $scope.reportinfo.length;i++) {								
				$scope.stocksold_quantity = $scope.stocksold_quantity + $scope.reportinfo[i].sold_quantity; 					 
				
				$scope.j =[];
				$scope.j[i] = r[i].available_quantity;
				//alert($scope.j[i]);
				$scope.k =[];
				$scope.k[i] = r[i].sold_quantity;
				//alert($scope.k[i]);
				if($scope.k[i] == null || $scope.k[i] == "undefined"){
					$scope.k[i] = 0;
					
				}
				
				$scope.l[i] = $scope.j[i] + $scope.k[i];
				
				$scope.reportinfo[i].purchased_quantity = $scope.l[i];
				$scope.reportinfo[i].sold_quantity = $scope.k[i];
				
					$scope.stockpurchased_quantity = $scope.stockpurchased_quantity + $scope.reportinfo[i].purchased_quantity;
				
					$scope.stockavailable_quantity = $scope.stockavailable_quantity + $scope.reportinfo[i].available_quantity; 
				
			}
			
			
				});
		}
			
		if($scope.report === 9){			
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			deemsoft.postHTTP('../purchasesitemsrest/fastmovingstockreport/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
				
				$scope.print = false;
				$scope.showstock = false;
				$scope.showinvoice = false;  
				$scope.showpurchase = false;
				$scope.showcatlog = false;
				$scope.showcontact = false; 				
				$scope.showaccount = false;
				$scope.showsales = false;
				$scope.showlowstock = false;
				$scope.showfastmovingstock = true;
				$scope.showreturnstock = false;
				$scope.l =[];
				$scope.reportinfo = r;
				$scope.fastOptions.data = r;
				$scope.beginDate = ($scope.bd).toLocaleDateString();				
				$scope.endDate = ($scope.ed).toLocaleDateString();
				
			
		});
		
			var rowTemp = '<div  ng-click="grid.appScope.ShowDetails(row)" class="ui-grid-cell-contents">{{row.entity[col.field]}}</div>';
			$scope.fastOptions = {
				enableSorting: true,
				enableGridMenu:true,
				enablePinning: true,
				enableFiltering: true,
				showFilter: true,
				showGridFooter: true,
				showColumnFooter: true,
				enableSelectAll: true,
				exporterCsvFilename: 'pharmacysoftReports.csv',
				exporterPdfFilename: 'pharmacysoftReports.pdf',
				exporterPdfDefaultStyle: {fontSize: 8},
				exporterPdfTableStyle: {margin: [10, 10, 10, 10]},
				exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: false, color: 'red'},
				exporterPdfHeader: { text: "Report", style: 'headerStyle' },
				exporterPdfFooter: function ( currentPage, pageCount ) {
					return { text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
				},
				exporterPdfCustomFormatter: function ( docDefinition ) {
					docDefinition.styles.headerStyle = { fontSize: 20, bold: true, alignment:'center', decoration: 'underline' };
					docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
					return docDefinition;
					},
				exporterPdfOrientation: 'portrait',
				exporterPdfPageSize: 'LETTER',
				exporterPdfMaxGridWidth: 500,
				exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
				onRegisterApi: function(gridApi){	$scope.gridApi = gridApi; 	},
				columnDefs: [
										
					{ field:'batchno', width:'30%', displayName: 'BARCODE',cellTemplate: rowTemp},
					{ field:'name',width:'30%', displayName: 'PRODUCT NAME',cellTemplate: rowTemp},
					{ field:'sold_quantity', width:'45%', displayName: 'SOLD QUANTITY',cellTemplate: rowTemp},
					
				]	 
			}	
			
		}
		
		if($scope.report === 10){			
			$scope.reportType = $scope.reportTypes[parseInt($scope.report)-1].name;
			$scope.gridOptions.columnDefs = [ 
					{ field:'id', width:'10%', displayName: 'RETURN NUMBER',cellClass:'text-center' },
					{ field:'title', width:'10%',cellClass:'text-center', displayName: 'INVOICE NAME' },
					{ field:'invoice_date', width:'10%',cellClass:'text-center',cellFilter: 'date:"dd-MM-yyyy"', displayName: 'RETURN DATE' },
					{ field:'total',aggregationType: uiGridConstants.aggregationTypes.sum, width:'18%', displayName: 'INVOICE AMOUNT',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'discount',aggregationType: uiGridConstants.aggregationTypes.sum , width:'15%', displayName: 'DISCOUNT',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'cgst_amount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'CGST',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'sgst_amount',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'SGST',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'paid',aggregationType: uiGridConstants.aggregationTypes.sum, width:'10%', displayName: 'PAID',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' },
					{ field:'balance',aggregationType: uiGridConstants.aggregationTypes.sum, width:'15%', displayName: 'BALANCE',cellClass:'text-right', cellFilter:'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total: {{col.getAggregationValue() | number:2 }}</div>' }
				];
			$scope.gridOptions.exporterCsvFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.csv";
			$scope.gridOptions.exporterPdfFilename = "pharmacysoft-"+$scope.reportType+$scope.bd+"Reports.pdf";
			deemsoft.postHTTP('../returnstockrest/periodreturnstock/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(r){
				
					$scope.repors = r; 
					$scope.reportreturnstockinfo = r; 
					$scope.showinvoice = false; 
					$scope.showstock = false; 
					$scope.showpurchase = false;  
					$scope.print = false; 
					$scope.showcatlog = false;
					$scope.showcontact = false; 				
					$scope.showaccount = false;
					$scope.showsales = false;
					$scope.showlowstock = false;
					$scope.showfastmovingstock = false;
					$scope.showreturnstock = true;
					
					$scope.gridOptions.data = $scope.repors;
					
					$scope.returntotal =0;
					$scope.retbalance =0;
					$scope.retpaid =0;
					$scope.returncgst_amount =0;
					$scope.returnsgst_amount =0;
					
					for(var i =0;i<= $scope.reportreturnstockinfo.length;i++){
						
						
					$scope.returntotal = $scope.returntotal + $scope.reportreturnstockinfo[i].total; 
					$scope.returncgst_amount = $scope.returncgst_amount + $scope.reportreturnstockinfo[i].cgst_amount; 
					$scope.returnsgst_amount = $scope.returnsgst_amount + $scope.reportreturnstockinfo[i].sgst_amount; 
					$scope.retpaid = $scope.retpaid + $scope.reportreturnstockinfo[i].paid; 
					$scope.retbalance = $scope.retbalance + $scope.reportreturnstockinfo[i].balance; 
					}
										
				});		
		}
	
	}
	
	
	$scope.calculateInvoices = function(){	
		
		$scope.finalinvoicetotal =0;
		$scope.finalinvbalance =0;
		$scope.finalinvpaid =0;
		$scope.finalinvoiccecgst_amount =0;
		$scope.finalnvoicesgst_amount =0;
			
		$scope.finalinvoicetotal = $scope.invoicetotal - $scope.returntotal; 
		$scope.finalinvoiccecgst_amount = $scope.invoiccecgst_amount - $scope.returncgst_amount; 
		$scope.finalnvoicesgst_amount = $scope.nvoicesgst_amount - $scope.returnsgst_amount; 
		$scope.finalinvpaid = $scope.invpaid - $scope.retpaid; 
		$scope.finalinvbalance = $scope.invbalance - $scope.retbalance;
						
	}
	
	
	
	$scope.gridOptions = {
		enableSorting: true,
		enableGridMenu:true,
		enablePinning: true,
		enableFiltering: true,
		showFilter: true,
		showGridFooter: true,
		showColumnFooter: true,
		enableSelectAll: true,
		exporterCsvFilename: 'pharmacysoftReports.csv',
		exporterPdfFilename: 'pharmacysoftReports.pdf',
		exporterPdfDefaultStyle: {fontSize: 8},
		exporterPdfTableStyle: {margin: [10, 10, 10, 10]},
		exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: false, color: 'red'},
		exporterPdfHeader: { text: "Report", style: 'headerStyle' },
		exporterPdfFooter: function ( currentPage, pageCount ) {
			return { text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
		},
		exporterPdfCustomFormatter: function ( docDefinition ) {
			docDefinition.styles.headerStyle = { fontSize: 20, bold: true, alignment:'center', decoration: 'underline' };
			docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
			return docDefinition;
			},
		exporterPdfOrientation: 'portrait',
		exporterPdfPageSize: 'LETTER',
		exporterPdfMaxGridWidth: 500,
		exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
		onRegisterApi: function(gridApi){	$scope.gridApi = gridApi; 	},
		
	}
	
$scope.doprint = function(){  window.print();  }

setInterval(function(){$scope.send();},1000);
$scope.send = function(){
	var time = new Date();
		var hh = String(time.getHours()).padStart(2, '0');//24 hour format
			//hh=	((hh + 11) % 12 + 1);
		var mm = String(time.getMinutes()).padStart(2, '0');
		var ss = String(time.getSeconds()).padStart(2, '0');
		time = hh + '/' + mm + '/' + ss;
		
		if(time === "13/50/00"){
			$scope.sendemail();
		}
}

$scope.sendemail = function(){	
	var today = new Date().toISOString();	
		
	deemsoft.postHTTP('../purchasesitemsrest/lowstockreport/',{beginDate:today,endDate:today},csrf).then(function(r){
		$scope.reportinfo = r;
	
	deemsoft.postHTTP('../invoicesrest/periodinvoices/',{beginDate:today,endDate:today},csrf).then(function(r){
		$scope.repors = r;
			
	 $scope.pdfHeaderName = [
                            [ {alignment:'center',text:'|| Jai Sri Gurudev ||',bold: false,style: 'headeraddfont'}],
							[ {alignment:'center',text:'Sri Adichunchanagiri Shikshana Trust (R)',bold: true,style: 'headeraddfont'}],
							[ {alignment:'center',text:'ADICHUNCHANAGIRI AYURVEDIC MEDICAL COLLEGE, HOSPITAL AND RESEARCH CENTER',bold: true,style: 'headeraddfont'}],
							[ {alignment:'center',text:'Nagarur, Bengaluru - 562 123',bold: false,style: 'headeraddfont'}],
							[ {alignment:'center',text:'Ph.No : +91 9481861519, Email : principal@amc.org.in',bold: false,style: 'headeraddfont'}],
							[ {alignment:'center',text:'website: www.amc.org.in',bold: false,style: 'headeraddfont'}]
                           
                        ];
	 $scope.lowStockReport = [                           
                            [{alignment:'center', text: 'Sl #', bold: true, fillColor: '#fff' ,style: 'topaddress'},
                            {alignment:'center', text: 'Barcode', bold: true, fillColor: '#fff' ,style: 'topaddress'},
                            {alignment:'center', text:'Product Name', bold: true, fillColor: '#fff' ,style: 'topaddress'},
                            {alignment:'center', text:'Available Quantity',bold: true, fillColor: '#fff' ,style: 'topaddress'}]                       
                           
                            ];

                        for (i = 0; i < $scope.reportinfo.length; i++) {                                
                                j = i;
                                j = j+1;			
                                $scope.lowStockReport.push([    
                                { alignment:'center',text:j.toString(),style: 'topaddress'},
                                { alignment:'left',text:$scope.reportinfo[i].batchno,style: 'topaddress'},
                                { alignment:'left',text:$scope.reportinfo[i].name,style: 'topaddress'},
                                { alignment:'right',text:$scope.reportinfo[i].available_quantity.toString(),style: 'topaddress'}
                                ]);
                           }
	
	$scope.invoicesDailyReport = [                           
                            [{alignment:'center', text: 'Sl #', bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
                            {alignment:'center', text: 'Invoice Date', bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
                            {alignment:'center', text:'Title', bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
                            {alignment:'center', text:'Total',bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
							{alignment:'center', text: 'CGST', bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
                            {alignment:'center', text: 'SGST', bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
                            {alignment:'center', text:'Paid', bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
                            {alignment:'center', text:'Balance',bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'}]                       
                           
                            ];

                        for (i = 0; i < $scope.repors.length; i++) {                                
                                j = i;
                                j = j+1;			
                                $scope.invoicesDailyReport.push([    
                                { alignment:'center',text:j.toString(),style: 'topaddress'},
                                { alignment:'center',text:new Date($scope.repors[i].invoice_date).toLocaleDateString("en-US"),style: 'topaddress'},
                                { alignment:'center',text:$scope.repors[i].title,style: 'topaddress'},
                                { alignment:'right',text:$scope.repors[i].total.toString(),style: 'topaddress'},								
                                { alignment:'right',text:$scope.repors[i].cgst_amount.toString(),style: 'topaddress'},
								{ alignment:'right',text:$scope.repors[i].sgst_amount.toString(),style: 'topaddress'},
                                { alignment:'right',text:$scope.repors[i].paid.toString(),style: 'topaddress'},
                                { alignment:'right',text:$scope.repors[i].balance.toString(),style: 'topaddress'}
                                ]);
                           }
						   var total = 0;var cgst = 0;var sgst = 0;var paid = 0;var balance = 0;
							for(var i = 0; i < $scope.repors.length; i++)
							{
								total = total +  parseInt($scope.repors[i].total);
								cgst = cgst +  parseInt($scope.repors[i].cgst_amount);
								sgst = sgst +  parseInt($scope.repors[i].sgst_amount);
								paid = paid +  parseInt($scope.repors[i].paid);
								balance = balance +  parseInt($scope.repors[i].balance);		
							}
							$scope.invoicesDailyReport.push([
                                { alignment:'center',colSpan: 3, text:'TOTAL',style: 'topaddress'},'','',
                                { alignment:'right',text:total.toString(),style: 'topaddress'},								
                                { alignment:'right',text:cgst.toString(),style: 'topaddress'},
								{ alignment:'right',text:''+sgst,style: 'topaddress'},
                                { alignment:'right',text:paid.toString(),style: 'topaddress'},
                                { alignment:'right',text:balance.toString(),style: 'topaddress'}]);
						   
	var lowStock = {
            content:[    
                       {table: {
                        headerRows: 1,
                        widths: [  '*'],

                        body: $scope.pdfHeaderName
                       
                      },
                      layout: { hLineColor: function(i, node) {
                                            return (i === 0 || i === node.table.body.length) ? 'white' : 'white';
                                    },
                                    vLineColor: function(i, node) {
                                            return (i === 0 || i === node.table.widths.length) ? 'white' : 'white';
                                    }
                                }
                      },
						
					{table: {
                        headerRows: 1,
                        heights: 15,
                        widths: [ 60,110,220,120 ],                   
                        body: $scope.lowStockReport                       
                      }
                    },
                    ],
                    styles: {
                         header: {
                           fontSize: 11,
                           bold: true                          
                         }                         
                    }
        }
		
		var invoicesReport = {
            content:[    
                       {table: {
                        headerRows: 1,
                        widths: [  '*'],

                        body: $scope.pdfHeaderName
                       
                      },
                      layout: { hLineColor: function(i, node) {
                                            return (i === 0 || i === node.table.body.length) ? 'white' : 'white';
                                    },
                                    vLineColor: function(i, node) {
                                            return (i === 0 || i === node.table.widths.length) ? 'white' : 'white';
                                    }
                                }
                      },
						
					{table: {
                        headerRows: 1,
                        heights: 15,
                        widths: [ 40,80,100,50,40,40,50,50 ],                   
                        body: $scope.invoicesDailyReport                       
                      }
                    },
                    ],
                    styles: {
                         header: {
                           fontSize: 11,
                           bold: true                          
                         }                         
                    }
        }
	
	
	 pdfMake.createPdf(lowStock).getBase64(function(buffer) {
                    var data1 = {
                        'from': "info@educationsoft.org",
                        to: "chandru.deemsoft@gmail.com",
                        cc: "",
                        bcc:"",
                        subject: "Low Stock Report",
                        body:"Sir, <br> Please find the attachment for today's total stock status. <br><br> Thanks",
                        filename: "stockreport.pdf",
                        attachment: buffer
                        };
                        $http({       
                            method: 'POST',
                            url:'../email/sendwithattachment/',
                            headers: {'X-CSRF-TOKEN': csrf },
                            data:data1 }).then(function(response) {
                               // alert("A");
                        });           
               });
	
	pdfMake.createPdf(invoicesReport).getBase64(function(buffer) {
                    var data1 = {
                        'from': "info@educationsoft.org",
                        to: "chandru.deemsoft@gmail.com",
                        cc: "",
                        bcc:"",
                        subject: "Invoices Report",
                        body:"Sir, <br> Please find the attachment for today's total invoices. <br><br> Thanks",
                        filename: "invoices.pdf",
                        attachment: buffer
                        };
                        $http({       
                            method: 'POST',
                            url:'../email/sendwithattachment/',
                            headers: {'X-CSRF-TOKEN': csrf },
                            data:data1 }).then(function(response) {
                                //alert("A");
                        });           
               });
	});
	});
	
 }

});