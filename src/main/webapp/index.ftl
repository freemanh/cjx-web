<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
	<title>首页</title>
    
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
    <!-- bootstrap -->
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />
    <#--<link href="css/lib/bootstrap.datepicker.css" type="text/css" rel="stylesheet" />-->

    <!-- libraries -->
    <#--<link href="css/lib/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" />-->
    <link href="css/lib/font-awesome.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/layout.css" />
    <link rel="stylesheet" type="text/css" href="css/elements.css" />
    <link rel="stylesheet" type="text/css" href="css/icons.css" />

    <!-- this page specific styles -->
    <link rel="stylesheet" href="css/compiled/index.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/compiled/grids.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/compiled/tables.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/compiled/ui-elements.css" type="text/css" media="screen" />

    <#--<link rel="stylesheet" href="jquery/1.10.2/datetime/jquery.ui.datepicker.css" type="text/css" />-->
    <#--<link rel="stylesheet" href="jquery/1.10.2/datetime/jquery.ui.timepicker.addon.css" type="text/css"/>-->

    <!-- open sans font 
    <link href='css/open-sans-font.css' rel='stylesheet' type='text/css' />-->

    <!-- lato font -->
    <link href='css/lato-font.css' rel='stylesheet' type='text/css'/>
    <#--数据表格-->
    <link href='dataTables/jquery.dataTables.css' rel='stylesheet' type='text/css'/>
    <link href='dataTables/dataTables.tableTools.css' rel='stylesheet' type='text/css'/>

    <!--[if lt IE 9]>
      <script src="js/html5.js"></script>
    <![endif]-->
	
	
		<!-- scripts -->
    <#--<script src="js/jquery-1.10.2.js"></script>-->
    <script src="jquery/1.10.2/jquery.js"></script>
    <script src="jquery/1.10.2/ui/jquery.ui.min.js"></script>
     <script src="js/bootstrap.min.js"></script>
    <#--<script src="js/jquery-ui-1.10.2.custom.min.js"></script>-->
    <!-- knob -->
    <script src="js/jquery.knob.js"></script>
    <!-- flot charts -->
    <script src="js/jquery.flot.js"></script>
    <script src="js/jquery.flot.stack.js"></script>
    <script src="js/jquery.flot.resize.js"></script>
    <script src="js/theme.js"></script>
    <script src="js/jquery.cookie.js"></script>
    <#--<script src="js/bootstrap.datepicker.js"></script>-->
<#--支持导出-->
    <script src="dataTables/jquery.dataTables.js"></script>
    <script src="dataTables/dataTables.tableTools.js"></script>
    <script src="js/highcharts.js"></script>
    <script src="js/table2csv.js"></script>
    <#--jquery datetimepicker-->
    <#--<script src="jquery/1.10.2/datetime/jquery.ui.datepicker.js"></script>-->
    <#--<script src="jquery/1.10.2/datetime/jquery.ui.timepicker.addon.js"></script>-->
    <#--<script src="jquery/1.10.2/datetime/jquery.ui.datepicker.zh.CN.js"></script>-->
    <#--<script src="jquery/1.10.2/datetime/jquery.ui.timepicker.zh.CN.js"></script>-->

    <#--xdsoft datetimepicker-->
    <link href='thirdParty/xdsoft/jquery.datetimepicker.css' rel='stylesheet' type='text/css'/>
    <script src="thirdParty/xdsoft/jquery.datetimepicker.js"></script>

    <script type="text/javascript">

        $.ajaxSetup({
            cache: false
        });

        $(function () {

            // jQuery Knobs
            $(".knob").knob();


            // jQuery UI Sliders
            $(".slider-sample1").slider({
                value: 100,
                min: 1,
                max: 500
            });
            $(".slider-sample2").slider({
                range: "min",
                value: 130,
                min: 1,
                max: 500
            });
            $(".slider-sample3").slider({
                range: true,
                min: 0,
                max: 500,
                values: [ 40, 170 ]
            });
            
			var alarmCount = 0;
					
			var ajaxRequests =  function(){
				$.ajax('${rc.getContextPath()}/getSensors.action',{
					success : function(html){
						$("#pad-wrapper .grid-wrapper").html(html);
						
						$('.blink, .deviceBlink').each(function() {
				            var elem = $(this);
				            elem.css('color','red');
				            setInterval(function() {
				                if (elem.css('visibility') == 'hidden') {
				                    elem.css('visibility', 'visible');
				                } else {
				                    elem.css('visibility', 'hidden');
				                }
				
				            }, 500);
				        });
					}
				});
				
				$.ajax('${rc.getContextPath()}/getStatistics.action',{
					success : function(html){
						$("#main-stats .stats-row").html(html);
					}
				});
				
				$.ajax('${rc.getContextPath()}/getAlarmCount.action',{
					dataType : "json",
					success : function(data){
						$(".count").html(data.count);
						alarmCount = data.count;
						if(alarmCount > 0 && $('.slider-button').hasClass("on")){
							$("#alarmAudio").get(0).play();
						}else{
							$("#alarmAudio").get(0).pause();
						}
					}
				});
			};
			
			ajaxRequests();
			
			window.setInterval(ajaxRequests,30000);
			
			$pointerDiv = $("<div>", {class:"pointer"})
				.append($("<div>", {class:"arrow"}))
				.append($("<div>", {class:"arrow_border"}));
			
			$("#dashboard-menu a").click(function(){
				$("#dashboard-menu li").removeClass("active");
				$("#dashboard-menu div.pointer").remove();
				
				$(this).parent("li").addClass("active");
				$(this).parent("li").append($pointerDiv);
			});
			
			$("#history_link a").click(function(){
				$.ajax('${rc.getContextPath()}/historical_query.action',{
					success : function(html){
						$("div.content").html(html);
					}
				});
			});
            $("#mon_stat_link a").click(function(){
                $.ajax('${rc.getContextPath()}/mon_stat_query.action',{
                    success : function(html){
                        $("div.content").html(html);
                    }
                });
            });
			// Switch slide buttons
            $('.slider-button').click(function() {
                if ($(this).hasClass("on")) {
                    $(this).removeClass('on').html($(this).data("off-text"));  
                    $("#alarmAudio").get(0).pause(); 
                } else {
                    $(this).addClass('on').html($(this).data("on-text"));
                    if(alarmCount > 0){
	                    $("#alarmAudio").get(0).play();
	                }
                }
            });
            
            $(".icon-warning-sign").click(function(){
            	window.open('${rc.getContextPath()}/alarms.action');
            })
			
		});

	</script>

</head>
<body>
    	<audio id="alarmAudio" preload="auto" loop>
		    <source src="${rc.getContextPath()}/audio/alarm.wav">
		</audio>
<!-- nav bar -->
 	<div class="navbar navbar-inverse">
        <div class="navbar-inner">
            <button type="button" class="btn btn-navbar visible-phone" id="menu-toggler">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            
            <a class="brand" href="index.html"></a>
            
            <ul class="nav pull-right">                
                <li class="hidden-phone">
                    <input class="search" type="text" />
                </li>
                <li class="slider-frame" style="margin-top:10px">
                    <span data-on-text="声音开" data-off-text="声音关" class="slider-button on">声音开</span>
                </li>
                <li class="notification-dropdown hidden-phone">
                    <a href="" class="trigger">
                        <i class="icon-warning-sign"></i>
                        <span class="count"></span>
                    </a>
                </li>
                <li class="notification-dropdown hidden-phone">
                    <a href="#" class="trigger">
                        <i class="icon-envelope-alt"></i>
                    </a>
                    <div class="pop-dialog">
                        <div class="pointer right">
                            <div class="arrow"></div>
                            <div class="arrow_border"></div>
                        </div>
                        <div class="body">
                            <a href="#" class="close-icon"><i class="icon-remove-sign"></i></a>
                            <div class="messages">
                                <a href="#" class="item">
                                    <div class="name">系统管理员</div>
                                    <div class="msg">
                                        恭喜您，新用户注册成功！
                                    </div>
                                    <span class="time"><i class="icon-time"></i> 13 分钟前</span>
                                </a>
                                <a href="#" class="item">
                                    <div class="name">系统管理员</div>
                                    <div class="msg">
                                        温馨提示：您的账户有效期到2015-12-31
                                    </div>
                                    <span class="time"><i class="icon-time"></i> 26 分钟前</span>
                                </a>
                                <div class="footer">
                                    <a href="#" class="logout">查看所有历史消息</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle hidden-phone" data-toggle="dropdown">
                        Your account
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="personal-info.html">Personal info</a></li>
                        <li><a href="#">Account settings</a></li>
                        <li><a href="#">Billing</a></li>
                        <li><a href="#">Export your data</a></li>
                        <li><a href="#">Send feedback</a></li>
                    </ul>
                </li>
                <li class="settings hidden-phone">
                    <a href="personal-info.html" role="button">
                        <i class="icon-cog"></i>
                    </a>
                </li>
                <li class="settings hidden-phone">
                    <a href="j_spring_security_logout" role="button">
                        <i class="icon-share-alt"></i>
                    </a>
                </li>
            </ul>            
        </div>
    </div>
    
     <!-- sidebar -->
    <div id="sidebar-nav">
        <ul id="dashboard-menu">
            <li class="active">
            	<div class="pointer">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <a href="index.action">
                    <i class="icon-home"></i>
                    <span>首页</span>
                </a>
            </li>            
            <li>
                <a href="chart-showcase.html">
                    <i class="icon-signal"></i>
                    <span>趋势</span>
                </a>
            </li>
            <li id="history_link">
                <a href="#">
                    <i class="icon-th-large"></i>
                    <span>历史数据</span>
                </a>
            </li>
            <li id="mon_stat_link">
                <a href="#">
                    <i class="icon-play-circle"></i>
                    <span>统计</span>
                </a>
            </li>

            <li>
                <a href="personal-info.html">
                    <i class="icon-cog"></i>
                    <span>设置</span>
                </a>
            </li>
            <li>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-share-alt"></i>
                    <span>其他</span>
                    <i class="icon-chevron-down"></i>
                </a>
                <ul class="submenu">
                    <li><a href="signin.jsp">登录</a></li>
                    <li><a href="signup.jsp">退出</a></li>
                </ul>
            </li>
        </ul>
    </div>
    <!-- end sidebar -->
    
    	<!-- main container -->
    	<div class="content">
    		 <div class="container-fluid">
	            <!-- upper main stats -->
	            <div id="main-stats">
	                <div class="row-fluid stats-row">
	                    <!--load by ajax-->
	                </div>
	            </div>
            <!-- end upper main stats -->
    		</div>
    		
    		<div class="container-fluid">
    			<div id="pad-wrapper">
	                <!-- grid with .row-fluid -->
	                <div class="grid-wrapper">
						<!--load by ajax-->	                    
	                </div>
	            </div>
			</div>
    	</div>
    	
</body>
</html>    
