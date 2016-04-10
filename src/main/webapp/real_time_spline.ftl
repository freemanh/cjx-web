
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>实时曲线图</title>
<!-- bootstrap -->
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- libraries -->
    <link href="css/lib/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" />
    <link href="css/lib/font-awesome.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/layout.css" />
    <link rel="stylesheet" type="text/css" href="css/elements.css" />
    <link rel="stylesheet" type="text/css" href="css/icons.css" />

    <!-- this page specific styles -->
    <link rel="stylesheet" href="css/compiled/index.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/compiled/grids.css" type="text/css" media="screen" />
    

    <!-- open sans font -->
    <#--<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css' />-->

    <!-- lato font -->
    <#--<link href='http://fonts.googleapis.com/css?family=Lato:300,400,700,900,300italic,400italic,700italic,900italic' rel='stylesheet' type='text/css' />-->

    <!--[if lt IE 9]>
      <!--<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>-->
    <![endif]-->
    
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery-ui-1.10.2.custom.min.js"></script>
    <script src="js/date.js"></script>
    <!-- knob -->
    <script src="js/jquery.knob.js"></script>
    <!-- flot charts -->
    <script src="js/jquery.flot.js"></script>
    <script src="js/jquery.flot.stack.js"></script>
    <script src="js/jquery.flot.resize.js"></script>
    <script src="js/theme.js"></script>
    <script src="http://code.highcharts.com/highcharts.js"></script>
	<script src="http://code.highcharts.com/modules/exporting.js"></script>
    <script type="text/javascript">
        var sensorId = ${sensorId};
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });


        $(function () {
        	 var mychart = {
                chart: {
                    renderTo:'container',
                    zoomType: 'xy'

                                   },
                title: {
                    text: '实时温湿度'
                },
                subtitle: {
                    text: sensorId+"号探头 "+new Date().format("yyyy-MM-dd")
                },
                xAxis: [{
                    categories: []
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
                    data: [
                    ],
                    tooltip: {
                        valueSuffix: ' %'
                    }
        
                }, {
                    name: '温度',
                    color: '#89A54E',
                    type: 'spline',
                    data: [ ],
                    tooltip: {
                        valueSuffix: '°C'
                    }
                }]
            };
            var onRefresh =function() {
                $.ajax('${rc.getContextPath()}/refreshRealtimeChart.action?sensorId='+sensorId,{
                    dataType : "json",
                    success : function(list){
                        var humArr =  new Array();
                        var tmpArr = new Array();
                        var xArr = new Array();
                        var i=0;
                        //查出来的数据时倒序的
                        for(var i=list.length-1;i>=0;i--){
                            //湿度，温度，时间
                            humArr.push(list[i][1]);
                            tmpArr.push(list[i][0]);
                            var d = new Date(list[i][2]);
                            var str = d.format("hh:mm:ss");
                            xArr.push(str);
                        }
                        mychart.series[0].data = humArr;
                        mychart.series[1].data = tmpArr;
                        mychart.xAxis[0].categories =xArr;
                        var chart = new Highcharts.Chart(mychart);
                    }

                });
            };
            onRefresh();
            setInterval(onRefresh, 5000);

        });
    </script>
</head>
<body>
<div id="container" class="content" style="min-width: 310px; height: 400px; ">    <!-- end sidebar -->
    	</div>
<div id="msg"/>
</body>
</html>