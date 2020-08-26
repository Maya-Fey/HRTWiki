<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Claire's - Settings</title>
		<link rel="stylesheet" href="admin.css">
	</head>
	<body>
		<div id="sidebar">
			<jsp:directive.include file="sidebar.jsp"/>
		</div><div id="main">
			<div>
				<span>
					Change Password
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
						var validname = "${ name_allowedchars }";
						var validpronoun = "${ pronoun_allowedchars }";
						
						if(!validateField(form.name, "Display Name", validname, 2, 64)) return false;
						if(!validateField(form.pronouns, "Pronouns", validpronoun, 3, 20)) return false;
						
						return true;
					}
				</script>
				<form id="changesettings" method="POST" onsubmit="return form_validator(this)" action="/admin/settings.jsp">
					<h2>Change Settings</h2>
					<c:if test="${failure_reason != null}">
						<div id="settingschangefailure">
							${ failure_reason }
						</div>
					</c:if>
					<c:if test="${success != null}">
						<div id="settingschangesuccess">
							Settings updated
						</div>
					</c:if>
					<table>
						<tr>
							<td>Display Name</td>
							<td><input type="text" name="name" value="${ displayName }"></td>
						</tr>
						<tr>
							<td>Pronouns</td>
							<td><input type="text" name="pronouns" value="${ pronouns }"></td>
						</tr>
						<tr>
							<td>Role</td>
							<td>${ role }</td>
						</tr>
						<tr>
							<td>Password</td>
							<td>******* (<a href="password.jsp">change</a>)</td>
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