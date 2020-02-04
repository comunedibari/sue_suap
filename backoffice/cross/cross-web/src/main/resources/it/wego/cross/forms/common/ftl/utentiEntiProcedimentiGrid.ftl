[#ftl]
[#assign filtroRicerca=data.filtroRicerca]
<div  ${api.header}>
    [#assign tableId = api.randomId]
    [#assign pagerId = api.randomId]
<div id="ricerca">
    <h3><a href="#">Ricerca</a></h3>
    <div>
        [#include "ricercaProcedimenti.ftl"]
    </div>
</div>
    <table id="${tableId}" ></table>
    <div id="${pagerId}"></div>
    <br />

    <script type="text/javascript" >
        [#include "utentiEntiProcedimentiGrid.js.ftl"]
    </script>
</div>