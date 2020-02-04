<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="navbar" class="navbar navbar-default    navbar-collapse       h-navbar">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed');
        } catch (e) {
        }
    </script>

    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <!-- #section:basics/navbar.layout.brand -->
            <a href="#" class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    OpenCross
                </small>
            </a>

            <button class="pull-right navbar-toggle navbar-toggle-img collapsed" type="button" data-toggle="collapse" data-target=".navbar-buttons">
                <span class="sr-only">Apri menù utente</span>

                <img src="${path}/themes/default/images/user_cross.jpg" alt="Immagine ${utenteConnesso.nome} ${utenteConnesso.cognome}" />
            </button>

            <button class="pull-right navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".navbar-menu">
                <span class="sr-only">Apri menù navigazione</span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>
            </button>

        </div>

        <div class="navbar-buttons navbar-header pull-right  collapse navbar-collapse" role="navigation">
            <ul class="nav ace-nav">
                <li>
                    <a data-toggle="dropdown" class="notifiche dropdown-toggle" href="#">
                        <i class="ace-icon fa fa-bell <c:if test="${notificationCount>0}">icon-animated-bell</c:if>"></i>
                        <c:if test="${notificationCount>0}"><span class="badge badge-warning notifiche-count">${notificationCount}</span></c:if>
                        </a>

                        <div class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                            <div class="tabbable">
                                <ul class="nav nav-tabs">
                                    <li>
                                        <a data-toggle="tab" href="#navbar-messages">
                                            Notifiche
                                            <span class="badge badge-danger">${notificationCount}</span>
                                    </a>
                                </li>
                            </ul><!-- .nav-tabs -->

                            <div class="tab-content">

                                <div id="navbar-messages" class="tab-pane in active">
                                    <ul class="dropdown-menu-right dropdown-navbar dropdown-menu">
                                        <li class="dropdown-content">
                                            <ul class="dropdown-menu dropdown-navbar dropdown-menu-notifiche"></ul>
                                        </li>

                                        <li class="dropdown-footer">
                                            <a href="${path}/workflow/notifiche/list.htm">
                                                Vedi tutte le notifiche
                                                <i class="ace-icon fa fa-arrow-right"></i>
                                            </a>
                                        </li>
                                    </ul>
                                </div><!-- /.tab-pane -->
                            </div><!-- /.tab-content -->
                        </div><!-- /.tabbable -->
                    </div><!-- /.dropdown-menu -->
                </li>

                <!-- #section:basics/navbar.user_crossenu -->
                <li class="user">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="${path}/themes/default/images/user_cross.jpg" alt="Immagine ${utenteConnesso.nome} ${utenteConnesso.cognome}" />
                        <span class="user-info">
                            <div>${utenteConnesso.nome}</div>
                            <div>${utenteConnesso.cognome}</div>
                        </span>

                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>

                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <!--                        <li>
                                                    <a href="#">
                                                        <i class="ace-icon fa fa-cog"></i>
                                                        Settings
                                                    </a>
                                                </li>
                        
                                                <li>
                                                    <a href="profile.html">
                                                        <i class="ace-icon fa fa-user"></i>
                                                        Profile
                                                    </a>
                                                </li>
                        
                                                <li class="divider"></li>-->

                        <li>
                            <a href="${path}/esci.htm">
                                <i class="ace-icon fa fa-power-off"></i>
                                Logout
                            </a>
                        </li>
                    </ul>
                </li>

                <!-- /section:basics/navbar.user_menu -->
            </ul>
        </div>

        <!-- /section:basics/navbar.dropdown -->
        <nav role="navigation" class="navbar-menu pull-left collapse navbar-collapse">
            <!-- #section:basics/navbar.nav -->
            <ul class="nav navbar-nav">
                <c:if test="${isSuperuser || enableGestionePratiche}">
                    <li>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="ace-icon fa fa-envelope"></i>
                            Accettazione
                            &nbsp;
                            <i class="ace-icon fa fa-angle-down bigger-110"></i>
                        </a>

                        <ul class="dropdown-menu dropdown-grey dropdown-caret">
                            <c:if test="${enablePraticheDaProtocollo}">
                                <li>
                                    <a href="${path}/pratiche/nuove/protocollo.htm" title="<spring:message code="menu.nuovaGrafica.nuoveprotocollo"/>"><!--<i class="ace-icon fa fa-envelope-o bigger-110 gray"></i>--><spring:message code="menu.nuovaGrafica.nuoveprotocollo"/></a>
                                </li>
                            </c:if>
                            <c:if test="${enablePraticheDaProtocollo && enableComunicazioniIngresso}">
                                <li>
                                    <a href="${path}/documenti/nuovi/protocollo.htm" title="<spring:message code="menu.nuovaGrafica.documentiprotocollo"/>"><!--<i class="ace-icon fa fa-eye bigger-110 gray"></i>--><spring:message code="menu.nuovaGrafica.documentiprotocollo"/></a>
                                </li>
                            </c:if>
                            <li>
                                <a href="${path}/pratiche/nuove.htm" title="<spring:message code="menu.nuovaGrafica.nuovepratiche"/>"><!--<i class="ace-icon fa fa-users bigger-110 gray">--><spring:message code="menu.nuovaGrafica.nuovepratiche"/></a>
                            </li>
                        </ul>
                    </li>
                </c:if>

                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="ace-icon fa fa-folder-open"></i>
                        Gestione
                        &nbsp;
                        <i class="ace-icon fa fa-angle-down bigger-110"></i>
                    </a>

                    <ul class="dropdown-menu dropdown-grey dropdown-caret">
                        <c:if test="${isSuperuser || enableGestionePratiche}">
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.aperturanuovepratiche"/>" href="${path}/pratiche/nuove/apertura.htm"><spring:message code="menu.nuovaGrafica.aperturanuovepratiche"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.gestionepratiche"/>" href="${path}/pratiche/gestisci.htm"><spring:message code="menu.nuovaGrafica.gestionepratiche"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.scadenziario"/>" href="${path}/pratiche/scadenzario.htm"><spring:message code="menu.nuovaGrafica.scadenziario"/></a>
                            </li>
                        </c:if>
                        <li>
                            <a title="<spring:message code="menu.nuovaGrafica.gestioneanagrafiche"/>" href="${path}/gestione/anagrafiche/list.htm"><spring:message code="menu.nuovaGrafica.gestioneanagrafiche"/></a>
                        </li>
                        <c:if test="${isSuperuser || enableGestionePratiche}">
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.gestionemail"/>" href="${path}/console/mail.htm"><spring:message code="menu.nuovaGrafica.gestionemail"/></a>
                            </li>
                        </c:if>
                        <c:if test="${enableCds}">
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.cds"/>" href="${path}/form/view.htm?idForm=it.wego.cross.forms.common.GestioneOrganiCollegialiGrid"><spring:message code="menu.nuovaGrafica.cds"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.cds.sedute"/>" href="${path}/form/view.htm?idForm=it.wego.cross.forms.common.GestioneOrganiCollegialiSeduteGrid"><spring:message code="menu.nuovaGrafica.cds.sedute"/></a>
                            </li>
                        </c:if>
                    </ul>
                </li>
                <c:if test="${isSuperuser}">
                    <li>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="ace-icon fa fa-gear"></i>
                            Impostazioni
                            &nbsp;
                            <i class="ace-icon fa fa-angle-down bigger-110"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-grey dropdown-caret">
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.gestioneenti"/>" href="${path}/ente/index.htm"><spring:message code="menu.nuovaGrafica.gestioneenti"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.gestioneutenti"/>" href="${path}/utenti/index.htm"><spring:message code="menu.nuovaGrafica.gestioneutenti"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.gestionetemplate"/>" href="${path}/gestione/eventoTemplate/lista.htm"><spring:message code="menu.nuovaGrafica.gestionetemplate"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.gestioneprocessi"/>" href="${path}/processi/lista.htm"><spring:message code="menu.nuovaGrafica.gestioneprocessi"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.gestioneprocedimenti"/>" href="${path}/gestione/procedimenti/lista.htm"><spring:message code="menu.nuovaGrafica.gestioneprocedimenti"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.anagrafe.tributaria"/>" href="${path}/anagrafe_tributaria/export.htm"><spring:message code="menu.nuovaGrafica.anagrafe.tributaria"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.console"/>" href="${path}/console/index.htm"><spring:message code="menu.nuovaGrafica.console"/></a>
                            </li>
                            <c:if test="${enableTasklist}">
                                <li>
                                    <a title="<spring:message code="menu.nuovaGrafica.tasklist"/>" href="${path}/workflow/tasklist.htm"><spring:message code="menu.nuovaGrafica.tasklist"/></a>
                                </li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
            </ul>

            <!-- /section:basics/navbar.nav -->

            <!-- #section:basics/navbar.form -->
            <!--
            <form class="navbar-form navbar-left form-search" role="search">
                <div class="form-group">
                    <input type="text" placeholder="cerca pratica..." />
                </div>

                <button type="button" class="btn btn-mini btn-info2">
                    <i class="ace-icon fa fa-search icon-only bigger-110"></i>
                </button>
            </form>
            -->

            <!-- /section:basics/navbar.form -->
        </nav>
    </div><!-- /.navbar-container -->
</div>