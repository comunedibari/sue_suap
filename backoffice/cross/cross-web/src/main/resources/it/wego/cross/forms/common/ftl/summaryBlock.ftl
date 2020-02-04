[#ftl]
[#include "common.ftl"]
[#assign config=component.attributes.config]
[#assign datalist=config.source?eval]
[#assign rows=config.rows]


<div ${api.header} class="imbox_frame ">
    [#if config.handlers.add?? && config.handlers.add!=""]
        <div class="table-add-link">
            <a href="#" class="summary_block add ${config.cssClass}">
                Aggiungi ${config.label}
                <img src="/cross/themes/default/images/icons/add.png" alt="Aggiungi ${config.label}" title="Aggiungi ${config.label}">
            </a>
        </div>
    [/#if]
    <div class="container-${config.prefix}">
        <table cellpadding="0" cellspacing="0">
            <tbody>
                <tr>
                    [#list datalist as dataloop]
                    <td class="summary_block" id="summary_block-${config.prefix}-${getValue(dataloop, config.id)!}">
                        <div class="circle lastCircle">
                            [#list rows as row]
                                [#assign value=getValue(dataloop, row.value, row.type, row.emptypattern)!]
                                <div class="ctrlHolder">
                                    <label class="required [#if row_index==0]first[/#if]">
                                        ${row.description}
                                    </label>
                                    [#if row_index==0 && config.handlers.remove?? && config.handlers.remove!=""]
                                        <a class="summary_block remove ${config.cssClass}" data-holder="${getValue(dataloop, config.id)!}">
                                            <img src="/cross/themes/default/images/icons/link_break.png" alt="Scollega ${config.label}" title="Scollega ${config.label}">
                                            Scollega ${config.label}
                                        </a>
                                        </div>
                                        <div class="ctrlHolder">
                                    [/#if]
                                    [#if row.type=="text"]
                                        <div class="field big">${value}</div>
                                    [#else]
                                        <input class="input_disable" type="text" disabled="" value='${value}'>
                                    [/#if]
                                </div>
                            [/#list]

                            [#if config.handlers.detail?? && config.handlers.detail!=""]
                                <div class="cerca_lente_rosso summary_block detail ${config.cssClass}" data-holder="${getValue(dataloop, config.id)!}">
                                    Dettaglio ${config.label}
                                </div>
                            [/#if]
                        </div>
                    </td>
                    [/#list]
                </tr>
            </tbody>
        </table>
    </div>
    <div class="clear"></div>
</div>

<script id="template-pratica-collegata" type="text/x-handlebars-template">

	<td class="summary_block" id="summary_block-${config.prefix}-{{${config.id}}}">
		<div class="circle lastCircle">
			[#list rows as row]
				<div class="ctrlHolder">
					<label class="required [#if row_index==0]first[/#if]">
						${row.description}
					</label>
					[#if row_index==0 && config.handlers.remove?? && config.handlers.remove!=""]
						<a class="summary_block remove ${config.cssClass}" data-holder="{{${config.id}}}">
							<img src="/cross/themes/default/images/icons/link_break.png" alt="Scollega ${config.label}" title="Scollega ${config.label}">
							Scollega ${config.label}
						</a>
						</div>
						<div class="ctrlHolder">
					[/#if]
					[#if row.type=="text"]
						<div class="field big">{{${row.templateDataValue}}}</div>
					[#else]
						<input class="input_disable" type="text" disabled="" value='{{${row.templateDataValue}}}'>
					[/#if]
				</div>
			[/#list]

			[#if config.handlers.detail?? && config.handlers.detail!=""]
				<div class="cerca_lente_rosso summary_block detail ${config.cssClass}" data-holder="{{${config.id}}}">
					Dettaglio ${config.label}
				</div>
			[/#if]
		</div>
	</td>

</script>

<script type="text/javascript" >
    [#include "summaryBlock.js.ftl"]
</script>