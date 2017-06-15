var rubikAdmin = angular.module('rubikAdmin', ['ui.router.state', 'ngCookies', 'ngDialog', 'ncy-angular-breadcrumb', 'tm.pagination']);

rubikAdmin.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
    $stateProvider
        .state('dept', {
            url: '/dept',
            views: {
                "@": {
                    templateUrl: 'basedata/department/deptList.html',
                    controller: 'deptController'
                }
            },
            ncyBreadcrumb: {
                label: '科室列表'
            }
        })

        .state('dept.deptAdd', {
            url: '/deptAdd',
            views: {
                "@": {
                    templateUrl: 'basedata/department/addDept.html',
                    controller: 'deptController'
                }
            },
            ncyBreadcrumb: {
                label: '添加科室'
            }
        })

        .state('dept.deptEdit', {
            url: '/{sourceDeptId}/deptEdit',
            views: {
                "@": {
                    templateUrl: 'basedata/department/editDept.html',
                    controller: 'deptController'
                }
            },
            ncyBreadcrumb: {
                label: '编辑科室'
            }
        })

        .state('standard', {
            url: '/standard',
            views: {
                "@": {
                    templateUrl: 'basedata/standard/department.html',
                    controller: 'standardDepartmentController'
                }
            },
            ncyBreadcrumb: {
                label: '标准科室关联'
            }
        })
        .state('standard.standardEdit', {
            url: '/{cid}/standardEdit',
            views: {
                "@": {
                    templateUrl: 'basedata/standard/departmentEdit.html',
                    controller: 'standardDepartmentController'
                }
            },
            ncyBreadcrumb: {
                label: '修改标准科室关联'
            }
        })
    ;

    $urlRouterProvider.otherwise('/dept');


    //拦截器拦截器，用于拦截所有请求
    var interceptor = function ($q) {
        return {
            'response': function (resp) {
                return resp;
            },
            'responseError': function (rejection) {
                //  错误处理
                switch (rejection.status) {
                    case 401:
                        alert("登录状态过期，请重新登录。");
                        window.location.href = '#/locations';
                        break;
                    case 500:
                        alert("服务器错误，请查看服务器日志或通知管理员。");
                        break;
                }
                return $q.reject(rejection);
            }
        };
    };
    // 将拦截器和 $http的request/response链整合在一起
    $httpProvider.interceptors.push(interceptor);
})
    .run(['$rootScope', '$location', '$cookieStore', '$http',
        function ($rootScope, $location, $cookieStore, $http) {
            // keep user logged in after page refresh
            if (!$location.path()) {
                $location.path('/locations');
            }
        }
    ]);
