<#if  results??>
	<div class="row-fluid" id="dataTable">
		<#if results?has_content >
	     <div id="chart"/>
	    <table class="table table-hover" id="historyTable">
	        <thead>
	            <tr>
	                <th class="span3"><span class="line"></span>错误温度</th>
	                <th class="span3"><span class="line"></span>正确温度</th>
	                <th class="span3"><span class="line"></span>错误湿度</th>
	                <th class="span3"><span class="line"></span>正确湿度</th>
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
	                <td>${result.wrongTemp}</td>
	                <td>${result.rightTemp!''}</td>
	                <td>${result.wrongHum}</td>
	                <td>${result.rightHum!''}</td>
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
	    <div style="text-align:center">
			<input id="saveBtn" type="button" class="btn-flat primary" value="保存修改">
			<input id="closeBtn" type="button" class="btn-flat gray" value="关闭窗口">
		</div>
	</div>
	
</#if>

<script>
	$("#saveBtn").on("click", function(){
    	$("#oper").val('submit');
    	$("#queryForm").submit();
    });
</script>