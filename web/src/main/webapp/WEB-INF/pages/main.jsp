<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/static/css/style.css">
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,600,300" rel="stylesheet" type="text/css">
    <title>BNTU</title>
</head>

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
                <div class="header_toolbar">
                    <img class="profile_icon" src="${pageContext.servletContext.contextPath}/resources/static/assets/profile.png" alt="profile">
                    <div class="header_toolbar__buttons">
                        <a href="#!" class="header_toolbar__buttons_edit">Редактировать</a>
                        <form action="/logout" method="post">
                            <button class="header_toolbar__buttons_exit" type="submit">Выйти</button>
                        </form>
                    </div>
                </div>
            </div>

        </header>
        <h1>Личный кабинет</h1>
        <div class="main__grid">
            <div class="main__grid_body">
                <div class="main__grid_item main__grid_item__1">
                    <div class="main__grid_item_contract">
                        <p>Статус договора:</p>
                        <span class="main__grid_item_contract_circle">
                        <p th:text="${practice.status.status}" />
                        </span>
                    </div>
                </div>
                <div class="main__grid_item main__grid_item__2">
                    <div class="main__grid_item_name">
                        <p th:text="${user.username}" />
                    </div>
                </div>
                <div class="main__grid_item main__grid_item__3">
                    <div class="main__grid_item_email">
                        <p>Email:</p>
<%--                        <p class="main__grid_item_email_value" th:text="${user.email}" />--%>
                    </div>
                </div>
                <div class="main__grid_item main__grid_item__4">
                    <div class="main__grid_item_place">
                        <p>Место прохождения практики:</p>
                        <p th:text="${practice.location}" />
                    </div>
                </div>
                <div class="main__grid_item main__grid_item__5">
                    <div class="main__grid_item_number">
                        <p>Телефон:</p>
<%--                        <p class="main__grid_item_number_value" th:text="${user.phone}" />--%>
                    </div>
                </div>
                <div class="main__grid_item main__grid_item__6">
                    <div class="main__grid_item_teacher">
                        <p>Преподаватель:</p>
<%--                        <p class="main__grid_item_teacher_value" th:text="${user.teacher}" />--%>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="asddd">

    </section>
    <footer class="footer">
        <div class="footer__body">
            <div class="footer__body__right_side">
                <p>Copyright: ©BNTU</p>
            </div>
        </div>
    </footer>
</div>
<script src="${pageContext.servletContext.contextPath}/resources/static/script/script.js"></script>
</body>
</html>