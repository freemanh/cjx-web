<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>告警短信统计</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!-- bootstrap -->
    <link href="${rc.getContextPath()}/css/bootstrap/bootstrap.css" rel="stylesheet"/>
    <link href="${rc.getContextPath()}/css/bootstrap/bootstrap-responsive.css" rel="stylesheet"/>
    <link href="${rc.getContextPath()}/css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet"/>

    <!-- libraries -->
    <link href="${rc.getContextPath()}/css/lib/font-awesome.css" type="text/css" rel="stylesheet"/>
    <link href="${rc.getContextPath()}/css/lib/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet"/>
    <link href="${rc.getContextPath()}/css/lib/uniform.default.css" type="text/css" rel="stylesheet"/>


    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="${rc.getContextPath()}/css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.getContextPath()}/css/elements.css"/>
    <link rel="stylesheet" type="text/css" href="${rc.getContextPath()}/css/icons.css"/>

    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->


    <!-- scripts -->
    <script src="${rc.getContextPath()}/js/jquery-1.10.2.js"></script>
    <script src="${rc.getContextPath()}/js/bootstrap.min.js"></script>
    <script src="${rc.getContextPath()}/js/jquery-ui-1.10.2.custom.min.js"></script>

    <link href="${rc.getContextPath()}/css/jquery1.10.2/jquery-ui.css" type="text/css" rel="stylesheet"/>
    <link href="${rc.getContextPath()}/css/jquery1.10.2/jquery.ui.datepicker.css" type="text/css" rel="stylesheet"/>
    <script src="${rc.getContextPath()}/js/jquery1.10.2/jquery.ui.core.js"></script>
    <script src="${rc.getContextPath()}/js/jquery1.10.2/jquery.ui.widget.js"></script>
    <script src="${rc.getContextPath()}/js/jquery1.10.2/jquery.ui-1.10.2-datepicker.js"></script>
    <script src="${rc.getContextPath()}/js/locales/jquery.ui-1.10.2-datepicker-zh-CN.js"></script>

    <script>
        $(function () {
            $( "#fromTime" ).datepicker( $.datepicker.regional[ "cn" ] );
            $( "#toTime" ).datepicker( $.datepicker.regional[ "cn" ] );

            $("#queryBtn").click(
                    function submitQuery(){
                        $("div.result").html("");
                        $.ajax('${rc.getContextPath()}/alarm_sms_stat_query.action',{
                            success : function(html){
//                                alert("ok:"+html);
                                $("div.result").html(html);
                            },
                            data : {
                                from : $("#fromTime").val(),
                                to : $("#toTime").val()
                            }
                        });
                    })
        });
    </script>

</head>
<body>
<div class="container-fluid">
    <div class="page-header">
        <h3>告警短信发送数量统计</h3>
    </div>
<#--参数区 -->
    <div class="row-fluid">
        <#--开始时间-->
         <div class="span3">
            <p>开始时间：<input type="text" id="fromTime" value="${from?string("yyyy-MM-dd")}"></p>
         </div>
        <#--结束时间-->
            <div class="span3">
                <p>结束时间：<input type="text" id="toTime" value="${to?string("yyyy-MM-dd")}"></p>
            </div>
        <a href="#" id="queryBtn" class="btn-flat info">查询</a>
    </div>
<#--结果区-->
    <div class="result">
    </div>
</div>

</body>
</html>