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
                    <li id="create-new-participant" class="create-new"><a class="" href="#"><i
                            class="fa fa-plus"
                            aria-hidden="true"></i><fmt:message key="app.participant.control.new"/></a>
                    </li>

                </ul>

            </div>

        </div>

        <div class="col-sm-9 col-md-9 col-lg-10 main">
            <c:set var="ajaxUrl" value="ajax/participants/"/>

            <datatables:table id="participantTable" url="${ajaxUrl}" row="participant" cssClass="table table-hover" autoWidth="true">
                <fmt:message key="app.table.name" var="name"/>
                <datatables:column title="${name}" property="fullName"/>

                <fmt:message key="app.participant.table.rating" var="name"/>
                <datatables:column title="${name}" property="speechSet" renderFunction="renderParticipantRatings"/>

                <fmt:message key="app.participant.table.tags" var="tags"/>
                <datatables:column title="${tags}" property="participantTags" filterable="false" sortable="false" renderFunction="renderParticipantTags"/>

                <fmt:message key="app.table.phone" var="phone"/>
                <datatables:column title="${phone}" property="phone" filterable="false" sortable="false"/>

                <fmt:message key="app.table.registered" var="registered"/>
                <datatables:column title="${registered}" property="registered" renderFunction="renderDate"/>

                <fmt:message key="app.table.managment" var="managment"/>
                <datatables:column title="${managment}"  filterable="false" sortable="false" renderFunction="renderDeleteBtn"/>
            </datatables:table>

        </div>

    </section>

    <jsp:include page="fragments/footer.jsp"/>

    <div class="modal fade" id="editParticipant">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title"><fmt:message key="app.participant.modal.header"/></h2>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" method="post" id="detailsParticipantForm">
                        <input type="text" hidden="hidden" name="participantId" id="participantId">

                        <div class="form-group">
                            <label for="firstName" class="control-label col-xs-3"><fmt:message key="app.modal.firstName"/></label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="firstName" name="firstName">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="lastName" class="control-label col-xs-3"><fmt:message key="app.modal.lastName"/></label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="lastName" name="lastName">
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
                            <label for="birthday" class="control-label col-xs-3"><fmt:message key="app.participant.birthday"/></label>

                            <div class="col-xs-9">
                                <input type="datetime" class="form-control date-picker" id="birthday" name="birthday">
                            </div>
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
        makeParticipantTableEditable();
    });

</script>
</html>
