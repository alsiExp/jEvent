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

                </ul>

            </div>

        </div>

        <div class="col-sm-9 col-md-9 col-lg-10 main">
            <c:set var="ajaxUrl" value="ajax/admin/users/"/>

            <datatables:table id="userTable" url="${ajaxUrl}" row="user" cssClass="table table-hover" autoWidth="true">
                <fmt:message key="app.table.name" var="name"/>
                <datatables:column title="${name}" property="fullName" renderFunction="renderUserName"/>

                <fmt:message key="app.user.table.id" var="id"/>
                <datatables:column title="${id}" property="id"/>

                <fmt:message key="app.user.table.roles" var="roles"/>
                <datatables:column title="${roles}" property="roles"/>

                <fmt:message key="app.user.table.login" var="login"/>
                <datatables:column title="${login}" property="login"/>

                <fmt:message key="app.user.table.isActive" var="active"/>
                <datatables:column title="${active}" filterable="false" sortable="false"  property="enabled" renderFunction="renderUserStatus"/>

                <fmt:message key="app.table.managment" var="managment"/>
                <datatables:column title="${managment}"  filterable="false" sortable="false" renderFunction="renderDeleteBtn"/>
            </datatables:table>

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
                        <input type="text" hidden="hidden" name="userId" id="user_id">

                        <div class="form-group">
                            <label for="fullName" class="control-label col-xs-3"><fmt:message key="app.modal.firstName"/></label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="fullName" name="fullName">
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="control-label col-xs-3"><fmt:message key="app.modal.select.sex"/></label>
                            <div class="radio col-xs-9">
                                <label>
                                    <input type="radio" name="optionsSex" id="sex_male">
                                    <fmt:message key="app.sex.male"/>
                                </label>
                                <label>
                                    <input type="radio" name="optionsSex" id="sex_female">
                                    <fmt:message key="app.sex.female"/>
                                </label>
                            </div>
                            <input type="text" id="sex" hidden="hidden" name="sex">
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
                            <label for="enabled" class="control-label col-xs-3"><fmt:message key="app.user.modal.enabled"/> </label>
                            <div class="radio col-xs-9">
                                <label>
                                    <input id="enabled-true" type="radio" name="optionsEnabled"  value="true" checked>
                                    <fmt:message key="app.user.modal.true"/>
                                </label>
                                <label>
                                    <input id="enabled-false" type="radio" name="optionsEnabled" value="false">
                                    <fmt:message key="app.user.modal.false"/>
                                </label>
                            </div>
                            <input type="text" id="enabled" hidden="hidden" name="enabled">
                        </div>

                        <div class="form-group">
                            <div class="col-xs-offset-3 col-xs-9">
                                <button id="btn-submit" type="submit" class="btn btn-primary"><fmt:message key="app.user.modal.submit"/></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
<script type="text/javascript">
    var ajaxUrl = '${ajaxUrl}';
    $(function () {
        makeUserTableEditable();
    });

</script>
</html>
