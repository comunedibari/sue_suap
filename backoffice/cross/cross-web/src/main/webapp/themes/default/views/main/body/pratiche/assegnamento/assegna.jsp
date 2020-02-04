<%-- 
    Document   : lista
    Created on : 26-gen-2012, 15.21.12
    Author     : CS
--%>

<%
    String path = request.getContextPath();
    String url = path + "/pratiche/nuove/assegna.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
    //^^CS AGGIUNTA
    //    var url = document.URL;
    //    url= url.split("?");
    //    if(url.length>0)
    //    {
    //        url0=url[0].replace(".htm","/ajax.htm");
    //        url=url0+"?"+url[1];
    //    }
    //    else
    //    {
    //        url=url0;
    //    } 
    var url = getUrl();
    $(document).ready(function()
    {

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: ['<spring:message code="pratica.utente.nominativo"/>',
                        '<spring:message code="pratica.utente.username"/>',
                        '<spring:message code="pratica.utente.codicefiscale"/>',
                        '<spring:message code="pratica.azione"/>'],
                    colModel:
                            [
                                {
                                    name: 'nominativo',
                                    index: 'nominativo',
                                    width: 55
                                },
                                {
                                    name: 'username',
                                    index: 'username'

                                },
                                {
                                    name: 'codiceFiscale',
                                    index: 'codiceFiscale'
                                },
                                {
                                    name: 'codiceUtente',
                                    index: 'codiceUtente',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function(cellvalue, options, rowObject) {
                                        if ($("#abilitazione").val() === "true") {
                                            var link = '<div id="button"><form action="<%=url%>" method="post" ><input type="hidden" id="id_utente" name="id_utente" value="' + rowObject["codiceUtente"] + '"><input type="hidden" name="id_pratica" value="${id_pratica}"><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title="<spring:message code="pratica.button.assegna"/>"><div class="bottone_assegna_utente"></div></button></form> </div> ';
                                            return link;
                                        } else {
                                            var link = "<div>Privilegi non adeguati per procedere all'assegnamento</div>"
                                            return link;
                                        }
                                    }
                                }],
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    sortname: 'nominativo',
                    viewrecords: true,
                    sortorder: "desc",
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width(),
//                    gridComplete: function() {
//                        var ids = $(".list_azioni");
//                        for (var i = 0; i < ids.length; i++) {
//                            var id = $($(".list_azioni")[i]).html().replace(/<\/?[^>]+>/gi, '');
//                            var html = $("#button").clone().html().replace("_ID_", id);
//                            $($(".list_azioni")[i]).html(html);
//                        }
//                    }
                }).navGrid('#pager', {edit: false, add: false, del: false, search: false});
    });
</script>
<div id="accettazione_pratiche">
    <div class="tableContainer">
        <div id="descrizionepraticadiv">
            <div>${descrizionepratica}</div>
            <input id="abilitazione" name="abilitato" value="${abilitato}" style="display:none">
        </div>
            <div>
                <table id="list"><tr><td/></tr></table> 
                <div id="pager"></div> 
            </div>
            <!--    <div class="hidden" id="button">
                    <form action="<%=url%>" method="post" >
                        <input type="hidden" id="id_utente" name="id_utente" value="_ID_">
                        <input type="hidden" name="id_pratica" value="${id_pratica}">
                        <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title='<spring:message code="pratica.button.assegna"/>'>
                            <div class="bottone_assegna_utente"></div>
                        </button>
                    </form>
                </div>-->
        </div>

</div>