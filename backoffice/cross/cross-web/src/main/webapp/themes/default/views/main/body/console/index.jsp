<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    var url = getUrl();

    function setLastUpdate() {
        var now = getTodayDateString();
        $('.last_update').text(now);
    }

    function poll() {
        setTimeout(function() {
            $.ajax({
                url: "<%= request.getContextPath()%>/console/index/info.htm",
                type: "POST",
                success: function(data) {
//                    var praticheNonProtocollate = data.praticheNonProtocollate;
//                    var mailNonInviate = data.mailNonInviate;
//                    $('.praticheNonProtocollate').text(praticheNonProtocollate);
//                    $('.mailNonInviate').text(mailNonInviate);
                    $('#errori').trigger('reloadGrid');
                    setLastUpdate();
                },
                dataType: "json",
                complete: poll,
                timeout: 5000
            });
        }, 30000);
    }

    $(document).ready(function() {
        $("#errori").jqGrid({
            url: url,
            datatype: "json",
            colNames: ['Id errore', 'Data',
                'Descrizione', 'Trace', "Azione"],
            colModel: [
                {name: 'idErrore', index: 'idErrore', hidden: true},
                {name: 'data', index: 'data'},
                {name: 'descrizione', index: 'descrizione'},
                {name: 'trace', index: 'trace'},
                {
                    name: 'azione',
                    index: 'idErrore',
                    classes: "list_azioni",
                    sortable: false,
//                    formatter: function(cellvalue, options, rowObject) {
//                        var link = '<div class="gridActionContainer"><a href="<%=path%>errore/cancella?idErrore=' + rowObject["idErrore"]'" ><img src="${path}themes/default/css/images/bottone_gestisci2.png"></a></div>';
//                        return link;
//                    }
                    formatter: function(cellvalue, options, rowObject) {
                        var link = '<div class="gridActionContainer"><form action="<%=path%>/console/gestisci.htm"><input type="hidden" name="idErrore" value="' + rowObject["idErrore"] + '" ><button name="azione" class="cancella_ente" value="hide" /><button name="azione" class="modifica_ente" value="dettaglio" /> </form></div>';
                        return link;
                    }
                }
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#pagerErrori',
            sortname: 'data',
            jsonReader: {repeatitems: false},
            viewrecords: true,
            sortorder: "desc",
            caption: 'Errori rilevati',
            height: 'auto',
            width: $('.tableContainer').width()
        });
        $("#errori").jqGrid('navGrid', '#pagerErrori', {
            add: false,
            edit: false,
            del: false,
            search: false,
            refresh: true
        });
        setLastUpdate();
        //poll();
        $("#accordion").accordion();
    });
</script>
<h1><spring:message code="console.title"/></h1>
<div>
    <%-- DISABILITO TEMPORANEAMENTE LE PRATICHE NON PROTOCOLLATE
    <h2><spring:message code="console.pratiche.first"/> <a class="praticheNonProtocollate" style="border-bottom: 1px solid;" href="<%= request.getContextPath()%>/console/pratiche.htm">${praticheNonProtocollate}</a> <spring:message code="console.pratiche.last"/>.</h2>
    <h2><spring:message code="console.mail.first"/> <a class="mailNonInviate" style="border-bottom: 1px solid;"  href="<%= request.getContextPath()%>/console/mail.htm">${emailNonInviate}</a> <spring:message code="console.mail.last"/>.</h2>
    --%>
    <div id="impostazioni_div">
        <div class="tableContainer">
            <table id="errori"><tr><td/></tr></table> 
            <div id="pagerErrori"></div>
        </div>
    </div>
</div>
<spring:message code="console.lastupdate"/>: <span class="last_update"></span>