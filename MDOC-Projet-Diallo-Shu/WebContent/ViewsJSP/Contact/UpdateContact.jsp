<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="dao.*"%>
<%@ page import="domain.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Set"%>

<jsp:include page="../Header.jsp" />

<%
	IContact contact = (IContact)request.getAttribute("contact");
		String siretNum = "N/P";
		if(contact instanceof IEntreprise){
	siretNum = String.valueOf(((IEntreprise)contact).getNumSiret());
		}

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
%>
<ul class="pager">
  <li class="previous">
    <a href="ListContactServlet?action=update">&larr; Back contact list</a>
  </li>
</ul>
<form action="UpdateContactServlet" method="post"
	class="form-horizontal">
	<input type="hidden" name="id" value="<%=contact.getId()%>" />
	<h3>Siret Number :</h3>
	<br />
	<div class="control-group">
		<label class="control-label" for="inputSiretNum">Siret Number</label>
		<div class="controls">
			<input type="text" id="inputSiretNum" name="siretNum"
				value="<%= siretNum %>">
		</div>
	</div>
	<h3>Personal information :</h3>
	<br />
	<div class="control-group">
		<label class="control-label" for="inputFname">First Name</label>
		<div class="controls">
			<input type="text" id="inputFName" name="fname"
				value="<%=contact.getFirstname()%>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="inputLname">Last Name</label>
		<div class="controls">
			<input type="text" id="inputLName" name="lname"
				value="<%=contact.getLastname()%>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="inputEmail">Email</label>
		<div class="controls">
			<input type="text" name="email" id="inputEmail"
				value="<%=contact.getEmail()%>">
		</div>
	</div>


	<h3>Address :</h3>
	<br />
	<div class="control-group">
		<label class="control-label" for="inputStreet">Street</label>
		<div class="controls">
			<input type="text" name="street" id="inputStreet"
				value="<%=contact.getAddress().getStreet()%>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="inputZipCode">Zip code</label>
		<div class="controls">
			<input type="text" name="zip" id="inputZipCode"
				value="<%=contact.getAddress().getZip()%>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="inputCity">City</label>
		<div class="controls">
			<input type="text" name="city" id="inputCity"
				value="<%=contact.getAddress().getCity()%>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="inputCountry">Country</label>
		<div class="controls">
			<input type="text" name="country" id="inputCountry"
				value="<%=contact.getAddress().getCountry()%>">
		</div>
	</div>

	<h3>Phone numbers</h3>
	<div class="control-group">
		<label class="control-label" for="inputHome">Home</label>
		<div class="controls">
			<input type="text" name="homepn" id="inputHome" value="<%=home%>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="inputOffice">Office</label>
		<div class="controls">
			<input type="text" name="officepn" id="inputOffice"
				value="<%=office%>">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="inputMobile">Mobile</label>
		<div class="controls">
			<input type="text" name="mobilepn" id="inputMobile"
				value="<%=mobile%>">
		</div>
	</div>
	<div class="control-group">
		<div class="controls">
			<button type="reset" class="btn">Reset</button>
			<button type="submit" class="btn btn-primary">Update</button>
		</div>
	</div>
</form>

<jsp:include page="../Footer.jsp" />