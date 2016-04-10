<!DOCTYPE html>
<html>
<head>
	<title>首页</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<!-- bootstrap -->
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- libraries -->
     <link href="css/lib/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet" />
    <link href="css/lib/uniform.default.css" type="text/css" rel="stylesheet" />
    <link href="css/lib/select2.css" type="text/css" rel="stylesheet" />
    <link href="css/lib/bootstrap.datepicker.css" type="text/css" rel="stylesheet" />
    <link href="css/lib/font-awesome.css" type="text/css" rel="stylesheet" />
    

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/layout.css" />
    <link rel="stylesheet" type="text/css" href="css/elements.css" />
    <link rel="stylesheet" type="text/css" href="css/icons.css" />
    
     <!-- this page specific styles -->
    <link rel="stylesheet" href="css/compiled/form-showcase.css" type="text/css" media="screen" />
    
    <!-- open sans font -->
    <link href='css/open-sans-font.css' rel='stylesheet' type='text/css' />

    <!-- lato font -->
    <link href='css/lato-font.css' rel='stylesheet' type='text/css' />
    
    <script src="js/jquery-1.10.2.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery-ui-1.10.2.custom.min.js"></script>
    <!-- knob -->
    <script src="js/jquery.knob.js"></script>
    <!-- flot charts -->
    <script src="js/jquery.flot.js"></script>
    <script src="js/jquery.flot.stack.js"></script>
    <script src="js/jquery.flot.resize.js"></script>
    <script src="js/theme.js"></script>
    <script type="text/javascript">
        function getContextPath() {
            var pathName = document.location.pathname;
            var index = pathName.substr(1).indexOf("/");
            var result = pathName.substr(0,index+1);
            return result;
        }
        $(document).ready(function () {

            $("#saveAlarmCode").click(function () {
                var acc = [];
                var values = $(".span8");
                var ok=true;
                $.each(values, function (key, elem) {
                    if (elem.value) {
                        //验证号码正确性
                        if (!elem.value.match(/^1[3|4|5|7|8][0-9]\d{8}$/)) {
                            alert("手机号码不正确:"+elem.value+",请重新输入！");
                            elem.focus();
//                            elem.setSelection(elem.value.length);
                            elem.select();
                            return ok = false;
                        }
                        acc.push(elem.value);
                    }
                });
                if(!ok){
                    return false;
                }
                if(acc.length==0){
                    alert("请至少输入一个手机号码！");
                    return false;
                }

                var url = getContextPath()+"/save_alarm_phone.action";
                $.post(url,
                        {phones: acc},
                        function (data, status) {
                            alert("告警号码已保存!");
                        }
                )
            });
        })
    </script>
    
</head>
<body>
	<div id="pad-wrapper" class="form-page">
                <div class="row-fluid form-wrapper">
	<#assign x=3>
	<#list 0..x-1 as i>
	 	<div class="field-box">
            <label>告警号码${i+1}:</label>
            <input class="span8" type="text" value="${(phones[i])!''}"/>
        </div>

    </#list>
    </div>
    <button id="saveAlarmCode">保存</button>
</div>

</body>
</html>