<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="dao.*" %>
<%@ page import="domain.*" %>

<jsp:include page="../Header.jsp" />
<ul class="breadcrumb">
  <li><a href="ListContactServlet?action=update">List contacts</a> <span class="divider">/</span></li>
  <li class="active">Create contact group</li>
</ul>
<form action="CreateContactGroupServlet" method="post" >
        Group contact name : <input type="text" id="inputName" name="groupContactName" Value="Test" placeholder="Group contact name" />
        <button type="reset" class="btn">Reset</button>
        <button type="submit" class="btn btn-primary">Create</button>
        <input type="hidden" name="idContact" value="<%= request.getParameter("idContact") %>" />
</form>
<%
List contactGroups = (List)request.getAttribute("contactGroups");
if(contactGroups == null){
    out.println("Error");
    return;
}
if (contactGroups.size() == 0) {
%>
    <div class="alert alert-info">
        <h3 class="text-center">No contact group found </h3>
    </div>
    <jsp:include page="../Footer.jsp" />
<%
    return;
}

%>
<table class="table table-bordered table-hover">
    <tr>
        <th>Name of book</th>
        <th>Add contact</th>

    </tr>
    <%
    	for (int i = 0; i < contactGroups.size(); i++) {
                Object result = (Object) contactGroups.get(i);
                IContactGroup contactGroup = (IContactGroup) result;
                out.println("<tr>");
                out.println("<td>" + contactGroup.getGroupName() +"</td>");
                out.println("<td><a href=\"AddContactToContactGroup?idContactGroup=" + contactGroup.getGroupId()
                        + "&idContact="+ request.getParameter("idContact") +"\">Add contact</a></td>");
                out.println("</tr>");
            }
    %>
</table>

<jsp:include page="../Footer.jsp" />