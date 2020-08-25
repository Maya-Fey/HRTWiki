<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Claire's - Change Password</title>
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
					
					function validatePassword(field)
					{
						if(!containsOnly(field.value, validpassword))
						{
							alert("Allowed password character set: '" + validpassword + "'.");
							field.focus();
							return (false);
						}
							
						if(field.value.length < 8)
						{
							alert("Minimum password length is 8 characters");
							field.focus();
							return false;
						}
				
						if(field.value.length > 64)
						{
							alert("Please do not enter more than 64 characters in the \"Password\" field.");
							field.focus();
							return false;
						}
						
						return true;
					}
					
					
					function form_validator(form) {
						var validpassword = "${ password_allowedchars }";
						
						if(!validatePassword(form.curpassword)) return false;
						if(!validatePassword(form.password)) return false;
						if(!validatePassword(form.confirmpassword)) return false;
				
						if(form.password.value != form.confirmpassword.value)
						{
							alert("Passwords do not match");
							return false;
						}
						
						return true;
					}
				</script>
				<form id="changepassword" method="POST" onsubmit="return form_validator(this)" action="/admin/changepassword.jsp">
					<h2>Login</h2>
					<c:if test="${failure_reason != null}">
						<div>
							${ failure_reason }
						</div>
					</c:if>
					<table>
						<tr>
							<td>Current Password</td>
							<td><input type="password" name="curpassword"></td>
						</tr>
						<tr>
							<td>Password</td>
							<td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td>Confirm Password</td>
							<td><input type="password" name="confirmpassword"></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="Change Password"/></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>