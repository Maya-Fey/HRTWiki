<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Claire's - Login</title>
		<link rel="stylesheet" href="admin.css">
	</head>
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
		
		function form_validator(form) {
			var validpassword = "${ password_allowedchars }";
			var validusername = "${ username_allowedchars }";
			
			if(form.username.value == "")
			{
				alert("Please enter a value for the \"Username\" field.");
				form.username.focus(); 
				return (false);
			}
	
			if(form.username.value.length > 32)
			{
				alert("Please do not enter more than 32 characters in the \"Username\" field.");
				form.username.focus();
				return (false);
			}
			
			if(!containsOnly(form.password.value, validpassword))
			{
				alert("Allowed password character set: '" + validpassword + "'.");
				form.password.focus();
				return (false);
			}
			
			if(!containsOnly(form.username.value, validusername))
			{
				alert("Please only enter letters, digits, and underscores in the \"Username\" field.");
				form.username.focus();
				return (false);
			}
				
			if(form.password.value == "")
			{
				alert("Please enter a value for the \"Password\" field.");
				form.password.focus();
				return (false);
			}
	
			if(form.password.value.length > 64)
			{
				alert("Please do not enter more than 64 characters in the \"Password\" field.");
				form.password.focus();
				return (false);
			}
	
			return true;
		}
	</script>
	<body>
		<form method="POST" onsubmit="return form_validator(this)" action="/admin/login.jsp">
			<h2>Login</h2>
			<c:if test="${failure_reason != null}">
				<div>
					${ failure_reason }
				</div>
			</c:if>
			<table>
				<tr>
					<td>Username</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td>Submit</td>
					<td><input type="submit"/></td>
				</tr>
			</table>
		</form>
	</body>
</html>