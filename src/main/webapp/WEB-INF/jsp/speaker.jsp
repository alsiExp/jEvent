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
                    <%--                    <li id="create-new-participant" class="create-new"><a class="" href="#"><i
                                                class="fa fa-plus"
                                                aria-hidden="true"></i><fmt:message key="app.participant.control.new"/></a>
                                        </li>
                                        <li id="all-participants" class=""><a class="" href="../participants"><i
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

    <div class="modal fade" id="editSpeech">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="app.participant.modal.header"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsSpeechForm">
                    <input type="text" hidden="hidden" name="speechId" id="speechId">

                    <div class="form-group">
                        <label for="name" class="control-label col-xs-3"><fmt:message key="app.modal.speech.name"/></label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="name" name="name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="nameEN" class="control-label col-xs-3"><fmt:message key="app.modal.speech.nameEN"/></label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="nameEN" name="nameEN">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="shortDescription" class="control-label col-xs-3"><fmt:message key="app.modal.speech.shortDescription"/></label>
                        <div class="col-xs-9">
                            <textarea class="form-control" id="shortDescription" name="shortDescription"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="shortDescriptionEN" class="control-label col-xs-3"><fmt:message key="app.modal.speech.shortDescriptionEN"/></label>
                        <div class="col-xs-9">
                            <textarea class="form-control" id="shortDescriptionEN" name="shortDescriptionEN"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="fullDescription" class="control-label col-xs-3"><fmt:message key="app.modal.speech.fullDescription"/></label>
                        <div class="col-xs-9">
                            <textarea class="form-control" id="fullDescription" name="fullDescription"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="fullDescriptionEN" class="control-label col-xs-3"><fmt:message key="app.modal.speech.fullDescriptionEN"/></label>
                        <div class="col-xs-9">
                            <textarea class="form-control" id="fullDescriptionEN" name="fullDescriptionEN"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="focus" class="control-label col-xs-3"><fmt:message key="app.modal.speech.focus"/></label>
                        <div class="col-xs-9">
                            <textarea class="form-control" id="focus" name="focus"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="viewerValue" class="control-label col-xs-3"><fmt:message key="app.modal.speech.viewerValue"/></label>
                        <div class="col-xs-9">
                            <textarea class="form-control" id="viewerValue" name="viewerValue"></textarea>
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

    <div class="modal fade" id="editTags">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title"><fmt:message key="app.tags.modal.header"/></h2>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" method="post" id="detailsTagsForm">
                        <input type="text" hidden="hidden" name="speechId" id="speechIdTags">

                            <div class="form-group">
                                <label for="name" class="control-label col-xs-3"><fmt:message
                                        key="app.modal.tags.possible"/></label>
                                <div id="tag-container" class="col-xs-9">

<%--                                    <label class="checkbox-inline">
                                        <input type="checkbox" id="inlineCheckbox1" name="tags" value="tag1"> Stream API
                                    </label>--%>

                                </div>
                            </div>
                            <div class="form-group">
                                <label for="new-tag" class="control-label col-xs-3"><fmt:message key="app.modal.tags.addNew"/></label>

                                <div class="col-xs-7">
                                    <input type="text" class="form-control" id="new-tag">
                                </div>
                                <div class="col-xs-2">
                                    <a id="add-new-tag" class="btn btn-success" href="#"><i class="fa fa-plus" aria-hidden="true"></i></a>
                                </div>
                            </div>



                        <div class="form-group">
                            <div class="col-xs-offset-3 col-xs-9">
                                <button id="btn-submit-tags" type="submit" class="btn btn-primary"><fmt:message key="app.user.modal.submit"/></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <%--<div class="modal fade" id="editParticipant">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title"><fmt:message key="app.participant.modal.header"/></h2>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" method="post" id="detailsParticipantForm">
                        <input type="text" hidden="hidden" name="participantId" id="participantId">
                        <input type="text" hidden="hidden" name="registered" id="registered">

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
                            <label for="phone" class="control-label col-xs-3"><fmt:message key="app.table.phone"/></label>

                            <div class="col-xs-9">
                                <input type="tel" class="form-control" id="phone" name="phone" placeholder="+7-000-123-45-67">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="skype" class="control-label col-xs-3"><fmt:message key="app.table.skype"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="skype" name="skype">
                            </div>
                        </div>

                        <div class="form-group" id="emailContainer">
                            <label for="email" class="control-label col-xs-3"><fmt:message key="app.table.email"/></label>
                            <input type="hidden" class="form-control" id="email" name="email">

                            <div class="col-xs-7">
                                <input type="email" class="form-control" id="email-0">
                            </div>
                            <div class="col-xs-2">
                                <a id="addEmail" class="btn btn-success" href="#"><i class="fa fa-plus" aria-hidden="true"></i></a>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="github" class="control-label col-xs-3"><fmt:message key="app.table.github"/></label>

                            <div class="col-xs-9">
                                <div class="input-group">
                                    <div class="input-group-addon">https://github.com/</div>
                                    <input type="text" class="form-control" id="github" name="github">
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="twitter" class="control-label col-xs-3"><fmt:message key="app.table.twitter"/></label>

                            <div class="col-xs-9">
                                <div class="input-group">
                                    <div class="input-group-addon">https://twitter.com/</div>
                                    <input type="text" class="form-control" id="twitter" name="twitter">
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="city" class="control-label col-xs-3"><fmt:message key="app.table.city"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="city" name="city">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="employer" class="control-label col-xs-3"><fmt:message key="app.table.employer"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="employer" name="employer">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="bio" class="control-label col-xs-3"><fmt:message key="app.table.bio"/></label>

                            <div class="col-xs-9">
                                <fmt:message key="app.html.enabled" var="htmlOn"/>
                                <textarea class="form-control" id="bio" name="bio" placeholder="${htmlOn}" >
                                </textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="description" class="control-label col-xs-3"><fmt:message key="app.participant.description"/></label>

                            <div class="col-xs-9">
                                <textarea class="form-control" id="description" name="description">
                                </textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="travelHelp" class="control-label col-xs-3"><fmt:message key="app.participant.help"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="travelHelp" name="travelHelp">
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
    </div>--%>


</div>

</body>
<script type="text/javascript">
    var ajaxUrl = '${ajaxUrl}';
    var speakerID = ${speakerId};
    var speaker;
    var tagForm = $('#detailsTagsForm');
    var tagModal = $('#editTags');
    var tagContainer = tagForm.find('#tag-container');

    $(function () {
        makeSpeechTableEditable();
        initSpeaker();
        initSpeechForm();

    });

</script>
</html>
