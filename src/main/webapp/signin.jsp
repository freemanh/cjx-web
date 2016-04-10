<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html class="login-bg">
<head>
	<title>车吉星仓储运输管理系统 - 登录</title>
    
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
    <!-- bootstrap -->
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/layout.css" />
    <link rel="stylesheet" type="text/css" href="css/elements.css" />
    <link rel="stylesheet" type="text/css" href="css/icons.css" />

    <!-- libraries -->
    <link rel="stylesheet" type="text/css" href="css/lib/font-awesome.css" />
    
    <!-- this page specific styles -->
    <link rel="stylesheet" href="css/compiled/signin.css" type="text/css" media="screen" />

    <!--[if lt IE 9]>
      <script src="js/html5.js"></script>
    <![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<body>


    <!-- background switcher -->
    <div class="bg-switch visible-desktop">
        <div class="bgs">
            <a href="#" data-img="landscape.jpg" class="bg active">
                <img src="img/bgs/landscape.jpg" />
            </a>
            <a href="#" data-img="blueish.jpg" class="bg">
                <img src="img/bgs/blueish.jpg" />
            </a>            
            <a href="#" data-img="7.jpg" class="bg">
                <img src="img/bgs/7.jpg" />
            </a>
            <a href="#" data-img="8.jpg" class="bg">
                <img src="img/bgs/8.jpg" />
            </a>
            <a href="#" data-img="9.jpg" class="bg">
                <img src="img/bgs/9.jpg" />
            </a>
            <a href="#" data-img="10.jpg" class="bg">
                <img src="img/bgs/10.jpg" />
            </a>
            <a href="#" data-img="11.jpg" class="bg">
                <img src="img/bgs/11.jpg" />
            </a>
        </div>
    </div>


    <div class="row-fluid login-wrapper">
        <a href="index.html">
        	<!-- FIXME 使用新logo图片代替 -->
            <!-- <img class="logo" src="img/logo-white.png" /> -->
        </a>

        <div class="span4 box">
            <div class="content-wrap">
<form name='f' action="j_spring_security_check" method='POST'>
                <h6>登录</h6>
                <input name="j_username" class="span12" type="text" placeholder="用户名" />
                <input name="j_password" class="span12" type="password" placeholder="密码" />
                <a href="#" class="forgot">忘记密码?</a>
                <div class="remember">
                    <!-- <input id="remember-me" type="checkbox" /> -->
                    <label for="remember-me">记住我</label>
                </div>
                <input id="submitBtn" type="submit" class="btn-glow primary login" value="登录"/>
</form>
            </div>
        </div>

        <div class="span4 no-account">
            <p>没有账户?</p>
            <a href="signup.html">点这里注册</a>
        </div>
    </div>

	<!-- scripts -->
    <script src="js/jquery-1.10.2.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/theme.js"></script>

    <!-- pre load bg imgs -->
    <script type="text/javascript">
        $(function () {
            // bg switcher
            var $btns = $(".bg-switch .bg");
            $btns.click(function (e) {
                e.preventDefault();
                $btns.removeClass("active");
                $(this).addClass("active");
                var bg = $(this).data("img");

                $("html").css("background-image", "url('img/bgs/" + bg + "')");
            });
            <% if(request.getParameter("login_error") != null && request.getParameter("login_error").equals("1")) {%>
            	alert("用户名密码错误！");
            <% }  %>
        });
    </script>
    
    	
</body>
</html>