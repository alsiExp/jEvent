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
                    <li id="create-new-user" class="create-new"><a class="" href="#"><i
                            class="fa fa-plus"
                            aria-hidden="true"></i><fmt:message key="app.user.control.new"/></a>
                    </li>
<%--

                    <li id="update" class=""><a class="" href="#"><i
                            class="fa fa-refresh"
                            aria-hidden="true"></i>Обновить</a>
                    </li>

--%>
                </ul>

            </div>

        </div>

        <div class="col-sm-9 col-md-9 col-lg-10 main">

            <datatables:table id="datatable" data="${userList}" row="user" cssClass="table table-hover" autoWidth="true"  >
                <fmt:message key="app.user.table.name" var="name"/>
                <datatables:column title="${name}" property="fullName">
                    ${user.firstName} ${user.lastName}
                </datatables:column>

                <fmt:message key="app.user.table.id" var="id"/>
                <datatables:column title="${id}" property="id">
                    ${user.id}
                </datatables:column>

                <fmt:message key="app.user.table.roles" var="roles"/>
                <datatables:column title="${roles}" property="roles">
                    ${user.roles}
                </datatables:column>

                <fmt:message key="app.user.table.login" var="login"/>
                <datatables:column title="${login}" property="login">
                    ${user.login}
                </datatables:column>

                <fmt:message key="app.user.table.isActive" var="active"/>
                <datatables:column title="${active}" filterable="false" sortable="false"  property="enabled" renderFunction="userStatusRender">
                    <input type="checkbox"
                           <c:if test="${user.enabled}">checked</c:if> id="active_${user.id}"/>
                </datatables:column>

                <fmt:message key="app.user.table.managment" var="managment"/>
                <datatables:column title="${managment}"  filterable="false" sortable="false" property="id" renderFunction="userDeleteBtnRender">
                    <a class="btn btn-xs btn-danger delete" id="${user.id}"><fmt:message key="app.user.table.delete"/></a>
                </datatables:column>
            </datatables:table>

            <%--            <c:forEach items="${userList}" var="user">
                            <jsp:useBean id="user" scope="page" type="ru.jevent.model.User"/>
                            <div>
                                <c:out value="${user.login}"/>
                                <c:out value="${user.lastName}"/>
                            </div>
                        </c:forEach>--%>
        </div>

    </section>

    <jsp:include page="fragments/footer.jsp"/>

    <div class="modal fade" id="editUser">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title"><fmt:message key="app.user.modal.header"/></h2>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" method="post" id="detailsUserForm">
                        <input type="text" hidden="hidden" name="user_id" id="user_id">

                        <div class="form-group">
                            <label for="firstName" class="control-label col-xs-3"><fmt:message key="app.user.modal.firstName"/></label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="firstName" name="firstName">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="lastName" class="control-label col-xs-3"><fmt:message key="app.user.modal.lastName"/></label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="lastName" name="lastName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-3"><fmt:message key="app.user.modal.select.sex"/></label>
                            <div class="radio col-xs-9">
                                <label>
                                    <input type="radio" name="sex" id="sex_male" value="male" checked>
                                    <fmt:message key="app.sex.male"/>
                                </label>
                                <label>
                                    <input type="radio" name="sex" id="sex_female" value="female">
                                    <fmt:message key="app.sex.female"/>
                                </label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="login" class="control-label col-xs-3"><fmt:message key="app.user.table.login"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="login" name="login" placeholder=<fmt:message key="app.user.modal.login.placeholder"/>>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="password" class="control-label col-xs-3"><fmt:message key="app.user.modal.password"/></label>

                            <div class="col-xs-9">
                                <input type="password" class="form-control" id="password" name="password" placeholder=<fmt:message key="app.user.modal.password.placeholder"/>>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="password" class="control-label col-xs-3"><fmt:message key="app.user.modal.enabled"/> </label>
                            <div class="col-xs-1">
                                <input type="checkbox" class="form-control" id="enabled" name="enabled">
                            </div>

                        </div>

                        <div class="form-group">
                            <div class="col-xs-offset-3 col-xs-9">
                                <button type="submit" class="btn btn-primary"><fmt:message key="app.user.modal.submit"/></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
<%--<jsp:include page="fragments/js.jsp"/>--%>
</body>
<script type="text/javascript">
    var ajaxUrl='ajax/admin/users/';
    $(function () {
        makeEditable();
    });
</script>
</html>
