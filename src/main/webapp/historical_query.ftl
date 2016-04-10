
    <script type="text/javascript">
        var startDateTextBox = $('#startTime');
        var endDateTextBox = $('#endTime');
        var queryCmd = '${rc.getContextPath()}/historical_query_result.action';
        //每次查询返回的记录条数
        var resultCountPerQuery = 200;
        var dataTable = $("#historyTable");

        function getParamType(param) {
            return ((_t = typeof (param)) == "object" ? Object.prototype.toString.call(param).slice(8, -1) : _t).toLowerCase();
        }
        <#--xdsoft datetimepicker-->
         startDateTextBox.datetimepicker({
             lang:'ch'
             ,format:'Y-m-d H:m'
             ,value:'2014-03-04 12:00'
             ,startDate:'2014-05-10'
         });

        endDateTextBox.datetimepicker({
             lang:'ch'
            ,format:'Y-m-d H:m'
            ,value:'2014-11-15 01:00'
            ,startDate:'2014-07-10'
        });



        function refreshChart(resp){
            var temps = [];
            var hums = [];
            var times=[];
            var arr = resp.data;
            for(var i=0;i<arr.length;i++){
                var item = arr[i];
                hums.push(item[2]);
                temps.push(item[1]);
                times.push(item[3]);
            }

            var chart = $('#chart').highcharts({
                credits:{enabled:false},
                chart: {
                    zoomType: 'xy'
                },
                title: {
                    text: '历史温湿度曲线图'
                },
                subtitle: {
                    text: ''
                },
                xAxis: [{
                    labels:{enabled:false},
                    categories: times
                }],
                yAxis: [{ // Primary yAxis
                    labels: {
                        format: '{value}°C',
                        style: {
                            color: '#89A54E'
                        }
                    },
                    title: {
                        text: '温度',
                        style: {
                            color: '#89A54E'
                        }
                    }
                }, { // Secondary yAxis
                    title: {
                        text: '湿度',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    labels: {
                        format: '{value} %',
                        style: {
                            color: '#4572A7'
                        }
                    },
                    opposite: true
                }],
                tooltip: {
                    shared: true,
                    valueDecimals: 2
                },
                legend: {
                    layout: 'vertical',
                    align: 'left',
                    x: 120,
                    verticalAlign: 'top',
                    y: 100,
                    floating: true,
                    backgroundColor: '#FFFFFF'
                },
                series: [{
                    name: '湿度',
                    color: '#4572A7',
                    type: 'spline',
                    yAxis: 1,
                    data: hums,
                    tooltip: {
                        valueSuffix: ' %'
                    }

                }, {
                    name: '温度',
                    color: '#89A54E',
                    type: 'spline',
                    data:temps,
                    tooltip: {
                        valueSuffix: '°C'
                    }
                }]
            });


        }

        $(function () {
             function submitQueryForm(){
                var resultArea = $("div.content");
                resultArea.innerHTML="";
                $("#statusMsg").innerHTML="正在查询....";
                 var table = $("#historyTable").dataTable({
                     "processing":true
                     ,"bServerSide":true
                     ,"destroy":true
					, "columnDefs":[{
						"render":function(data, type, row){
							return parseFloat(data).toFixed(2);
						},
						"targets":[1,2]
					}]	
                     ,"sAjaxSource":queryCmd
                    ,"fnServerParams": function (aoData) {  //查询条件
                         aoData.push(
                         { "name": "sensorSelect", "value": $("#sensorSelect").val() },
                         { "name": "startTime", "value": $("#startTime").val() },
                         { "name": "endTime", "value": $("#endTime").val() }
                       );
                         if(aoData[0]["value"]==null){
                         }
                     }
                     ,"fnServerData": function ( sSource, d, fnCallback ) {
                         $.ajax( {
                             "dataType": 'json',
                             "type": "POST",
                             "url": sSource,
                             "data": d
                             ,"success": function(resp){
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
                var s = $("#sensorSelect").val();
                if(s<0){
                    alert("请选择一个传感器！");
                    return;
                }
                s = $("#startTime").val();
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
        		var csv_value = $("#historyTable").table2CSV({delivery:'value'});
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
                            <select id="sensorSelect" name="sensorSelect">
                            	<option value="-1">--请选择探头名称--</option>
                            	<#list sensors as sensor>
                                	<#if sensorSelect?? && sensorSelect==sensor.id>
                                	<option value="${sensor.id}" selected>${sensor.deviceName}_${sensor.name!'未知'}</option>
                                	<#else>
                                	<option value="${sensor.id}">${sensor.deviceName}_${sensor.name!'未知'}</option>
                                	</#if>
                            	</#list>
                            </select>
                            <input id="startTime"/>
                            <input id="endTime"/>
                             <input type="button" class="btn-flat" value="查询" id="queryBtn" />
                             <input type="button" class="btn-flat" value="导出..." id="exportBtn" />
                        </div>
                    </div>

                    <div class="row-fluid">
                          <div id="chart"/>
                        <table class="table table-hover" id="historyTable">
                            <thead>
                                <tr>
                                    <th class="span3"><span class="line"/>序列号</th>
                                    <th class="span3"><span class="line"></span>温度</th>
                                    <th class="span3"><span class="line"></span>湿度</th>
                                    <th class="span3"><span class="line"></span>采集时间</th>
                                </tr>
                            </thead>
                        </table>
                        <#--<#if !results?has_content >-->
                        		<#--<div class="alert alert-info">-->
	                                <#--<i class="icon-exclamation-sign"></i>-->
	                               <#--<p id="statusMsg"> 没有找到记录！</p>-->
	                            <#--</div>-->
                        <#--</#if>-->
	          </div>
	    </div>
	    <form id="exportForm" action="${rc.getContextPath()}/export.action" method="post">
	    	<input type="hidden" id="expContent" name="content">
	    </form>
