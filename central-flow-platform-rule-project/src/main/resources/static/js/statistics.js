function selectInput(demo){
    var $this=$(demo);
    var id = $this.attr("data-id");
    if(id == "all"){
        var params = {
            executeResult: "" ,
            simulationId:simulationId
        };
        tableList(params);
    }else if(id == "yes"){
        var params = {
            executeResult: true ,
            simulationId:simulationId
        };
        tableList(params);
    }else{
        var params = {
            executeResult: false ,
            simulationId:simulationId
        };
        tableList(params);
    }
    var parentDemo=$this.parents(".chooseCon");
    parentDemo.find("label").removeClass('selected');
    $this.addClass('selected');


    /*if($this.hasClass("all")){
        if(!$this.hasClass("selected")){
            parentDemo.find("label").addClass('selected')
            // 自己添加
            console.log("自己添加")
        }else{
            parentDemo.find("label").removeClass('selected')
            // 这里是同级选择的情况
            console.log("这里是同级选择的情况")
        }

    }else{
        if(!$this.hasClass("selected")){
            $this.addClass('selected');
            var num=parentDemo.find(".inputChecked").length;
            if(parentDemo.find("label.selected").length==num){
                parentDemo.find(".all").addClass('selected')
                // 这里是全部的情况
                console.log("这里是全部的情况")
            }else{
                console.log("自己添加")
            }
        }else{
            $this.removeClass('selected');
            parentDemo.find(".all").removeClass('selected')
            // 这里是同级选择的情况
            console.log("同级选择")
        }
    }*/
}

function PercentPie(option){
    this.backgroundColor = option.backgroundColor||'#fff';
    this.color           = option.color||['#38a8da','#d4effa'];
    this.fontSize        = option.fontSize||12;
    this.domEle          = option.domEle;
    this.value           = option.value;
    this.name            = option.name;
    this.title           = option.title;
}

PercentPie.prototype.init = function(){
    var _that = this;
    var option = {
        backgroundColor:_that.backgroundColor,
        color:_that.color,
        title: {
            text: _that.title,
//            top:'3%',
//            left:'1%',
            textStyle:{
                color: '#333',
                fontStyle: 'normal',
                fontWeight: 'normal',
                fontFamily: 'sans-serif',
                fontSize: 16,
            }
        },
        series: [{
            name: '来源',
            type: 'pie',
            radius: ['60%', '75%'],
            avoidLabelOverlap: false,
            hoverAnimation:false,
            label: {
                normal: {
                    show: true,
                    position: 'center',
                    textStyle: {
                        fontSize: '24',
                    }
                }
            },
            data: [{
                value: _that.value,
                name: _that.value+'%',
                label:{
                    normal:{
                        show:true
                    }
                }
            },
                {
                    value: 100-_that.value,
                    name: ''
                }
            ]
        }]
    };

    echarts.init(_that.domEle).setOption(option);
};


//折线图
function line(data1,data2){
    var myLine=echarts.init(document.getElementById("line"));
    option = {
        grid: {
            top: '10px',
            bottom: '15px',
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: data1
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: data2,
            type: 'line',
            areaStyle: {
                normal: {
                    color: '#cde0fb' //改变区域颜色
                }
            },
            itemStyle : {
                normal : {
                    color:'#cde0fb', //改变折线点的颜色
                    lineStyle:{
                        color:'#4582f1' //改变折线颜色
                    }
                }
            },
            symbol:'none', //去掉折线图中的节点
            smooth: true  //true 为平滑曲线，false为直线
        }]
    };
    myLine.setOption(option)
//    myLine.resize({height:'240px'});
}

var timer=new Date();
var nowTime=timer.getTime();
var simulationId = window.location.search.split("=")[1];
$(function(){
    var params = {
        executeResult: "" ,
        simulationId:simulationId
    };
    tableList(params);
    IEVersion();
    $.ajax({
        url: "../api/v1/simulation/statistics/getSimulationStatictisData?nowTime="+nowTime,
        type: "get",
        contentType: "application/json",
        async: false,
        data:{"simulationId":simulationId},
        success:function(data){
            //数据填充
            if(data.result == "success"){
                fill(data.data)
            }else{
                console.log("数据异常")
            }

        },
        error:function(error){

        }
    })

    $.ajax({
        url: "../api/v1/simulation/statistics/getAISimilarData?nowTime="+nowTime,
        type: "get",
        contentType: "application/json",
        async: false,
        data:{"simulationId":simulationId},
        success:function(data){
            if(data.result == "success" && !!data.data){
                var nowData = data.data;
                var data1 = [];
                var data2 = [];
                for (i in nowData){
                    data1.push(i)
                    data2.push(nowData[i])
                }
                line(data1,data2);
            }else{
            }
        },
        error:function(error){

        }
    })

    $(document).on("click",".layui-table-body table.layui-table tbody tr",function(){
        var obj = event ? event.target : event.srcElement;
        var tag = obj.tagName;
        var checkbox = $(this).find("td .e-sheetId");
        if(checkbox.length!=0){
            checkbox.click();
        }
    });

    $(document).on("click","td .e-sheetId",function(e){
        e.stopPropagation();
        var dataId = $(this).attr("data-id");
        window.location.href="../details?simulationId="+dataId;
    });
})
//数据填充
function fill(data) {
    if(!!data.passRate){
        $(".general").text(data.totalTask)
        $(".completed").text(data.complateTask)
        $(".speed").text(data.complatePercent)
        $(".coverage").text(data.coverRate)
        $(".gdNum").text(data.reCheckTask)
        $(".consuming").text(data.average)
        data.passRate = data.passRate.split("%")[0];
        var option1 = {
            value:data.passRate,//百分比,必填
            backgroundColor:null,
            color:["#80b0f9", "#ccc"],
            fontSize:24,
            domEle:document.getElementById("pie")//必填
        }
        percentPie1 = new PercentPie(option1);
        percentPie1.init();
    }else{
        var option1 = {
            value:0,//百分比,必填
            backgroundColor:null,
            color:["#80b0f9", "#ccc"],
            fontSize:24,
            domEle:document.getElementById("pie")//必填
        }
        percentPie1 = new PercentPie(option1);
        percentPie1.init();
    }

}

function tableList(data){
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#table',
            url: '../api/v1/simulation/statistics/getSheets?nowTime='+nowTime,
            where: data,
            method: 'post',
            async: false,
            contentType: 'application/json',
            request: {
                pageName: 'curPage', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            skin: 'nob', //表格风格
            even:  true,
            page: true,//是否显示分页
            /*limits: [3,5,10],*/
            limit: 10, //每页默认显示的数量
            parseData: function(res){ //res 即为原始返回的数据
                return {
                    "code": '0',
                    "count": res.total,
                    "data": res.rows
                };
            },
            cols: [[
                {field:'sheetId',title: '工单流水号', align:'center',templet:function(d){
                        return '<span class="e-sheetId" data-id="'+d.id+'">'+d.sheetId+'</span>'
                    }},
                {field:'sheetTitle', title: '工单主题',width:400,align:'center'},
                {field:'executeDuration', title: 'AI质检状态',align:'center',templet:function(d){
                        if(!!d.executeResult && d.executeResult == "true"){
                            return   '<img class="e-up" src="../images/tongguo.png" alt="">';
                        }else if(!!d.executeResult && d.executeResult == "false"){
                            return '<img class="e-up" src="../images/weitongguo.png""></img>';
                        }else if(d.executeResult =="exception"){
                            return '<img src="../images/yichang.png" style="width:20px;height:20px;"></img>'
                        }else{
                            return '';
                        }

                    }},
            ]] ,
            done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度

            }
        })
    })
}

/*GO到第几页*/
function getVal(){
    var num = $(".turnPAge div input").val()*1;
    $(".layui-laypage-skip .layui-input").val(num)
    $(".layui-laypage-skip .layui-laypage-btn").trigger("click")
}
$(".turnPAge>div>span").click(function () {
    getVal();
});

function IEVersion() {
    // 取得浏览器的userAgent字符串
    var userAgent = navigator.userAgent;
    // 判断是否为小于IE11的浏览器
    var isLessIE11 = userAgent.indexOf('compatible') > -1 && userAgent.indexOf('MSIE') > -1;
    // 判断是否为IE的Edge浏览器
    var isEdge = userAgent.indexOf('Edge') > -1 && !isLessIE11;
    // 判断是否为IE11浏览器
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf('rv:11.0') > -1;
    if (isLessIE11) {
        var IEReg = new RegExp('MSIE (\\d+\\.\\d+);');
        // 正则表达式匹配浏览器的userAgent字符串中MSIE后的数字部分，，这一步不可省略！！！
        IEReg.test(userAgent);
        // 取正则表达式中第一个小括号里匹配到的值
        var IEVersionNum = parseFloat(RegExp['$1']);
        if (IEVersionNum === 7) {
            // IE7
            return 7
        } else if (IEVersionNum === 8) {
            // IE8
            $("#tableCon .layui-laypage-skip").css({"margin-right":"0"});
            $("#tableCon .layui-table-header tr").css({"background":"#f2f2f2"});
            $("#tableCon .layui-table-body tr:odd").css({"background":"#f2f2f2"});
            return 8
        } else if (IEVersionNum === 9) {
            // IE9
            return 9
        } else if (IEVersionNum === 10) {
            // IE10
            return 10
        } else {
            // IE版本<7
            return 6
        }
    } else if (isEdge) {
        // edge
        return 'edge'
    } else if (isIE11) {
        // IE11
        return 11
    } else {
        // 不是ie浏览器
        return -1
    }
}