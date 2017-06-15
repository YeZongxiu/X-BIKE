rubikAdmin.factory("DataService", function ($http, $state, ngDialog) {
    var sourceDeptModel = new SourceDeptModel($http, $state, ngDialog);
    var standardDepartmentModel = new StandardDepartmentModel($http, $state, ngDialog);


    return {
        sourceDeptModel:sourceDeptModel,
	standardDepartmentModel : standardDepartmentModel
    };
});

