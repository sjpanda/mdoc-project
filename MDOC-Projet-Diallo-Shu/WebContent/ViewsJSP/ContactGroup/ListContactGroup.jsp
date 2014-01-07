<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="dao.IDAOContactGroup" %>
<%@ page import="domain.*" %>

<jsp:include page="../Header.jsp" />
<%
List contactGroups = (List)request.getAttribute("contactGroups");
if(contactGroups == null){
    out.println("Error");
    return;
}
if(contactGroups.size() == 0){
    out.println("No contact found");
    return;
}

%>
<ul class="breadcrumb">
  <li><a href="ListContactServlet?action=list">List contacts</a> <span class="divider">/</span></li>
  <li class="active">List contact group</li>
</ul>
<table class="table table-bordered table-hover">
    <tr>
        <th>Name</th>
        <th>Views contacts</th>

    </tr>
    <%
    	for (int i = 0; i < contactGroups.size(); i++) {
                Object result = (Object) contactGroups.get(i);
                IContactGroup contactGroup = (IContactGroup) result;
                out.println("<tr>");
                out.println("<td>" + contactGroup.getGroupName() +"</td>");
                out.println("<td><a href=\"ListContactsGroupServlet?idContactGroup=" + contactGroup.getGroupId()
                        + "&idContact="+ request.getParameter("idContact") +"\">View contacts</a></td>");
                out.println("</tr>");
            }
    %>
</table>

<jsp:include page="../Footer.jsp" />