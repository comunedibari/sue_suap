<%--
Copyright (c) 2011, Regione Emilia-Romagna, Italy
 
Licensed under the EUPL, Version 1.1 or - as soon they
will be approved by the European Commission - subsequent
versions of the EUPL (the "Licence");
You may not use this work except in compliance with the
Licence.

For convenience a plain text copy of the English version
of the Licence can be found in the file LICENCE.txt in
the top-level directory of this software distribution.

You may obtain a copy of the Licence in any of 22 European
Languages at:

http://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.

This product includes software developed by Yale University

See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@ page language="java" import="java.util.*" %>
<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String smartCardPersonalDataConsumerURL = (String)request.getAttribute("smartCardPersonalDataConsumerURL");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<object
		classid="CLSID:15D151C8-5180-43C1-9360-4D794663BD6E"
		codebase="http://www.crs.lombardia.it/components/OcsKitCittadino.cab#Version=1,3,4,0"
		id=LIKitCRS
		type="application/x-oleobject">
	</object>
	
  <!--
  <object
		classid="CLSID:3263F297-5CB9-4D8C-A2DB-CDFB8C69CB6D"
		codebase="http://www.crs.lombardia.it/components/OcxCertUpdate.cab#Version=1,3,1,0"
		id=PdlCitSsl
		type="application/x-oleobject">
	</object>
  <object
		classid="CLSID:4384AA75-43AB-4095-84F9-C5B35EC62B5D"
		codebase="http://www.crs.lombardia.it/components/OcxCrsInfo.cab#Version=1,3,1,0"
		id=CRSInfo
		type="application/x-oleobject">
	</object>
  -->
  <link rel="stylesheet" href="styles/signIn.css" />
</head>
<body onload="GetBinaryFileContent_OnClick()">

<div style="top: 0;left: 0;     
			 font-weight: bold; font-size: 18pt;
			 color: #3300FF;background-color: #E5E8EC;">
	<%@ include file="./include/siracHeader.jsp" %>
</div>

<div id="signInBox" align="center">
  <div id="header">
      <div id="logo">
         <img src="images/logo.png" width="200" height="40" alt="PeopleImg" />
      </div>
  </div>
  	        
	  <div id="form">
    <table align="center">
   	  <tr>
    	<td><img src="images/smartcard.jpg" height="30" width="50"/></td>
		<td><b>Accesso con Smartcard</b><br><i>Lettura dati personali da smart card in corso...</i></td>
      </tr>
  	</table>
  </div>

<input type="hidden" name="GetBinaryFileContent_Name" value="EF.Dati_personali"/>

<form name="myForm" action="<%=smartCardPersonalDataConsumerURL%>" method="POST">
<input type="hidden" name="DatiPersonaliRaw" value="" size="150"/>
</form>

<script LANGUAGE=VBScript>
	<!--
	rem PdlCitSsl.Start

	Const KC_E_USER_CANCELLED = -2147220940
	
	Sub ShowError ()
		MsgBox ( _
			"NUMBER: " & Err.Number & vbCrLf _
			& "SOURCE: " & Err.Source & vbCrLf _
			& "DESCRIPTION: " & Err.Description)
	End Sub

	Function CheckError ()
		Select Case Err.Number
			Case 0 CheckError = False
			Case KC_E_USER_CANCELLED
				MsgBox ("Operazione annullata.")
				CheckError = False
			Case Else
				ShowError ()
				Err.Clear
				CheckError = True
		End Select
	End Function
	
	Function ByteAsHexString (TheByte)
		Dim Result
		Result = Hex (CByte (TheByte))
		If Len (Result) = 1 Then
			Result = "0" & Result
		End If
		ByteAsHexString = Result
	End Function
	
	Sub DumpFileContent (FileContent)
		Dim i
		Dim ContentAsString
		
		GetBinaryFileContent_ResultLength.value = UBound (FileContent) + 1
		ContentAsString = ""
		For i = 0 To UBound (FileContent)
			If 0 < i Then
				ContentAsString = ContentAsString & " "
			End If
			ContentAsString = ContentAsString & ByteAsHexString (CByte (FileContent (i)))
		Next
		myform.GetBinaryFileContent_Result.value = ContentAsString
		CheckError ()
	End Sub
	
	Sub DumpFileContent2 (FileContent)
		Dim i
		Dim ContentAsString
		
		ContentAsString = ""
		For i = 0 To UBound (FileContent)
			ContentAsString = ContentAsString & Chr(FileContent (i))
		Next
		myform.DatiPersonaliRaw.value = ContentAsString
		myform.submit
		CheckError ()
		
	End Sub
	
	Sub GetBinaryFileContent (FileId)
		Dim FileContent

		On Error Resume Next
		Err.Clear
		FileContent = LIKitCRS.GetBinaryFileContent (FileId)
		If Not CheckError Then
			DumpFileContent2 FileContent
		End If
	End Sub
	
	Sub GetBinaryFileContent_OnClick ()
		GetBinaryFileContent GetBinaryFileContent_Name.value
	End Sub
	
' -->
</script>
<noscript>Richiesto VBScript abilitato.</noscript>
</body>
</html>	
