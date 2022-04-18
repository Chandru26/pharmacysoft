app.controller('ModalInstanceController', function ($scope, $uibModalInstance, items) {

  $scope.ok = function () {
    $uibModalInstance.close('OK');
  };

  $scope.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});