// com.pinyougou.controller: 定义控制器;                         传入模块
app.controller('brandController',function($scope,$controller,brandService){

    // 伪继承: 传递$scope来实现:{$scope:$scope}让当前$scope等于baseController模块中的$scope,让他们通用.
    $controller('baseController',{$scope:$scope});

    // 查询品牌列表
    $scope.findAll=function(){
        brandService.findAll().success(
            function(response){
                $scope.list=response ;	// 给列表变量赋值
            }
        );
    }

    //分页方法
    $scope.findPage=function(page,rows) {
        brandService.findPage(page,rows).success(
            function(response){
                $scope.list = response.rows ; // 显示当前页数据 rows:行.
                $scope.paginationConf.totalItems = response.total; // 更新总记录数, 给分页控件(变量);
            }

        );
    }

    //增加品牌
    // add: 方法名; post: 请求方法; (请求路径,请求变量);
    $scope.save=function(){
        var object=null ; // 定义一个对象
        if($scope.entity.id!=null){
            object=brandService.update($scope.entity) ;
        }else{
            object=brandService.add($scope.entity) ;
        }

        object.success(
            function(response){
                if(response.success) {
                    $scope.reloadList(); //调用方法刷新列表
                }else{
                    alert(response.message) ; //打印message显示失败原因
                }
            }
        );
    }

    // 查询实体
    $scope.findOne=function(id){
        brandService.findOne(id).success(
            function(response){
                $scope.entity=response;
            }
        );
    }

    // 删除品牌信息
    $scope.dele=function(){
        brandService.dele($scope.selectIds).success(
            function(response){
                if(response.success){
                    $scope.reloadList(); //调用方法刷新列表
                }else{
                    alert(response.message) ; //打印message显示失败原因
                }
            }
        );
    }

    $scope.searchEntity={};     // 变量初始化;
    // 条件查询
    $scope.search=function(page, rows){
        brandService.search(page, rows, $scope.searchEntity).success(
            function(response){
                $scope.list = response.rows ; // 显示当前页数据 rows:行.
                $scope.paginationConf.totalItems = response.total; // 更新总记录数, 给分页控件(变量);
            }
        );
    }



});