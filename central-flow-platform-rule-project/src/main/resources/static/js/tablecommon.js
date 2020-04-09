

$( function() {	
	// 点击状态切换
	$("#onoffswitch").on('click', function(){
		clickSwitch()
	});
 
	var clickSwitch = function() {
		if ($("#onoffswitch").is(':checked')) {
			console.log("在ON的状态下");
		} else {
			console.log("在OFF的状态下");
		}
	};
	
	// 总量数据获取
	tableNums();
	// 表格数据获取   初始的时候是总数据，切换的时候根据id来区分,  显示的页数
	//tableDate("",1)
	//启用表格
	//initTable();
    
	// 添加显示
	$(".e-add>img").click(function(){
		$(this).next().fadeIn(300);
	})

	// 向上移动  
	$("#table").on("click", ".e-up", function () {
         var $tr = $(this).parents("tr");
         
         if ($tr.index() != 0) {
             var prames = [];
             var arr = {};
             arr.id = $(this).attr("data-id")
             arr.gatewayId = $(this).attr("data-gatewayId")
             arr.ruleOrder = $(this).attr("data-ruleOrder")
             arr.createTime = $(this).attr("data-createTime")
             prames.push(arr)
             var arr2 = {};
             arr2.id = $tr.prev().find(".e-up").attr("data-id")
             arr2.gatewayId = $tr.prev().find(".e-up").attr("data-gatewayId")
             arr2.ruleOrder = $tr.prev().find(".e-up").attr("data-ruleOrder")
             arr2.createTime = $tr.prev().find(".e-up").attr("data-createTime")
             prames.push(arr2)
             
             $.ajax({
	     		url: '../api/v1/rulegateway/changeRuleOrder',
	     		type: "put",
	     		contentType: 'application/json; charset=UTF-8',
	     		data: JSON.stringify(prames),
	     		success:function(data){
	     	        //title数据获取
	     			$('#table').bootstrapTable('refresh');
	     		},
	     		error:function(){
	     			console.log("数据出错了")
	     		}
	     	 })
         }
     })

     // 向下移动  
     $("#table").on("click", ".e-down", function () {
    	 var _len = $("#table").find("tbody>tr").length;
         var $tr = $(this).parents("tr");
         if ($tr.index() != _len - 1) {
        	 var prames = [];
             var arr = {};
             arr.id = $(this).attr("data-id")
             arr.gatewayId = $(this).attr("data-gatewayId")
             arr.ruleOrder = $(this).attr("data-ruleOrder")
             arr.createTime = $(this).attr("data-createTime")
             prames.push(arr)
             var arr2 = {};
             arr2.id = $tr.next().find(".e-up").attr("data-id")
             arr2.gatewayId = $tr.next().find(".e-up").attr("data-gatewayId")
             arr2.ruleOrder = $tr.next().find(".e-up").attr("data-ruleOrder")
             arr2.createTime = $tr.next().find(".e-up").attr("data-createTime")
             prames.push(arr2)
             
             $.ajax({
	     		url: '../api/v1/rulegateway/changeRuleOrder',
	     		type: "put",
	     		contentType: 'application/json; charset=UTF-8',
	     		data: JSON.stringify(prames),
	     		success:function(data){
	     	        //title数据获取
	     			$('#table').bootstrapTable('refresh');
	     		},
	     		error:function(){
	     			console.log("数据出错了")
	     		}
	     	 })
         }
     })
     
     $("#table").on("click", ".e-delect", function () {
         var gatewayId = $(this).attr("data-gatewayId");
         var id = $(this).attr("data-id");
         $.ajax({
     		url: '../api/v1/rulegateway/'+gatewayId+'/'+id,
     		type: "delete",
     		success:function(data){
     	        //title数据获取
     			if(data.result == "success"){
     				$(".e-g-false-right>p").text("您确定要删除吗？");
     				$(".e-g-tips").fadeIn(300);
     		    	$(".e-m-background").fadeIn(300);
     		    	$(".e-g-sure").addClass("e-r-ref");
     			}
     		},
     		error:function(){
     			console.log("数据出错了")
     		}
     	})
    })
    
    // 全选/未选
	$(":radio").click(function(){
		searchTable();
	});
    // 回车搜索
    $(document).keyup(function(event){
	  if(event.keyCode ==13){
		searchTable();
	  }
	});
    
    $(".e-form-left>img").click(function(){
    	searchTable();
    })
    
    // 取消
    $(".e-g-cancel").click(function(){
    	// 移除确定的其他类名
    	$(".e-g-sure").removeClass("e-y-ref")
    	$(".e-g-sure").removeClass("e-n-ref")
    	$(".e-g-sure").removeClass("e-r-ref")
    	
    	$(".e-g-tips").fadeOut(300);
    	$(".e-m-background").fadeOut(300);
    	$(".e-add-list").fadeOut(300);
    })
    
    // 添加
    $(".e-add>img").click(function(){
    	$(".e-add-list").fadeIn(300)
    })
    
    //隐藏
    $(document).click(function(){
    	$(".e-add-list").hide();
    })
    
    $(".e-add-list").click(function(){
    	return false;
    })

    // 提示关闭 并且点击确定后是否刷新
    $(".e-g-tips").on("click",".e-y-ref",function(){
    	var row = $(".e-g-false-right").find(".e-add-rule").text();
    	var _el = $(this);
    	$.ajax({
			url: "../api/v1/rulegateway",
			type: "POST",
			contentType: 'application/json; charset=UTF-8',
			data: row,
			success:function(data){
				//重新获取数量
		    	tableNum();
		    	_el.removeClass("e-y-ref");
		    	$(".e-g-tips").fadeOut(300);
		    	$(".e-m-background").fadeOut(300);
		    	$(".e-add-list").fadeOut(300);
		    	// 重新加载表格
		    	$('#table').bootstrapTable('refresh');
			},
			error:function(){
				console.log("数据出错了")
			}
		})
		$(".e-g-false-right").find(".e-add-rule").remove()
    })
    
    $(".e-g-tips").on("click",".e-r-ref",function(){
    	//重新获取数量
    	tableNum();
    	$(this).removeClass("e-r-ref");
    	$(".e-g-tips").fadeOut(300);
    	$(".e-m-background").fadeOut(300);
    	$(".e-add-list").fadeOut(300);
    	// 重新加载表格
    	$('#table').bootstrapTable('refresh');
    })
    
    $(".e-g-tips").on("click",".e-n-ref",function(){
    	$(".e-g-false-right").find(".e-add-rule").remove()
    	$(this).removeClass("e-n-ref");
    	$(".e-g-tips").fadeOut(300);
    	$(".e-m-background").fadeOut(300);
    	$(".e-add-list").fadeOut(300);
    })	
});

function tableNums(){
	$.ajax({
		url: "../api/v1/gateway/getGateway",
		type: "get",
		async: true,
		success:function(data){
	        //title数据获取
		    allTableNums(data)	
		},
		error:function(){
			console.log("数据出错了")
		}
	})
}

// 总量数据获取
function tableNum(){
	$.ajax({
		url: "../api/v1/gateway/getGateway",
		type: "get",
		async: true,
		success:function(data){
	        //title数据获取
		    allTableNum(data)	
		},
		error:function(){
			console.log("数据出错了")
		}
	})
}

// 全量数据展示
function fstTable(el){
	//刷新页面
//	location.reload();
	//修改title
	var tableTitle = $(el).find(".ui-header").text();
	$(".e-g-resources-head").text(tableTitle)
	$(".e-add").show();
	$(el).siblings().removeClass('active');
	$(el).addClass('active');
	$(".e-form>.e-form-right").show(300);
	$(".e-form-left").removeClass("e-son-ser")
	searchTable();
}

function searchTable(){
	var ruleName = $(".e-form-left>input").val().trim();
	var chooseValue = $('input:radio:checked').val();
	if($("#cart").find(".ui-widget.active").attr("id") != "allRule"){
		var gatewayId = $("#cart").find(".ui-widget.active").attr("data-id")
		toggleTable(gatewayId,ruleName)
	}else if(chooseValue == "未选"){

		initTable('true',ruleName)
	}else{
		//筛选全选数据
		initTable('',ruleName)
	}

}

function allTableNums(data){
	for(i in data){
		if(i != 0){
			var j = i-1;
			$(".e-add-list>li").eq(j).text(data[i].gatewayName)
			$(".e-add-list>li").eq(j).attr("data-id",data[i].id)
		}
		
		$("#cart>.e-float").eq(i).find(".e-number").text(data[i].groupNum)
		$("#cart>.e-float").eq(i).find(".ui-header").text(data[i].gatewayName)
		$("#cart>.e-float").eq(i).attr("data-id",data[i].id)
	}
	var nChoose = window.location.search;
	if(nChoose != ""){
		var n = nChoose.split("=")[1];
		$(".ui-widget").eq(n).addClass("active");
		
		if(n != 0){
			var dId,nId = $(".ui-widget").eq(n).attr("id");
		    if( nId == "allRule" ){
		    	dId = ""
		    }else{
		    	dId = $(".ui-widget").eq(n).attr("data-id") 
		    }
		    var ruleName ="";
		    var tableTitle = $(".ui-widget").eq(n).find(".ui-header").text();
		    $(".e-g-resources-head").text(tableTitle)
		    $(".e-add").hide();
		    // 隐藏搜索
		    $(".e-form>.e-form-right").hide(300)
		    $(".e-form-left").addClass("e-son-ser")
		    toggleTable(dId,ruleName)
		    
		}else{
			initTable();
		}
	}
}

//title数据获取
function allTableNum(data){
	for(i in data){
		if(i != 0){
			var j = i-1;
			$(".e-add-list>li").eq(j).text(data[i].gatewayName)
			$(".e-add-list>li").eq(j).attr("data-id",data[i].id)
		}
		
		$("#cart>.e-float").eq(i).find(".e-number").text(data[i].groupNum)
		$("#cart>.e-float").eq(i).find(".ui-header").text(data[i].gatewayName)
		$("#cart>.e-float").eq(i).attr("data-id",data[i].id)
	}
}

//表格数据获取   初始的时候是总数据，切换的时候根据id来区分
function tableDate(id,page){
	var prames = {};
	if(id){
		prames.gatewayId = id;
	}else{
		prames.gatewayId = "";
	}
	prames.curPage = page;
	prames.pageSize = "10";
	$.ajax({
		url: "../api/v1/rulegateway/getRules",
		type: "POST",
		contentType: 'application/json; charset=UTF-8',
		data: JSON.stringify(prames),
		success:function(data){
			$('#table').bootstrapTable('load', data);
		},
		error:function(){
			console.log("数据出错了")
		}
	})	
}

//切换规则
function swgTable(el){
	var dId,nId = $(el).attr("id");
    if( nId == "allRule" ){
    	dId = ""
    }else{
    	dId = $(el).attr("data-id") 
    }
    var ruleName ="";
    var tableTitle = $(el).find(".ui-header").text();
    $(".e-g-resources-head").text(tableTitle)
    // 加号隐藏
    $(".e-add").hide();
    // 隐藏搜索
    $(".e-form>.e-form-right").hide(300)
    $(".e-form-left").addClass("e-son-ser")
    $(el).siblings().removeClass('active')
	$(el).addClass('active')
    toggleTable(dId,ruleName)
}

//初始表格代码
function initTable(isRelevance,ruleName) {
  
    $('#table').bootstrapTable('destroy');

    $("#table").bootstrapTable({
    	url: "../api/v1/rulegateway/getRules",
    	method: "post",
    	contentType: 'application/json; charset=UTF-8',
    	cache: false,
        classes:'table-no-bordered',  //表格的类名称。默认情况下，表格是有边框的
        striped:true,  //设置为 true 会有隔行变色效果。
        maintainSelected: true,          
        pageNumber:1,
        pagination:true,  // 在表格底部显示分页组件，默认false
        pageSize: 10,                     //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        sidePagination:'server',
        queryParams : function (params) {
        	if (isRelevance != "" && typeof isRelevance != "undefined") {
        		isRelevance = true
        	}
            var temp = {
            	pageSize: params.limit, // 每页显示数量
                curPage: (params.offset / params.limit) + 1,   //当前页码
                isRelevance: isRelevance,
                ruleName: ruleName
            };
            return JSON.stringify(temp);
        },
        onLoadSuccess : function(data) {
            var tableLength = data.total;
            if( tableLength < 11 ){
            	$(".turnPAge").hide()
            }else{
            	$(".turnPAge").show()
            }
        },
        columns: [
    	{
     	    checkbox:true, 
     	    formatter : stateFormatter
        },
        {
            field: 'ruleOrder',
            title: '规则编号',
            align:'center',
            formatter: function (value, row, index) {
        	    //获取每页显示的数量
        	    var pageSize=$('#table').bootstrapTable('getOptions').pageSize;  
        	    //获取当前是第几页  
        	    var pageNumber=$('#table').bootstrapTable('getOptions').pageNumber;
        	    //返回序号，注意index是从0开始的，所以要加上1
        	    return pageSize * (pageNumber - 1) + index + 1;
        	}
        }, {
            field: 'groupName',
            title: '质检内容',
            align:'center'
        }, {
            field: 'groupDescription',
            title: '质检规则算法描述',
            align:'center'
        }, {
            field: 'groupRemark',
            title: '备注',
            align:'center'
        }, {
            field: 'gatewayName',
            title: '所属网关',
            align:'center',
            formatter:function (value,row,index) {
            	switch (row.gatewayName){
            	    case '限制回单规则网关':
            	    	 return '<img class="e-choose" src="../../images/xianzhihuidan-lan.png" alt="">'
                         break; 
            	    case '质检后驳回规则网关':
           	    	    return '<img class="e-choose" src="../../images/jinrurengongzhijian-lan.png" alt="">'
                        break;
            	    case '进入人工规则质检网关':
           	    	    return '<img class="e-choose" src="../../images/zhijianbohui-lan.png" alt="">'
                        break;
            	    case '进入人工复检规则网关':
           	    	    return '<img class="e-choose" src="../../images/jinrurengonggujian-lan.png" alt="">'
                        break;
            	}
               
            }
        }]
    });
}

//对应的函数进行判断；
function stateFormatter(value, row, index) {
    if (row.gatewayName === null){
	     return {
             disabled : false,//设置是否可用
             checked : false//设置选中
         };
    }else{
    	 return {
             disabled : true,//设置是否可用
             checked : true//设置选中
         };
    }
}


function toggleTable(gatewayId,ruleName) {

    $('#table').bootstrapTable('destroy');

    $("#table").bootstrapTable({
    	url: "../api/v1/rulegateway/getRules",
    	method: "post",
    	contentType: 'application/json; charset=UTF-8',
    	cache: false,
        classes:'table-no-bordered',  //表格的类名称。默认情况下，表格是有边框的
        striped:true,  //设置为 true 会有隔行变色效果。
        maintainSelected: true,          
        pageNumber:1,
        pagination:true,  // 在表格底部显示分页组件，默认false
        pageSize: 10,                     //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        sidePagination:'server',
        queryParams : function (params) {
            var temp = {
            	pageSize: params.limit, // 每页显示数量
                curPage: (params.offset / params.limit) + 1,   //当前页码
                gatewayId: gatewayId,
                ruleName: ruleName  
            };
            return JSON.stringify(temp);
        },
        onLoadSuccess : function(data) {
        	var tableLength = data.total;
            if( tableLength < 11 ){
            	$(".turnPAge").hide()
            }else{
            	$(".turnPAge").show()
            }
        },
        columns: [
        {
            field: 'ruleOrder',
            title: '规则编号',
            align:'center',
            formatter: function (value, row, index) {
        	    //获取每页显示的数量
        	    var pageSize=$('#table').bootstrapTable('getOptions').pageSize;  
        	    //获取当前是第几页  
        	    var pageNumber=$('#table').bootstrapTable('getOptions').pageNumber;
        	    //返回序号，注意index是从0开始的，所以要加上1
        	    return pageSize * (pageNumber - 1) + index + 1;
        	}

        }, {
            field: 'groupName',
            title: '质检内容',
            align:'center'
        }, {
            field: 'groupDescription',
            title: '质检规则算法描述',
            align:'center'
        }, {
            field: 'groupRemark',
            title: '备注',
            align:'center'
        }, {
            field: 'gatewayName',
            title: '所属网关',
            align:'center'
        }, {
            title: '排序',
            align:'center',
            formatter:function (value,row,index) {
                return '<img class="e-up" data-id="'+row.id +'" data-gatewayId="'+row.gatewayId +'" data-ruleOrder="'+row.ruleOrder +'" data-createTime="'+row.createTime +'" src="../../images/up.png" alt=""><img class="e-down" data-id="'+row.id +'" data-gatewayId="'+row.gatewayId +'" data-ruleOrder="'+row.ruleOrder +'" data-createTime="'+row.createTime +'" src="../../images/down.png" alt="">'
            }
        },{
        	field: 'status',
            title: '启停',
            align:'center',
            formatter : statusFormatter
        }, {
            title: '操作',
            align:'center',
            formatter:function (value,row,index) {
                return '<img class="e-delect" data-id="'+ row.id +'" data-gatewayId="'+ row.gatewayId +'" src="../../images/shanchu.png" alt="">'
            }
        }]
    });
}

function statusFormatter(value, row, index) {
   if (row.status === "enable"){
	   return '<span class="e-start e-cge" onClick="changeStatus(this)" data-id="'+row.id+'" data-status="'+row.status+'"><img src="../../images/qiyong.png" /></span>'
   }else{
	   return '<span class="e-end e-cge" onClick="changeStatus(this)" data-id="'+row.id+'" data-status="'+row.status+'"><img src="../../images/ting.png" /></span>'
   }
}

/*GO到第几页*/
function getVal(){
   var num = $(".turnPAge div input").val()*1;
   console.log(num);
    $('#table').bootstrapTable('selectPage', num);
    $(".turnPAge div input").val("");
}
 $(".turnPAge>div>span").click(function () {
     getVal();
});
 
function changeStatus(el){
	var id= $(el).attr("data-id");
	var status = $(el).attr("data-status");
	if(status == "enable"){
		status = "forbidden"
	}else{
		status = "enable"
	}
	var prames = {};
	prames.id = id;
	prames.status = status;
	$.ajax({
 		url: '../api/v1/rulegateway',
 		type: "put",
 		contentType: 'application/json; charset=UTF-8',
 		data: JSON.stringify(prames),
 		success:function(data){
 	        //title数据获取
 			$('#table').bootstrapTable('refresh');
 		},
 		error:function(){
 			console.log("数据出错了")
 		}
 	 })
}

function getVals(){
   var num = $(".turnPAge2 div input").val()*1;
    $('#table2').bootstrapTable('selectPage', num);
    $(".turnPAge2 div input").val("");
}
$(".turnPAge2>div>span").click(function () {
     getVals();
});

// 获取选中的数据   
function choose() {
    var row = $("#table").bootstrapTable('getSelections');
    console.log(row)
}

// 在全量数据中往规则添加数据
function addTable(el) {
	var gatewayName = $(el).text();
	var gatewayId = $(el).attr("data-id");
	var row = $("#table").bootstrapTable('getSelections');
	var newArr = [];
	// 筛选出gatewayId 为空的值  即为选中的值
	newArr = row.filter(checkAdult)
	if(newArr.length != 0 ){
		for(var i=0,iLength = newArr.length; i<iLength; i++){
			delete newArr[i]["0"]; 
			delete newArr[i]["ruleOrder"]; 
			newArr[i].status = "enable";
			newArr[i].gatewayId = gatewayId;
			newArr[i].gatewayName = gatewayName;
			/*var a= {};
			a.gatewayName = gatewayName;
			a.status = "enable";
			a.gatewayId = gatewayId;
			a.createTime = newArr[i].createTime;
			a.groupName = newArr[i].groupName;
			a.groupId = newArr[i].groupId;
			a.groupDescription = newArr[i].groupDescription;
			a.groupRemark = newArr[i].groupRemark;
			a.id = newArr[i].id;
			newArr.push(a)*/
		}
		
		$(".e-m-background").show();
		$(".e-g-tips").show(300);
		$(".e-g-false-right>p").text("是否将勾选的对象添加到"+gatewayName+"?");
		
		$(".e-g-false-right").append('<p class="e-add-rule">'+JSON.stringify(newArr)+'</p>')
		$(".e-g-sure").addClass("e-y-ref");	
	}else{
		$(".e-m-background").show();
		$(".e-g-tips").show(300);
		$(".e-g-false-right>p").text("请先勾选所要添加的规则");
		$(".e-g-sure").addClass("e-n-ref");
	} 
}

function checkAdult(row){
	return row.gatewayId === null
}