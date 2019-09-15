//控制层
app.controller('seckillGoodsController' ,function($scope,$location,seckillGoodsService,$interval){
    //读取列表数据绑定到表单中
    $scope.findList=function(){
        seckillGoodsService.findList().success(
            function(response){
                $scope.list=response;
            }
        );
    }

    // 查询商品
    $scope.findOne=function(){
        // 接受参数Id
        var id = $location.search()['id'];
        seckillGoodsService.findOne(id).success(
            function(response){
                $scope.entity=response;

                // 倒计时开始
                // 获取从结束时间到当前日期的秒数      (Math.floor: 取整数)
                allsecond = Math.floor((new Date($scope.entity.endTime).getTime()- new Date().getTime())/1000);

                time = $interval(function(){
                    allsecond=allsecond-1;
                    $scope.timeString = convertTimeString(allsecond);
                    if (allsecond<=0){
                        $interval.cancel(time);
                    }
                },1000);
            }
        );
    }

    // 转换秒为date格式(DD天hh:mm:ss)
    convertTimeString=function(allsecond){
        var days = Math.floor(allsecond/(60*60*24));    // 天数
        var hours = Math.floor((allsecond-days*60*60*24)/(60*60));  //小时数
        var minutes = Math.floor((allsecond-days*60*60*24-hours*60*60)/60); // 分钟数
        var seconds = allsecond - days*60*60*24-hours*60*60-minutes*60;
        var timeString = "";
        if (days>0){
            timeString+=days+"天";
        }
        return days+"天"+hours+":"+"minutes"+";"+seconds;
    }

    //提交订单
    $scope.submitOrder=function(){
        seckillGoodsService.submitOrder($scope.entity.id).success(
            function(response){
                if(response.success){// 如果下单成功
                    alert("抢购成功，请在5分钟内完成支付!");
                    location.href="pay.html";   // 跳转页面
                }else{
                    alert(response.message);
                }
            }
        );
    }
});