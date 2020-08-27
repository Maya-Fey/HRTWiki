<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="claire.hrt.wiki.data.enumerate.UserRole" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Claire's - Add User</title>
		<link rel="stylesheet" href="admin.css">
	</head>
	<body>
		<div id="sidebar">
			<jsp:directive.include file="sidebar.jsp"/>
		</div><div id="main">
			<div>
				<span>
					Add User
				</span>
				<span>
					Time
				</span>
			</div><div>
				<script type="text/javascript">
					function containsOnly(checkstr, valid)
					{
						let validFinal = true;
						for(i = 0; i < checkstr.length; i++)
						{
							let ch = checkstr.charAt(i);
							if(valid.indexOf(ch) == -1)
								return false;
						}
						return true;
					}
					
					function validateField(field, name, valid, min, max)
					{
						if(!containsOnly(field.value, valid))
						{
							alert("Allowed " + name + " character set: '" + validpassword + "'.");
							field.focus();
							return (false);
						}
							
						if(field.value.length < min)
						{
							alert("Minimum " + name + " + length is " + min + " characters");
							field.focus();
							return false;
						}
				
						if(field.value.length > max)
						{
							alert("Please do not enter more than " + max + " + characters in the \"" + name + "\" field.");
							field.focus();
							return false;
						}
						
						return true;
					}
					
					
					function form_validator(form) {
						var validusername = "${ username_allowedchars }";
						var validname = "${ name_allowedchars }";
						var validpronoun = "${ pronoun_allowedchars }";
						var validpassword = "${ password_allowedchars }";
						
						if(!validateField(form.username, "Userame", validusername, 1, 32)) return false;
						if(!validateField(form.name, "Display Name", validname, 2, 64)) return false;
						if(!validateField(form.pronouns, "Pronouns", validpronoun, 3, 20)) return false;
						if(!validateField(form.password, "Password", validpassword, 8, 64)) return false;
						
						return true;
					}
				</script>
				<form id="adduser" method="POST" onsubmit="return form_validator(this)" action="/admin/settings.jsp">
					<h2>Change Settings</h2>
					<c:if test="${failure_reason != null}">
						<div id="useraddfailure">
							${ failure_reason }
						</div>
					</c:if>
					<c:if test="${success != null}">
						<div id="useraddsuccess">
							User added
						</div>
					</c:if>
					<table>
						<tr>
							<td>Username</td>
							<td><input type="text" name="username"></td>
						</tr>
						<tr>
							<td>Display Name</td>
							<td><input type="text" name="name"></td>
						</tr>
						<tr>
							<td>Pronouns</td>
							<td><input type="text" name="pronouns"></td>
						</tr>
						<tr>
							<td>Role</td>
							<td>
								<select name="role">
								<c:forEach var="role" items="${ UserRole.values() }">
									<option value="${ role.ordinal() }">${ role.toString() }</option>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>Temp Password</td>
							<td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="Change Settings"/></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>