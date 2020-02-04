import org.springframework.context.ApplicationContext;
import javax.servlet.http.HttpServletRequest;
import it.wego.utils.wegoforms.FormEngine;
import org.springframework.ui.Model;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.controller.AbstractController;
import it.wego.cross.constants.*;
import it.wego.cross.service.*;
import it.wego.cross.dao.*;
import it.wego.cross.entity.*;
import org.slf4j.Logger;

def execute(Binding binding) {    
    ApplicationContext ctx = binding.getVariable("ctx");
    Logger log = binding.getVariable("log");
    
    HttpServletRequest request = binding.getVariable("requesto");
    Model model = binding.getVariable("model");
    FormEngine formEngine = binding.getVariable("formEngine");
    
    EntiService entiService = ctx.getBean(EntiService.class);
    UtentiService utentiService = ctx.getBean(UtentiService.class);
    PraticheService praticheService = ctx.getBean(PraticheService.class);
    LookupDao lookupDao = ctx.getBean(LookupDao.class);
    
    UtenteDTO utente = utentiService.getUtenteConnessoDTO(request);

    formEngine.putInstanceDataValue("path", model.asMap().get("path"));
    List<String> anniRiferimento = praticheService.popolaListaAnni();
    formEngine.putInstanceDataValue("anniRiferimento", anniRiferimento);
    List<Enti> enti = entiService.getListaEntiPerRicerca(utente);
    formEngine.putInstanceDataValue("enti", enti);
    List<LkTipoSistemaCatastale> lkTipoSistemaCatastale = lookupDao.findAllTipoCatastale();
    formEngine.putInstanceDataValue("lkTipiCatasto", lkTipoSistemaCatastale);
    List<LkTipoUnita> lkTipoUnita = lookupDao.findAllLkTipoUnita();
    formEngine.putInstanceDataValue("lkTipiUnita", lkTipoUnita);
    List<LkTipoParticella> lkTipoParticella = lookupDao.findAllLkTipoParticella();
    formEngine.putInstanceDataValue("lkTipiParticella", lkTipoParticella);
    List<LkStatoPratica> lookup = praticheService.getLookupsStatoPratica(StatoPratica.RICEVUTA);
    formEngine.putInstanceDataValue("statiPratica", lookup);

}