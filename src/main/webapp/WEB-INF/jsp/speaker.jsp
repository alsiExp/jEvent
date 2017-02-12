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
                    <li id="edit-participant" class="create-new"><a class="" href="#">
                        <i class="fa fa-pencil" aria-hidden="true"></i>
                        <fmt:message key="app.participant.edit"/></a>
                    </li>
                    <%--                    <li id="all-participants" class=""><a class="" href="../participants"><i
                                                class="fa fa-list-ul"
                                                aria-hidden="true"></i><fmt:message key="app.participant.control.list"/></a>
                                        </li>--%>

                </ul>

            </div>

        </div>

        <div class="col-sm-9 col-md-9 col-lg-10 main">
            <c:set var="ajaxUrl" value="../ajax/participants/${speakerId}/speeches/"/>



            <div class="row header-row">
                <h2 id="page-name"></h2>
            </div>

            <div class="row speaker-info">
                <div id="photo" class="col-lg-3 col-md-6 col-sm-6 col-xs-12 speaker-photo">

                </div>

                <div id="contacts" class="col-lg-3 col-md-6 col-sm-6 col-xs-12 speaker-contacts">

                </div>

                <div id="bio" class="col-lg-6 col-md-6 col-sm-6 col-xs-12 speaker-bio">

                </div>
            </div>

            <datatables:table id="speechTable" url="${ajaxUrl}" cssClass="row table table-hover" autoWidth="true">
                <fmt:message key="app.event" var="event"/>
                <datatables:column title="${event}" property="eventName" renderFunction="renderSpeechEventLink"/>

                <fmt:message key="app.speech" var="speech"/>
                <datatables:column title="${speech}" property="name" sortable="false" renderFunction="renderSpeechName"/>

                <fmt:message key="app.speech.rating" var="rating"/>
                <datatables:column title="${rating}" property="rating"/>

                <fmt:message key="app.participant.table.tags" var="tags"/>
                <datatables:column title="${tags}" property="tags" filterable="false" sortable="false"
                                   renderFunction="renderSpeechTags"/>

                <fmt:message key="app.speech.status" var="jiraStatus"/>
                <datatables:column title="${jiraStatus}" property="jiraStatus" filterable="false" renderFunction="renderSpeechStatus"/>

                <fmt:message key="app.speech.jiraLink" var="jiraLink"/>
                <datatables:column title="${jiraLink}" property="jiraLink" filterable="false" sortable="false" renderFunction="renderSpeechJiraLink"/>

            </datatables:table>

        </div>

    </section>

    <jsp:include page="fragments/footer.jsp"/>

    <jsp:include page="forms/editTags.jsp"/>

    <jsp:include page="forms/editParticipant.jsp"/>

</div>

</body>
<script type="text/javascript">
    var ajaxUrl = '${ajaxUrl}';
    var speakerID = ${speakerId};
    var speaker;

    mainForm = $('#detailsParticipantForm');
    modal = $('#editParticipant');

    var tagForm = $('#detailsTagsForm');
    var tagModal = $('#editTags');
    var tagContainer = tagForm.find('#tag-container');

    $(function () {
        initSpeaker();
        initTagForm();
        initParticipantControl();
        initErrorNotify();
    });

</script>
</html>
