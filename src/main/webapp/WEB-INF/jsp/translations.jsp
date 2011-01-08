<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="header.jsp" />
<body>
<div class="pageHeading">Translations - <c:out value="${language.name}" /> (<c:out value="${language.code}" />)</div>
<form id="translations" name="translations" action="" method="post" >
<input type="submit" value="Save" />
<table>
		<thead>
		<tr>
			<th width="15%">Name</th>
			<th width="15%">Description</th>
			<th width="40%">Value</th>
			<th width="30%">Default</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${translations}" var="t">
		<tr id="<c:out value='${t.token.keyString}' />" title="<c:out value='${t.key.kind}' />">
			<td><c:out value="${t.token.name}" /></td>
			<td><c:out value="${t.token.description}" /></td>
			<td><input id="<c:out value='${t.keyString}' />" 
				name="<c:out value='${t.token.keyString}' />" 
				type="text" value="<c:out value='${t.local.local}' />" /></td>
			<td><c:out value="${t.parent.local}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<input type="submit" value="Save" />
</form>
</body>
</html>