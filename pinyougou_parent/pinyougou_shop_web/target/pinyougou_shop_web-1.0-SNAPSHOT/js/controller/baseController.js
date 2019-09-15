// com.pinyougou.controller: 定义控制器;
app.controller('baseController',function($scope){

    //分页控件配置(自动分页)
    $scope.paginationConf = {
        currentPage: 1,							// currentPage: 当前页;
        totalItems: 10,							// totalItems:  总记录数;
        itemsPerPage: 10,						// itemsPerPage: 每页记录数;
        perPageOptions: [10, 20, 30, 40, 50],	// perPageOptions: 分页选项;
        onChange: function(){			// onChange: 当页码改变后自动触发的方法;
            $scope.reloadList();
        }
    };

    // 刷新列表
    $scope.reloadList=function(){
        $scope.search($scope.paginationConf.currentPage , $scope.paginationConf.itemsPerPage);
    }

    // 用户勾选品牌信息
    $scope.selectIds=[] ;   // 用户勾选的id集合; selectIDs:自定义的变量名;
    // updateSelection: 自定义的方法名;
    $scope.updateSelection=function($event, id) {
        if ($event.target.checked) {       // 通过$event.target的获得$event绑定的数据, 在勾选的时候才调用;

            $scope.selectIds.push(id);         // push方法向集合添加元素
        }else{
            var index=$scope.selectIds.indexOf(id) ;    // 查找值的位置
            $scope.selectIds.splice(index,1) ;      // 参数1: 移除的位置; 参数2: 移除的个数;
        }
    }

    $scope.jsonToString=function(jsonString,key){
        var json = JSON.parse(jsonString);
        var value="";

        for (var i=0; i<json.length ; i++){
            // 第加载第一个文本不给逗号显示;
             if(i>0){
                value+=",";
             }
            value +=json[i][key];       // 数组变量: a[x]中,x为变量;a也为变量;
        }                                   // json[i][key]: key为变量,json[i]也为变量;
        return value ;
    }

    // 在list集合中根据某key的值查询对象
    $scope.searchObjectByKey=function(list,key,keyValue){
        for (var i=0;i<list.length;i++){
            if (list[i][key]==keyValue){
                return list[i] ;
            }
        }
        return null ;
    }
});