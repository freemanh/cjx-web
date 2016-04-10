<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="css/map.css"/>
    <!-- scripts -->
    <script src="js/jquery-1.10.2.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery-ui-1.10.2.custom.min.js"></script>

    <script src="js/common.js"></script>

    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7p4CuEPSPcRzX9XUD8rp571R"></script>
    <script type="text/javascript" src="js/map-convertor.js"></script>

    <!-- bootstrap -->
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />
    <link href="css/lib/bootstrap.datepicker.css" type="text/css" rel="stylesheet" />


    <!-- libraries -->
    <link href="css/lib/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" />
    <link href="css/lib/font-awesome.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/layout.css" />
    <link rel="stylesheet" type="text/css" href="css/elements.css" />
    <link rel="stylesheet" type="text/css" href="css/icons.css" />

    <!-- this page specific styles -->
    <link rel="stylesheet" href="css/compiled/tables.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/compiled/ui-elements.css" type="text/css" media="screen" />


    <!-- open sans font -->
    <link href='css/open-sans-font.css' rel='stylesheet' type='text/css' />

    <!-- lato font -->
    <link href='css/lato-font.css' rel='stylesheet' type='text/css' />

       <!-- scripts -->
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
    <script src="js/bootstrap.datepicker.js"></script>

    <title>轨迹回放</title>
</head>
<body>
<div id="header">
    <form action="queryLocations.action" method="post">
        结束时间：<input type="datetime" name="to"/>
        开始时间:<input type="text" value="03/29/2014" class="small datepicker"/>
        <input type="button" id="submit" value="提交"/>
    </form>
</div>
<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
    $('.datepicker').datepicker();
    // 百度地图API功能
    var map = new BMap.Map("allmap");
    var point = new BMap.Point(${lon},${lat});
    var icon = new BMap.Icon("img/trunk.png", new BMap.Size(32, 32));
    map.centerAndZoom(point, 14);
    var offset = 0.00;

    map.addEventListener("click", function (e) {
        alert(e.point.lng + "," + e.point.lat);
    });
    // 创建地址解析器实例
    var myGeo = new BMap.Geocoder();
    map.enableScrollWheelZoom(true);
    function refreshLocations() {
        map.clearOverlays();
        offset += 0.01;
        //获取实时位置信息
        $.get(getContextPath() + "/refreshLocations.action", function (result) {
            $.each(result, function (i, item) {
                //在地图上标注
                var point = new BMap.Point(item.lon + offset * (i + 1), item.lat);
                BMap.Convertor.translate(point, 0, function (point) {
                    var text = '<div class="mapText">' + item.name + '</div>';
                    var label = new BMap.Label(text,
                            {   offset: new BMap.Size(15, 1),
                                position: point});
                    label.setStyle({border: 0});
                    var marker = new BMap.Marker(point, {icon: icon});
                    marker.setTitle(item.name);
                    map.addOverlay(label);
                    map.addOverlay(marker);
                });

            });
        });
    }
    //    setInterval(function(){
    //        refreshLocations();
    //    },3000);
    //
    //    refreshLocations();
</script>
