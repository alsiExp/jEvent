<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>


<div class="container wrapper">


    <div class="row">
        <div class="col-md-offset-4 col-md-4">
            <div class="form-login">
                <h4>Welcome back.</h4>

                <form role="form" action="spring_security_check" method="post">
                    <div class="form-group">
                        <input type="text" placeholder="Username" class="form-control" name='username'>
                    </div>
                    <div class="form-group">
                        <input type="password" placeholder="Password" class="form-control" name='password'>
                    </div>
                    <button type="submit" class="btn btn-success">Sign in <i class="fa fa-sign-in"></i></button>
                </form>

            </div>

        </div>
    </div>

</div>


<div class="container">
    <c:if test="${error}">
        <div class="error">
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
        </div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="message">
            <fmt:message key="${message}"/>
        </div>
    </c:if>
</div>


</body>
</html>

