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

            <datatables:table id="participantTable" url="${ajaxUrl}" row="participant" cssClass="table table-hover" autoWidth="true">
                <fmt:message key="app.table.name" var="name"/>
                <datatables:column title="${name}" property="fullName" renderFunction="renderParticipantName"/>

                <fmt:message key="app.participant.table.rating" var="rating"/>
                <datatables:column title="${rating}" property="speechSet" renderFunction="renderParticipantRatings"/>

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
                        <input type="text" hidden="hidden" name="registered" id="registered">

                        <div class="form-group">
                            <label for="fullName" class="control-label col-xs-3"><fmt:message key="app.modal.fullName"/></label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="fullName" name="fullName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="fullNameEN" class="control-label col-xs-3"><fmt:message key="app.modal.fullNameEN"/></label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="fullNameEN" name="fullNameEN">
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
                                <textarea class="form-control" id="bio" name="bio" placeholder="${htmlOn}"></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="bioEN" class="control-label col-xs-3"><fmt:message key="app.table.bioEN"/></label>

                            <div class="col-xs-9">
                                <textarea class="form-control" id="bioEN" name="bioEN" placeholder="${htmlOn}"></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="speakerBackground" class="control-label col-xs-3"><fmt:message key="app.table.speakerBackground"/></label>

                            <div class="col-xs-9">
                                <textarea class="form-control" id="speakerBackground" name="speakerBackground" >
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
