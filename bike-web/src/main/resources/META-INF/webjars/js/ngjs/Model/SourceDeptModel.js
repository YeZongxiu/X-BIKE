/**
 * Created by meng on 2016/2/26.
 */

//构造SourceDeptModel对象
function SourceDeptModel($http, $state, ngDialog) {

    //初始化每页条数
    this.pageSize = 10;

    this.deptList = [];

    this.server = $http;
    this.state = $state;
    this.ngDialog = ngDialog;
}


//获取科室列表
SourceDeptModel.prototype.loadAllDepts = function (paginationConf, search) {
    var searchURL = '/dept/deptSearch.json?pageSize=' + this.pageSize + '&currentPageNo=' + (paginationConf.currentPage == 0 ? 1 : paginationConf.currentPage);
    if (search || search != "") {
        searchURL += '&searchValue=' + search;
    }
    var pData = new Array();
    var self = this;
    this.server({
        method: 'GET',
        url: searchURL
    }).success(function (data) {
        if (data != "" && data.list.length > 0) {
            for (var i = 0; i < data.list.length; i++) {
                pData.push(data.list[i]);
            }
            paginationConf.totalItems = pData.totalCount;
        }
        self.deptList = pData;
    })
}

//根据sourceDeptId获取SourceDept详细记录
SourceDeptModel.prototype.setSourceDeptInstance = function (pid) {
    for (var i = 0; i < this.deptList.length; i++) {
        if (this.deptList[i].sourceDeptId == pid) {
            if (window.localStorage) {
                localStorage[pid] = JSON.stringify(this.deptList[i]);
            }
            return this.deptList[i];
        }
    }
    return null;
}

//添加科室
SourceDeptModel.prototype.addDept = function (isValid, sourceDept) {
    if (isValid) {
        this.server({
            method: 'POST',
            url: '/dept/addDept.json',
            data: sourceDept,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (data) {
            window.location.href = '#/dept';

        })
    }
}

//编辑科室
SourceDeptModel.prototype.updateDept = function (isValid, sourceDept) {

    var state = this.state;
    if (isValid) {
        this.server({
            method: 'POST',
            url: '/dept/editDept.json',
            data: sourceDept,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (data) {
            localStorage.removeItem(sourceDept.sourceDeptId); //清除值
            state.go('^');

        })
    }
};

//删除科室
SourceDeptModel.prototype.deleteDept = function (dept) {
    var state = this.state;
    var http = this.server;
    this.ngDialog.openConfirm({
        template: '../js/ngjs/templates/deleteDept.html',
        className: 'ngdialog-theme-default',
        controller: ['$scope', function ($scope) {
            $scope.data = dept;
        }]
    }).then(function () {
        http({
            method: 'POST',
            url: '/dept/deleteDept.json',
            data: dept,
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (data) {
            if (data && data.code != 0) {
                alert(data.info);
            } else {
                state.go('dept', {}, {
                    reload: true
                });
            }
        })
    }, function (reason) {
    });
}
