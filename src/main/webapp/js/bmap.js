//显示地图
function showMap(mapId,lon,lat){
// 百度地图API功能
    var map = new BMap.Map(mapId);
    var point = new BMap.Point(lon,lat);
    var icon = new BMap.Icon("img/trunk.png", new BMap.Size(32, 32));
    map.centerAndZoom(point, 12);
    var offset = 0.00;
    // 创建地址解析器实例
    var myGeo = new BMap.Geocoder();
    map.enableScrollWheelZoom(true);
}
function addArrow(map,polyline,length,angleValue,style){ //绘制箭头的函数
    //样式
    if(!style){
        style = {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5};
    }
    var linePoint=polyline.getPath();//线的坐标串
    var arrowCount=linePoint.length;
    for(var i =1;i<arrowCount;i++){ //在拐点处绘制箭头
        var pixelStart=map.pointToPixel(linePoint[i-1]);
        var pixelEnd=map.pointToPixel(linePoint[i]);
        var angle=angleValue;//箭头和主线的夹角
        var r=length; // r/Math.sin(angle)代表箭头长度
        var delta=0; //主线斜率，垂直时无斜率
        var param=0; //代码简洁考虑
        var pixelTemX,pixelTemY;//临时点坐标
        var pixelX,pixelY,pixelX1,pixelY1;//箭头两个点
        if(pixelEnd.x-pixelStart.x==0){ //斜率不存在是时
            pixelTemX=pixelEnd.x;
            if(pixelEnd.y>pixelStart.y)
            {
                pixelTemY=pixelEnd.y-r;
            }
            else
            {
                pixelTemY=pixelEnd.y+r;
            }
            //已知直角三角形两个点坐标及其中一个角，求另外一个点坐标算法
            pixelX=pixelTemX-r*Math.tan(angle);
            pixelX1=pixelTemX+r*Math.tan(angle);
            pixelY=pixelY1=pixelTemY;
        }
        else  //斜率存在时
        {
            delta=(pixelEnd.y-pixelStart.y)/(pixelEnd.x-pixelStart.x);
            param=Math.sqrt(delta*delta+1);

            if((pixelEnd.x-pixelStart.x)<0) //第二、三象限
            {
                pixelTemX=pixelEnd.x+ r/param;
                pixelTemY=pixelEnd.y+delta*r/param;
            }
            else//第一、四象限
            {
                pixelTemX=pixelEnd.x- r/param;
                pixelTemY=pixelEnd.y-delta*r/param;
            }
            //已知直角三角形两个点坐标及其中一个角，求另外一个点坐标算法
            pixelX=pixelTemX+ Math.tan(angle)*r*delta/param;
            pixelY=pixelTemY-Math.tan(angle)*r/param;

            pixelX1=pixelTemX- Math.tan(angle)*r*delta/param;
            pixelY1=pixelTemY+Math.tan(angle)*r/param;
        }

        var pointArrow=map.pixelToPoint(new BMap.Pixel(pixelX,pixelY));
        var pointArrow1=map.pixelToPoint(new BMap.Pixel(pixelX1,pixelY1));
        var Arrow = new BMap.Polyline([
            pointArrow,
            linePoint[i],
            pointArrow1
        ], style);
        map.addOverlay(Arrow);
    }
}