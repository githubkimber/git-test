// 定义模块
var app=angular.module('pinyougou',[]);		// 'pagination': 从pagination.js中引入的模块;
// filter定义过滤器 (trustHtml名字自定义)     $sce服务:引用信任html的转换服务;
app.filter('trustHtml',['$sce',function($sce){

    return function(data){ //传入的参数是被过滤的内容
        return $sce.trustAsHtml(data) ; // 返回的是过滤后的内容(信任html的转换)
    }
}]);