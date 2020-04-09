(function($) {
    $.fn.extend({
        ulFill: function(data,appId,appName) {
            var $this = $(this);
            var newArray=[];
            var thirdSelectMap=[];
            var myMethods = {
                linkageEvent: function() {
                    $this.find('.e-g-linkage>div').on('click', function() {
                        var linkageId=$(this).parent().attr("id");
                        var roleRootId=$(this).parent().attr("roleRootId");
                        var deptRootId=$(this).parent().attr("deptRootId");
                        $(this).parents().find(".e-g-row").after('<iframe name="'+linkageId+'" id="'+linkageId+'" class="eomsIinkage" src="showDealperformChoiceTree?roleRootId='+roleRootId+'&deptRootId='+deptRootId+'" frameborder="0" align="center" scrolling="no"><p>你的浏览器不支持iframe标签</p></iframe>');
                        var id=$(".e-g-row>.eomsDistribute>div").attr("id")?$(".e-g-row>.eomsDistribute>div").attr("id"):"";
                        var uName=$(".e-g-row>.eomsDistribute>div>span").eq(0).text();
                        var flagUname=$(".e-g-row>.eomsDistribute>div>span").eq(1).text();
                        var urlFrom=$(".e-g-row>.eomsDistribute").attr("urlFrom")?$(".e-g-row>.eomsDistribute").attr("urlFrom"):"";
                        var dispatch={
                            "id":id,
                            "uName":uName,
                            "flagUname":flagUname,
                            "urlFrom":urlFrom
                        };
                        var send=[];
                        for(i=0;i<$(".e-g-row>.eomsCopyfor").length;i++){
                            var id=$(".e-g-row>.eomsCopyfor").eq(i).find("div").attr("id")?$(".e-g-row>.eomsCopyfor").eq(i).find("div").attr("id"):"";
                            var uName=$(".e-g-row>.eomsCopyfor").eq(i).find("div>span").eq(0).text();
                            var flagUname=$(".e-g-row>.eomsCopyfor").eq(i).find("div>span").eq(1).text();
                            var urlFrom=$(".e-g-row>.eomsCopyfor").eq(i).attr("urlFrom")?$(".e-g-row>.eomsCopyfor").eq(i).attr("urlFrom"):"";
                            var sendObj={
                                "id":id,
                                "uName":uName,
                                "flagUname":flagUname,
                                "urlFrom":urlFrom
                            };
                            send.push(sendObj);
                        }

                        var obj={
                            "distribute":dispatch,
                            "copyFor":send
                        }
                        $(".e-g-row>.eomsCopyfor").remove();
                        $(".e-g-row>.eomsDistribute").remove();
                        $(".eomsIinkage")[0].contentWindow.hellobaby=obj;
                    })
                },
                switchEvent: function() {
                    $this.find('.eoms-tinyWhiteBall').on('click', function() {
                        if ($(this).css('left') == '0px') {
                            $(this).css('left', '-40px');
                            $(this).find('input').attr("value","否");
                        } else {
                            $(this).css('left', '0');
                            $(this).find('input').attr("value","是");
                        }
                    })
                },
                specialheight: function() {
                    var $titlep = $this.find('li>div>p:nth-child(1)')
                    for (var i = 0; i < $titlep.length; i++) {
                        var titlepdiv = $($titlep[i]).parent();
                        var titlepnext = $($titlep[i]).next('*');
                        var width = "";
                        if(titlepdiv.width() < 1){
                            var titlepdivs = $($titlep[i]).parent().parent().parent().parent().parent().parent().parent();
                            if((titlepdivs.width() - $($titlep[i]).width() - 100) < 50){
                            	width= 412;
                            }else{
                            	width=titlepdivs.width() - $($titlep[i]).width() - 100;
                            }
                            $(titlepnext).css({
                                'width': width
                            })
                        }else{
                        	if((titlepdiv.width() - $($titlep[i]).width() - 60) < 50){
                            	width= 412;
                            }else{
                            	width=titlepdiv.width() - $($titlep[i]).width() - 60;
                            }
                            $(titlepnext).css({
                                'width': width
                            })
                        }

                        /* if(titlepnext.width() < 41){
                        	 var titlepdivs = $($titlep[i]).parent().parent().parent().parent().parent().parent();
                             $(titlepnext).css({
                                  'width': titlepdivs.width() - $($titlep[i]).width() - 100
                              })
                         }*/
                    }
                },
                radioEvent: function() {
                    /* var arr=[];
                     var arr2=[];*/
                    $this.find('.eomsRadio').on('click', function() {
                        $("#layui-laydate1").css({"display":"none"});
                        $("#layui-laydate2").css({"display":"none"});
                        if (!$(this).hasClass('selectdivFocus')) {
                            $('.eomsRadio').removeClass('selectdivFocus').children('div').hide()
                            $(this).children('div').css({ 'top': $(this).height() + 2 })
                            $(this).addClass('selectdivFocus').children('div').show();
                        } else {
                            $(this).removeClass('selectdivFocus').children('div').hide();
                        }
                        return false
                    })
                    $this.find('.eomsRadio div ul li').on('click', function() {
                        $(this).parents('.eomsRadio').find('strong').remove();
                        $(this).parents('.eomsRadio').find('input').remove();
                        var eomsId=$(this).parents('.eomsRadio').find('ul').attr("data-type");
                        $(this).parents('.eomsRadio').append('<strong data-id='+$(this).index()+'>' + $(this).attr('value') + '</strong>')
                        $(this).parents('.eomsRadio').append('<input type="hidden" name="'+eomsId+'"  id="'+eomsId+'" value="'+ $(this).attr('value') +'" ></input>')
                    })
                    $this.find('.eomsRadio>div').on('mouseleave blur', function() {
                        $(this).hide()
                        $(this).parent('.eomsRadio').removeClass('selectdivFocus');
                        $(this).siblings("span:eq(0)").css({"transform":"rotate(270deg)","bottom":"7px"});
                        return false
                    })
                    /*$this.on('click', function() {
                        $('.selectdiv>div').trigger('blur')
                        return false
                    })*/
                },
                checkboxEvent: function() {
                    $this.find('.eomsCheckbox').on('click', function() {
                        if (!$(this).hasClass('selectdivFocus')) {
                            $('.eomsCheckbox').removeClass('selectdivFocus').children('div').hide()
                            $(this).children('div').css({ 'top': $(this).height() + 2 })
                            $(this).addClass('selectdivFocus').children('div').show()
                        } else {
                            $(this).removeClass('selectdivFocus').children('div').hide()
                        }
                        return false
                    })
                    $this.find('.eomsCheckbox div ul li').on('click', function() {
                        if ($(this).parents('.eomsCheckbox').find('b').text().indexOf($(this).text()) == -1) {
                            $(this).parents('.eomsCheckbox').append('<b>' + $(this).attr('value') + '</b>')
                        } else {

                        }

                        $(this).parent('ul').parent('div').parent('div').prev('p').height($(this).parent('ul').parent('div').parent('div').height() + 12)
                        $(this).parent('ul').parent('div').css({ 'top': $(this).parent('ul').parent('div').parent('div').height() + 2 })
                        return false
                    })
                    $this.find('.eomsCheckbox').on('click', 'b', function() {
                        $(this).remove()
                        return false
                    })

                },
                textAreaEvent: function() {
                    $this.find('textarea').on('focus', function() {
                        $(this).css({
                            'box-shadow': '0 0 1px 1px #80b0f9',

                        })

                    }).on('blur', function() {
                        $(this).css({
                            'box-shadow': 'none',

                        })
                        $(this).prev('p').height($(this).height()+12)
                    })
                },
                dynamicTableEvent:function() {
                    $this.find('textarea').on('focus', function() {
                        $(this).css({
                            'box-shadow': '0 0 1px 1px #80b0f9',

                        })

                    }).on('blur', function() {
                        $(this).css({
                            'box-shadow': 'none',

                        })
                        $(this).prev('p').height($(this).height()+12)
                    })
                },
                iframeEvent :function(){
                    var newWidth=$this.find('.e-g-iframe .e-u-analysis').width();
                    $this.find('.e-g-iframe .e-u-analysis>i').css("width",newWidth);
                    $this.find('.e-g-iframe').on("click",".e-u-analysis",function(){
                        var iframeHtml=$(this).parent().parent().attr("data-id");
                        var iframeId=$(this).parent().parent().attr("id");
                        $(this).parents().find(".e-g-row").after('<iframe name="'+iframeId+'" id="'+iframeId+'" class="eomsIframe" src="'+iframeHtml+'" frameborder="0" align="center" scrolling="no"><p>你的浏览器不支持iframe标签</p></iframe>')
                    })
                },
                thirdEvent : function() {
                    for(var i=0; i<newArray.length; i++){
                        var index=thirdSelectMap[i]
                        //var newClass=".thirdSelect";
                        $this.find('li[data-index=' + index + ']').append('<div data-id="' + newArray[i].id + '"  data-necessary = ' + newArray[i].isNecessary + '><p>' + newArray[i].name + '</p><div class="selectdiv eomsRadioss eomsthreeRadio"><span></span><div><ul data-ulNumber="indexselect" class="' + 'select' + i + '" data-id="'+newArray[i].pId+'" data-type="' + $this.attr('id') + 'select' + i + '"></ul></div><strong><p></p><input type="hidden" name="'+newArray[i].pId+'" id="'+newArray[i].pId+'" value=""></strong></div></div>')
                        if(newArray[i].value!=undefined&&newArray[i].value.liandong!=undefined){
                            var liandongStr="";
                            for(var k=0;k<newArray[i].value.liandong.length;k++){
                                var shuttle=newArray[i].value.liandong[k];
                                var id = shuttle.id;
                                liandongStr+='<li id="'+newArray[i].value.liandong[k].id+'" value = "' + newArray[i].value.liandong[k].uName + '" >' + newArray[i].value.liandong[k].uName + '</li>'
                                if(newArray[i].value.liandong[k].select == true){
                                    $this.find("strong p").eq(0).text(newArray[i].value.liandong[k].uName)
                                    $this.find("strong input").eq(0).attr("value",newArray[i].value.liandong[k].uName)
                                }
                            }
                            $('ul[data-type="' + $this.attr('id') + 'select0'+'"]').html(liandongStr);
                        }
                    }

                    $this.find('.eomsRadioss').on('click', function() {
                        if (!$(this).hasClass('selectdivFocus')) {
                            $('.eomsRadioss').removeClass('selectdivFocus').children('div').hide()
                            $(this).children('div').css({ 'top': $(this).height() + 2 })
                            $(this).addClass('selectdivFocus').children('div').show()
                        } else {
                            $(this).removeClass('selectdivFocus').children('div').hide()
                            /*   $(this).removeClass('selectdivFocus').children('div').show()*/
                        }
                        var nowState=$(this).find("span:eq(0)").css("bottom");
                        if(nowState=="7px"){
                            $(this).find("span:eq(0)").css({"transform":"rotate(90deg)","bottom":"0"})
                        }else if(nowState=="0px"){
                            $(this).find("span:eq(0)").css({"transform":"rotate(270deg)","bottom":"7px"})
                        }
                        return false;
                    })

                    var newLength=$this.find('.eomsRadioss div .select0 li').length;
                    var newValue=$this.find("strong input").eq(0).attr("value");
                    for(var i =0; i<newLength ; i++){
                        var newText=$this.find('.eomsRadioss div .select0 li').eq(i).text();
                        if(newText == newValue){
                            var linewId=$this.find('.eomsRadioss div .select0 li').eq(i).attr("id");
                            if(newArray["0"].value[linewId] == undefined){
                                $this.find('.eomsRadioss .select1').parent().parent().css("background","#e9e9e9");
                                $this.find('.eomsRadioss .select2').parent().parent().css("background","#e9e9e9");
                                $this.find('.eomsRadioss .select1').parent().parent().addClass("eomsBg");
                                $this.find('.eomsRadioss .select2').parent().parent().addClass("eomsBg");
                                $this.find('.eomsRadioss strong').eq(1).hide();
                                $this.find('.eomsRadioss strong').eq(2).hide();
                                $this.find(".eomsthreeRadio strong p").eq(1).text("");
                                $this.find(".eomsthreeRadio strong input").eq(1).attr("value"," ");
                                $this.find(".eomsthreeRadio strong p").eq(2).text("");
                                $this.find(".eomsthreeRadio strong input").eq(2).attr("value"," ");

                                $this.find(".select2 li").hide();
                            }else{
                                for (var n = 0; n < newArray["0"].value[linewId].length; n++) {
                                    if(newArray["0"].value[linewId][n].select == true){
                                        $this.find(".eomsthreeRadio strong p").eq(1).text(newArray["0"].value[linewId][n].uName)
                                        $this.find(".eomsthreeRadio strong input").eq(1).attr("value",newArray["0"].value[linewId][n].uName)
                                        $this.find('.eomsRadioss .select1').parent().parent().css("background","rgba(0,0,0,0)");
                                    }
                                    $this.find('.eomsRadioss div .select1').append('<li id="'+newArray["0"].value[linewId][n].id+'" value = "' + newArray["0"].value[linewId][n].uName + '" >' + newArray["0"].value[linewId][n].uName + '</li>')

                                }
                            }
                        }
                    }


                    var newsLength=$this.find('.eomsRadioss div .select1 li').length;
                    var newsValue=$this.find("strong input").eq(1).attr("value");
                    for(var i =0; i<newsLength ; i++){

                        var newsText=$this.find('.eomsRadioss div .select1 li').eq(i).text();
                        if(newsText == newsValue){
                            var linewsId=$this.find('.eomsRadioss div .select1 li').eq(i).attr("id");
                            if(newArray["0"].value[linewsId] == undefined){
                                $this.find('.eomsRadioss .select2').parent().parent().css("background","#e9e9e9");
                                $this.find('.eomsRadioss .select2').parent().parent().addClass("eomsBg");
                                $this.find('.eomsRadioss strong').eq(2).hide();
                                $this.find(".eomsthreeRadio strong p").eq(2).text("");
                                $this.find(".eomsthreeRadio strong input").eq(2).attr("value"," ");

                            }else{
                                for (var n = 0; n < newArray["0"].value[linewsId].length; n++) {
                                    if(newArray["0"].value[linewsId][n].select == true){
                                        $this.find(".eomsthreeRadio strong p").eq(2).text(newArray["0"].value[linewsId][n].uName)
                                        $this.find(".eomsthreeRadio strong input").eq(2).attr("value",newArray["0"].value[linewsId][n].uName)
                                        $this.find('.eomsRadioss .select2').parent().parent().css("background","rgba(0,0,0,0)");
                                    }
                                    $this.find('.eomsRadioss div .select2').append('<li id="'+newArray["0"].value[linewsId][n].id+'" value = "' + newArray["0"].value[linewsId][n].uName + '" >' + newArray["0"].value[linewsId][n].uName + '</li>')

                                }
                            }
                        }
                    }

                    $this.find('.eomsRadioss div .select0 li').on('click', function() {
                        var eomsId=$(this).parents('.eomsRadioss').find('ul').attr("data-id");
                        $(this).parents('.eomsRadioss').find('strong').remove()
                        $(this).parents('.eomsRadioss').append('<strong><p>'+ $(this).attr('value') +'</p><input type="hidden" name="'+eomsId+'"  id="'+eomsId+'" value="'+ $(this).attr('value') +'" ></input></strong>')
                        $this.find('.eomsRadioss div .select1 li').remove()
                        var newLengths=$this.find('.eomsRadioss div .select0 li').length;
                        var newValues=$this.find("strong input").eq(0).attr("value");
                        for(var i =0; i<newLengths; i++){
                            var newText=$this.find('.eomsRadioss div .select0 li').eq(i).text();
                            if(newText == newValues){

                                var linewId=$this.find('.eomsRadioss div .select0 li').eq(i).attr("id");
                                if(newArray["0"].value[linewId] == undefined){
                                    $this.find('.eomsRadioss .select1').parent().parent().css("background","#e9e9e9");
                                    $this.find('.eomsRadioss .select2').parent().parent().css("background","#e9e9e9");
                                    $this.find('.eomsRadioss .select1').parent().parent().addClass("eomsBg");
                                    $this.find('.eomsRadioss .select2').parent().parent().addClass("eomsBg");
                                    $this.find('.eomsRadioss strong').eq(1).hide();
                                    $this.find('.eomsRadioss strong').eq(2).hide();
                                    $this.find(".eomsthreeRadio strong p").eq(1).text("");
                                    $this.find(".eomsthreeRadio strong input").eq(1).attr("value"," ");
                                    $this.find(".eomsthreeRadio strong p").eq(2).text("");
                                    $this.find(".eomsthreeRadio strong input").eq(2).attr("value"," ");
                                    $this.find(".select2 li").hide();

                                }else{
                                    $this.find('.eomsRadioss .select1').parent().parent().css("background","rgba(0,0,0,0)");
                                    $this.find('.eomsRadioss .select2').parent().parent().css("background","rgba(0,0,0,0)");
                                    $this.find('.eomsRadioss .select1').parent().parent().removeClass("eomsBg");
                                    $this.find('.eomsRadioss .select2').parent().parent().removeClass("eomsBg");
                                    $this.find('.eomsRadioss strong').eq(1).hide();
                                    $this.find('.eomsRadioss strong').eq(2).hide();
                                    $this.find(".select2 li").show();
                                    for (var n = 0; n < newArray["0"].value[linewId].length; n++) {

                                        $this.find(".eomsthreeRadio strong p").eq(1).text("");
                                        $this.find(".eomsthreeRadio strong input").eq(1).attr("value"," ");
                                        $this.find(".eomsthreeRadio strong p").eq(2).text("");
                                        $this.find(".eomsthreeRadio strong input").eq(2).attr("value"," ");

                                        $this.find('.eomsRadioss div .select1').append('<li id="'+newArray["0"].value[linewId][n].id+'" value = "' + newArray["0"].value[linewId][n].uName + '" >' + newArray["0"].value[linewId][n].uName + '</li>')
                                    }
                                    fristadd();

                                }
                            }
                        }

                    })

                    function fristadd(){
                        var newsLengths=$this.find('.eomsRadioss div .select1 li').length;
                        var newsValues=$this.find("strong input").eq(1).attr("value");
                        $this.find('.eomsRadioss div .select2 li').remove()
                        for(var i =0; i<newsLengths ; i++){
                            var newsText=$this.find('.eomsRadioss div .select1 li').eq(i).text();
                            if(newsText == newsValues){
                                var linewsId=$this.find('.eomsRadioss div .select1 li').eq(i).attr("id");
                                if(newArray["0"].value[linewsId] == undefined){

                                }else{
                                    for (var n = 0; n < newArray["0"].value[linewsId].length; n++) {

                                        $this.find(".eomsthreeRadio strong p").eq(2).text("");
                                        $this.find(".eomsthreeRadio strong input").eq(2).attr("value","");

                                        $this.find('.eomsRadioss div .select2').append('<li id="'+newArray["0"].value[linewsId][n].id+'" value = "' + newArray["0"].value[linewsId][n].uName + '" >' + newArray["0"].value[linewsId][n].uName + '</li>')

                                    }

                                }
                            }
                        }
                        fristone();
                    }

                    function secondadd(){
                        $this.find('.eomsRadioss div .select2 li').on('click', function() {
                            var eomsId=$(this).parents('.eomsRadioss').find('ul').attr("data-id");
                            $(this).parents('.eomsRadioss').find('strong').remove();
                            $(this).parents('.eomsRadioss').append('<strong><p>'+ $(this).attr('value') +'</p><input type="hidden" name="'+eomsId+'"  id="'+eomsId+'" value="'+ $(this).attr('value') +'" ></input></strong>')
                        })

                    }

                    function fristone(){
                        $this.find('.eomsRadioss div .select1 li').on('click', function() {
                            var eomsId=$(this).parents('.eomsRadioss').find('ul').attr("data-id");
                            $(this).parents('.eomsRadioss').find('strong').remove();
                            $(this).parents('.eomsRadioss').append('<strong><p>'+ $(this).attr('value') +'</p><input type="hidden" name="'+eomsId+'"  id="'+eomsId+'" value="'+ $(this).attr('value') +'" ></input></strong>')
                            var newsLengths=$this.find('.eomsRadioss div .select1 li').length;
                            var newsValues=$this.find("strong input").eq(1).attr("value");
                            $this.find('.eomsRadioss div .select2 li').remove()
                            for(var i =0; i<newsLengths ; i++){
                                var newsText=$this.find('.eomsRadioss div .select1 li').eq(i).text();
                                if(newsText == newsValues){
                                    var linewsId=$this.find('.eomsRadioss div .select1 li').eq(i).attr("id");
                                    if(newArray["0"].value[linewsId] == undefined){
                                        $this.find('.eomsRadioss .select2').parent().parent().css("background","#e9e9e9");
                                        $this.find('.eomsRadioss .select2').parent().parent().addClass("eomsBg");
                                        $this.find('.eomsRadioss strong').eq(2).hide();
                                        $this.find(".eomsthreeRadio strong p").eq(2).text("");
                                        $this.find(".eomsthreeRadio strong input").eq(2).attr("value"," ")
                                    }else{
                                        $this.find('.eomsRadioss .select2').parent().parent().css("background","rgba(0,0,0,0)");
                                        $this.find('.eomsRadioss .select2').parent().parent().removeClass("eomsBg");
                                        $this.find('.eomsRadioss strong').eq(2).hide();
                                        for (var n = 0; n < newArray["0"].value[linewsId].length; n++) {
                                            $this.find(".eomsthreeRadio strong p").eq(2).text("");
                                            $this.find(".eomsthreeRadio strong input").eq(2).attr("value"," ")

                                            $this.find('.eomsRadioss div .select2').append('<li id="'+newArray["0"].value[linewsId][n].id+'" value = "' + newArray["0"].value[linewsId][n].uName + '" >' + newArray["0"].value[linewsId][n].uName + '</li>')

                                        }
                                        secondadd();
                                    }
                                }
                            }

                        })
                    }
                    fristone() ;
                    secondadd();
                    /*$this.find('.eomsRadioss div .select2 li').on('click', function() {
                         var eomsId=$(this).parents('.eomsRadioss').find('ul').attr("data-id");
                         $(this).parents('.eomsRadioss').find('strong').remove();
                         $(this).parents('.eomsRadioss').append('<strong><input type="text" name="'+eomsId+'"  id="'+eomsId+'" value="'+ $(this).attr('value') +'" ></input></strong>')
                    })*/

                    $this.find('.eomsRadioss div ul li').on('click', function() {
                        $.each($this.find('.selectdiv>div>ul'),function(i,item){
                            if($(item).is(':has(li)')){
                                $(this).parent().parent().css("background-color","rgba(0,0,0,0)");
                                $(this).parent().parent().removeClass("eomsBg")
                            }else{
                                $(this).parent().parent().css("background-color","#e9e9e9");
                                $(this).parent().parent().addClass("eomsBg")
                            }
                        })
                    })


                    $this.find('.eomsRadioss>div').on('mouseleave blur', function() {
                        $(this).hide()
                        $(this).parent('.eomsRadioss').removeClass('selectdivFocus');
                        $(this).siblings("span:eq(0)").css({"transform":"rotate(270deg)","bottom":"7px"})
                        return false
                    })              
                },
            }


            $this.addClass('eoms-ul')

            $this.empty()

            for (var i = 0; i < data.length; i++) {
                var content = data[i]
                if (content.length !== 1) {
                    $this.append('<li class="eoms-halfp" data-index=' + i + '></li>')
                } else {
                    $this.append('<li class="eoms-wholep" data-index=' + i + '></li>')
                }
                for (var j = 0; j < content.length; j++) {
                	   if(content[j].relFieldENName != "" && content[j].relFieldENName != undefined && content[j].relFieldENName != null){
                		   $this.attr("relDictId",content[j].relDictId);
                		   $this.attr("relFieldENName",content[j].relFieldENName);
                		   $this.prev().hide();
//                		   $this.hide();
                		   $this.css("height","0px");
                		   $this.css("overflow","hidden");
                	   }
                	   
                    switch (content[j].type) {
                        case 'CHOICE':
                            var nn=content.length
                            if(content[j].value=="是"){
                                $this.find('li[data-index=' + i + ']').append('<div class="e-g-di">'+'<p class="e-u-secondLeft">'+'<i></i>'+content[j].name+'<img src="../images/gong-dan-liu-zhuan-yi-xuan-zhong.png" />'+'</p>'+'</div>')
                            }else{
                                $this.find('li[data-index=' + i + ']').append('<div class="e-g-di">'+'<p class="e-u-secondRight">'+'<i></i>'+content[j].name+'</p>'+'</div>')
                            }
                            break;
                        case 'SPLIT':
                            $this.find('li[data-index=' + i + ']').append('<div class="e-u-emrequire'+j+' e-u-emAdd e-u-emelse" id="'+j+'">'+'<p class="e-u-secondLeft">'+content[j].name+'</p>'+'<p class="eomsNews"><a href="'+content[j].url+'" target="_blank">'+content[j].value+'</a></p><input type="hidden" value="'+content[j].value+'" name="eomsSplit'+j+'" id="eomsSplit'+j+'" /></div>')
                            break;
                        case 'LINKAGE':
                            $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id  + '" id="' + content[j].pId + '"  roleRootId="'+content[j].roleRootId+'" deptRootId="'+content[j].deptRootId+'" class="e-g-linkage">'+'<p>'+content[j].name+'</p>'+'<div class="selectdiv eomsRadio"><span></span><div><ul data-type="' + content[j].id + '"></ul></div></div>'+'</div>')
                            break;
                        case 'THIRDSELECT':
                            $this.find('li[data-index=' + i + ']').addClass("thirdSelect");
                            thirdSelectMap.push(i);
                            newArray.push(content[j])
                            break;
                        case 'IFRAME':
                            $this.find("li[data-index=" + i + "]").append("<div data-id='" + content[j].html + "'  class='e-g-iframe'>"+"<p>"+content[j].name+"</p>"+"<div><span class='e-u-analysis'>"+content[j].name+"<i></i></span><input type='hidden' id='"+content[j].id+"' name =" + content[j].id + " value='"+content[j].value+"'></input></div>"+"</div>")
                            break;
                        case 'P':
                            if(content[j].value!=undefined&&content[j].value!=''){
                                if(content[j].iconImg!=undefined&&content[j].iconImg!=''){
                                    $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p class="title"><i class="triangle"></i>' + content[j].name + '</p><p class="content"><img src="'+content[j].iconImg+'"></img>' + content[j].value + '</p></div>')
                                }else{
                                    $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p class="title"><i class="triangle"></i>' + content[j].name + '</p><p class="content">' + content[j].value + '</p></div>')
                                }

                            }else{
                            	if(!content[j].name){
                            		var nowText='';
                            		var uName='';
                            	}else{
                            		var nowText='无';
                            		var uName=content[j].name;
                            	}
                                $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p class="title"><i class="triangle"></i>' + uName + '</p><p class="content">'+nowText+'</p></div>')
                            }
                            break;
                        case 'TEXTINPUT':
                            if(content[j].value2){
                                var maxWidth1="max-width:"+(content[j].value1.length)*14+"px";
                                var maxWidth2="max-width:"+content[j].value2.length*14+"px";
                                $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p>' + content[j].name + '</p ><p><input readonly="readonly" id="wk" value =' + content[j].value1+'-' + ' style='+maxWidth1+'></input><input readonly="readonly"  id="Ak" value =' + content[j].value2 + ' style='+maxWidth2+'></input></p ></div>');
                            }else{
                                if(content[j].readonly=="readonly"){
                                    $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p>' + content[j].name + '</p ><input  readonly="readonly" name='+content[j].id+' id='+content[j].id+' value ="' + content[j].value + '"></input></div>')
                                }else{
                                    $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p>' + content[j].name + '</p ><input  name='+content[j].id+' id='+content[j].id+' value ="' + content[j].value + '"></input></div>')
                                }
                            }
                            break;
                        case 'TEXTAREA':  
                            $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p style="height:60px;line-height:60px">' + content[j].name + '</p><textarea style="height:50px;" name='+content[j].id+' id='+content[j].id+'>' + content[j].value + '</textarea></div>')
                            break;
                        case 'SELECT':
                            $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p>' + content[j].name + '</p><div onclick="addSelectLi(this)"  class="selectdiv eomsRadio"><span></span><div><ul linkageFieldENName="'+content[j].linkageFieldENName+'" appId="'+appId+'" appName="'+appName+'" attrId="'+content[j].attrId+'" attrType="'+content[j].attrType+'" data-type="' + content[j].id + '"></ul></div></div></div>')
                            for (var k = 0; content[j].value!=undefined&&k < content[j].value.length; k++) {
                                $('ul[data-type="' + content[j].id + '"]').append('<li value = "' + content[j].value[k].uName + '" >' + content[j].value[k].uName + '</li>')
                                if(content[j].value[k].select == true){
                                    $('ul[data-type="' + content[j].id + '"]').parent().parent().append('<strong><p>'+content[j].value[k].uName+'</p><input type="hidden" name="'+content[j].id+'"  id="'+content[j].id+'" value="'+content[j].value[k].uName+'" ></input></strong>')
                                }
                            }
                            break;
                        case 'MULTISELECT':
                            $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p>' + content[j].name + '</p><div class="selectdiv eomsCheckbox"><div><ul data-type="' + $this.attr('id') + 'multiselect' + i + j + '"></ul></div></div></div>')
                            for (var k = 0; k < content[j].value.length; k++) {
                                $('ul[data-type="' + $this.attr('id') + 'multiselect' + i + j + '"]').append('<li value = "' + content[j].value[k] + '" >' + content[j].value[k] + '</li>')
                            }
                            break;
                        case 'TIMEBOX':
                            var min=content[j].min?content[j].min:"1997-1-1";
                            if(min==""){
                                var myDate=new Date();
                                min= myDate.toLocaleDateString();
                            };
                            var dateTypes=content[j].dateTypes?content[j].dateTypes:'datetime';
                            var formatStyle=content[j].formatStyle?content[j].formatStyle:"yyyy-MM-dd HH:mm:ss";
                            var moduleId=$this.attr("id");
                            $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p>' + content[j].name + '</p><p><span class="e-f-icon"></span><input type="text" readonly="readonly" name="' + content[j].id + '"  id="'+moduleId+'timebox' + i + j + '"></p></div>')
                         
                            laydate.render({
                            	elem: '#'+ moduleId+'timebox'+i+j,
                                value: content[j].value,
                                min:min,
                                type: dateTypes,
                                format: formatStyle

                            });
                            break;
                        case 'TIMEBOXRANGE':
                        	var moduleId=$this.attr("id");
                            $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p>' + content[j].name + '</p><p><span class="e-f-icon"></span><input type="text" readonly="readonly" name="' + content[j].id + '"  id="'+moduleId+'timeBox' + i + j + '"></p></div>')
                            laydate.render({
                            	elem: '#'+ moduleId+'timeBox'+i+j,
                                value: content[j].value,
                                range:"至",
                            });
                            break;
                        case 'WHETHERBOX':
                            $this.find('li[data-index=' + i + ']').append('<div data-id="' + content[j].id + '"  data-necessary = ' + content[j].isNecessary + '><p>' + content[j].name + '</p><div class="switchDiv" data-type= "' + $this.attr('id') + 'whetherbox' + i + j + '"></div></div>')
                            var whethervalue = content[j].value
                            if (whethervalue === "是" || whethervalue == 1) {
                                $('div[data-type="' + $this.attr('id') + 'whetherbox' + i + j + '"]').append('<div class="eoms-tinyWhiteBall" style="left:0px"><p>是</p><input type="text" name="'+$this.attr('id') + 'whetherbox' + i + j + '" id="'+$this.attr('id') + 'whetherbox' + i + j + '" value="是" /><div ></div><p>否</p></div>')
                            } else if (whethervalue === "否" || whethervalue == 0) {
                                $('div[data-type= "' + $this.attr('id') + 'whetherbox' + i + j + '"]').append('<div class="eoms-tinyWhiteBall" style="left:-40px"><p>是</p><div ></div><p>否</p><input type="text" name="'+$this.attr('id') + 'whetherbox' + i + j + '" id="'+$this.attr('id') + 'whetherbox' + i + j + '" value="否" /></div>')
                            }
                            break;
                    }

                }

            }
            $('.eoms-ul>li>div').each(function() {
                if ($(this).data('necessary') === true) {
                    $(this).append('<i class="iconStar">*</i>')
                }
            })
            myMethods.iframeEvent()
            myMethods.thirdEvent()
            myMethods.textAreaEvent()
            myMethods.radioEvent()
            myMethods.specialheight()
            myMethods.switchEvent()
            myMethods.checkboxEvent()
            myMethods.linkageEvent()
            myMethods.dynamicTableEvent()
            $(window).resize(function() {
                myMethods.specialheight()
            })
        },
    })
})(jQuery)








