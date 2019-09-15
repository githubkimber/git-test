 //控制层 
app.controller('goodsController' ,function($scope,$controller,$location,goodsService,uploadService,itemCatService,typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };

    //查询实体
    $scope.findOne = function () {
        var id = $location.search()['id'];
        if (id==null){
            return ;
        }
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
                // 读取商品介绍
                editor.html($scope.entity.goodsDesc.introduction);
                // 读取商品图片
                $scope.entity.goodsDesc.itemImages=JSON.parse($scope.entity.goodsDesc.itemImages);
                // 读取商品扩展属性
                $scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.entity.goodsDesc.customAttributeItems);
                // 读取规格选择
                $scope.entity.goodsDesc.specificationItems=JSON.parse($scope.entity.goodsDesc.specificationItems);
                // 转换SKU列表中的规格对象

                for (var i=0; i<$scope.entity.itemList.length; i++ ){
                    $scope.entity.itemList[i].spec=JSON.parse($scope.entity.itemList[i].spec) ;
                }
            }
        );
    };

    //保存
    $scope.save = function () {
        $scope.entity.goodsDesc.introduction = editor.html();

        var serviceObject;//服务层对象
        if ($scope.entity.goods.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    alert("保存成功");
                    location.href='goods.html' ;
                    // $scope.entity = {};
                    // editor.html("");	// 清空富文本编辑器
                } else {
                    alert(response.message);
                }
            }
        );
    };

    // //增加商品
    // $scope.add = function () {
    //     // 提取富文本内容存储到entity中, entity是组合实体类;
    //     $scope.entity.goodsDesc.introduction = editor.html();
    //     goodsService.add($scope.entity).success(
    //         function (response) {
    //             if (response.success) {
    //                 alert("新增成功");
    //                 $scope.entity = {};
    //                 editor.html("");	// 清空富文本编辑器
    //             } else {
    //                 alert(response.message);
    //             }
    //         }
    //     );
    // };

    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                }
            }
        );
    };

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };

    // 上传图片
    $scope.uploadFile = function () {
        uploadService.uploadFile().success(
            function (response) {
                if (response.success) {
                    $scope.image_entity.url = response.message;
                } else {
                    alert(response.message);
                }
            }
        );
    };

    // 定义一个集合
    $scope.entity = {goods: {}, goodsDesc: {itemImages: [], specificationItems:[]}};
    // 将当前上传的图片实体存入图片列表
    $scope.add_image_entity = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    };

    // 移除图片
    $scope.remove_image_entity = function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    };

    // 查询一级商品分类列表
    $scope.selectItemCat1List = function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.itemCat1List = response;
            }
        );
    };

    // 查询二级商品分类列表
    // 监控第一个变量的变化;
    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
        // alert(newValue) ;
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat2List = response;
            }
        );
    });

    // 查询三级商品分类列表
    // 监控第二个变量的变化;
    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        // alert(newValue) ;
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat3List = response;
            }
        );
    });

    // 读取模板Id
    // 监控第三个变量的变化;
    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.entity.goods.typeTemplateId = response.typeId;
            }
        );
    });


    // 读取品牌列表,	扩展属性, 规格列表,
    // 监控模板Id的变化;
    $scope.$watch('entity.goods.typeTemplateId', function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(
            function (response) {
                $scope.typeTemplate = response;	// 模板对象
                $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);	// 品牌列表字符串类型转换成JSON类型;
                // 扩展属性
                if ($location.search()['id']==null){// id空为增加,不空为修改
                    $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
                }

            }
        );
        // 读取规格
        typeTemplateService.findSpecList(newValue).success(
            function (response) {
				$scope.specList=response;
            }
        );
    });

    // 由规格复选框调用该方法
	$scope.updateSpecAttribute=function($event,name,value){
        var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,'attributeName',name);
        if (object!=null){
            if ($event.target.checked) {
                object.attributeValue.push(value);
            }else{
                // 取消勾选
                object.attributeValue.splice(object.attributeValue.indexOf(value),1);  // 移除选项
                // 如果选项取消了将此条记录移除
                if (object.attributeValue.length==0){
                    $scope.entity.goodsDesc.specificationItems.splice(
                        $scope.entity.goodsDesc.specificationItems.indexOf(object),1);
                }
            }
		}else{
        	$scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]});
		}
	}

	//创建SKU列表
    $scope.createItemList=function(){
	    // 列表初始化
        $scope.entity.itemList=[{spec:{},price:0,num:99999,status:'0',isDefault:'0'}];

        var items = $scope.entity.goodsDesc.specificationItems;

        for (var i=0; i<items.length; i++){
            $scope.entity.itemList = addColumn($scope.entity.itemList, items[i].attributeName, items[i].attributeValue);
        }

    }
    // 局部方法不在外部调用所以开头不用写$scope; columnName:规格名称;  columnValues:规格值的集合;
    addColumn=function(list,columnName,columnValues){
        var newList = [] ;
        for (var i=0; i<list.length; i++){
            var oldRow = list[i] ;
            for (var j=0; j<columnValues.length; j++){
                // 深克隆  (将oldRow先转字符串,再转JSON对象)
                var newRow =JSON.parse(JSON.stringify(oldRow)) ;
                // 为新的对象添加规格名称
                newRow.spec[columnName]=columnValues[j] ;
                // 再添入新的对象
                newList.push(newRow) ;
            }
        }
        return newList ;
    }

    $scope.status=['未审核','已审核','审核未通过','已关闭'];

	$scope.itemCatList=[] // 定义一个数组,商品分类列表
	// 查询商品分类列表
    $scope.findItemCatList=function(){
        itemCatService.findAll().success(
            function(response){ // response的值: 商品分类列表的结果
                for(var i=0; i<response.length ; i++){
                    // response[i]: 为当前循环的记录;
                    $scope.itemCatList[response[i].id]=response[i].name ;
                }
            }
        );
    }

    // 判断规格与规格选项是否应该被勾选
    $scope.checkAttributeValue=function(specName,optionName){
       var items = $scope.entity.goodsDesc.specificationItems ;
       var object = $scope.searchObjectByKey(items,'attributeName',specName) ;

       if (object!=null){
           // 如果能够查询到规格选项
           if (object.attributeValue.indexOf(optionName)>=0){
               return true ;   // 返回true启用规格复选框默认勾选,false则相反;
           }else{
               return false ;
           }
       }else{
           return false ;
       }
    }
});