<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/static/css/style.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/static/css/login.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,600,300" rel="stylesheet" type="text/css">
    <title>Login</title>
</head>

<script>
    function login() {
        var login = $('#login').val();
        var password = $('#password').val();
        var errors = '#errors';
        var user = {};
        user["login"] = login;
        user["password"] = password;
        $.ajax({
            type : "POST",
            data : JSON.stringify(user),
            contentType : "application/json",
            dataType : "json",
            url : "login",
            async : false,
            success: function (data) {
                if (!data.successful) {
                    $(errors).text(data.message).css({'color': 'red'});
                }
                window.location.replace("${pageContext.servletContext.contextPath}/main");
            }
        })
    }
</script>
<body>
<div class="wrapper">
        <section class="main">
            <header class="header">
                <div class="header__container">
                    <div class="header__body">
                        <div class="header__body__left_side">
                            <img src="${pageContext.servletContext.contextPath}/resources/static/assets/belarusian-national-technical-university.png" alt="BTNU logotype"
                                 class="main_news__header__logotype">
                            <nav class="header__menu">
                                <ul class="header__links">
                                    <li>
                                        <a href="#!">Документы</a>
                                    </li>
                                    <li>
                                        <a href="#!">Преподаватели</a>
                                    </li>
                                    <li>
                                        <a href="#!">Расписание</a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                        <div class="header__body__right_side">
                            <p>Профиль</p>
                            <div class="header__body__right_side__profile_arrows">
                                <span class="profile_arrow left">

                                </span>
                                <span class="profile_arrow right">

                                </span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="header_toolbar">

                </div>
            </header>
            <div class="main__body">
                <img class="profile_icon" src="${pageContext.servletContext.contextPath}/resources/static/assets/profile.png" alt="profile">
                <p>Логин</p>
                <input type="text" id="login" name="login">
                <p>Пароль</p>
                <input type="password" id="password" name="password">
                <div id="errors"></div>
                <!--                <a href="greeting.html" class="main__body__button">Войти</a>-->
                <button class="main__body__button" type="submit" onclick="login()">Войти</button>
            </div>
        </section>
    <footer class="footer">
        <div class="footer__body">
            <div class="footer__body__right_side">
                <p>Copyright: ©BNTU</p>
            </div>
        </div>
    </footer>
</div>
</body>
</html>