<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Claire's - Users</title>
		<link rel="stylesheet" href="admin.css">
	</head>
	<body>
		<div id="sidebar">
			<jsp:directive.include file="sidebar.jsp"/>
		</div><div id="main">
			<div>
				<span>
					User List
				</span>
				<span>
					Time
				</span>
			</div><div>
				<table class="datatable">
					<thead>
						<tr>
							<td>
								Username
							</td>
							<td>
								Display Name
							</td>
							<td>
								Pronouns
							</td>
							<td>
								Role
							</td>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="user" items="${ users }">
						<tr>
							<td>
								${ user.getUsername() }
							</td>
							<td>
								${ user.getDisplayName() }
							</td>
							<td>
								${ user.getPronouns() }
							</td>
							<td>
								${ user.getPermissions().toString() }
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<div class="datatablenav">
					<span>
					<c:if test="${ current > per) }">
						<a href="users.jsp?current=0&per=${ per }">
							First
						</a>
					</c:if>
					<c:if test="${ current > 0) }">
						<a href="users.jsp?current=${ (int) Math.max(0, current - per)) }&per=${ per }">
							Prev
						</a>
					</c:if>
					</span>
					<span>
						Showing 
						<select>
						<c:forEach var="selectPer" items="${ new int[] \{ 25, 50, 75, 100 \} }">
						<c:if test="${ selectPer == per }">
							<option value="${ selectPer }" selected="selected">
						</c:if>
						<c:if test="${ selectPer != per }">
							<option value="${ selectPer }">
						</c:if>
						</c:forEach>
						</select>
						users
					</span>
					<span>
					<c:if test="${ current > (max - per)) }">
						<a href="users.jsp?current=${ current + per }&per=${ per }">
							Next
						</a>
					</c:if>
					<c:if test="${ current < (max - per)) }">
						<a href="users.jsp?current=${ max - per }&per=${ per }">
							Last
						</a>
					</c:if>
					</span>
				</div>
			</div>
		</div>
	</body>
</html>