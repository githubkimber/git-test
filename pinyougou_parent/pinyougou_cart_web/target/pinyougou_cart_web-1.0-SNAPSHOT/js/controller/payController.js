app.controller('payController' ,function($scope,$location ,payService){
    //本地生成二维码
    $scope.createNative=function(){
        payService.createNative().success(
            function(response){
                // 显示订单号和金额
                $scope.money=  (response.total_fee/100).toFixed(2) ;	//金额
                $scope.out_trade_no= response.out_trade_no;//订单号
                //生成二维码
                var qr = new QRious({
                    element:document.getElementById('qrious'),
                    size:250,
                    level:'H',
                    value:response.code_url
                });
                // 生成二维码后调用查询
                queryPayStatus();
            }
        );
    }

    // 查询支付状态
    queryPayStatus = function(){
        payService.queryPayStatus($scope.out_trade_no).success(
            function (response) {
                if (response.success){
                    location.href="paysuccess.html#?money="+$scope.money;
                }else{
                    if (response.message=="二维码超时"){
                        // 重新生成二维码
                        $scope.createNative();
                    }else{
                        location.href="payfail.html";
                    }
                }
            }
        );
    }

    // 获取金额
    $scope.getMoney = function(){
        return  $location.search()['money'];
    }
});