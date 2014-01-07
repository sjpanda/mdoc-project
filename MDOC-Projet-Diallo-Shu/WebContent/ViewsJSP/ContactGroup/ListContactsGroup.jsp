<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Set"%>
<%@ page import="dao.*"%>
<%@ page import="domain.*"%>
<%@ page import="util.ApplicationContextUtils"%>
<%@ page import="org.springframework.context.ApplicationContext" %>

<jsp:include page="../Header.jsp" />
<%
    List contacts = (List)request.getAttribute("contactGroups");
    if (contacts == null) {
        out.println("Error");
        return;
    }
    if (contacts.size() == 0) {
        out.println("No contact found idContactGroup = " + request.getParameter("idContactGroup"));
        return;
    }
%>
<ul class="breadcrumb">
  <li><a href="ListContactServlet?action=list">List contacts</a> <span class="divider">/</span></li>
  <li><a href="ListContactGroupServlet?idContact=<%= request.getParameter("idContact") %>">List contact group</a> <span class="divider">/</span></li>
  <li class="active">Contacts in contact group</li>
</ul>
<br />
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
    	for (int i = 0; i < contacts.size(); i++) {          
                        IContact contact = (IContact) contacts.get(i);
                		String siretNum = "N/P";
                		if(contact instanceof IEntreprise){
                			siretNum = String.valueOf(((IEntreprise)contact).getNumSiret());
                		}
                        
                        out.println("<tr>");
                        out.println("<td>"+siretNum+"</td>");
                        out.println("<td>" + contact.getFirstname() + "</td>");
                        out.println("<td>" + contact.getLastname() + "</td>");
                        out.println("<td>" + contact.getEmail() + "</td>");
                        out.println("<td>" + contact.getAddress().getStreet() + "</td>");
                        out.println("<td>" + contact.getAddress().getZip() + "</td>");
                        out.println("<td>" + contact.getAddress().getCity() + "</td>");
                        out.println("<td>" + contact.getAddress().getCountry() + "</td>");

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