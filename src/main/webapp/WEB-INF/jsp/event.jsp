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
                    <li id="create-new-speech" class="create-new"><a class="" href="#"><i
                            class="fa fa-plus"
                            aria-hidden="true"></i><fmt:message key="app.speech.new"/></a>
                    </li>
                    <li id="add-speech-from-jira" class=""><a class="" href="#"><i
                            class="fa fa-cloud-download"
                            aria-hidden="true"></i><fmt:message key="app.event.control.importSpeechesFromJira"/></a>
                    </li>

                </ul>

            </div>

        </div>

        <div class="col-sm-9 col-md-9 col-lg-10 main">
            <c:set var="ajaxUrl" value="../ajax/events/${eventId}/speeches/"/>


            <div class="row header-row">
                <h2 id="page-name"></h2>
            </div>

            <div class="row event-info">
                <div id="photo" class="col-lg-3 col-md-6 col-sm-6 col-xs-12 speaker-photo">

                </div>

                <div id="contacts" class="col-lg-3 col-md-6 col-sm-6 col-xs-12 speaker-contacts">

                </div>

                <div id="bio" class="col-lg-6 col-md-6 col-sm-6 col-xs-12 speaker-bio">

                </div>
            </div>

            <datatables:table id="speechTable" url="${ajaxUrl}" cssClass="row table table-hover" autoWidth="true">
                <fmt:message key="app.speech" var="speech"/>
                <datatables:column title="${speech}" property="name" sortable="false"
                                   renderFunction="renderSpeechName"/>

                <fmt:message key="app.speaker" var="speaker"/>
                <datatables:column title="${speaker}" property="participants" sortable="false"  renderFunction="renderSpeakerName"/>

                <fmt:message key="app.speech.rating" var="rating"/>
                <datatables:column title="${rating}" property="rating"/>

                <fmt:message key="app.participant.table.tags" var="tags"/>
                <datatables:column title="${tags}" property="tags" filterable="false" sortable="false"
                                   renderFunction="renderSpeechTags"/>

                <fmt:message key="app.speech.status" var="jiraStatus"/>
                <datatables:column title="${jiraStatus}" property="jiraStatus" filterable="false" sortable="false"/>

                <fmt:message key="app.speech.jiraLink" var="jiraLink"/>
                <datatables:column title="${jiraLink}" property="jiraLink" filterable="false" sortable="false"/>

                <fmt:message key="app.table.managment" var="managment"/>
                <datatables:column title="${managment}" filterable="false" sortable="false"
                                   renderFunction="renderDeleteBtn"/>
            </datatables:table>

        </div>

    </section>

    <jsp:include page="fragments/footer.jsp"/>

    <jsp:include page="forms/editSpeech.jsp"/>

    <jsp:include page="forms/editTags.jsp"/>

</div>

</body>
<script type="text/javascript">
    var ajaxUrl = '${ajaxUrl}';
    var eventID = ${eventId};
    var event;
    var tagForm = $('#detailsTagsForm');
    var tagModal = $('#editTags');
    var tagContainer = tagForm.find('#tag-container');

    $(function () {
        makeEventSpeechTableEditable();
        initSingleEventControl();

    });

</script>
</html>
