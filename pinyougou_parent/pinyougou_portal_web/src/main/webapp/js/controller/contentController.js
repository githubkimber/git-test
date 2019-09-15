app.controller('contentController',function($scope,contentService){

    $scope.contentList=[] ;
    // 根据分类ID查询广告的方法
    $scope.findByCategoryId=function(categoryId){
        contentService.findByCategoryId(categoryId).success(
            function(response){
                $scope.contentList[categoryId]=response ;
            }
        );
    }

    // 搜索 (传递参数)
    $scope.search=function(){
        // 把文本框绑定到$scope.keywords变量然后跳转页面
        location.href="http://localhost:9104/search.html#?keywords="+$scope.keywords;
    }
});