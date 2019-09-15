app.controller('searchController',function($scope,$location,searchService){

    // 定义搜索对象的结构 category: 商品分类
    $scope.searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':40,'sort':'','sortField':''};

    // 搜索
    $scope.search=function(){
        // 转换为数字
        $scope.searchMap.pageNo = parseInt($scope.searchMap.pageNo) ;
         searchService.search($scope.searchMap).success(
             function(response){
                 $scope.resultMap=response ;
                 // 构建分页栏
                 buildPageLabel() ;
                     // 初始化当前页
                     // $scope.searchMap.pageNo=1 ;



             }
         );
    }

    // 构建分页栏
    buildPageLabel=function(){
        // 设置初始化分页栏变量
        $scope.pageLabel=[] ;
        var firstPage=1 ;                       // 开始页码
        var lastPage = $scope.resultMap.totalPages ;    // 尾页码等于总页数
        $scope.firstDot=true ;  // 前面有省略号
        $scope.lastDot =true ;  // 后边有省略号
        // 如果总页码数大于五
        if ($scope.resultMap.totalPages>5) {
            // 如果当前页小于等于3
            if ($scope.searchMap.pageNo <= 3) {
                lastPage = 5;
                $scope.firstDot = false; // 前面没有省略号
                // 如果当前页大于等于总页数减二
            } else if ($scope.searchMap.pageNo >= $scope.resultMap.totalPages - 2) {
                firstPage = $scope.resultMap.totalPages - 4;
                $scope.lastDot = false;  // 后边没有省略号
            } else {
                // 否则以当前页为中心的5页都能显示
                lastPage = $scope.searchMap.pageNo + 2;
                firstPage = $scope.searchMap.pageNo - 2;
            }
        }else{
                $scope.firstDot=false ;  // 前面没有省略号
                $scope.lastDot =false ;  // 后边没有省略号
        }

        // 遍利总页数
        for (var i=firstPage; i<=lastPage; i++){
            $scope.pageLabel.push(i) ;
        }
    }

    // 添加搜索项 改变searchMap的值
    $scope.addSearchItem=function(key,value){

        if (key=='category' || key=='brand' || key=='price'){
            // 如果用于点击的是分类或品牌
            $scope.searchMap[key]=value ;
        }else{
            // 用户点击的规格
            $scope.searchMap.spec[key]=value ;
        }
        // 查询
        $scope.search();
    }

    // 撤销搜索项
    $scope.remevoSearchItem=function(key){

        if (key=='category' || key=='brand' || key=='price'){
            // 如果用于点击的是分类或品牌
            $scope.searchMap[key]="" ;
        }else{
            // 用户点击的规格 直接删掉小key(值和key就都没了)
            delete $scope.searchMap.spec[key] ;
        }
        // 查询
        $scope.search();
    }

    // 分页查询
    $scope.queryByPage = function(pageNo){
        // 当前页吗小于1或当前页大于总页数就停止
        if (pageNo<1 || pageNo>$scope.resultMap.totalPages){
            return  ;
        }
        // 当前页
        $scope.searchMap.pageNo = pageNo ;
        $scope.search() ;   // 查询
    }

    // 判断当前页是否为第一页
    $scope.isTopPage=function(){
        if($scope.searchMap.pageNo==1){
            return true ;
        }else{
            return false ;
        }
    }

    $scope.isEndPage=function(){
        if ($scope.searchMap.pageNo==$scope.resultMap.totalPages){
            return true ;
        }else{
            return false ;
        }
    }

    $scope.sortSearch=function(sortField,sort){
        $scope.searchMap.sortField=sortField ;
        $scope.searchMap.sort=sort ;

        $scope.search() ;   // 查询
    }

    // 判断关键字是否是品牌
    $scope.keywordsIsBrand=function(){
        // 遍历品牌列表
        for(var i=0; i<$scope.resultMap.brandList.length ; i++){
            // 查看品牌列表里文本是否有含关键字字段, indexOf()是查找索引位置方法;
            if($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[i].text)>=0){
                return true ;
            }
        }
        // 找完一遍没有就false
        return false ;
    }

    // 加载关键字
    $scope.loadkeywords=function(){
        // 接受页面的传递参数关键字
        $scope.searchMap.keywords = $location.search()['keywords'] ;
        // 执行查询
        $scope.search() ;
    }
});