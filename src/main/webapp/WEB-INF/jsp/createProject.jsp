<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Projects</title>
</head>
<body>
<form id="project" name="project" action="" method="post">
<table>
	<tr>
		<td>Project Name:</td>
		<td><input id="name" name="name" type="text" value="" /></td>
	</tr>
	<tr>
		<td></td>
		<td><input id="create" name="create" type="submit" value="Create project" /></td>
	</tr>
</table>
</form>
</body>
</html>