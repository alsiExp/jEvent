<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>
<%@ taglib prefix="dandelion" uri="http://github.com/dandelion" %>

<html>
<dandelion:bundle includes="jeventDatatable"/>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<div class="container-fluid">
    <jsp:include page="fragments/topMenu.jsp"/>

    <section class="row" id="main-content">
        <div id="left-menu" class="col-sm-3 col-md-3 col-lg-2 hidden-xs sidebar">
            <!--Left content menu here-->
            <div id="left-user-view-menu">
                <ul class="nav nav-pills nav-stacked">
                    <li class="subheader"><fmt:message key="app.control.header"/></li>
                    <li role="separator" class="divider"></li>
                    <%--                    <li id="update-profile" class="create-new"><a class="" href="#"><i
                                                class="fa fa-refresh"
                                                aria-hidden="true"></i><fmt:message key="app.user.control.update"/></a>
                                        </li>--%>

                </ul>

            </div>

        </div>

        <div class="col-sm-9 col-md-9 col-lg-10 main">
            <c:set var="ajaxUrl" value="ajax/profile/"/>
            <div class="row header-row">
                <h2 id="page-name"></h2>
            </div>
            <div class="col-xs-12 col-md-6 col-lg-4 main">
                <form class="form-horizontal" method="post" id="detailsUserForm">

                    <div class="form-group">
                        <label for="fullName" class="control-label col-xs-4"><fmt:message
                                key="app.modal.firstName"/></label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" id="fullName" name="fullName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="login" class="control-label col-xs-4"><fmt:message
                                key="app.user.table.login"/></label>

                        <div class="col-xs-8">
                            <input type="text" class="form-control" id="login" name="login" placeholder=<fmt:message
                                    key="app.user.modal.login.placeholder"/>>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password" class="control-label col-xs-4"><fmt:message
                                key="app.user.modal.password"/></label>

                        <div class="col-xs-8">
                            <input type="password" class="form-control" id="password" name="password" placeholder=
                            <fmt:message key="app.user.modal.password.placeholder"/>>
                        </div>
                    </div>

                    <div id="group-jiraLogin" class="form-group">
                        <label for="jiraLogin" class="control-label col-xs-4"><fmt:message
                                key="app.user.jiraLogin"/></label>

                        <div class="col-xs-8">
                            <input type="text" class="form-control" id="jiraLogin" name="jiraLogin">
                        </div>
                    </div>

                    <div id="group-jiraPassword" class="form-group">
                        <label for="jiraPassword" class="control-label col-xs-4"><fmt:message
                                key="app.user.jiraPassword"/></label>

                        <div class="col-xs-8">
                            <input type="password" class="form-control" id="jiraPassword" name="jiraPassword">
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="col-xs-offset-4 col-xs-9">
                            <button id="btn-submit" type="submit" class="btn btn-primary"><fmt:message
                                    key="app.user.modal.submit"/></button>
                        </div>
                    </div>
                </form>
            </div>

            <div id="jira-info" class="col-xs-12 col-md-6 col-lg-4 main">

            </div>

        </div>

    </section>

    <jsp:include page="fragments/footer.jsp"/>

</div>

</body>
<script type="text/javascript">
    var ajaxUrl = '${ajaxUrl}';
    $(function () {
        initUserProfile();
    });

</script>
</html>
