<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>AttachmentCleaner 1.0</title>
        <%@include file="style.jsp" %>
    </head>

    <body role="document">
        <%@include file="scripts.jsp" %>
        <div id="wrapper">
            <nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}">AttachmentCleaner v1.0</a>
                </div>
            </nav>
            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Benvenuto su AttachmentCleaner 1.0</h1>
                    </div>
                    <div class="col-lg-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">Sincronizza i documenti</div>
                            <div class="panel-body">

                                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                <h4 class="modal-title" id="myModalLabel">Esito operazione</h4>
                                            </div>
                                            <div class="modal-body">

                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <p>Vuoi avviare la pulizia degli allegati di CROSS?</p>                                   
                                <form role="form">
                                    <div class="col-lg-12">
                                        <div class="col-lg-4">
                                            <div class="form-group">
                                                <label>Data inizio</label>
                                                <input type="text" class="form-control datepicker" data-date-format="dd/mm/yyyy" id="startDate" >
                                                <p class="help-block">Campo obbligatorio</p>
                                            </div>
                                        </div>
                                        <div class="col-lg-4">
                                            <div class="form-group">
                                                <label>Data fine</label>
                                                <input type="text" class="form-control datepicker" data-date-format="dd/mm/yyyy" id="endDate" >
                                                <p class="help-block">Campo obbligatorio</p>
                                            </div>
                                        </div>
                                        <div class="col-lg-4">
                                            <div class="form-group">
                                                <label>Cancella file</label>
                                                <div class="checkbox">
                                                    <label>
                                                        <input type="checkbox" value="true" id="deleteFile">Ok
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="col-lg-6">
                                            <button type="button" id="simulate" class="btn btn-success">Simula pulizia</button>
                                            <button type="button" id="synchronize" class="btn btn-danger">Sincronizza</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
