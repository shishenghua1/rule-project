
$( function() {	
	// 总量数据获取
	tableNum();
	// 表格数据获取   初始的时候是总数据，切换的时候根据id来区分,  显示的页数
	//tableDate("",1)
	//启用表格
	initTable();
	
	// 添加显示
	$(".e-add>img").click(function(){
		$(this).next().fadeIn(300);
	})

	// 启停设置
	$("#table").on("click", ".e-cge", function () {
		var id= $(this).attr("data-id");
		var status = $(this).attr("data-status");
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
    	$.ajax({
			url: "../api/v1/rulegateway",
			type: "POST",
			contentType: 'application/json; charset=UTF-8',
			data: row,
			success:function(data){
				//刷新当前页
		    	location.reload() 
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
    	$(this).removeClass("e-n-ref");
    	$(".e-g-false-right").find(".e-add-rule").remove();
    	$(".e-g-tips").fadeOut(300);
    	$(".e-m-background").fadeOut(300);
    	$(".e-add-list").fadeOut(300);
    })	
});

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
	location.reload();
	//修改title
	/*var tableTitle = $(el).find(".ui-header").text();
	$(".e-g-resources-head").text(tableTitle)
	
	$(el).siblings().removeClass('active');
	$(el).addClass('active');

	$(".e-form>.e-form-right").show(300);
	$(".e-form-left").removeClass("e-son-ser")
	initTable();*/
}

function searchTable(){
	if($(".e-form-left").hasClass("e-son-ser")){
		var gatewayId = $("#cart>.active").attr("data-id");
		var ruleName = $(".e-form-left>input").val().trim();
		toggleTable(gatewayId,ruleName)
	}else{
		var ruleName = $(".e-form-left>input").val().trim();
		var chooseValue = $('input:radio:checked').val();

		if(chooseValue == "未选"){
			initTable('true',ruleName)
		}else{
			//筛选全选数据
			initTable('',ruleName)
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
            
            $( "#table" ).accordion();
            $( "#table" ).find("tbody>tr").draggable({
                containment: "window",  //约束在指定元素或区域的边界内拖拽。可能的值："parent"、"document"、"window"。
                cursor: "move",  //拖拽操作期间的 CSS 光标。
                helper:"clone",   //允许一个 helper 元素用于拖拽显示。 original
                appendTo: "body"
            });
            
            goDrag("#limitRule");
            goDrag("#inRule");
            goDrag("#qulityRule");
            goDrag("#checkRule");
            
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

//拖拽位置
function goDrag(id){
	$(id).droppable({
        activeClass: "ui-state-default",
        hoverClass: "ui-state-hover",
        accept: ":not(.ui-sortable-helper)",
        drop: function( event, ui ) {
        	addTable(id)
        }
    }).sortable({
        items: "li:not(.placeholder)",
        sort: function() {
          // 获取由 droppable 与 sortable 交互而加入的条目
          // 使用 connectWithSortable 可以解决这个问题，但不允许您自定义 active/hoverClass 选项
          $( this ).removeClass( "ui-state-default" );
        }
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

function statusFormatter(value, row, index) {
   if (row.status === "enable"){
	   return '<span class="e-start e-cge" data-id="'+row.id+'" data-status="'+row.status+'"><img src="../../images/qiyong.png" /></span>'
   }else{
	   return '<span class="e-end e-cge" data-id="'+row.id+'" data-status="'+row.status+'"><img src="../../images/ting.png" /></span>'
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
         
            $( "#table" ).find("tbody").sortable({
            	update:function(event, ui){ //移动的时候并且排序发生变化的时候执行的方法
            		 // 分页后需要把当前页加上
                    var pages = $(".e-g-resources-contract").find(".pagination>.active>a").text() - 1;
                    var idx = pages*10;
                    var arr = [];
                    
                    var tlg = $("#table").find("tbody>tr").length;
                    for(var i = 0; i<tlg; i++){
                    	var prames = {};
                    	prames.id = $("#table").find("tbody>tr").eq(i).find(".e-up").attr("data-id");
                    	prames.ruleOrder = idx+i+1;
                    	arr.push(prames)
                    }
                    $.ajax({
          	     		url: '../api/v1/rulegateway/changeRuleOrder',
          	     		type: "put",
          	     		contentType: 'application/json; charset=UTF-8',
          	     		data: JSON.stringify(arr),
          	     		success:function(data){
          	     	        //title数据获取    把 arr置空
          	     		},
          	     		error:function(){
          	     			console.log("数据出错了")
          	     		}
          	     	})
            	},
            	
            }).disableSelection();
/*            $( "#table" ).find("tbody").sortable();
        	$( "#table" ).find("tbody").disableSelection();*/
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
	var gatewayName = $(el).find(".ui-header").text();
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