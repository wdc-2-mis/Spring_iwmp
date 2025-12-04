<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Media Report</title>

    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            font-family: Arial;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: center;
            font-size: 14px;
        }
        th {
            background: #3c6eaf;
            color: #fff;
        }
        tr:nth-child(even) {
            background: #f9f9f9;
        }

        .video {
            font-weight: bold;
            color: darkred;
        }
        .image {
            font-weight: bold;
            color: darkgreen;
        }
        .unknown {
            font-weight: bold;
            color: gray;
        }

        a {
            color: #0645AD;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>
    <h2 style="text-align:center; margin-bottom: 15px;">Media Upload Report</h2>

    <table>
        <thead>
            <tr>
                <th>S.No</th>
                <th>Mahotsav Registration ID</th>
                <th>Media URL</th>
                <th>Detected Media Type</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${mediaList}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${item.mahotsavReg.mahotsavRegId}</td>
                    <td>
                        <a href="${item.mediaUrl}" target="_blank">${item.mediaUrl}</a>
                    </td>

                    <td>
                        <c:choose>
    <c:when test="${item.media_type.toString() == 'V'}">
        <span class="video">VIDEO</span>
    </c:when>
    <c:when test="${item.media_type.toString() == 'I'}">
        <span class="image">IMAGE</span>
    </c:when>
    <c:otherwise>
        <span class="unknown">NA</span>
    </c:otherwise>
</c:choose>

                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
