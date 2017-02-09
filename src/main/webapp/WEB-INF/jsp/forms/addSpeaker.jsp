<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<div class="modal fade" id="addSpeaker">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="app.speaker.modal.header"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsSpeakersFindForm">

                    <div class="form-group">
                        <label for="speaker-container" class="control-label col-xs-3"><fmt:message
                                key="app.speaker.modal.chosen"/></label>
                        <div id="speaker-container" class="col-xs-9">

                        </div>
                    </div>

                    <div id="speakerSearchContainer" class="control-panel-row">
                        <table id="speakerSearchTable" class="display" width="100%"></table>
                    </div>


                    <div class="form-group">
                        <div class="col-xs-offset-4 col-xs-8">
                            <button id="btn-submit-speakers-search" type="submit" class="btn btn-primary"><fmt:message
                                    key="app.user.modal.submit"/></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>