var timer=new Date();
var nowTime=timer.getTime();
var nowDateTime=timer.toLocaleString( );        //获取日期与时间
var startTime="",endTime="";
$(function(){
    var param={
              "simulationEndTime": endTime,
              "simulationStartTime": startTime
    };
    tableList(param);
})
//查询
function search(){
    var searchTime=$("#dateTime").val();
    if(searchTime.length>0){
        startTime=searchTime.substring(0,searchTime.indexOf("~"));
        endTime=searchTime.substring(searchTime.indexOf("~")+1);
        var param={
            "simulationEndTime": endTime,
            "simulationStartTime": startTime
        };
        tableList(param);
    };

}
//取消
function cancel(){

}
//获取仿真记录数据
function tableList(param){
    layui.use('table', function(){
        var table = layui.table;
        var laypage=layui.laypage;
        table.render({
            elem: '#table',
            url: '../api/v1/simulation/statistics/getSimulations?nowTime='+nowTime,
            where: param,
            method: 'post',
            contentType: 'application/json',
            skin: 'line', //表格风格
            page: true ,//是否显示分页
            even:true,
            limit: 10, //每页默认显示的数量
        /*    layout:['prev', 'page', 'next'],//未生效*/
            request: {
                pageName: 'curPage', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            parseData: function(res){ //res 即为原始返回的数据
                return {
                    "code": '0',
                    "count": res.total,
                    "data": res.rows
                };
            },
            done: function(){
                //obj包含了当前分页的所有参数
                $("#tableCon .layui-table-header tr").css({"background":"#f2f2f2"});
                $("#tableCon .layui-table-body tr:odd").css({"background":"#f2f2f2"});
            },
            cols: [[
                {field:'operator',title: '执行仿真人', align:'center'},
                {field:'operationTime', title: '执行仿真时间',width:380,align:'center'},
                {field:'id', title: '操作',align:'center',templet:function(d){
                        return  '<img class="e-up active" id="'+d.id+'"  onClick="goSkip(this)"  src="../../images/fangdajing.png" alt="">';
                    }},


            ]]
        });

    });
};

layui.use('laydate', function() {
    var laydate = layui.laydate;
    laydate.render({
        elem: '#dateTime',
        type: 'datetime',
        max:nowDateTime,
        range: '~',
        theme: '#80b0f9',
        trigger: 'click',
        btns: ['clear', 'confirm']
    })
})
$(function(){
    IEVersion();
})
function goSkip(demo){
    var simulationId=$(demo).attr("id");
    window.location.href="../statistics?simulationId="+simulationId;
}
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