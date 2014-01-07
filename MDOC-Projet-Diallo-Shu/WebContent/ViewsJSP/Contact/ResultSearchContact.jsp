<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="domain.*"%>
<%@ page import="dao.*"%>
<jsp:include page="../Header.jsp" />
<%
	List contacts = (List) request.getAttribute("contacts");
	Iterator iter = contacts.iterator();
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
		<th>Home</th>
		<th>Office</th>
		<th>Mobile</th>
	</tr>
	<%
		while (iter.hasNext()) {
		// 		Object[] o = (Object[])iter.next();
		// 		Contact contact = (Contact)o[0];
		// 		Address address = (Address)o[1];

		IContact contact = (IContact) iter.next();
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

			            Set<IPhoneNumber> phoneNumbers = contact.getProfiles();
			            String home = "";
			            String office = "";
			            String mobile = "";
			            if (phoneNumbers != null) {
			                for (IPhoneNumber phoneNumber : phoneNumbers) {
			                    if (phoneNumber.getPhoneKind().equalsIgnoreCase("home")) {
			                        home = phoneNumber.getPhoneNumber();
			                    }
			                    if (phoneNumber.getPhoneKind().equalsIgnoreCase(
			                            "office")) {
			                        office = phoneNumber.getPhoneNumber();
			                    }
			                    if (phoneNumber.getPhoneKind().equalsIgnoreCase(
			                            "mobile")) {
			                        mobile = phoneNumber.getPhoneNumber();
			                    }
			                }
			            }
			            home = "<td>" + home + "</td>";
			            office = "<td>" + office + "</td>";
			            mobile = "<td>" + mobile + "</td>";
			            out.println(home);
			            out.println(office);
			            out.println(mobile);
			}
	%>
</table>

<jsp:include page="../Footer.jsp" />