<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="dao.*"%>
<%@ page import="domain.*"%>

<jsp:include page="../Header.jsp" />
<%
List contacts = (List)request.getAttribute("contacts");
if(contacts == null){
    out.println("Error");
    return;
}
if(contacts.size() == 0){
    out.println("No contact found");
    return;
}
%>
<ul class="breadcrumb">
  <li><a href="ListContactServlet?action=update">List contacts</a> <span class="divider">/</span></li>
  <li><a href="CreateContactGroupServlet?idContact=<%= request.getParameter("idContact") %>">Create contact group</a> <span class="divider">/</span></li>
  <li class="active">Add contact</li>
</ul>
<br />
<form method="post">
	<table class="table table-bordered table-hover">
		<tr>
			<th>Siret Number</th>
			<th>Firstname</th>
			<th>Lastname</th>
			<th>Email</th>
			<th>AddContact</th>
		</tr>
		<%
			for (int i = 0; i < contacts.size(); i++) {
				IContact contact = (IContact)contacts.get(i);

			            out.println("<tr>");
			            if(contact instanceof IEntreprise){
			                out.println("<td>" + ((IEntreprise)contact).getNumSiret() + "</td>");
			            } else {
			                out.println("<td>N/P</td>");
			            }
			            out.println("<td>" + contact.getFirstname() + "</td>");
			            out.println("<td>" + contact.getLastname() + "</td>");
			            out.println("<td>" + contact.getEmail() + "</td>");
			            out.println("<td><input name='contacts' value='"+ contact.getId() +"' type='checkbox' /></td>");
			            out.println("</tr>");
			        }
		%>
	</table>
	
	<p class="text-center">
		<button type="reset" class="btn">Reset</button>
		<button type="submit" class="btn btn-primary">Add contacts</button>
	</p>

</form>
<jsp:include page="../Footer.jsp" />