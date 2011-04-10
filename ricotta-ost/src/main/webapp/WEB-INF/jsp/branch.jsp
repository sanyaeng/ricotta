<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="header.jsp" />
<body>
<a href="/">Home</a> | <a href="/proj/">Projects</a>
<div class="pageHeading">Project: <c:out value="${projName}" /> (<c:out value="${branchName}" />)</div>
<p />
<h3>Languages</h3>
<table>
		<thead>
		<tr>
			<th>Code</th>
			<th>Name</th>
			<th>Default</th>
		</tr>
	</thead>
	<tbody>
	<c:set var="even" scope="page" value="${true}" />
	<c:forEach items="${languages}" var="plm">
		<c:set var="even" scope="page" value="${!even}" />
		<tr class="evenRow<c:out value='${even}' />">
			<td><c:out value="${plm.lang.code}" /></td>
			<td><a href="lang/<c:out value='${plm.lang.code}' />/" ><c:out value="${plm.lang.name}" /></a></td>
			<td><c:out value="${plm.defaultCode}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<a href="lang/create.html">Add language</a>

<h3>Tokens</h3>
<a href="tokn/">Edit tokens...</a>

<h3>Contexts</h3>
<table>
		<thead>
		<tr>
			<th>Name</th>
			<th>Description</th>
		</tr>
	</thead>
	<tbody>
	<c:set var="even" scope="page" value="${true}" />
	<c:forEach items="${ctxts}" var="c">
		<c:set var="even" scope="page" value="${!even}" />
		<tr class="evenRow<c:out value='${even}' />">
			<td><c:out value="${c.name}" /></td>
			<td><c:out value="${c.description}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<a href="uploadCtxt.html">Create context...</a>

<h3>Subsets</h3>
<table>
		<thead>
		<tr>
			<th>Name</th>
			<th>Description</th>
		</tr>
	</thead>
	<tbody>
	<c:set var="even" scope="page" value="${true}" />
	<c:forEach items="${subsets}" var="a">
		<c:set var="even" scope="page" value="${!even}" />
		<tr class="evenRow<c:out value='${even}' />">
			<td><c:out value="${a.name}" /></td>
			<td><c:out value="${a.description}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<a href="subset.html">Create Subset...</a>

<form action="deleteBranches.html" method="post" name="deleteBranches" id="deleteBranches">
<h3>Branches</h3>
<table>
		<thead>
		<tr>
			<th>Delete</th>
			<th>Name</th>
			<th>Description</th>
			<th>Date</th>
		</tr>
	</thead>
	<tbody>
	<c:set var="even" scope="page" value="${true}" />
		<tr class="evenRow<c:out value='${even}' />">
			<td></td>
			<td><a href="?" ><c:out value="${HEAD.name}" /></a></td>
			<td><c:out value="${HEAD.description}" /></td>
			<td><c:out value="${HEAD.datum}" /></td>
		</tr>
	<c:forEach items="${branches}" var="v">
		<c:set var="even" scope="page" value="${!even}" />
		<tr class="evenRow<c:out value='${even}' />">
			<td><input type="checkbox" name="versions" id="versions" value="<c:out value='${v.keyString}' />" /></td>
			<td><a href="../<c:out value='${v.name}' />/"><c:out value="${v.name}" /></a></td>
			<td><c:out value="${v.description}" /></td>
			<td><c:out value="${v.datum}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<c:if test="${pageContext.request.userPrincipal.name == project.owner}">
	<input type="submit" id="deleteSelected" name="deleteSelected" value="Delete selected branches" />
</c:if>
</form>
<a href="create.html">Create branch...</a>

<h3>Users</h3>
<form action="" method="post" name="deleteForm" id="deleteForm">
<input type="submit" id="deleteSelected" name="deleteSelected" value="Delete selected users" />
<table>
		<thead>
		<tr>
			<th>Delete</th>
			<th>Email</th>
		</tr>
	</thead>
	<tbody>
	<c:set var="even" scope="page" value="${true}" />
	<c:forEach items="${users}" var="u">
		<c:set var="even" scope="page" value="${!even}" />
		<tr class="evenRow<c:out value='${even}' />">
			<td><input type="checkbox" name="users" id="users" value="<c:out value='${u.keyString}' />" /></td>
			<td><c:out value="${u.user}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<input type="submit" id="deleteSelected" name="deleteSelected" value="Delete selected users" /><br />
</form>
<a href="user.html">Add user...</a>

</body>
</html>