
app.controller('resetPassController', function($scope,$http,$timeout,$window, $log, Flash, deemsoft) 
{
 
var csrf = document.getElementById("csrf_token").value;
var resetid = document.getElementById("reset__id").value;
 $scope.setpass = function(){
	  if( $scope.userid !== "" && $scope.passwd === $scope.rtpasswd ){
		deemsoft.getHTTP('../settingsrest/setpass/'+resetid+"/"+$scope.userid+"/"+$scope.passwd).then(function(r){
			if(r === undefined || r === null ){
				Flash.create('danger', "Unable to  Set Password!");
			}else
			{
				Flash.create('success', "Password Saved Successfully!");
				
				$timeout(callAtTimeout, 3000); 
			}
		});
	}
 }

 function callAtTimeout(){
	 $window.location.href = '/pharmacysoft/login.html';
 }
 
});