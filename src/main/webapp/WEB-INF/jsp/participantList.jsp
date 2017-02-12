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
                    <li id="all-participants" class=""><a class="" href="../participants"><i
                            class="fa fa-list-ul"
                            aria-hidden="true"></i><fmt:message key="app.participant.control.list"/></a>
                    </li>

                </ul>

            </div>

        </div>

        <div class="col-sm-9 col-md-9 col-lg-10 main">
            <c:set var="ajaxUrl" value="ajax/participants/"/>

            <datatables:table id="participantTable" url="${ajaxUrl}" row="participant" cssClass="table table-hover" autoWidth="false" stateSave="true">
                <fmt:message key="app.table.name" var="name"/>
                <datatables:column title="${name}" property="fullName" renderFunction="renderParticipantName"/>

                <fmt:message key="app.participant.table.rating" var="rating"/>
                <datatables:column title="${rating}" property="speechSet" sortType="natural" renderFunction="renderParticipantRatings"/>

                <fmt:message key="app.participant.table.tags" var="tags"/>
                <datatables:column title="${tags}" property="participantTags" sortable="false" renderFunction="renderParticipantTags"/>

                <fmt:message key="app.table.phone" var="phone"/>
                <datatables:column title="${phone}" property="phone" filterable="false" sortable="false"/>

                <fmt:message key="app.table.email" var="email"/>
                <datatables:column title="${email}" property="emails" filterable="false" sortable="false" renderFunction="renderParticipantEmails"/>

            </datatables:table>

        </div>

    </section>

    <jsp:include page="fragments/footer.jsp"/>

    <jsp:include page="forms/editParticipant.jsp"/>


</div>

</body>
<script type="text/javascript">
    var ajaxUrl = '${ajaxUrl}';
    $(function () {
        makeParticipantTableEditable();
    });

</script>
</html>
