rubikAdmin.controller('deptController', function ($scope, $stateParams,
                                                  DataService, $http, $location, $rootScope, ngDialog) {

    $scope.sourceDeptModel = DataService.sourceDeptModel;

    if ($location.path() == '/dept') {

        $scope.paginationConf = {
            currentPage: 1,
            itemsPerPage: DataService.sourceDeptModel.pageSize
        };

        $scope.sourceDeptModel.search = "";

        var reGetSourceDepts = function () {
            $scope.startIndex = ($scope.paginationConf.currentPage - 1) * $scope.paginationConf.itemsPerPage + 1;
            $scope.sourceDeptModel.loadAllDepts($scope.paginationConf,
                $scope.sourceDeptModel.search);
        };
        $scope.$watch('paginationConf.currentPage', reGetSourceDepts);
    }

    // dept详细记录
    if ($stateParams.sourceDeptId != null) {
        $scope.sourceDept = $scope.sourceDeptModel
            .setSourceDeptInstance($stateParams.sourceDeptId);
        if (!$scope.sourceDept && window.localStorage) {
            $scope.sourceDept = JSON.parse(localStorage[$stateParams.sourceDeptId]);
        }
    }

    $scope.searchKeyDown = function (ev) {
        //只响应回车时间
        if (ev.keyCode !== 13) {
            return;
        }
        $scope.paginationConf.currentPage = 1;
        $scope.sourceDeptModel.loadAllDepts($scope.paginationConf, $scope.sourceDeptModel.search)
    }
});
