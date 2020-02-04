<%
    String urlparams = "?ServiceId=ETTRIB1&CodiceFiscale=" + 000000000000 + "&Email=" + "pincopallo@burp.it" + "&Importo=" + "100";

    request.setAttribute("urlparams", urlparams); 
%>


        <%
        String linkUrl = "/people/startPayment.do" + ((String) request.getAttribute("urlparams"));

        %>
        <a class="tab" href="<%=linkUrl%>" title="Pagamento" accesskey="p">Pagamento</a> 