<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="error-container">
    <div class="well">
        <h1 class="grey lighter smaller">
            <span class="blue bigger-125">
                <i class="icon-random"></i>
                500
            </span>
            Qualcosa è andato male
        </h1>

        <hr>
        <h3 class="lighter smaller">
            Ci siamo lavorando<i class="icon-wrench icon-animated-wrench bigger-125"></i>!
        </h3>

        <div class="space"></div>

        <div>
            <h4 class="lighter smaller">Nel frattempo prova con le seguenti opzioni:</h4>

            <ul class="list-unstyled spaced inline bigger-110 margin-15">
                <li>
                    <i class="icon-hand-right blue"></i>
                    Leggi faq
                </li>

                <li>
                    <i class="icon-hand-right blue"></i>
                    Dacci alcune informazioni per riprodurre l'errore.
                </li>
            </ul>
        </div>

        <hr>
        <div class="space"></div>

        <div class="center">
            <a href="#" class="btn btn-primary">
                <i class="icon-dashboard"></i>
                Home
            </a>
        </div>
    </div>
</div>