<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>

<jsp:include page="../Header.jsp" />

<form action="CreateContactServlet" method="post"
	class="form-horizontal">
	<div class="control-group">
		<label class="control-label" for="inputSiretNum">Siret number</label>
		<div class="controls">
			<input type="text" id="inputSiretNum" name="siretNum"
				Value="123456789" placeholder="Siret number" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="inputFname">First Name</label>
		<div class="controls">
			<input type="text" id="inputFName" name="fname" Value="Test4"
				placeholder="Firstname" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="inputLname">Last Name</label>
		<div class="controls">
			<input type="text" id="inputLName" name="lname" Value="Test4"
				placeholder="Lastname" />
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="inputEmail">Email</label>
		<div class="controls">
			<input type="text" name="email" id="inputEmail" Value="Test4"
				placeholder="Email" />
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="inputStreet">Street</label>
		<div class="controls">
			<input type="text" name="street" id="inputStreet" Value="Test4"
				placeholder="Street" />
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="inputZip">Zip code</label>
		<div class="controls">
			<input type="text" name="zip" id="inputZip" Value="Test4"
				placeholder="Zip code" />
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="inputCity">City</label>
		<div class="controls">
			<input type="text" name="city" id="inputCity" Value="Test4"
				placeholder="City" />
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="inputCountry">Country</label>
		<div class="controls">
			<input type="text" name="country" id="inputCountry" Value="Test4"
				placeholder="Country" />
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="inputCountry">Home</label>
		<div class="controls">
			<input type="text" name="homepn" id="inputHomepn" Value="777"
				placeholder="Home phone number" />
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="inputOfficepn">Office</label>
		<div class="controls">
			<input type="text" name="officepn" id="inputOfficepn" Value="777"
				placeholder="Office phone number" />
		</div>
	</div>

	<div class="control-group">
		<label class="control-label" for="inputOfficepn">Mobile</label>
		<div class="controls">
			<input type="text" name="mobilepn" id="inputOfficepn" Value="777"
				placeholder="Mobile phone number" />
		</div>
	</div>

	<div class="control-group">
		<div class="controls">
			<button type="reset" class="btn">Reset</button>
			<button type="submit" class="btn btn-primary">Create</button>
		</div>
	</div>
</form>
<jsp:include page="../Footer.jsp" />