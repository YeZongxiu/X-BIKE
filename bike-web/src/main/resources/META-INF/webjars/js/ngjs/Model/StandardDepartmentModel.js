/**
 * Created by sbwkl on 2016/2/29.
 */

function StandardDepartmentModel($http, $state, ngDialog) {
    this.server = $http;
    this.state = $state;
    this.ngDialog = ngDialog;
    this.standardDeptList = [];
    this.deptList = [];
    this.selected = [];
}

StandardDepartmentModel.prototype.loadAll = function (name) {
    var searchURL = '/standardDepartments.json';
    if (name && name != "") {
        searchURL += '?name=' + name;
    }
    var self = this;
    this.server({
        method: 'GET',
        url: searchURL
    }).success(function (diseaseClasses) {
        self.diseaseClasses = diseaseClasses;
        self.dept = {className: '1111'};
        self.standardDeptList = diseaseClasses;
    })
};

StandardDepartmentModel.prototype.setStandardDeptInstance = function (cid) {

    var self = this;

    self.selected = [];

    for (var i = 0; i < this.standardDeptList.length; i++) {
        if (this.standardDeptList[i].classId == cid) {
            if (window.localStorage) {
                localStorage[cid] = JSON.stringify(this.standardDeptList[i]);
            }
            if (this.standardDeptList[i].hospitalDepts != null && this.standardDeptList[i].hospitalDepts.length > 0) {
                for (var j = 0; j < this.standardDeptList[i].hospitalDepts.length; j++) {
                    self.selected.push(this.standardDeptList[i].hospitalDepts[j].sourceDeptId);
                }
            }
            var searchURL = '/dept/deptAll.json';
            var pData = new Array();

            this.server({
                method: 'GET',
                url: searchURL
            }).success(function (data) {
                if (data != "" && data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        if (self.selected.indexOf(data[i].sourceDeptId) >= 0) {
                            data[i].checked = true;
                        } else {
                            data[i].checked = false;
                        }

                        pData.push(data[i]);
                    }
                }
                self.deptList = pData;
            });
            return this.standardDeptList[i];
        }
    }
    return null;
};

//修改科室关联
StandardDepartmentModel.prototype.updateStandardDept = function (classId) {
    var checkDeptIds = [];
    for (var i = 0; i < this.deptList.length; i++) {
        if (this.deptList[i].checked) {
            checkDeptIds.push(this.deptList[i].sourceDeptId);
        }
    }
    var state = this.state;
    this.server({
        method: 'PUT',
        url: '/standardDepartments/edit.json?classId=' + classId,
        data: checkDeptIds,
        headers: {
            'Content-Type': 'application/json'
        }
    }).success(function (data) {
            state.go('^');
    })
};