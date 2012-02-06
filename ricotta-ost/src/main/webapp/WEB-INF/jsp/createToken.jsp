<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="header.jsp" />
<body>
<a href="/index.html">Home</a> | <a href="/proj/index.html">Projects</a> | <a href="../index.html"><c:out value="${projName}" /></a> | <a href="index.html">Tokens</a> 
<div class="pageHeading">Create Token</div>
<form id="token" name="token" action="" method="post">
<table>
	<tr>
		<td>Token Name:</td>
		<td><input id="name" name="name" type="text" value="" /></td>
	</tr>
	<tr>
		<td>Token Description:</td>
		<td><input id="description" name="description" type="text" value="" /></td>
	</tr>
	<tr>
		<td>Context:</td>
                <td><select name="ctxt" id="ctxt">
                        <c:forEach items="${viewContexts}" var="c">
                                <option value="<c:out value='${c.key}'/>" <c:if test="${token.viewContext == c.value.primaryKey}"> selected="selected" </c:if> >
                                        <c:out value="${c.value.name}" /></option>
                        </c:forEach>
                </select></td>
	</tr>
	<tr>
		<td>Subsets:</td>
                <c:forEach items="${subsets}" var="subset">
                        <td><input type="checkbox" id="mappings" name="mappings" value="<c:out value='${subset.keyString}' />" title=":<c:out value='${subset.name}' />"  />
                        </td>
                </c:forEach>
	</tr>
	<tr>
		<td></td>
		<td><input id="create" name="create" type="submit" value="Create token" /></td>
	</tr>
</table>
</form>
</body>
</html>