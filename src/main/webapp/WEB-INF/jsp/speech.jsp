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
                    <%--
                                        <li id="all-participants" class=""><a class="" href="../participants"><i
                                                class="fa fa-list-ul"
                                                aria-hidden="true"></i><fmt:message key="app.participant.control.list"/></a>
                                        </li>--%>

                </ul>

            </div>

        </div>

        <div class="col-sm-9 col-md-9 col-lg-10 main">

            <div class="row header-row">
                <h2 id="page-name"></h2>
            </div>

            <div class="row speech-info">
                <div id="speech-info" class="col-lg-3 col-md-6 col-sm-6 col-xs-12 speaker-photo">

                </div>

                <div id="speech-links" class="col-lg-3 col-md-6 col-sm-6 col-xs-12 speaker-contacts">

                </div>

                <div id="speech-short-description" class="col-lg-6 col-md-6 col-sm-6 col-xs-12 speaker-bio">

                </div>

                <div id="speech-full-description" class="col-lg-6 col-md-6 col-sm-6 col-xs-12 speaker-bio">

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
