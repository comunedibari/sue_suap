<%-- 
    Document   : main
    Created on : 25-gen-2012, 15.25.36
    Author     : CS
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
        <meta content="text/html;charset=utf-8" http-equiv="Content-Type"/>
        <meta content="utf-8" http-equiv="encoding"/>
    
        <title>${applicationName} ${version} - <tiles:getAsString name="title" />
        </title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />

        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/jquery-ui.min.css"/>"  media="all"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/ui.jqgrid.css"/>"  media="all"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/main.css" />" media="all"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/token-input-facebook.css" />" media="all"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/modal-basic.css" />" media="all"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/chosen/chosen.min.css" />" media="all"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/wizard.css" />" media="all"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/jqueryui-editable/css/jqueryui-editable.css" />" media="all"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/cross3.css" />" media="all"/>

        <style type="text/css" media="screen"></style>

        <script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-1.11.0.js"/>"></script>
        <%--<script type="text/javascript" src="<c:url value="/javascript/jquery-ui/jquery-ui-1.10.4.min.js"/>"></script> --%>
        <!--<script type="text/javascript" src="<c:url value="/javascript/jquery-ui/jquery-ui.min.js"/>"></script>-->
        <script type="text/javascript" src="<c:url value="/javascript/jquery-ui/jquery-ui.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/javascript/jquery-ui/i18n/jquery.ui.datepicker-it.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/javascript/jqgrid/jquery.jqGrid.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/javascript/jqgrid/i18n/grid.locale-it.js"/>"></script>

        <script type="text/javascript" src="<c:url value="/javascript/jquery_print_area.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/javascript/jquery.tokeninput.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/javascript/gestioneAnagrafiche.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/javascript/dropdown.js"/>"></script> 
        <script type="text/javascript" src="<c:url value="/javascript/pagecontrol.js"/>"></script> 
        <script type="text/javascript" src="<c:url value="/javascript/jquery.masonry.min.js"/>"></script> 
        <script type="text/javascript" src="<c:url value="/javascript/jquery.timeline.min.js"/>"></script> 
        <script type="text/javascript" src="<c:url value="/javascript/jquery.carouFredSel-6.2.0-packed.js"/>"></script> 
        <script type="text/javascript" src="<c:url value="/javascript/jquery.chained.js"/>"></script> 
        <script type="text/javascript" src="<c:url value="/javascript/jquery.isloading.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/javascript/chosen/chosen.jquery.min.js"/>"></script> 
        <script type="text/javascript" src="<c:url value="/javascript/jquery-mask/jquery.mask.min.js"/>"></script> 
        <script type="text/javascript" src="<c:url value="/javascript/jquery.prevent.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/javascript/jqueryui-editable/js/jqueryui-editable.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/javascript/handlebars/handlebars-v1.3.0.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/javascript/validate/jquery.validate.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/javascript/load-mask/jquery.loadmask.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/lib/js/wego-forms/wego-forms.js"/>"></script>
<!--        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-ui-bootstrap/0.5pre/assets/css/bootstrap.min.css" media="all"/>
        <link rel="stylesheet" type="text/css" href="http://www.soliman.nl/test/jqgrid.bootstrap/jqGrid/css/ui.jqgrid.css" media="all"/>-->

        <script type="text/javascript">
            if (!window.console) console = {log: function() {}};
            
            function adjustLayout() {
                var footer = "#footer";
                var bodyTop = $(document).height() - $(footer).height();
                var windowTop = $(window).height() - $(footer).height();
                newTop = Math.max(bodyTop, windowTop) - 1;
                var newLeft = ($(window).width() - $(footer).width()) / 2;


                $(footer).css({
                    'position': 'relative',
                    'bottom': 1
                });
                /*
                 
                 $(footer).css({
                 'position': 'absolute',
                 'left': newLeft,
                 'top': newTop
                 });
                 */

//                if ($.browser.msie) {
//                    $('#column-1').css({
//                        'margin-left': -$(window).width() + 10
//                    });
//                }
            }

            function confirmation(url) {
                var answer = confirm("Sei sicuro di voler eliminare la riga?", "ssss");
                if (answer) {
                    window.location = url;
                }
            }


            //            function abilitaValutazionePositiva(stato){
            //                if(!stato){
            //                    $('#ric_firmata').attr('disabled', 'disabled');
            //                    $('#aoo').attr('disabled', 'disabled');
            //                    $('#numero').attr('disabled', 'disabled');
            //                    $('#anno').attr('disabled', 'disabled');
            //                    $('#data').attr('disabled', 'disabled');
            //                }else{
            //                    $('#ric_firmata').attr('disabled', '');
            //                    $('#aoo').attr('disabled', '');
            //                    $('#numero').attr('disabled', '');
            //                    $('#anno').attr('disabled', '');
            //                    $('#data').attr('disabled', '');
            //                }
            //
            //            }


            function getTodayDateString() {
                var objToday = new Date(),
                        curDay = objToday.getDate(),
                        curMonth = objToday.getMonth() + 1,
                        curYear = objToday.getFullYear(),
                        curHour = objToday.getHours(),
                        curMinute = objToday.getMinutes() < 10 ? "0" + objToday.getMinutes() : objToday.getMinutes(),
                        curSeconds = objToday.getSeconds() < 10 ? "0" + objToday.getSeconds() : objToday.getSeconds();
                return curDay + "/" + curMonth + "/" + curYear + " " + curHour + ":" + curMinute + ":" + curSeconds;
            }


            $(document).ready(function() {

                function Arrow_Points()
                {
                    var s = $('#container').find('.item');
                    $.each(s, function(i, obj) {
                        var posLeft = $(obj).css("left");
                        $(obj).addClass('borderclass');
                        if (posLeft == "0px")
                        {
                            html = "<span class='rightCorner'></span>";
                            $(obj).prepend(html);
                        }
                        else
                        {
                            html = "<span class='leftCorner'></span>";
                            $(obj).prepend(html);
                        }
                    });
                }

                $('.timeline_container').mousemove(function(e) {
                    var topdiv = $("#containertop").height();
                    var pag = e.pageY - topdiv - 26;
                    $('.plus').css({"top": pag + "px", "background": "url('images/plus.png')", "margin-left": "1px"});
                }).mouseout(function() {
                    $('.plus').css({"background": "url('')"});
                });

                $("#update_button").on('click', function()
                {
                    var x = $("#update").val();
                    $("#container").prepend('<div class="item"><a href="#" class="deletebox">X</a><div>' + x + '</div></div>');

                    //Reload masonry
                    $('#container').masonry('reload');

                    $('.rightCorner').hide();
                    $('.leftCorner').hide();
                    Arrow_Points();

                    $("#update").val('');
                    $("#popup").hide();
                    return false;
                });

                // Divs
                $('#container').masonry({itemSelector: '.item'});
                Arrow_Points();

                //Mouseup textarea false
                $("#popup").mouseup(function()
                {
                    return false;
                });

                $(".timeline_container").click(function(e)
                {
                    var topdiv = $("#containertop").height();
                    $("#popup").css({'top': (e.pageY - topdiv - 33) + 'px'});
                    $("#popup").fadeIn();
                    $("#update").focus();


                });


                $(".deletebox").on('click', function() {
                    if (confirm("Are your sure?"))
                    {
                        $(this).parent().fadeOut('slow');
                        //Remove item
                        $('#container').masonry('remove', $(this).parent());
                        //Reload masonry
                        $('#container').masonry('reload');
                        $('.rightCorner').hide();
                        $('.leftCorner').hide();
                        Arrow_Points();
                    }
                    return false;
                });

                //Textarea without editing.
                $(document).mouseup(function() {
                    $('#popup').hide();
                });

                $('ul.menu li').hover(function() {
                    $(this).addClass('hover');
                }, function() {
                    $(this).removeClass('hover');
                });

                $("#data").datepicker({dateFormat: 'dd/mm/yy'});

                $("#search_data_from").datepicker({dateFormat: 'dd/mm/yy'});
                $("#search_data_to").datepicker({dateFormat: 'dd/mm/yy'});
                $("#data_proto").datepicker({dateFormat: 'dd/mm/yy'});

                $("#ricerca").accordion({
                    collapsible: true,
                    alwaysOpen: false,
                    active: false
                });

                $('#stampa_dettaglio_pratica').click(function() {
                    $("#dettaglio_pratica").printArea();
                });

                adjustLayout();

                //Setto i default per il datepicker
                $.datepicker.setDefaults($.datepicker.regional['it']);

                $.datepicker.setDefaults({
                    dateFormat: 'dd/mm/yy',
                    changeMonth: true,
                    changeYear: true,
                    yearRange: "-120:+0"
                });
                $(document).ajaxSend(function() {
                    $("#loading").show();
                });

                $(document).ajaxComplete(function() {
                    $("#loading").hide();
                });

                $("#banner").slideUp('slow'); //appena carica la pagina appare il banner

                $("#scadenziario_banner").slideUp('slow'); //appena carica la pagina appare il banner

                $(".scadenziario_nascondi").click(//funzione per quando si clicca su nascondi
                        function() {

                            $("#scadenziario_banner").slideUp("slow"); //faccio nascondere il banner
                            $(".scadenziario_apri").slideDown("slow"); //faccio apparire il pulsante di ri-apertura

                        });//fine chiudi banner

                $(".messaggi_nascondi").click(//funzione per quando si clicca su nascondi
                        function() {

                            $("#messaggi_banner").slideUp("slow"); //faccio nascondere il banner
                            //                    $(".messaggi_apri").slideDown("slow"); //faccio apparire il pulsante di ri-apertura

                        });//fine chiudi banner

                $(".scadenziario_apri").click(
                        function() {

                            $("#scadenziario_banner").slideDown('slow'); //faccio ricomparire il banner
                            $(".scadenziario_apri").slideUp("slow");  //chiudo il pulsante che è pronto per il nuovo ciclo

                        });//fine ri-apri banner

                $(".pratiche-assegnare").click(//funzione per quando si clicca su nascondi
                        function() {

                            $("#menu_accettazione").slideToggle("slow"); //faccio vedere/nascondere il banner
                            $("#menu_impostazioni").slideUp("slow"); //faccio nascondere il banner
                            $("#menu_apertura").slideUp("slow"); //faccio nascondere il banner
                            $("#menu_scadenziario").slideUp("slow"); //faccio nascondere il banner

                        });//fine chiudi banner

                $(".apertura-pratiche").click(//funzione per quando si clicca su nascondi
                        function() {

                            $("#menu_apertura").slideToggle("slow"); //faccio vedere/nascondere il banner
                            $("#menu_impostazioni").slideUp("slow"); //faccio nascondere il banner
                            $("#menu_accettazione").slideUp("slow"); //faccio nascondere il banner
                            $("#menu_scadenziario").slideUp("slow"); //faccio nascondere il banner

                        });//fine chiudi banner

                $(".impostazioni_home").click(//funzione per quando si clicca su nascondi
                        function() {

                            $("#menu_impostazioni").slideToggle("slow"); //faccio vedere/nascondere il banner
                            $("#menu_accettazione").slideUp("slow"); //faccio nascondere il banner
                            $("#menu_apertura").slideUp("slow"); //faccio nascondere il banner
                            $("#menu_scadenziario").slideUp("slow"); //faccio nascondere il banner

                        });//fine chiudi banner

                $(".scadenziario").click(//funzione per quando si clicca su nascondi
                        function() {

                            $("#menu_scadenziario").slideToggle("slow"); //faccio vedere/nascondere il banner
                            $("#menu_impostazioni").slideUp("slow"); //faccio nascondere il banner
                            $("#menu_apertura").slideUp("slow"); //faccio nascondere il banner
                            $("#menu_accettazione").slideUp("slow"); //faccio nascondere il banner
                        });//fine chiudi banner

            });



            /**
             * ^^CS AGGIUNTa
             */
            $().ready(function() {
                $("td button").addClass("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-focus");

//                $('#foo1').carouFredSel({
//                    width: 360,
//                    height: 'auto',
//                    prev: '#prev1',
//                    next: '#next1',
//                    auto: false
//                });
//
//                $('#lala').carouFredSel({
//                    width: 360,
//                    height: 'auto',
//                    prev: '#prev2',
//                    next: '#next2',
//                    auto: false
//                });

            });
            var personaFisica = "F";
            //var personaProfessionista = "P";
            var personaGiuridica = "G";
            var personaDittaIndividuale = "I";
            function getTipoAnagrafica(data, variante)
            {
                var tipoAna;
                var varianteAna;
                if (typeof maybeObject == 'object')
                {
                    tipoAna = $.trim(data['tipoAnagrafica']);
                    varianteAna = $.trim(data['varianteAnagrafica']);
                }
                else
                {
                    tipoAna = $.trim(data);
                    varianteAna = $.trim(variante);
                }
                var tipo = tipoAna;
                if (varianteAna != null && varianteAna != '')
                {
                    tipo = varianteAna;
                }
                return tipo;
            }
            ;

            function getUrl() {
                var url = document.URL;
                url = url.split("?");
                url = url[0];
                if (url.length == 0)
                {
                    url = document.URL;
                }

                //return document.URL.replace(".htm","/ajax.htm");
                return url.replace(".htm", "/ajax.htm");
            }
            ;
            var numDialog = 0;

            $.extend($.ui.dialog.prototype.options, {
                open: function() {
                    if (numDialog == 0)
                    {
                        //$("html").css("overflow","hidden");
                    }
                    numDialog++;
                },
                close: function() {
                    numDialog--;
                    if (numDialog <= 0)
                    {
                        // $("html").css("overflow","scroll");
                        numDialog = 0;
                    }
                }
            });

            $(window).load(function() {
                // light
                if ($(".timelineCard").length) {
                    $('.timelineCard').timeline({
                        yearsOn: true,
                        openTriggerClass: '.read_more'
                    });
                }

                if ($(".timelineCard2").length) {
                    $('.timelineCard2').timeline({
                        yearsOn: true,
                        openTriggerClass: '.read_more'
                    });
                }

            });




        </script>
    </head>
    <body >
        <div>
            <div id="wrapper">
                <div id="header">
                    <div id="loading" style="display:none"><spring:message code="anagrafica.ri.loading"/></div>
                    <tiles:insertAttribute name="header" />
                    <tiles:insertAttribute name="infobar" />
                </div>
                <div id="content">
                    <div id="column-2">
                        <tiles:insertAttribute name="body" />
                    </div>
                    <!-- Menu -->

                    <!-- Fine Menu -->
                </div>
                <!--     footer pagina      -->
            </div>
            <div class="clear"></div>
            <tiles:insertAttribute name="footer" />
        </div>

    </body>
</html>
