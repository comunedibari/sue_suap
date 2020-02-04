[#ftl]
<div  ${api.header}>
    [#assign tableId = api.randomId]
    [#assign pagerId = api.randomId]
    [#include "ricercaPraticheOldWrapper.ftl"]
    <table id="${tableId}" ></table>
    <div id="${pagerId}"></div>
    <br />

    <script type="text/javascript" >
        [#include "praticheCollegateGrid.js.ftl"]
    </script>
</div>