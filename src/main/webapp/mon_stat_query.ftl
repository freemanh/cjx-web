
    <script type="text/javascript">
        var startDateTextBox = $('#startTime');
        var endDateTextBox = $('#endTime');
        var queryCmd = '${rc.getContextPath()}/mon_stat_query_result.action';
        //每次查询返回的记录条数
        var resultCountPerQuery = 200;
        var dataTable = $("#resultTable");

        function getParamType(param) {
            return ((_t = typeof (param)) == "object" ? Object.prototype.toString.call(param).slice(8, -1) : _t).toLowerCase();
        }
        <#--xdsoft datetimepicker-->
         startDateTextBox.datetimepicker({
             lang:'ch'
             ,format:'Y-m-d h:m'
             ,value:'2014-03-04 12:00'
             ,startDate:'2014-05-10'
         });

        endDateTextBox.datetimepicker({
             lang:'ch'
            ,format:'Y-m-d h:m'
            ,value:'2014-11-15 01:00'
            ,startDate:'2014-07-10'
        });

        $(function () {
             function submitQueryForm(){
                var resultArea = $("div.content");
                resultArea.innerHTML="";
                $("#statusMsg").innerHTML="正在查询....";
                 var table = dataTable.dataTable({
                     "processing":true
                     ,"bServerSide":true
                     ,"destroy":true

                     ,"sAjaxSource":queryCmd
                    ,"fnServerParams": function (aoData) {  //查询条件
                         aoData.push(
                          { "name": "startTime", "value": $("#startTime").val() },
                         { "name": "endTime", "value": $("#endTime").val() }
                       );
                         alert(aoData[0].name+":"+s);
                         if(aoData[0]["value"]==null){
                         }
                     }
                     ,"fnServerData": function ( sSource, d, fnCallback ) {
//                         alert(JSON.stringify(d));
                         $.ajax( {
                             "dataType": 'json',
                             "type": "POST",
                             "url": sSource,
                             "data": d
//                             ,success:fnCallback
                             ,"success": function(resp){
//                                 alert("success"+JSON.stringify(resp));
                                 fnCallback(resp);
                                 refreshChart(resp);

                             }
                         } );
                     }
//                    ,"fnServerData" : retrieveData
                     ,"paging":true
                     ,"bPaginate":true
                     ,"bFilter":false
                     ,"iDisplayLength":50
                      ,"bLengthChange": false
                     ,"sPaginationType" : "full_numbers"//详细分页组，可以支持直接跳转到某页
                     ,"bSort" : false //是否启动各个字段的排序功能
//                    ,"aaSorting" : [[1, "asc"]]//默认的排序方式，第2列，升序排列
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


            $("#queryBtn").click(function(){
                var s = $("#startTime").val();
                alert(typeof(s)) ;
                if(s=="" || s==undefined){
                    alert("请正确设置开始时间！");
                    return;
                }
                s = $("#endTime").val();
                if(s=="" || s==undefined){
                    alert("请正确设置结束时间！");
                    return;
                }

        		submitQueryForm();
        	});
        	
        	$("#exportBtn").click(function(){
        		var csv_value = dataTable.table2CSV({delivery:'value'});
        		var datas = csv_value.split("\n");
        		var sensorName = $("#sensorSelect").find("option:selected").text();
        		$.each(datas, function(i, element){
        			if(i == 0){
        				datas[i] = element + ',' + '传感器名称'
        			}else{
        				datas[i] = element + ',' + sensorName;
        			}
        		});
        		$("#expContent").val(datas.join("\n"));
        		$("#exportForm").submit();
        	});


        });
    </script>
		<div class="container-fluid">
            <div id="pad-wrapper">
                <div class="table-wrapper products-table">
                    <div class="row-fluid filter-block">
                        <div class="pull-right">
                            <input id="startTime"/>
                            <input id="endTime"/>
                             <input type="button" class="btn-flat" value="查询" id="queryBtn" />
                             <input type="button" class="btn-flat" value="导出..." id="exportBtn" />
                        </div>
                    </div>

                    <div class="row-fluid">
                          <div id="chart"/>
                        <table class="table table-hover" id="resultTable">
                            <thead>
                                <tr>
                                    <th class="span3"><span class="line"/>探头</th>
                                    <th class="span3"><span class="line"></span>最高温度</th>
                                    <th class="span3"><span class="line"></span>最低温度</th>
                                    <th class="span3"><span class="line"></span>平均温度</th>
                                    <th class="span3"><span class="line"></span>最高湿度</th>
                                    <th class="span3"><span class="line"></span>最低湿度</th>
                                    <th class="span3"><span class="line"></span>平均湿度</th>
                                </tr>
                            </thead>
                        </table>

	          </div>
	    </div>
	    <form id="exportForm" action="${rc.getContextPath()}/export.action" method="post">
	    	<input type="hidden" id="expContent" name="content">
	    </form>
