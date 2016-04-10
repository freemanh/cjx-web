<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
	<title>传感器信息</title>
    
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
	 <script type="text/javascript">
         $(function () {

           			
		});

	</script>

</head>
<body>
	<!-- main container -->
	 <div class="container-fluid">
        <div id="pad-wrapper" class="form-page">
            <div class="row-fluid form-wrapper">
                <!-- left column -->
                <div class="span8 column">
                    <form action="${rc.getContextPath()}/sensor/${sensor.id}.action" method="post" accept-charset="utf-8">
                        <div class="field-box">
                            <label>传感器名称:</label>
                            <input name="sensorName" class="span8" type="text" value="${sensor.name}" placeholder="最多40个字符" maxlength="40" required/>
                        </div>
                        <div class="field-box">
                            <label>最高温度:</label>
                            <input name="maxTemp" class="span2" type="text" value="${sensor.maxTemp}" max="100" min="-50" required/>
                        </div>
                        <div class="field-box">
                            <label>最低温度:</label>
                            <input name="minTemp" class="span2" type="text" value="${sensor.minTemp}" max="100" min="-50" required/>
                        </div>
                        <div class="field-box">
                            <label>最高湿度:</label>
                            <input name="maxHumidity" class="span2" type="text" value="${sensor.maxHumidity}" max="100" min="0" required/>
                        </div>
                        <div class="field-box">
                            <label>最低湿度:</label>
                            <input name="minHumidity" class="span2" type="text" value="${sensor.minHumidity}" max="100" min="0" required/>
                        </div>
                        <#if isShowRevision>
                        <div class="field-box">
                            <label>修正温度:</label>
                            <input name="tempRevision" class="span2" type="text" value="${sensor.config.tempRevision}" max="100" min="-100" required/>
                        </div>
                        <div class="field-box">
                            <label>修正湿度:</label>
                            <input name="humRevision" class="span2" type="text" value="${sensor.config.humRevision}" max="100" min="-100" required/>
                        </div>
                        <div class="field-box">
                            <label>上传频率:</label>
                            <input name="uploadFrequency" class="span2" type="text" value="${sensor.config.uploadFrequency}" max="100" min="0" required/>
                        </div>
                        <div class="field-box">
                            <label>声光报警模式:</label>
                            <select name="alarmMode">
                            	<#if sensor.config.alarmMode==0>
                            	<option value="0" selected>默认模式</option>
                            	<option value="1">按键可关断模式</option>
                            	<#else>
                            	<option value="0">默认模式</option>
                            	<option value="1" selected>按键可关断模式</option>
                            	</#if>
                            </select>
                        </div>
                        </#if>
                        <div class="row ctrls">
                        	<div style="text-align:center">
                        		<input type="submit" class="btn-flat success" style="margin-right:50px" value="保 存">
                        		<input type="button" class="btn-flat gray" value="取 消" onclick="window.close()">
                        	</div>
                        </div>
                     </form>
                 </div>
            </div>
       	</div>
   	</div>   	
    	
</body>
</html>    
