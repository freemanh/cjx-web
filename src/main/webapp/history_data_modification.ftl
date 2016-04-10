<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
	<title>数据修改</title>
    
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
	<link href="css/lib/bootstrap.datepicker.css" type="text/css" rel="stylesheet" />

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
    <script src="js/bootstrap.datepicker.js"></script>
    <script>
    	$(function(){
    		$("#closeBtn").click(function(){
    			window.close();
    		});
    		$('.datepicker').datepicker().on('changeDate', function (ev) {
                $(this).datepicker('hide');
            });
            
            $("#queryForm").submit(function(event){
            	if($("#oper").val() != 'submit'){
            		event.preventDefault();
	            	if(this.checkValidity()){
	            		$.ajax('${rc.getContextPath()}/xiugai/load.action',{
	            			method : "post",
	            			data : {
	            				wrongSensorId : $("#wrongSensorId").val(),
	            				rightSensorId : $("#rightSensorId").val(),
	            				wrongDate : $("#wrongDate").val(),
	            				rightDate : $("#rightDate").val(),
	            				wrongHour : $("#wrongHour").val(),
	            				rightHour : $("#rightHour").val()
	            			},
	            			success : function(html){
	            				$("#dataTable").remove();
			            		$(".products-table").append(html);
			            	}
	            		});
	            	}
            	}
            });
            
    	});
    </script>

</head>
<#assign x=23>
<body>
	<div class="container-fluid">
		<div id="pad-wrapper">
			<div class="table-wrapper products-table">
				<div class="row-fluid head">
                    <div class="span12">
                        <h4>历史数据修改</h4>
                    </div>
                </div>
                <form id="queryForm" action="xiugai/save.action">
				<div class="row-fluid filter-block">
	                    <select id="wrongSensorId" name="wrongSensorId" required>
	                    	<option value=''>--选择错误数据探头--</option>
	                    	<#list sensors as sensor>
	                        	<option value="${sensor.id}">${sensor.name!'未知'}</option>
	                    	</#list>
	                    </select>
	                    <input type="text" id="wrongDate" name="wrongDate" class="input-large datepicker" placeholder="错误数据日期" required>
	                    <select id="wrongHour" name="wrongHour" required>
                                <option value="">--请选择时间--</option>
                                	<#list 0..x as i>
                                		<option value="${i}" >${i}</option>
                                	</#list>
                        </select>
	            </div>
	            <div class="row-fluid filter-block">
	                    <select id="rightSensorId" name="rightSensorId" required>
	                    	<option value=''>--选择正确数据探头--</option>
	                    	<#list sensors as sensor>
	                        	<option value="${sensor.id}">${sensor.name!'未知'}</option>
	                    	</#list>
	                    </select>
	                    <input type="text" id="rightDate" name="rightDate" class="input-large datepicker" placeholder="正确数据日期" required>
	                    <select id="rightHour" name="rightHour" required>
                                <option value="">--请选择时间--</option>
                                	<#list 0..x as i>
                                		<option value="${i}" >${i}</option>
                                	</#list>
                        </select>
                </div>
                <div class="row-fluid filter-block pull-right">
                	<input type="submit" value="查询" class="btn-flat">
                </div>
                <input type="hidden" id="oper">
                </form>
			</div>
			
		</div>
	</div>
</body>
</html>