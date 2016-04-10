<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
	<title>历史告警信息</title>
    
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
    <script>
    	$(function(){
    		$("#closeBtn").click(function(){
    			window.close();
    		})
    	});
    </script>

</head>
<body>
	<div class="container-fluid">
		<div id="pad-wrapper">
			<div class="table-wrapper products-table">
					<div class="row-fluid head">
                        <div class="span12">
                            <h4>历史告警</h4>
                        </div>
                    </div>
                    <div class="row-fluid filter-block">
                    </div>
				<#if results??>
	            <div class="row-fluid">
	            	<#if results?has_content >
	                <table class="table table-hover">
	                    <thead>
	                        <tr>
	                            <th class="span3">
	                            	描述
	                            </th>
	                            <th class="span3">
	                                <span class="line"></span>
	                                发生时间
	                            </th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <!-- row -->
	                        <#list results as result>
	                        	<#if result_index == 0>
	                        <tr class="first">
	                        	<#else>
	                        <tr>
	                        	</#if>
	                            <td>
	                                ${result.desc}
	                            </td>
	                            <td>
	                                ${result.createTime}
	                            </td>
	                        </tr>
	                        </#list>
	                    </tbody>
	                </table>
	                <#else>
	                		<div class="alert alert-info">
	                            <i class="icon-exclamation-sign"></i>
	                            没有找到记录！
	                        </div>
	                </#if>
	            </div>
	            </#if>
			</div>
			
			<div style="text-align:center"><input id="closeBtn" type="button" class="btn-flat" value="关闭窗口"></div>
		</div>
	</div>
</body>
</html>