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
                    <li id="create-new-event" class="create-new"><a class="" href="#"><i
                            class="fa fa-plus"
                            aria-hidden="true"></i><fmt:message key="app.event.control.new"/></a>
                    </li>
<%--                    <li id="all-evnts" class=""><a class="" href="../events"><i
                            class="fa fa-list-ul"
                            aria-hidden="true"></i><fmt:message key="app.participant.control.list"/></a>
                    </li>--%>
                    <li id="add-all-from-jira" class=""><a class="" href="#"><i
                            class="fa fa-cloud-download"
                            aria-hidden="true"></i><fmt:message key="app.event.control.importFromJira"/></a>
                    </li>

                </ul>

            </div>

        </div>

        <div class="col-sm-9 col-md-9 col-lg-10 main">
            <c:set var="ajaxUrl" value="ajax/events/"/>

            <datatables:table id="eventTable" url="${ajaxUrl}" row="event" cssClass="table table-hover" autoWidth="true" stateSave="true">
                <fmt:message key="app.event.name" var="name"/>
                <datatables:column title="${name}" property="name" renderFunction="renderEventLink"/>

                <fmt:message key="app.event.version" var="version"/>
                <datatables:column title="${version}" property="version"/>

                <fmt:message key="app.event.confirmedSpeeches" var="confirmedSpeeches"/>
                <datatables:column title="${confirmedSpeeches}" property="speechesCount" renderFunction="renderConfirmedSpeeches"/>

                <fmt:message key="app.event.jiraLink" var="jiraLink"/>
                <datatables:column title="${jiraLink}" property="jiraLink" filterable="false" sortable="false" renderFunction="renderJiraLink"/>

            </datatables:table>

        </div>

    </section>

    <jsp:include page="fragments/footer.jsp"/>

    <div class="modal fade" id="editEvent">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title"><fmt:message key="app.participant.modal.header"/></h2>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" method="post" id="detailsEventForm">
                        <input type="text" hidden="hidden" name="eventId" id="eventId">

                        <div class="form-group">
                            <label for="eventName" class="control-label col-xs-3"><fmt:message key="app.event.name"/></label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="eventName" name="eventName">
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
        makeEventTableEditable();
    });

</script>
</html>
