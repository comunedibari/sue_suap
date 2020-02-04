[#ftl]
[#if stileCella=='Paragrafo']
	[#list table.tr as tr]
		[#list tr.td as td]
			[#if td.coloreBackground??]
				<text:p text:style-name="ParagrafoBold">${td.valore}</text:p>
			[#else]
				<text:p text:style-name="ParagrafoNormale">${td.valore}</text:p>			
			[/#if]			
		[/#list]
	[/#list]
[#else]
	<table:table table:name="${nomeTabella}" table:style-name="${stileTabella}">
	[#if colonneRipetute == 'true']
		<table:table-column table:number-columns-repeated="${numeroColonne}"/>
	[#else]
		[#list larghezzeRelativeColonne as l]
		<table:table-column table:style-name="DydotColonna${l}Style"/>
		[/#list]
	[/#if]
	[#list table.tr as tr]
		<table:table-row>
	[#list tr.td as td]
	[#assign l_stileCella=stileCella]
	[#if td.coloreBackground??]
		[#assign l_stileCella=stileBoldCella]
	[/#if]
	[#if td.colspan=='0']
			<table:table-cell table:style-name="${l_stileCella}" office:value-type="string">
	[#else]
			<table:table-cell table:style-name="${l_stileCella}" table:number-columns-spanned="${td.colspan}" office:value-type="string">
	[/#if]
	[#if td.nomeTabelleInterne??]
				${td.nomeTabelleInterne} 
	[#else]
	 [#if td.tipoInput??]
	  [#if inputGrafici=='false'] 
	  	<text:p text:style-name="Table_20_Contents">
		[#if td.tipoInput=='radio'] 
			[#if td.inputSelezionato='true']
				<draw:control text:anchor-type="as-char" draw:style-name="DydotStyleGrafico" svg:width="0.500cm" svg:height="0.500cm" draw:control="DydotRadioChecked${td.indice}"/>
			[#else]
				<draw:control text:anchor-type="as-char" draw:style-name="DydotStyleGrafico" svg:width="0.500cm" svg:height="0.500cm" draw:control="DydotRadioUnChecked${td.indice}"/>
			[/#if]
		[#else]
			[#if td.inputSelezionato='true']
				<draw:control text:anchor-type="as-char" draw:style-name="DydotStyleGrafico" svg:width="0.500cm" svg:height="0.500cm" draw:control="DydotCheckBoxChecked"/>
			[#else]
				<draw:control text:anchor-type="as-char" draw:style-name="DydotStyleGrafico" svg:width="0.500cm" svg:height="0.500cm" draw:control="DydotCheckBoxUnChecked"/>		
			[/#if]
	  	[/#if]
		</text:p>
	 [#else]
		 <text:p text:style-name="ParagrafoCentrato">
			 <draw:frame draw:style-name="FrameCentrato" text:anchor-type="as-char" svg:width="0.350cm" svg:height="0.350cm">
		        [#if td.tipoInput=='radio']
		                [#if td.inputSelezionato='true']
		                        <draw:image xlink:href="WegoPictures/radiook.png" xlink:type="simple" xlink:show="embed" xlink:actuate="onLoad"/>
		                [#else]
		                        <draw:image xlink:href="WegoPictures/radioko.png" xlink:type="simple" xlink:show="embed" xlink:actuate="onLoad"/>
		                [/#if]
		        [#else]
		                [#if td.inputSelezionato='true']
		                        <draw:image xlink:href="WegoPictures/checkok.png" xlink:type="simple" xlink:show="embed" xlink:actuate="onLoad"/>
		                [#else]
		                        <draw:image xlink:href="WegoPictures/checkko.png" xlink:type="simple" xlink:show="embed" xlink:actuate="onLoad"/>
		                [/#if]
		        [/#if]
		     </draw:frame>
	     </text:p>
	 [/#if]
	[#else]
				<text:p text:style-name="Table_20_Contents">${td.valore}</text:p>
	[/#if]
	[/#if]
			</table:table-cell>
	[/#list]
		</table:table-row>
	[/#list]
	</table:table>
[/#if]