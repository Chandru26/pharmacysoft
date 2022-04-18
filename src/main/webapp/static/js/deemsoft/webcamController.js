angular.module('webcamDemo',['webcam'])
  .controller('webcamController', function ($scope) {
    'use strict';
    var _video = null;    
    $scope.channel = {};
    $scope.webcamError = false;
    $scope.onError = function(err){ $scope.$apply(  function() { $scope.webcamError = err; } ); };
    $scope.onSuccess = function(){ _video = $scope.channel.video; };
    $scope.onStream = function(stream){ };
	$scope.sanpshotView = false;
	$scope.preView = true;
	
	$scope.previewOn = function(){
		$scope.snapshotView = false;
		$scope.preView = true;
	};
	
    $scope.takeSnapshot = function() {
        if (_video) {
            var patCanvas = document.querySelector('#snapshot');
            if (!patCanvas) return;
            patCanvas.width = 320;
            patCanvas.height = 240;
            var ctxPat = patCanvas.getContext('2d');
            var idata = getVideoData(0,0,320,240);
            ctxPat.putImageData(idata, 0, 0);
            sendSnapshotToServer(patCanvas.toDataURL());
    		$scope.snapshotView = true;
			$scope.preView = false;
        }
    };

  function getVideoData(x, y, w, h) {
        var hiddenCanvas = document.createElement('canvas');
        hiddenCanvas.width = _video.width;
        hiddenCanvas.height = _video.height;
        var ctx = hiddenCanvas.getContext('2d');
        ctx.drawImage(_video, 0, 0, _video.width, _video.height);
        return ctx.getImageData(x, y, w, h);
    };

   function sendSnapshotToServer(imgBase64) {
        $scope.snapshotData = imgBase64;
    };

  });