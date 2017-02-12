<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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