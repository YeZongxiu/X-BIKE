rubikAdmin.controller('standardDepartmentController', function ($scope, $stateParams,
                                                                DataService, $http, $location, $rootScope, ngDialog) {

    $scope.standardDept = {};

    $scope.standardDepartmentModel = DataService.standardDepartmentModel;

    $scope.standardDepartmentModel.loadAll();

    $scope.startIndex = 1;

    if ($stateParams.cid != null) {
        $scope.standardDept = $scope.standardDepartmentModel
            .setStandardDeptInstance($stateParams.cid);
        if (!$scope.standardDept && window.localStorage) {
            $scope.standardDept = JSON.parse(localStorage[$stateParams.cid]);
        }
    }

});
