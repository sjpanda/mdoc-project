<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Set"%>
<%@ page import="dao.*"%>
<%@ page import="domain.*"%>

<jsp:include page="../Header.jsp" />
<%
	List contacts = (List) request.getAttribute("contacts");
	if (contacts == null) {
		out.println("Error");
		return;
	}
	if (contacts.size() == 0) {
%>
		<div class="alert alert-info">
		    <h3 class="text-center">No contact found </h3>
		</div>
		<jsp:include page="../Footer.jsp" />
<%
        return;
	}
%>
<table class="table table-bordered table-hover">
	<tr>
		<th>Siret Number</th>
		<th>Firstname</th>
		<th>Lastname</th>
		<th>Email</th>
		<th>Street</th>
		<th>Zip Code</th>
		<th>City</th>
		<th>Country</th>
<!-- 		<th>Home</th> -->
<!-- 		<th>Office</th> -->
<!-- 		<th>Mobile</th> -->
		<%
			if (request.getParameter("action").equals("update")) {
				out.println("<th>Update</th><th>Delete</th><th>Add book</th>");
			} else {
				out.println("<th>Book</th>");
			}
		%>
	</tr>
	<%
		for (int i = 0; i < contacts.size(); i++) {
		IContact contact = (IContact) contacts.get(i);
		IAddress address = contact.getAddress();

		out.println("<tr>");
		if (contact instanceof IEntreprise) {
			out.println("<td>" + ((IEntreprise) contact).getNumSiret()
			+ "</td>");
		} else {
			out.println("<td>N/P</td>");
		}
		out.println("<td>" + contact.getFirstname() + "</td>");
		out.println("<td>" + contact.getLastname() + "</td>");
		out.println("<td>" + contact.getEmail() + "</td>");
		out.println("<td>" + address.getStreet() + "</td>");
		out.println("<td>" + address.getZip() + "</td>");
		out.println("<td>" + address.getCity() + "</td>");
		out.println("<td>" + address.getCountry() + "</td>");

// 		Set<IPhoneNumber> phoneNumbers = contact.getProfiles();
// 		String home = "";
// 		String office = "";
// 		String mobile = "";
// 		if (phoneNumbers != null) {
// 			for (IPhoneNumber phoneNumber : phoneNumbers) {
// 		if (phoneNumber.getPhoneKind().equalsIgnoreCase("home")) {
// 			home = phoneNumber.getPhoneNumber();
// 		}
// 		if (phoneNumber.getPhoneKind().equalsIgnoreCase(
// 				"office")) {
// 			office = phoneNumber.getPhoneNumber();
// 		}
// 		if (phoneNumber.getPhoneKind().equalsIgnoreCase(
// 				"mobile")) {
// 			mobile = phoneNumber.getPhoneNumber();
// 		}
// 			}
// 		}
// 		home = "<td>" + home + "</td>";
// 		office = "<td>" + office + "</td>";
// 		mobile = "<td>" + mobile + "</td>";
// 		out.println(home);
// 		out.println(office);
// 		out.println(mobile);

		if (request.getParameter("action").equals("update")) {
			out.println("<td><a href=\"UpdateContactServlet?idContact="
			+ contact.getId() + "\">Update</a></td>");
			out.println("<td><a href=\"#myModal" + i
			+ "\" data-toggle=\"modal\">Delete</a></td>");
			out.println("<td><a href=\"CreateContactGroupServlet?idContact="
			+ contact.getId() + "\">Add book</a></td>");
		} else {
			out.println("<td><a href=\"ListContactGroupServlet?idContact="
			+ contact.getId() + "&action=view\">List</a></td>");
		}
		out.println("</tr>");
	%>
	<div id="myModal<%=i%>" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h3 id="myModalLabel">Delete contact</h3>
		</div>
		<div class="modal-body">
			<p>
				Are you sure you want to delete this contact :
				<%=contact.getFirstname()%></p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal">Close</button>
			<a class="btn btn-primary"
				href="DeleteContactServlet?idContact=<%=contact.getId()%>">Delete</a>
		</div>
	</div>
	<%
		}
	%>
</table>

<jsp:include page="../Footer.jsp" />