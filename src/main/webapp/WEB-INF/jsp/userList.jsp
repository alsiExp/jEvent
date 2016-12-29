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
        </div>

        <div class="col-sm-9 col-md-9 col-lg-10 main">
            <datatables:table id="datatable" data="${userList}" row="user" theme=""
                              cssClass="table table-hover avatar-table" pageable="false" info="false">
                <datatables:column title="Name">
                    <a href="<c:url value="/users/${user.id}"/>">${user.firstName} ${user.lastName}</a>
                </datatables:column>

                <datatables:column title="Roles" property="roles"/>
                <datatables:column title="Active">
                    <input type="checkbox"
                           <c:if test="${user.enabled}">checked</c:if> id="active_${user.id}"/>
                </datatables:column>

                <datatables:column filterable="false" sortable="false">
                    <a class="btn btn-xs btn-danger delete" id="${user.id}">Delete</a>
                </datatables:column>
            </datatables:table>

<%--            <c:forEach items="${userList}" var="user">
                <jsp:useBean id="user" scope="page" type="ru.jevent.model.User"/>
                <div>
                    <c:out value="${user.login}"/>
                    <c:out value="${user.lastName}"/>
                </div>
            </c:forEach>--%>
        </div>

    </section>

    <jsp:include page="fragments/footer.jsp"/>

</div>
<%--<jsp:include page="fragments/js.jsp"/>--%>
</body>
</html>
