[#ftl]
<div id="ricerca">
    <h3><a href="#">Ricerca</a></h3>
    <div>
        [#include "ricercaPratiche.ftl"]
    </div>
</div>

<script type="text/javascript" >
    $(function() {
        $("#ricerca").accordion({
            collapsible: true,
            alwaysOpen: false,
            active: false
        });
    });
</script>