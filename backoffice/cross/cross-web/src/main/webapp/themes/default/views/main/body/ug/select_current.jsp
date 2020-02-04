<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<tiles:insertAttribute name="body_error" />

<style type="text/css" >
    .uniForm ul {
        width: 99%!important;
    }

    .chosen-container abbbr{
        border: 0;
        border-bottom: none;
        cursor: pointer;
    }
    .errorMsg h3{
        color:red;
        margin:0px;
        font-size: 0.9em;
    }
</style>

<div>
    <div  class="uniForm ">

        <div class="sidebar_auto">
            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <ul>
                    <li class="active"><a href="#frame1"><spring:message code="ente.riferimento.title"/></a></li>
                </ul>
                <div class="frames">
                    <div class="frame active" id="frame1">
                        <div><spring:message code="ente.riferimento.message"/></div>
                        <form action="${path}/ug/select_current_submit.htm" class="uniForm inlineLabels" method="post">
                            <input type='hidden' name='previousUrl' value='${previousUrl}'/>

                            <div>
                                <div class="inlineLabels">
                                    <div class="ctrlHolder">
                                        <select id="idEnte" name="idEnte" class="chosen-select" data-placeholder="<spring:message code="ente.riferimento.label"/>">
                                            <option  value=""></option>
                                            <c:forEach items="${enti}" var="ente" begin="0">
                                                <option  value="${ente.idEnte}" <c:if test="${ente.idEnte == selectedUG.idEnte.idEnte}">selected</c:if>>${ente.descrizione}</option>
                                            </c:forEach>
                                        </select>
                                    </div>     
                                </div>
<!--                                <div class="errorMsg" style="display:none;">
                                    <h3><spring:message code="ente.riferimento.errror"/></h3>
                                </div>-->
                            </div>

                            <div class="buttonHolder">
                                <a href="${referPath}" class="secondaryAction">&larr; Indietro</a>
                                <button type="submit" name="submit" class="primaryAction" value="<spring:message code="protocollo.ente.riferimento.button"/>"><spring:message code="protocollo.ente.riferimento.button"/></button>
                            </div>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(".chosen-select").chosen({allow_single_deselect: true});

//    $("form").submit(function(event) {
//        if ($("#idEnte").val()) {
//            return;
//        }
//
//        $(".errorMsg").show();
//        event.preventDefault();
//    });

//    $("#idEnte").chosen().change(function(a, b, c) {
//        if ($("#idEnte").val()) {
//            $(".errorMsg").hide();
//        } else {
//            $(".errorMsg").show();
//        }
//    });
</script>