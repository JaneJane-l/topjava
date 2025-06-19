<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Таблица</title>
    <style type="text/css">
        TABLE {
            width: 500px; /* Ширина таблицы */
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
        }
        TD, TH {
            padding: 3px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table border="1">

    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>

    <c:forEach items="${meals}" var="meal">

        <c:choose>
            <c:when test="${meal.isExcess()}">
                <tr style="color: red">
                    <td>${meal.getDateTime() }</td>
                    <td>${meal.getDescription()}</td>
                    <td>${meal.getCalories()}</td>
                    <td>${meal.isExcess()}</td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr style="color: green">
                    <td>${meal.getDateTime() }</td>
                    <td>${meal.getDescription()}</td>
                    <td>${meal.getCalories()}</td>
                    <td>${meal.isExcess()}</td>
                </tr>
            </c:otherwise>
        </c:choose>


    </c:forEach>


</table>
</body>
</html>