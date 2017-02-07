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
                    <li id="edit-speech" class=""><a class="" href="#"><i
                            class="fa fa-pencil"
                            aria-hidden="true"></i><fmt:message key="app.speech.edit"/></a>
                    </li>
                    <li id="edit-tags" class=""><a class="" href="#"><i
                            class="fa fa-tags"
                            aria-hidden="true"></i><fmt:message key="app.speech.edit.tags"/></a>
                    </li>
                    <li id="delete-speech" class=""><a class="" href="#"><i
                            class="fa fa-trash"
                            aria-hidden="true"></i><fmt:message key="app.speech.delete"/></a>
                    </li>

                </ul>

            </div>

        </div>

        <div class="col-sm-9 col-md-9 col-lg-10 main">

            <div class="row header-row">
                <h2 id="page-name"></h2>
            </div>

            <div class="row speech-info">
                <div id="common-speech-info" class="col-lg-4 col-md-4 col-sm-12 col-xs-12">

                </div>

                <div id="speech-description" class="col-lg-8 col-md-8 col-sm-12 col-xs-12">

                </div>

                <div id="speech-others" class="col-lg-6 col-md-6 col-sm-12 col-xs-12">

                </div>


            </div>

        </div>

    </section>

    <jsp:include page="fragments/footer.jsp"/>

    <jsp:include page="forms/editSpeech.jsp"/>

    <jsp:include page="forms/editTags.jsp"/>

</div>

</body>
<script type="text/javascript">
    var speechId = ${speechId};
    var speech;

    var tagForm = $('#detailsTagsForm');
    var tagModal = $('#editTags');
    var tagContainer = tagForm.find('#tag-container');

    $(function () {
        initTagForm();
        initSpeech();
        initSingleSpechControl();
    });

</script>
</html>
