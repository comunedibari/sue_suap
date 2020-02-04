<%-- 
    Document   : html
    Created on : 15-gen-2015, 12.45.32
    Author     : Gabriele
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html lang="it">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta charset="utf-8" />
        <title>${applicationName} ${version} - <tiles:insertAttribute name="title" /></title>

        <meta name="description" content="top menu &amp; navigation" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

        <!-- bootstrap & fontawesome -->
        <link rel="stylesheet" href="${path}/assets/css/bootstrap.css" />
        <link rel="stylesheet" href="${path}/assets/css/font-awesome.css" />

        <!-- text fonts -->
        <link rel="stylesheet" href="${path}/assets/css/ace-fonts.css" />


        <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

        <!--[if lte IE 8]>
        <script src="${path}/assets/js/html5shiv.js"></script>
        <script src="${path}/assets/js/respond.js"></script>
        <![endif]-->
        <script type="text/javascript">
            var path = "${path}";
        </script>
        <!-- basic scripts -->

        <!--[if !IE]> -->
        <script type="text/javascript">
            window.jQuery || document.write("<script src='${path}/assets/js/jquery.js'>" + "<" + "/script>");
        </script>

        <!-- <![endif]-->

        <!--[if IE]>
            <script type="text/javascript">
            window.jQuery || document.write("<script src='${path}/assets/js/jquery1x.js'>"+"<"+"/script>");
            </script>
        <![endif]-->
        <script type="text/javascript">
            if ('ontouchstart' in document.documentElement)
                document.write("<script src='${path}/assets/js/jquery.mobile.custom.js'>" + "<" + "/script>");
        </script>
        
        
        <script src="${path}/assets/js/jquery.loadmask.min.js"></script>
        <script src="${path}/assets/js/jquery.gritter.js"></script>
        <!-- jqueryui scripts -->
        <script src="${path}/assets/js/jquery-ui.js"></script>
        <script src="${path}/assets/js/jquery.ui.touch-punch.js"></script>
        
        <script src="${path}/assets/js/bootstrap.js"></script>
        
        <link rel="stylesheet" href="${path}/assets/css/jquery-ui.css" />
        <link rel="stylesheet" href="${path}/assets/css/datepicker.css" />
        <link rel="stylesheet" href="${path}/assets/css/ui.jqgrid.css" />
        
        <script src="${path}/assets/js/jqGrid/jquery.jqGrid.src.js"></script>
        <script src="${path}/assets/js/jqGrid/i18n/grid.locale-en.js"></script>
        
        <script src="${path}/assets/js/jquery.form.min.js"></script>

    </head>

    <body class="no-skin">
        <tiles:insertAttribute name="header" />

        <div class="main-container" id="main-container">
            <script type="text/javascript">
//                try {
//                    ace.settings.check('main-container', 'fixed');
//                } catch (e) {
//                }
            </script>

            <!-- /section:basics/sidebar.horizontal -->
            <div class="main-content">
                <div class="main-content-inner">

                    <!-- #section:settings.box -->
<!--                    <div class="ace-settings-container" id="ace-settings-container">
                        <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                            <i class="ace-icon fa fa-cog bigger-130"></i>
                        </div>

                        <div class="ace-settings-box clearfix" id="ace-settings-box">
                            <div class="pull-left">
                                 #section:settings.skins 
                                <div class="ace-settings-item">
                                    <div class="pull-left">
                                        <select id="skin-colorpicker" class="hide">
                                            <option data-skin="no-skin" value="#438EB9">#438EB9</option>
                                            <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                                            <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                                            <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
                                        </select>
                                    </div>
                                    <span>&nbsp; Scegli tema</span>
                                </div>

                                 /section:settings.skins 

                                 #section:settings.navbar 
                                <div class="ace-settings-item">
                                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
                                    <label class="lbl" for="ace-settings-navbar"> Fissa Topbar</label>
                                </div>

                                 /section:settings.navbar 

                                 /section:settings.container 
                            </div> /.pull-left 
                        </div> /.ace-settings-box 
                    </div> /.ace-settings-container -->

                    <!-- /section:settings.box -->
                    <div class="page-header">
                        <h1>
                            Top Menu Style
                            <small>
                                <i class="ace-icon fa fa-angle-double-right"></i>
                                top menu &amp; navigation
                            </small>
                        </h1>
                    </div><!-- /.page-header -->

                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <tiles:insertAttribute name="messages" />
                                <tiles:insertAttribute name="body" />
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.page-content -->
                </div>
            </div><!-- /.main-content -->

            <tiles:insertAttribute name="footer" />

            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div><!-- /.main-container -->


        <link rel="stylesheet"  href="${path}/themes/cross4/default.css" />
        <link rel="stylesheet" href="${path}/assets/css/jquery.loadmask.css" />
        <link rel="stylesheet" href="${path}/assets/css/jquery.gritter.css" />
        <link rel="stylesheet" href="${path}/assets/css/select2.css" />

        <!-- ace styles -->
        <link rel="stylesheet" href="${path}/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

        <!--[if lte IE 9]>
                <link rel="stylesheet" href="${path}/assets/css/ace-part2.css" class="ace-main-stylesheet" />
        <![endif]-->

        <!--[if lte IE 9]>
          <link rel="stylesheet" href="${path}/assets/css/ace-ie.css" />
        <![endif]-->



        
        <link rel="stylesheet"  href="${path}/themes/cross4/default.css" />
        <!--<link rel="stylesheet"  href="${path}/themes/cross4/blue.css" />-->


        <!-- ace scripts -->
        
        <script src="${path}/assets/js/date-time/bootstrap-datepicker.js"></script>
        <script src="${path}/assets/js/ace-elements.js"></script>
        <script src="${path}/assets/js/ace-extra.js"></script>
        <script src="${path}/assets/js/ace/ace.js"></script>
        <script src="${path}/assets/js/ace/ace.ajax-content.js"></script>
        <script src="${path}/assets/js/ace/ace.touch-drag.js"></script>
        <script src="${path}/assets/js/ace/ace.sidebar.js"></script>
        <script src="${path}/assets/js/ace/ace.sidebar-scroll-1.js"></script>
        <script src="${path}/assets/js/ace/ace.submenu-hover.js"></script>
        <script src="${path}/assets/js/ace/ace.widget-box.js"></script>
        <script src="${path}/assets/js/ace/ace.settings.js"></script>
        <script src="${path}/assets/js/ace/ace.settings-rtl.js"></script>
        <script src="${path}/assets/js/ace/ace.settings-skin.js"></script>
        <script src="${path}/assets/js/ace/ace.widget-on-reload.js"></script>
        <script src="${path}/assets/js/select2.js"></script>
        <!--script src="${path}/assets/js/ace/ace.searchbox-autocomplete.js"></script-->
        <script src="${path}/javascript/validate/jquery.validate.js"></script>
        <script src="https://cdn.jsdelivr.net/handlebarsjs/4.0.5/handlebars.min.js"></script>
        <script src="${path}/lib/js/wego-forms/wego-forms.js"></script>
        <script src="${path}/javascript/cross4/main.js"></script>


    </body>
</html>
