<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
                        <label for="tag-container" class="control-label col-xs-3"><fmt:message
                                key="app.modal.tags.possible"/></label>
                        <div id="tag-container" class="col-xs-9">

                            <%--                                    <label class="checkbox-inline">
                                                                    <input type="checkbox" id="inlineCheckbox1" name="tags" value="tag1"> Stream API
                                                                </label>--%>

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="new-tag" class="control-label col-xs-3"><fmt:message
                                key="app.modal.tags.addNew"/></label>

                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="new-tag">
                        </div>
                        <div class="col-xs-2">
                            <a id="add-new-tag" class="btn btn-success" href="#"><i class="fa fa-plus"
                                                                                    aria-hidden="true"></i></a>
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button id="btn-submit-tags" type="submit" class="btn btn-primary"><fmt:message
                                    key="app.user.modal.submit"/></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>