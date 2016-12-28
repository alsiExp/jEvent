<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<section class="row" id="navigation">
    <!--top menu-->
    <nav class="navbar navbar-default navbar-fixed-top navbar-inverse" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#top-navbar-collapse" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">
                    <img class="" src="${pageContext.request.contextPath}/resources/img/jEvent-logo-xl.png" alt="jEvent">

                </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="top-navbar-collapse">
                <ul class="nav navbar-nav">

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">Конференции <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="../event/list.html">Все конференции</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Новая конференция</a></li>
                        </ul>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">Спикеры <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="../speaker/list.html">Все спикеры</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Новый спикер</a></li>

                        </ul>
                    </li>

                    <li class="dropdown hidden-sm">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">Партнеры <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="../partner/list.html">Все партнеры</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Новый партнер</a></li>
                        </ul>
                    </li>
                    <!--
                    <li class="dropdown hidden-sm">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">Аналитика <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><b>  В разработке</b></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Спикеры</a></li>
                            <li><a href="#">Конференции</a></li>
                            <li><a href="#">Партнеры</a></li>
                        </ul>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">Задачи <span class="caret"></span>
                            &lt;!&ndash;<span class="label label-warning">1</span>&ndash;&gt;
                        </a>
                        <ul class="dropdown-menu">
                            <li><b>  В разработке</b></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#"><i class="fa fa-list-ul" aria-hidden="true"></i>
                                Все задачи</a></li>
                            <li><a href="#"><i class="fa fa-archive" aria-hidden="true"></i> Архив задач</a></li>
                        </ul>
                    </li>-->

                </ul>


                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">
                                <span class="visible-sm hidden-lg hidden-md hidden-xs">ЛК <span
                                        class="caret"></span></span>
                            <span class="hidden-sm">Личный кабинет <span class="caret"></span></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Настройки</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="../users">Управление командой</a></li>
                            <li><a href="#">API</a></li>
                            <li><a href="#">Интеграция</a></li>
                            <li><a href="#">Организация</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Выйти&nbsp;<i class="fa fa-sign-out fa-fw" aria-hidden="true"></i></a>
                        </ul>
                    </li>

                </ul>

            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</section>      <!-- end menu section-->
