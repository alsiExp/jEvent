<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
                        <label for="name" class="control-label col-xs-3"><fmt:message
                                key="app.modal.speech.name"/></label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="name" name="name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="nameEN" class="control-label col-xs-3"><fmt:message
                                key="app.modal.speech.nameEN"/></label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="nameEN" name="nameEN">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="shortDescription" class="control-label col-xs-3"><fmt:message
                                key="app.modal.speech.shortDescription"/></label>
                        <div class="col-xs-9">
                            <textarea class="form-control" id="shortDescription" name="shortDescription"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="shortDescriptionEN" class="control-label col-xs-3"><fmt:message
                                key="app.modal.speech.shortDescriptionEN"/></label>
                        <div class="col-xs-9">
                                <textarea class="form-control" id="shortDescriptionEN"
                                          name="shortDescriptionEN"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="fullDescription" class="control-label col-xs-3"><fmt:message
                                key="app.modal.speech.fullDescription"/></label>
                        <div class="col-xs-9">
                            <textarea class="form-control" id="fullDescription" name="fullDescription"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="fullDescriptionEN" class="control-label col-xs-3"><fmt:message
                                key="app.modal.speech.fullDescriptionEN"/></label>
                        <div class="col-xs-9">
                                <textarea class="form-control" id="fullDescriptionEN"
                                          name="fullDescriptionEN"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="focus" class="control-label col-xs-3"><fmt:message
                                key="app.modal.speech.focus"/></label>
                        <div class="col-xs-9">
                            <textarea class="form-control" id="focus" name="focus"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="viewerValue" class="control-label col-xs-3"><fmt:message
                                key="app.modal.speech.viewerValue"/></label>
                        <div class="col-xs-9">
                            <textarea class="form-control" id="viewerValue" name="viewerValue"></textarea>
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button id="btn-submit" type="submit" class="btn btn-primary"><fmt:message
                                    key="app.user.modal.submit"/></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>