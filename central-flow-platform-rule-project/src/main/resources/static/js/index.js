var timer=new Date();
var nowTime=timer.getTime();
$(function(){
    gateway();
    city();
    typeList(".typeList",{});
});
var simulationIdGetData="";//获取数据执行仿真的id
var simulationIdDownLoad="";//下载数据模板执行仿真的id
//网关选择功能
function selectInput(demo){
    var $this=$(demo);
    if($this.attr("ifCan")=="true"){
        var parentDemo=$this.parents(".checkedCon");
        if($this.hasClass("all")){
            if(!$this.hasClass("selected")){
                parentDemo.find("label").addClass('selected')
            }else{
                parentDemo.find("label").removeClass('selected')
            }

        }else{
            if(!$this.hasClass("selected")){
                $this.addClass('selected');
                var num=parentDemo.find(".inputChecked").length;
                if(parentDemo.find("label.selected").length==num){
                    parentDemo.find(".all").addClass('selected')
                }
            }else{
                $this.removeClass('selected');
                parentDemo.find(".all").removeClass('selected')
            }
        }
    }


}

//打开页面
function fangzhenShow(){
    $("#fangzhenCon").show();
    complateHeight();
}
//关闭页面
function closePage(){
    $("#fangzhenCon").hide();
    window.history.go(0);
}

/*网关查询*/
function gateway(){
    $.ajax({
        url: "../api/v1/gateway/getGateway?nowTime="+nowTime,
        type: "get",
        contentType: 'application/json; charset=UTF-8',
        success:function(data){
            var str='';
            for(var i=data.length;i>0;i--) {
                var thisIndex=Math.abs(i-data.length);
                var thisData = data[thisIndex];
                if(thisIndex == 0){
                    str+=' <p>\n' +
                        '                        <input type="checkbox"/>\n' +
                        '                        <label class="all selected" ifCan="true"  id="null" onClick="selectInput(this)" ></label>\n' +
                        '                        <span>全部</span>\n' +
                        '                    </p>';
                }else if(thisIndex<5){
                    var thisClass="";
                    if(thisIndex==4){
                        thisClass="lastType";
                    }
                    str+=' <p class="'+thisClass+'">\n' +
                        '                     <input type="checkbox" />\n' +
                        '                        <label class="inputChecked selected" ifCan="true" id="'+thisData.id+'" onClick="selectInput(this)" ></label>\n' +
                        '                        <span>'+thisData.gatewayName+'</span>\n' +
                        '                    </p>'
                }

            }
            $(".fangzhenBody .checkedCon").html(str);
            $(".downLoadTemplateBody .checkedCon").html(str);

        },
        error:function(error){
        }
    })

};
//网络工单接入
function typeList(demo,params){
    $.ajax({
        url: "../api/v1/simulation/getDicts?nowTime="+nowTime,
        type: "get",
        data:params,
        success:function(data){
            var thisDatas=data.data;
            var str='';
            for(var i=thisDatas.length;i>0;i--) {
                var thisIndex = Math.abs(i - thisDatas.length);
                var thisData = thisDatas[thisIndex];
                str+='  <li onClick="selectLi(this)" dictId="'+thisData.dictId+'">'+thisData.dictName+'</li>'
            }
            $(demo).html(str);
            $(".item").perfectScrollbar();

        },
        error:function(error){

        }
    })
}
//地市接入
function city(){
    $.ajax({
        url: "../api/v1/simulation/getCitys?nowTime="+nowTime,
        type: "get",
        success:function(data){
            var thisDatas=data.data;
            var str='';
            for(var i=thisDatas.length;i>0;i--) {
                var thisIndex = Math.abs(i - thisDatas.length);
                var thisData = thisDatas[thisIndex];
                str+='  <li onClick="selectLi(this)" areaId="'+thisData.areaId+'">'+thisData.areaName+'</li>'
            }
            $(".cityList").html(str);
            $(".item").perfectScrollbar()

        },
        error:function(error){

        }
    })
}
//离开下拉框事件
function leaveList(demo){
    $(demo).hide();
}
layui.use('laydate', function() {
    var laydate = layui.laydate;
    var options={
        elem: '#endTime',
        type: 'datetime',
        trigger: 'click',
        theme: '#80b0f9',
        btns: ['clear', 'confirm']
    };
    var startDate = laydate.render({
        elem:'#startTime',
        type: 'datetime',
        min:'1999-1-1',
        max:'2111-1-1',
        theme: '#80b0f9',
        trigger: 'click',
        btns: ['clear', 'confirm'],
        done: function (value, date) {
            if(value.length==0){
                $("#endTime").val("");
                $("#endTime").addClass("noSelectTime");
                $("#endTime").attr("disabled","disabled");
            }else{
                $(".noSelectTime").removeClass("noSelectTime");
                $("#endTime").removeAttr("disabled");
                var year=date.year;
                var month=date.month<10?"0"+date.month:date.month;
                var date1=date.date;
                var endTime=date.year+"-"+month+"-"+date.date+" 23:59:59";
                 options={
                    elem: '#endTime',
                    type: 'datetime',
                    min: value,
                    max: endTime,
                    value:endTime,
                    trigger: 'click',
                    theme: '#80b0f9',
                    btns: ['clear', 'confirm']
                };
                laydate.render(options);
                endDate.config.min ={
                    year:date.year,
                    month:date.month-1,
                    date: date.date,
                    hours: date.hours,
                    minutes: date.minutes,
                    seconds: date.seconds
                };
                endDate.config.max={
                    year:date.year,
                    month:date.month-1,
                    date: date.date,
                    hours: 23,
                    minutes: 59,
                    seconds: 59
                };

            }


        }
    });

    var endDate = laydate.render(options);
});




function chooseFiles(demo){
    $("#tipCon").hide();
    var fileName=$(demo).val().substring($(demo).val().lastIndexOf("\\")+1);
    var fileType=fileName.substring(fileName.lastIndexOf(".")+1);

    if(fileType=="xlsx"){
        $("#fileName").val(fileName);
    }else{
        layer.open({
            title: '提示'
            ,content: '请选择excel文件'
        });

    }

}

/*数据获取中*/
function getData(){
    layui.use('form', function() {
        var form = layui.form;
        //监听提交
        form.on('submit(formSubmit)', function (data) {

            var gateWayDemo=$(".fangzhenBody .checkedCon>p");
            var arr=[];
            for(var i=gateWayDemo.length;i>0;i--){
                if(gateWayDemo.eq(i).find(".inputChecked").hasClass("selected")){
                    var thisId=gateWayDemo.eq(i).find(".inputChecked").attr("id");
                    arr.push(thisId)
                }
            };
            var newArr=arr.reverse();
            var params={
                "city": $("#cityCon").attr("areaId"),
                "cityName": data.field.city,
                "gateWayIds": newArr,
                "networkSheetOneName": data.field.type,
                "networkSheetOne": $("#typeCon").attr("dictId"),
                "networkSheetTwoName":data.field.secondType,
                "networkSheetTwo": $("#secondCon").attr("dictId"),
                "sheetEndTime": data.field.endTime,
                "sheetStartTime": data.field.startTime
            };
            if(newArr.length>0){
                $.ajax({
                    type: "post",
                    url: "../api/v1/simulation/getSimulationData?nowTime="+nowTime,
                    contentType: 'application/json; charset=UTF-8',
                    data:JSON.stringify(params),
                    dataType:"json",
                    success: function(data){
                        if(data.result=="success"){
                            simulationIdGetData=data.data.simulationId;
                            dataProgess(data.data.simulationId);
                        }else{
                            layer.open({
                                title: '提示',
                                content: data.message
                            });
                        }

                    }
                });
            }else{
                layer.open({
                    title: '提示'
                    ,content: '请选择网关'
                });
            }

            return false;
        });

    });


}
//       获取仿真数据进度
var clearDataProgess;
function dataProgess(simulationId){
    $.ajax({
        type: "get",
        url: "../api/v1/simulation/getSimulationDataProgess?nowTime="+nowTime,
        data: {"simulationId":simulationId},
        asyc:false,
        success: function(data){
                 var simulationProgess=data.simulationProgess;//进度百分数
                $("#btns").css({"visibility":"hidden"});
                $("#progressBar").show();
                $("#barOuter .barInter").css({"width":parseInt(simulationProgess*100)+"%"});
                $("#barOuter .barInter .rate").text(parseInt(simulationProgess*100)+"%");
                clearInterval(clearDataProgess);
                if(simulationProgess==1){
                    $("#progressBar").hide();
                    $("#tipCon1").show();
                    $("#btns").css({"visibility": "visible"});
                    $("#btns .getDataBtn").hide();
                    $("#btns .implement").show();
                }else{
                    clearDataProgess=setInterval(dataProgess(simulationId),800);
                };
                $(".fangzhenBody .checkedCon label").attr({"ifCan":"false"});
        }
    });


}


//列表展开
function showList(demo){
    if( !$(demo).hasClass("noSelected")){
        $(demo).siblings(".list").show();
    }
}

// 下拉框功能
function selectLi(demo){
    var $this=$(demo);
    $this.parent().siblings("input").val($this.text());
    if($this.attr("dictId")){
        $this.parent().siblings("input").attr("dictId",$this.attr("dictId"))
    }else{
        $this.parent().siblings("input").attr("areaId",$this.attr("areaId"))
    }
    $this.parent().find("li").removeClass("selected");
    $this.addClass("selected");
    $this.parent().hide();
    if($this.parent().hasClass("typeList")){
        $("#secondCon").val("");
        typeList(".secondList",{"parentDictId":$this.attr("dictId")});
        $(".noSelected").removeClass("noSelected")
    }

}

//上传表单
function submitForm(){
    var gateWayDemo=$(".downLoadTemplateBody .checkedCon>p");
    var arr=[];
    for(var i=gateWayDemo.length;i>0;i--){
        if(gateWayDemo.eq(i).find(".inputChecked").hasClass("selected")){
            var thisId=gateWayDemo.eq(i).find(".inputChecked").attr("id");
            arr.push(thisId)
        }
    };
    var newArr=arr.reverse();
    options = {
        type: 'POST',     // 设置表单提交方式
        url: "../api/v1/simulation/upload?nowTime="+nowTime,    // 设置表单提交URL,默认为表单Form上action的路径
        dataType: 'text',// 返回数据类型
        cache:false,
        ansyc:true,
        data:{"gatewayIds":newArr.join()},
        success: function(responseText, statusText, xhr, $form){    // 成功后的回调函数(返回数据由responseText获得)
            if(JSON.parse(responseText).result=="success"){
               simulationIdDownLoad=JSON.parse(responseText).simulationId;
               $("#tipCon").show();
               $("#btnsDownLoad .confirm").hide();
               $("#btnsDownLoad .implement").show();
               $(".downLoadTemplateBody .checkedCon label").attr({"ifCan":"false"});
           }else if(JSON.parse(responseText).result=="fail"){
               layer.open({
                   title: '提示',
                   content: JSON.parse(responseText).message
               });
           }


        },
        error: function(xhr, status, err) {
            layer.open({
                title: '提示',
                content: '操作失败'
            });    // 访问地址失败，或发生异常没有正常返回
        }
    };

}

var options={};
$("#downLoadForm").submit(function(){
    $(this).ajaxSubmit(options);
    return false;
});

/*确定提交模板*/
function confirm(){
    var ifSelected=$(".downLoadTemplateBody .selected").length>0?true:false;
    var fieldIf=$("#fileName").val().length>0?true:false;
    if(ifSelected){
        if(fieldIf){
            submitForm();//获取当前最新参数
            $("#downLoadForm").submit()

        }else{
            layer.open({
                title: '提示',
                content: '请上传附件'
            });
        }

    }else{
        layer.open({
            title: '提示',
            content: '请选择网关'
        });

    }

}

/*下载模板*/
function downLoad(){
    var gateWayDemo=$(".downLoadTemplateBody .checkedCon>p");
    var arr=[];
    for(var i=gateWayDemo.length;i>0;i--){
        if(gateWayDemo.eq(i).find(".inputChecked").hasClass("selected")){
            var thisId=gateWayDemo.eq(i).find(".inputChecked").attr("id");
            arr.push(thisId)
        }
    };
    var newArr=arr.reverse();
    var param=({'gatewayIds':newArr.join()});
    $("#downLoadImg").attr({"href":"javaScript:;"});
    if(newArr.length>0){
        $("#downLoadImg").attr({"href":"../api/v1/simulation/download?gatewayIds="+newArr.join()});
    }else{
        layer.open({
            title: '提示'
            ,content: '请选择网关'
        });
    }



}




/*仿真执行*/
var flag=true;//代表是通过获取仿真数据执行仿真,初始值
function implement(demo){
    var arr=[];
    if($(demo).hasClass("getData")){
        flag=true;//代表是通过获取仿真数据执行仿真
        var gateWayDemo=$(".fangzhenBody .checkedCon>p");
        for(var i=gateWayDemo.length;i>0;i--){
            if(gateWayDemo.eq(i).find(".inputChecked").hasClass("selected")){
                var thisId=gateWayDemo.eq(i).find(".inputChecked").attr("id");
                var thisName=gateWayDemo.eq(i).find("span").text();
                var obj={"gateWayId":thisId,"gateWayName":thisName}
                arr.push(obj)
            }
        };

    }else if($(demo).hasClass("downLoad")){
        flag=false;//代表是通过下载数据模板执行仿真
        var gateWayDemo=$(".downLoadTemplateBody .checkedCon>p");
        for(var i=gateWayDemo.length;i>0;i--){
            if(gateWayDemo.eq(i).find(".inputChecked").hasClass("selected")){
                var thisId=gateWayDemo.eq(i).find(".inputChecked").attr("id");
                var thisName=gateWayDemo.eq(i).find("span").text();
                var obj={"gateWayId":thisId,"gateWayName":thisName}
                arr.push(obj)
            }
        };

    };
    var str='';
    var newArr=arr.reverse();
    for(var i=newArr.length;i>0;i--){
        var thisIndex=Math.abs(i-newArr.length);
        var imgStr='';
        switch(newArr[thisIndex].gateWayName){
            case "限制回单规则网关":
                imgStr= ' <img src="../images/bigIcon4.png"/>\n' ;
                break;
            case "进入人工质检规则网关":
                imgStr= ' <img src="../images/jinrurengong-xiao.png"/>\n' ;
                break;
            case "质检后驳回规则网关":
                imgStr= ' <img src="../images/bigIcon.png"/>\n' ;
                break;
            case "进入人工复检规则网关":
                imgStr= ' <img src="../images/bigIcon2.png"/>\n' ;
                break;
            default:
                imgStr= ' <img src="../images/bigIcon2.png"/>\n' ;
                break;
        };
        if(thisIndex==newArr.length-1){
            var thisClass='lastType'
        }else{
            var thisClass=''
        }
        str+=' <p class="'+thisClass+'">\n' +imgStr+

            '                    <br/>\n' +
            '                    <span class="title" id="'+newArr[thisIndex].gateWayId+'">'+newArr[thisIndex].gateWayName+'</span>\n' +
            '                    <br/>\n' +
            '                    <span class="progress">待仿真</span>\n' +
            '                </p>';
    }
    $("#fangzhenFlow").html(str);
    //执行仿真上半部分

    //以下时执行仿真进度条计算
    var demos=$("#fangzhenFlow>p");
    var gateWayId=demos.eq(0).find(".title").attr("id");
    var gateWayName=demos.eq(0).find(".title").text()
    if(flag){
        var simulationId=simulationIdGetData;
    }else{
        var simulationId=simulationIdDownLoad;
    };
    var obj={
        "gatewayId":gateWayId,
        "gatewayName":gateWayName,
        "simulationId":simulationId,
        "isLastGateWay":"false"
    };
    if(demos.length==1){
        obj["isLastGateWay"]="true";
    }
    startBtn(obj);
    /*}*/

    //$("#barOuter1").myProgress({speed: 4000, percent: 100,width: "100%",height:"10px"});
    //执行仿真进行


}

//仿真执行进度
function startBtn(param){
    //index为当前正在执行的仿真索引
    //条件仿真规则 （若不触发，之后调取进度会一直为0）
    //param包括网关id和仿真id还有网关名称
    $.ajax({
        type: "post",
        url: "../api/v1/simulation/excuteRule?nowTime="+nowTime,
        data: param,
        async:true,
        success: function(data){

        }
    });
    delete param["gatewayName"];
    delete param["isLastGateWay"];
    fangZhenProgess(param);
}

var clearFangZhenProgess;
function fangZhenProgess(param){
    //index为当前正在执行的仿真索引
    //param包括网关id和仿真id
    var len=$("#fangzhenFlow>p").length;//执行网关的个数
    $.ajax({
        type: "get",
        url: "../api/v1/simulation/simulationExecuteProgess?nowTime="+nowTime,
        contentType: 'application/json; charset=UTF-8',
        data:param,
        async:false,
        success: function(data){
            var simulationExecuteProgess=data.simulationExecuteProgess;//进度百分数
                $("#fangzhenFrame").hide();
                $("#implementCon").show();
                 complateHeight();
                //$(".finish").length为除当前执行网关之前执行完成的个数
                var finishNum=$(".finish").length>0?$(".finish").length:0;
                var nowRate=parseInt(100/len*finishNum+simulationExecuteProgess*100/len);//网关执行的百分比
                $("#barOuter1 .barInter").css({"width":nowRate+"%"});
                $("#barOuter1 .barInter .rate").text(nowRate+"%")
                if(nowRate==100){
                    if(flag){
                        window.location.href="../statistics?simulationId="+simulationIdGetData;
                    }else{
                        window.location.href="../statistics?simulationId="+simulationIdDownLoad;
                    };


                };
                clearInterval(clearFangZhenProgess);
                if(simulationExecuteProgess==1){
                    $("#fangzhenFlow>p").eq(finishNum).find(".progress").addClass("finish");
                    $("#fangzhenFlow>p").eq(finishNum).find(".progress").text("仿真完成");
                    var newFinishNum=$(".finish").length>0?$(".finish").length:0;
                    if(len>newFinishNum){
                        //说明并未全部执行完
                        var nowIndex=newFinishNum//下一个要执行的网关索引(已经完成的网关个数)
                        var demos=$("#fangzhenFlow>p");
                        var gateWayId=demos.eq(nowIndex).find(".title").attr("id");
                        var gateWayName=demos.eq(nowIndex).find(".title").text()
                        if(flag){
                            var simulationId=simulationIdGetData;
                        }else{
                            var simulationId=simulationIdDownLoad;
                        };
                        var obj={
                            "gatewayId":gateWayId,
                            "gatewayName":gateWayName,
                            "simulationId":simulationId,
                            "isLastGateWay":"false"
                        };
                        if(nowIndex==len-1){
                            obj["isLastGateWay"]="true";
                        }
                        startBtn(obj);
                    }
                }else{
                    var nowIndex=finishNum;//下一个要执行的网关索引及完成的个数
                    $("#fangzhenFlow>p").eq(nowIndex).find(".progress").removeClass("doing");
                    $("#fangzhenFlow>p").eq(nowIndex).find(".progress").addClass("doing");
                    $("#fangzhenFlow>p").eq(nowIndex).find(".progress").text("仿真中...");

                    clearFangZhenProgess=setInterval(function(){
                        fangZhenProgess(param)
                    },800)
                }


        }
    });
}
function complateHeight(){
    var documentWidth= document.documentElement.clientWidth || document.body.clientWidth;
    var documentHeight= document.documentElement.clientHeight || document.body.clientHeight;
    var fangzhenConHeight=$("#fangzhenFrame").height();
    var implementConHeight=$("#implementCon").height();
    $("#fangzhenFrame").css({"position":"absolute","top":(documentHeight-fangzhenConHeight)/2,"left":(documentWidth-736)/2});
    $("#implementCon").css({"position":"absolute","top":(documentHeight-implementConHeight)/2,"left":(documentWidth-736)/2});
}


function sPage(num){
    if(num == 4){
        window.location.href="../commonPage?choose=4";
    }else if(num == 1){
        window.location.href="../commonPage?choose=1";
    }else if(num == 2){
        window.location.href="../commonPage?choose=2";
    }else if(num == 3){
        window.location.href="../commonPage?choose=3";
    }else{
        window.location.href="../commonPage?choose=0";
    }
}
function tagSelect(demo){
    $(demo).addClass("selected").siblings().removeClass("selected");
    if($(demo).text()=="获取仿真数据"){
        $(".tagBodySelected").removeClass("tagBodySelected");
        $(".fangzhenBody").addClass("tagBodySelected");

    }else if($(demo).text()=="下载数据模板"){
        $(".tagBodySelected").removeClass("tagBodySelected");
        $(".downLoadTemplateBody").addClass("tagBodySelected");
    }
    complateHeight();
}



//以下代码是原有代码
   $(function(){
        	 $.ajax({
     			url: "../api/v1/gateway/getGateway",
     			type: "get",
     			async: true,
     			success:function(data){
     		        //title数据获取
     				for(i in data){
     					switch (i){
     					case '1':
     						if(data[i].status == 'enable'){
     							$("#onoffswitch").append('<img src="../../images/changeClose.png" />')
     						}else{
     							$("#onoffswitch").append('<img src="../../images/changeOne.png" />')
     						}
     						$("#onoffswitch").attr("data-id",data[i].id)
     						$("#onoffswitch").attr("data-status",data[i].status)
     						break;
     					case '2':
     						if(data[i].status == 'enable'){
     							$("#onoffswitch1").append('<img src="../../images/changeClose.png" />')
     						}else{
     							$("#onoffswitch1").append('<img src="../../images/changeOne.png" />')
     						}
     						$("#onoffswitch1").attr("data-id",data[i].id)
     						$("#onoffswitch1").attr("data-status",data[i].status)
     						break;
     					case '3':
     						if(data[i].status == 'enable'){
     							$("#onoffswitch2").append('<img src="../../images/changeClose.png" />')
     						}else{
     							$("#onoffswitch2").append('<img src="../../images/changeOne.png" />')
     						}
     						$("#onoffswitch2").attr("data-id",data[i].id)
     						$("#onoffswitch2").attr("data-status",data[i].status)
     						break;
     					case '4':
     						if(data[i].status == 'enable'){
     							$("#onoffswitch3").append('<img src="../../images/changeClose.png" />')
     						}else{
     							$("#onoffswitch3").append('<img src="../../images/changeOne.png" />')
     						}
     						$("#onoffswitch3").attr("data-id",data[i].id)
     						$("#onoffswitch3").attr("data-status",data[i].status)
     						break;
     					}
     				}
     			},
     			error:function(){
     				console.log("数据出错了")
     			}
     		})

     		$(".testswitch").click(function () {
            	var id = $(this).attr("data-id");
            	var status = $(this).attr("data-status");
            	if(status == "enable"){
            		status = "forbidden";
            	}else{
            		status = "enable";
            	}
            	var _el = $(this);
            	var prames = {};
            	prames.id = id;
            	prames.status = status;
            	$.ajax({
             		url: '../api/v1/gateway',
             		type: "put",
             		contentType: 'application/json; charset=UTF-8',
             		async: true,
             		data: JSON.stringify(prames),
             		success:function(data){
             			if(status == "forbidden"){
             				_el.find("img").attr("src","../../images/changeOne.png");
                    		_el.attr("data-status","forbidden");

                    	}else{
                    		_el.find("img").attr("src","../../images/changeClose.png");
                    		_el.attr("data-status","enable");
                    	}
             		},
             		error:function(){
             			console.log("数据出错了")
             		}
             	 })
             	return false
            })
        })
