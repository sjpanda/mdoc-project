<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="Library/css/bootstrap.css" type="text/css" />
<link rel="stylesheet" href="Library/css/style1.css" type="text/css" />
<%
	    if(request.getAttribute("title") != null) { 
	    	out.println("<title>"+request.getAttribute("title")+"</title>");
	    }
	    else{
	    	out.println("<title>MDOC</title>");
	    }
    %>
</head>
<body>
	<div class="container-fluid">
		<header id="headBox"> <a href="HomeServlet"><img
			src="Library/images/logo_upmc1.png" alt="UPMC" /></a> <hgroup
			id="headTitleBox">
		<h1>M D O C</h1>
		<h2>Middlewares orientés composants</h2>
		</hgroup> </header>
		<nav class="navbar navbar-static-top">
			<div class="navbar-inner">
				<div class="container">
					<ul class="nav navbar-nav">
						<li id="accueil"><a href="HomeServlet">Accueil</a></li>
						<li id="configuration" class="dropdown"><a
							class="dropdown-toggle" data-toggle="dropdown"
							href="ListContactServlet"> Contact <b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="CreateContactServlet">Create</a></li>
								<li><a href="ListContactServlet?action=update">Update</a></li>
								<li><a href="ListContactServlet?action=list">List
										contact</a></li>
								<li><a href="SearchContactServlet">Search</a></li>
							</ul></li>
                    <%
                            if(request.getSession().getAttribute("user") != null) { 
                            	%>
                            	<li id="generateContact"><a href="GenerateContactsServlet">Generate
                                        contact</a></li>
                        <% }
                        %>
					</ul>
					<div class="pull-right">
					   <ul class="nav pull-right">
					<%
	                        if(request.getSession().getAttribute("user") != null) { 
	                            out.println("<li><a href=\"LogoutServlet\">Log out ( " + request.getSession().getAttribute("user") + " )</a></li>");
	                        }
	                        else{
	                            out.println("<li><a href=\"LoginServlet\">Log in</a></li>");
	                        }
	                    %>
	                    </ul>
                    </div>
				</div>
			</div>
		</nav>

		<div class="row-fluid">
			<div id="content" class="span12">
				<div class="well">
					<%
					    if(request.getAttribute("title") != null) { 
					    	out.println("<h4>"+request.getAttribute("title")+"</h4>");
					    }
					    else{
					    	out.println("<h4>MDOC</h4>");
					    }
				    %>
					<div id="contenu">
						<% 
                        if(request.getAttribute("result") != null) { 
                            out.println("<div class=\"alert alert-success\">" + 
                                "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>" + 
                                "<h3>"+ request.getAttribute("titleResult") +"</h3>" + request.getAttribute("result") + 
                            "</div>");
                         }
                        else if(request.getAttribute("errors") != null){ 
                            out.println("<div class=\"alert alert-error\">" + 
                                    "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>" + 
                                    "<h3>"+ request.getAttribute("titleErrors") +"</h3>" + request.getAttribute("errors") + 
                                "</div>");
                        }                               
                     %>