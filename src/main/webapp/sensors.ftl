<#list results as result>
<#if result_index % 4 == 0>
<div class="row-fluid show-grid">
</#if>
    <div class="span3" id="${result.sensorId}" style="cursor:pointer;">
    	<p class="${(result.deviceBlink)!''}">${result.deviceName}</p>
        <p class="thermometer ${(result.blink)!''}" >${(result.temperature)!"--.--"} °C</p>
    	<p class="thermometer ${(result.blink)!''}"><#if result.humidity<0>无<#else>${(result.humidity)!"--.--"} %</#if></p>
    	<a href="${rc.getContextPath() + '/sensor/' + result.sensorId}.action" target="sensorWin" style="text-decoration:underline">${(result.sensorName)!"未知"} : ${result.collectTime?string("HH:mm")}<#if result.status!='NORMAL'>(离线)</#if></a>
    </div>
<#if result_index % 4 == 3>
</div>
</#if>
</#list>
</div>

<script type="text/javascript">
     $(function () {
     	$(".span3 a").click(function(event){
	     	window.open('','sensorWin','width=500,height=800,location=no,menubar=no,toolbar=no');
     	});
     	$(".span3 .thermometer").click(function(event){
     		window.open("${rc.getContextPath()}/real_time_spline.action?sensorId=" + $(this).parent().attr('id'),"chartWin");
     	});
     });
</script>