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
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="pcfn" uri="/WEB-INF/peopleconsole-fn.tld" %>
<%@ taglib prefix="pcform" uri="/WEB-INF/peopleconsole.tld" %>
<html>
    <head>
        <title><spring:message code="admin.settings.title" /></title>
    </head>
    <body>
        <form:form modelAttribute="userNotificationProcess">
        <form:hidden path="sort" />
        <form:hidden path="column" />
        
        <div>
        
            <fieldset>
                <form:errors path="error" cssClass="error"/>
                
                <div class="panel">
                    <div class="panelRow">
                        <label class="label" for="name">
                            <spring:message code="userNotificationsError.fields.name" />
                        </label>
                        <form:input id="name" path="name" maxlength="255" />
                        <form:errors path="name" cssClass="error"/>
                    </div>
                    
                    <div class="panelRow">
                        <label class="label" for="lastname">
                            <spring:message code="userNotificationsError.fields.lastname" />
                        </label>
                        <form:input id="lastname" path="lastname" maxlength="255" />
                        <form:errors path="lastname" cssClass="error"/>
                    </div>
                    <div class="panelRow">
                        <label class="label" for="email">
                            <spring:message code="userNotificationsError.fields.email" />
                        </label>
                        <form:input id="email" path="email" maxlength="100" />
                        <form:errors path="email" cssClass="error"/>
                    </div>
                    <%--div class="panelRow">
                        <label class="label" for="municipalityCode">
                            <spring:message code="userNotificationsError.fields.municipalityCode" />
                        </label>
                        <form:input id="municipalityCode" path="municipalityCode" maxlength="100" />
                        <form:errors path="municipalityCode" cssClass="error"/>
                    </div --%>
                    <div class="panelRow">
                        <label>
                            <spring:message code="userNotificationsError.fields.searchByDate" />
                        </label>
                        <div class="panelRowDate">
                            <label class="labelDate" for="fromDate">
                                <spring:message code="userNotificationsError.fields.fromDate" />
                            </label>
                             <!-- calendar attaches to existing form element -->
                            <form:input path="from" maxlength="10" name="fromInput" id="fromInput" />
                                <script language="JavaScript">
                                    var o_cal = 
                                    new tcal ({
                                        // input name
                                        'controlname': 'fromInput'
                                    });
                                    // individual template parameters can be modified via the calendar variable
                                    o_cal.a_tpl.yearscroll = false;
                                    o_cal.a_tpl.weekstart = 1;
                                </script>
                            &nbsp;
                            <label class="labelDate">               
                                <spring:message code="userNotificationsError.fields.toDate" />
                            </label>
                            <form:input path="to" maxlength="10" name="toInput" id="toInput"/>
                                <script language="JavaScript">
                                    var o_cal2 = 
                                    new tcal ({
                                        'controlname': 'toInput'
                                    }, o_cal2);
                                </script>
                            <!--form:errors path="error" cssClass="error"/-->
                        </div>
                    </div>
                </div>
                <div class="buttonsbar">
                    <input type="submit" id="searchFilter" name="searchFilter" value="Ricerca" class="button" />
                    <input type="submit" id="cancel" name="cancel" value="< Indietro" class="button"  alt="Torna alla pagina precedente" title="Torna alla pagina precedente"/>
                </div>
                
                
            </fieldset>
        </div>
        
        <c:if test="${!empty userNotificationProcess && !empty userNotificationProcess.pagedListHolders}">
            
            <%--@ include file="/WEB-INF/jsp/includes/filters.jsp"--%>
            <pcform:listHolderTable pagedListHolderId="usernotificationList" 
                pagedListHoldersCache="${userNotificationProcess.pagedListHolders}"
                componentClass="pagedListHolderTable"
                pagerClass="pagedListHolderPager"
                pagerPagesTitleClass="pagedListHolderPagerTitleClass" 
                useTableBodyEvenOddRows="true"
                tableHeadRowClass="logLabelHeader"  
                tableHeadRowCellClass="logLabelHeader"  
                tableClass="log" 
                tableBodyEvenRowClass="logEvenRow" 
                tableBodyOddRowClass="logOddRow"
                rowsForPageClass="textNormal"  
                rowsForPageList="${rowsForPageList}"  
                rowsForPageLabel="${rowsForPageLabel}"  
                rowsForPageRefreshLabel="${rowsForPageRefreshLabel}"  
                highlightTableRows="true"  
                tableRowsHighlightingColor="${tableRowsHighlightingColor}" />   
        
        </c:if>
        
        <c:if test="${empty userNotificationProcess || empty userNotificationProcess.pagedListHolders}">
            <div><spring:message code="admin.userNotification.noResult" /></div>
        </c:if>
        
        </form:form>
    </body>
</html>
