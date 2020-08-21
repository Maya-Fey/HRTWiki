<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
			<c:forEach var="category" items="${ sidebar.entrySet() }">
				<table>
					<tr>
						<td>
							${ category.getKey().toString() }
						</td>
					</tr>
					<c:forEach var="link" items="${ category.getValue() }">
					<tr>
						<td>
							<a href="${ link.getPath() }">
								${ link.getName() }
							</a>
						</td>
					</tr>
					</c:forEach>
				</table>
			</c:forEach>