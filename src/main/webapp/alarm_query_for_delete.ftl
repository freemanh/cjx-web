<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>告警查询与删除</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <!-- bootstrap -->
    <link href="${rc.getContextPath()}/css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="${rc.getContextPath()}/css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
    <link href="${rc.getContextPath()}/css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- libraries -->
    <link href="${rc.getContextPath()}/css/lib/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" />
    <link href="${rc.getContextPath()}/css/lib/font-awesome.css" type="text/css" rel="stylesheet" />
    <link href="${rc.getContextPath()}/css/lib/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet" />
    <link href="${rc.getContextPath()}/css/lib/uniform.default.css" type="text/css" rel="stylesheet" />


    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="${rc.getContextPath()}/css/layout.css" />
    <link rel="stylesheet" type="text/css" href="${rc.getContextPath()}/css/elements.css" />
    <link rel="stylesheet" type="text/css" href="${rc.getContextPath()}/css/icons.css" />

    <!-- this page specific styles -->
    <link rel="stylesheet" href="${rc.getContextPath()}/css/compiled/form-showcase.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="${rc.getContextPath()}/css/compiled/ui-elements.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/compiled/tables.css" type="text/css" media="screen" />
    <#--数据表格-->
        <link href='dataTables/jquery.dataTables.css' rel='stylesheet' type='text/css'/>
        <link href='dataTables/dataTables.tableTools.css' rel='stylesheet' type='text/css'/>

    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->


    <!-- scripts -->
    <script src="${rc.getContextPath()}/js/jquery-1.10.2.js"></script>
    <script src="${rc.getContextPath()}/js/bootstrap.min.js"></script>
    <script src="${rc.getContextPath()}/js/jquery-ui-1.10.2.custom.min.js"></script>
    <!-- knob -->
    <script src="${rc.getContextPath()}/js/jquery.knob.js"></script>
    <!-- flot charts -->
    <script src="${rc.getContextPath()}/js/jquery.flot.js"></script>
    <script src="${rc.getContextPath()}/js/jquery.flot.stack.js"></script>
    <script src="${rc.getContextPath()}/js/jquery.flot.resize.js"></script>
    <script src="${rc.getContextPath()}/js/theme.js"></script>
     <#--支持导出-->
     <script src="${rc.getContextPath()}dataTables/jquery.dataTables.js"></script>
     <script src="${rc.getContextPath()}dataTables/dataTables.tableTools.js"></script>
     <script src="${rc.getContextPath()}/js/highcharts.js"></script>
     <script src="${rc.getContextPath()}/js/table2csv.js"></script>
<script type="text/javascript">
var queryCmd = '${rc.getContextPath()}/alarm_query_for_deleteResult.action';
//每次查询返回的记录条数
var resultCountPerQuery = 200;
var dataTable = null;

function getParamType(param) {
    return ((_t = typeof (param)) == "object" ? Object.prototype.toString.call(param).slice(8, -1) : _t).toLowerCase();
}

function init(){
    //若未创建表格则创建
    if(dataTable==null){
        dataTable = $("#alarm_query_for_deleteTable").dataTable({
            "processing":true
            ,"bServerSide":true
            ,"destroy":true
            ,"sAjaxSource":queryCmd
            ,"fnServerParams": function (aoData) {  //查询条件
                //    alert(JSON.stringify(aoData));
            }
            ,"fnServerData": function ( sSource, aoData, fnCallback ) {
                $.ajax( {
                    "dataType": 'json',
                    "type": "POST",
                    "url": sSource,
                    "data": aoData
                    ,"success": function(resp){
                        fnCallback(resp);
                    }
                } );
            }
            ,"paging":true
            ,"bPaginate":true
            ,"bFilter":false
            ,"iDisplayLength":50
            ,"bLengthChange": false
            ,"sPaginationType" : "full_numbers"//详细分页组，可以支持直接跳转到某页
            ,"bSort" : false //是否启动各个字段的排序功能
            ,"columns": [
                { "data": "id" },
                { "data": "desc" },
                { "data": "grade" },
                { "data": "createTime" },
                { "data": "clearTime" }
            ]
            ,"aoColumnDefs": [
                {
                    //Selector
                    "sClass": "center",
                    "mData":"id",
                    "mRender": function(data, type,full) {
                        return '<input type="checkbox" value="'+data+'">'+data+'</input>';
                    },
                    "bSortable": false,
                    "aTargets": 0
                }
                ,{
                    //createDate
                    "mData":"createTime",
                    "mRender": function(data, type,full) {
                        if(data==null) return '/';
                        var dt = new Date(data);
                        return dt.toLocaleString();
                    },
                    "aTargets": 3
                }
                ,{
                   "mData":"clearTime",
                    "mRender": function(data, type,full) {
                        if(data==null) return '/';
                        var dt = new Date(data);
                        return dt.toLocaleString();
                    },
                    "aTargets": 4
                }
            ]
            ,"oLanguage": {
                "sLengthMenu": "每页显示 _MENU_ 条记录",
                "sZeroRecords": "抱歉， 没有找到",
                "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                "sInfoEmpty": "没有数据",
                "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "前一页",
                    "sNext": "后一页",
                    "sLast": "尾页"
                },
                "sZeroRecords": "没有检索到数据",
                "sProcessing": "<img src='/img/loading.jpg' />"
            }
        });

    }
}


function submitQueryForm(){
    $("#statusMsg").innerHTML="正在查询....";
    init();

    $("#queryBtn").click(function(){
        //删除所选项
        var selected = dataTable.$(':checked');
        var size = selected.size();
        if(size==0){
            alert("请至少选择一项!");
            return;
        }
        if(confirm("您确定要删除选中的"+selected.size()+"项？")){
            var selectValues = new Array();
            selected.each(function(){
                var tv = $(this).val();
                selectValues.push(parseFloat(tv));
            });
            //提交到后台
            $.ajax( {
                "dataType": 'json',
                "type": "POST",
                "url": '${rc.getContextPath()}/AlarmDelete.action',
                "data": {"ids":selectValues}
                ,"success": function(resp){
                    alert(resp.result);
                    window.location.reload();
                }
            } );
        }

    });

    $("#exportBtn").click(function(){
        var csv_value = $("#alarm_query_for_deleteTable").table2CSV({delivery:'value'});
        var datas = csv_value.split("\n");
        $("#expContent").val(datas.join("\n"));
        $("#exportForm").submit();
    });

}

$(function () {
  submitQueryForm();
});
</script>
</head>

<body>
<div class="container-fluid">
    <div id="pad-wrapper">
        <div class="table-wrapper products-table">
            <div class="row-fluid filter-block">
                <div class="pull-right">
                    <input type="button" class="btn-flat" value="删除" id="queryBtn" />
                    <input type="button" class="btn-flat" value="导出..." id="exportBtn" />
                </div>
            </div>

            <div class="row-fluid">
                <div id="chart"/>
                <table class="table table-hover" id="alarm_query_for_deleteTable">
                    <thead>
                    <tr>
                        
                        <th class="span1"><span class="line"/>ID</th>
                        <th class="span3"><span class="line"/>描述</th>

                        <th class="span1"><span class="line"/>级别</th>
                        
                        <th class="span3"><span class="line"/>创建时间</th>
                        
                        <th class="span3"><span class="line"/>清除时间</th>
                        
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
        <form id="exportForm" action="${rc.getContextPath()}/export.action" method="post">
            <input type="hidden" id="expContent" name="content">
        </form>
</div>
</body>
</html>
