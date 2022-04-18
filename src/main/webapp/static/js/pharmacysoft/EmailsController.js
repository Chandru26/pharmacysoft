'use strict';
app.controller('EmailsController', function ($scope,$http,$timeout, uiGridConstants, $uibModal, $log, Upload, Flash,deemsoft)
{

var csrf = document.getElementById("csrf_token").value;
	var userID = document.getElementById("user__id").value;
		$scope.dateFormat = "dd-MM-yyyy";
	
	$scope.open1 = function($event) { $scope.status1 = true; };	  
	$scope.open2 = function($event) { $scope.status2 = true; }; 	
	$scope.dateOptions = { showWeeks: false, showButtonBar: false  };	
	$scope.sendinvoicesum = function(){
	$scope.invoices = {};
			  
deemsoft.postHTTP('../invoicesrest/periodinvoices/',{beginDate:$scope.bd,endDate:$scope.ed},csrf).then(function(d){
            $scope.invoices = d;
		
					  $scope.headersection = [                       
                          [ {alignment:'center',colSpan: 3,text:'Sri Adichunchanagiri Shikshana Trust (R) ADICHUNCHANAGIRI AYURVEDIC MEDICAL COLLEGE, HOSPITAL AND RESEARCH CENTER(AMC) Nagarur , Dasanapura Hobli,Nelamangala, Bengaluru North - 562 123 Ph.No - +91 9481861519,Email-aamchrcbengaluru@gmail.com web - acamc.org | acmch.org ',style: 'topaddress'}, '', '','']
                       
               
                        ];
                      
        $scope.pdfHeaderName = [
                            [ {alignment:'center',text:'Invoice Summary Report',bold: true,style: 'headeraddfont'}]                          
                        ];
        
                          
        $scope.pdfParts = [                          
                            [{alignment:'center', text: 'INVOICE NUM', bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
                            {alignment:'center', text:'INVOICE DATE', bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
                            {alignment:'center', text:'NAME',bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
                            {alignment:'center', text:'AMOUNT',bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
                           
							{alignment:'center', text:'PAID',bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'},
							{alignment:'center', text:'BALANCE',bold: true, fillColor: '#C0C0C0' ,style: 'topaddress'}
                          ]
                          
                            ];

                            for (i = 0; i < $scope.invoices.length; i++) {                               
                                j = i;
                                j = j+1;
                                $scope.pdfParts.push([
                                { alignment:'center',text:j,style: 'topaddress'},
                                { alignment:'center',text:$scope.invoices[i].id,style: 'topaddress'},
                                { alignment:'center',text:$scope.invoices[i].invoice_date,style: 'topaddress'},
                                { alignment:'center',text:$scope.invoices[i].title,style: 'topaddress'},
                                { alignment:'center',text:$scope.invoices[i].total,style: 'topaddress'},
                               
								{ alignment:'center', text:$scope.invoices[i].paid,style: 'topaddress'},
								{ alignment:'center', text:$scope.invoices[i].balance,style: 'topaddress'}                              
                                ]);
                           }                          
                      
                       
                      
      
        var docDefinition = {
            content:[   
          
                    {table: {
                        headerRows: 1,
                        widths: [ '*'],

                        body: $scope.headersection
                        },
                        layout: { hLineColor: function(i, node) {
                                            return (i === 0 || i === node.table.body.length) ? 'white' : 'white';
                                    },
                                    vLineColor: function(i, node) {
                                            return (i === 0 || i === node.table.widths.length) ? 'white' : 'white';
                                    }
                                }
                      },                    
                    '_______________________________________________________________________________________________',
                    '        ',                  
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
                        widths: [  'auto','auto', 'auto', 'auto', 'auto', 'auto'],                  
                        body: $scope.pdfParts
                      
                      },
					  layout: { hLineColor: function(i, node) {
                                            return (i === 0 || i === node.table.body.length) ? 'white' : 'white';
                                    },
                                    vLineColor: function(i, node) {
                                            return (i === 0 || i === node.table.widths.length) ? 'white' : 'white';
                                    }
                                }
                    }
                    
                    ],
                    styles: {
                         header: {
                           fontSize: 11,
                           bold: true                         
                         },
                         anotherStyle: {
                           italic: true,                         
                           alignment: 'right'
                         },
                         smallfonts: {
                          fontSize: 8
                         },
                         headersec: {
                          fontSize: 25
                        
                         },
                         termsheading:{
                             fontSize: 9
                         },
                         topaddress:{
                             fontSize: 9
                         }
                        
                      
                    }
        }
		
            
                pdfMake.createPdf(docDefinition).getBase64(function(buffer) {
                    var data1 = {
                        'from': "shreedhar@deemtek.com",
                        to: "santhrupthi.deemsoft@gmail.com",
                        cc: "santhrupthi.deemsoft@gmail.com",
                        bcc:"",
                        subject: "ACAMCH Pharmacy Invoice Summary",
                        body:"<html><body style='font:Calibri;line-height: 2;'><p>test</p></body></html>",
                        filename: "invoicesummary.pdf",
                        attachment: buffer
                        };
                        $http({      
                            method: 'POST',
                            url:'../../email/sendwithattachment/',
                            headers: {'X-CSRF-TOKEN': csrf },
                            data:data1 }).then(function(response) {
                                alert("Email has been sent. You will receive an email shortly.");
                        });          
               });
            
          
          
           
    })
	}

 });
 


