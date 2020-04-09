var timer=new Date();
var nowTime=timer.getTime();
//获取工单详情
function ordersDetail(params){
    $.ajax({
        url: "../api/v1/simulation/statistics/getSheetDetail?nowTime="+nowTime,
        type: "get",
        data:params,
        success:function(data){
            var inputData=data.sheetInput;
            formList(inputData);

            var outputData=data.sheetOutput;
            reformData(outputData);

            if(inputData.executeResult=="true"){
                $('#result').text("合格")
            }else if(inputData.executeResult=="false"){
                $('#result').text("不合格")
            }else if(inputData.executeResult=="exception"){
                $('#result').text("异常")
            }

        },
        error:function(error){

        }
    })
}
//form表单
function formList(formData){
    var formListArr=[];
    var childArr=[];
    var num=0;
    var arr=[];
    for(var key in formData){
        arr.push(key)
    };//为以先判断是否遍历到最后一个做准备
    for(var key in formData){
        num++;
        if(num==1){
            childArr=[{
                id:num,
                name:key,
                type:"P",
                value:formData[key],
                isNecessary:false
            }];
            formListArr.push(childArr);
            childArr=[];
        }else{
            if(key!="executeResult"){
                var obj={
                    id:num,
                    name:key,
                    type:"P",
                    value:formData[key],
                    isNecessary:false
                };
                if(childArr.length==0||childArr.length==1){
                    childArr.push(obj);
                    //Object.keys(formData)不兼容ie8
                    if(arr.length==num){
                        formListArr.push(childArr);
                        childArr=[];
                    }
                }else{
                    formListArr.push(childArr);
                    childArr=[];
                }

            }

        }

    }
    $("#formList").ulFill(formListArr);
    //不加延迟ie8下失效
    setTimeout(function(){
        var demo= $("#formList li")
        for(var i=0;i<demo.length;i++){
            var thisHeight=demo.eq(i).height();
            demo.eq(i).find("p").css({"height":thisHeight});
            demo.eq(i).find(".ieTitle .triangle").css({"position":"absolute","top":thisHeight/2-3+"px"});
        }
    },300)

}
//质检检测项重整数据
layui.config({
    base: '../js/module/'
}).extend({
    treetable: 'treetable-lay/treetable'
}).use(['treetable'], function () {
    var treetable = layui.treetable;

});
function reformData(datas){
    var newtableData=[];
    for(var i=0;i<datas.length;i++){
        datas[i].iconIf=false;//在treetable.js新加了属性，若是设置iconIf为false,则不显示三角小图标等，若不设置，则为默认显示
        if(datas[i].pId!=0&&datas[i].inputParamCnname){
            var paramValue=datas[i].inputParamValue!=null?datas[i].inputParamValue:"无";
            datas[i].returnString="<em data-identification='"+datas[i].pId+"'>"+datas[i].inputParamCnname+": "+paramValue+"</em>";
            //因为在treetable.js中有对展开收缩节点的回调，所以通过以往配置表格的回调对该字段无效
            //所以在treetable.js中新加了属性判断，若是配置有returnString，则返回该字符串，不配置则为默认字符串
        }else{
            datas[i].returnString="<em data-identification='"+datas[i].id+"'>"+datas[i].groupDescription+"</em>";
        }
        newtableData.push(datas[i]);


        layui.use('treetable', function(){
            var table = layui.treetable;
            table.render({
                elem: '#tableList',
                treeSpid: 0,//最上级的pid
                treeColIndex:2, //树形图标显示在第几列
                skin: 'line', //表格风格
                size:"sm",
                data: newtableData,
                treeIdName:"id",// 	id字段的名称
                treePidName:"pId",// 	pid字段的名称
                treeDefaultClose: true,     //是否默认折叠
                page: true ,//是否显示分页
                even:true,
                cols: [[
                    {field:'index',title: '序号', align:'center',width:100,templet:function(d){
                            if(d.index==0||d.index){
                                return d.index+1
                            }else{
                                return  ""
                            }
                        }},
                    {field:'gatewayName', title: '所属网关',align:'center',templet:function(d){
                            if(d.gatewayName){
                                return d.gatewayName
                            }else{
                                return  ""
                            }
                        }},
                    {field:'groupDescription', title: '规则描述',align:'center'},
                    {field:'executeDuration', title: '质检时间',align:'center',templet:function(d){
                            if(d.executeDuration){
                                return (d.executeDuration/1000).toFixed(2)+"s"
                            }else{
                                return  ""
                            }
                        }},

                    {field:'simulationResult', title: '是否通过', align:'center',width:100,templet:function(d){
                            if(d.simulationResult){
                                if(d.simulationResult=="true"){
                                    return '<img src="../images/tongguo.png" style="width:20px;height:20px;"></img>'
                                }else if(d.simulationResult=="false"){
                                    return '<img src="../images/weitongguo.png" style="width:20px;height:20px;"></img>'
                                }else if(d.simulationResult=="exception"){
                                    return '<img src="../images/yichang.png" style="width:20px;height:20px;"></img>'
                                }else{
                                    return d.simulationResult;
                                }
                            }else{
                                return  ""
                            }
                        }}

                ]] ,
                done:function(){
                    $("span[lay-ttype='dir']").parents("tr").addClass("parentsTr");
                    IEVersion();
                    $('#myTable table tr').on('mouseover',function(){
                        var thisDemo = $(this).find('em');
                        var demoIdent = thisDemo.attr("data-identification");
                        $("em[data-identification='" + demoIdent + "']").parents("tr").addClass("newOpenTr");
                   });
                    $('#myTable table tr').on('mouseout',function(){
                        $(".newOpenTr").removeClass("newOpenTr");
                    });
                }
            })
        })

    }
}

$(function(){
    var obj={
        "simulationDataId":window.location.search.split("=")[1]
    };
    ordersDetail(obj)
})

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
            $("#myTable .layui-table-header tr").css({"background":"#f2f2f2"});
            $("#myTable  .layui-table-body tr:odd").css({"background":"#f2f2f2"});
            $("#formCon .eoms-ul > LI > DIV > P.content").css({"padding-left":"10px","line-height":"35px"});
            $("#formCon .eoms-ul > LI > DIV > P.title").addClass("ieTitle");
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