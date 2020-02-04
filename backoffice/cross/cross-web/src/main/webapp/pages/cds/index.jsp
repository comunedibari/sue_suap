<%-- 
    Document   : index
    Created on : 16-gen-2015, 18.57.37
    Author     : Gabriele
--%>
<%@ taglib prefix="w" uri="/WEB-INF/wegoTag.tld"  %>

<!-- page specific plugin styles -->
<link rel="stylesheet" href="${path}/assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${path}/assets/css/datepicker.css" />
<link rel="stylesheet" href="${path}/assets/css/ui.jqgrid.css" />


<script src="${path}/assets/js/jqGrid/jquery.jqGrid.src.js"></script>
<script src="${path}/assets/js/jqGrid/i18n/grid.locale-en.js"></script>

<style type="text/css">
    div.ui-jqgrid-view table.ui-jqgrid-btable{
        border-left-style: none;
        border-collapse: initial;
    }
</style>


<w:form formId="it.wego.cross.forms.common.ConsoleGrid" />

<!--
    <div id="right-menu" class="modal aside" data-body-scroll="false" data-offset="true" data-placement="right" data-fixed="true" data-backdrop="false" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header no-padding">
                    <div class="table-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            <span class="white">&times;</span>
                        </button>
                        Based on Modal boxes
                    </div>
                </div>

                <div class="modal-body">
                    <h3 class="lighter">Custom Elements and Content</h3>

                    <br />
                    With no modal backdrop
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    1
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    2
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    3
                </div>
            </div>
            <button class="aside-trigger btn btn-info btn-app btn-xs ace-settings-btn" data-target="#right-menu" data-toggle="modal" type="button">
                <i data-icon1="fa-plus" data-icon2="fa-minus" class="ace-icon fa fa-plus bigger-110 icon-only"></i>
            </button>
        </div>
    </div>
-->