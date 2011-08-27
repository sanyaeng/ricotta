<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="header.jsp" />
<body>
<a href="/index.html">Home</a> 
| <a href="/proj/index.html">Projects</a> 
| <a href="/proj/<c:out value='${project.name}' />/index.html"><c:out value="${project.name}" /></a>
<div class="pageHeading">Confirm</div>
<div>Are you sure you want to <c:out value="${param.action}" /> <c:out value="${projName}" />?</div>
<form id="confirm" name="confirm" action="" method="post">
<table>
	<tr>
		<td></td>
		<td><input id="confirmed" name="confirmed" type="submit" value="<c:out value='${param.action}' />" /></td>
	</tr>
</table>
</form>
</body>
</html>