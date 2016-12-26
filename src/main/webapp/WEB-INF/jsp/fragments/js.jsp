<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!-- javascript -->
<script
        src="https://code.jquery.com/jquery-2.2.4.min.js"
        integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
        crossorigin="anonymous"></script>
<%--<script src="resources/js/tether.js"></script>
<script src="resources/js/bootstrap.js"></script>--%>
<script>
    function sendData() {
        var data = JSON.stringify({
            "id": 100006,
            "firstName": "Тестовый юзер",
            "lastName": "Тестовая фамилия",
            "sex": "MALE",
            "enabled": true,
            "photoURL": "photo.jpg",
            "login": "tester",
            "password": "1234"
        });

        console.log(" begin sendData()........... ");
        jQuery.ajax({
            type: "PUT",
            url: "http://localhost:8080/rest/admin/users/",
            data: data,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data){
                console.log("Данные отправлены.");
            },
            failure: function(errorMsg) {
                console.log("Ошибка.");
            }
        });

        console.log(" end sendData()........... ");
    }
</script>