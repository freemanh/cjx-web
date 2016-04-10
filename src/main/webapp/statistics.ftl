<div class="span3 stat">
    <div class="data">
        <span class="number">${sensorCount}</span>
        探头数
    </div>
    <span class="date">实时</span>
</div>
<div class="span3 stat">
    <div class="data">
        <span class="number" id="avgTemp">${(avg.temp?string("#.00"))!"暂无"} °C</span>
        平均温度
    </div>
    <span class="date">实时</span>
</div>
<div class="span3 stat">
    <div class="data">
        <span class="number">${(avg.humidity?string("#.00"))!"暂无"} %</span>
        平均湿度
    </div>
    <span class="date">实时</span>
</div>
<div class="span3 stat last">
    <div class="data">
        <span class="number"></span>
    </div>
    <span class="date"></span>
</div>
