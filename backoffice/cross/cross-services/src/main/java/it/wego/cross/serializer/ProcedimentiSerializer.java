package it.wego.cross.serializer;

import it.wego.cross.dto.ProcedimentiTestiDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.ProcedimentiTesti;
import it.wego.cross.entity.view.ProcedimentiCollegatiView;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Procedimento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriele
 */
public class ProcedimentiSerializer {

    static final String DEFAULT_LANG = "IT";

    public static it.wego.cross.xml.Procedimenti serialize(Pratica pratica) {
        List<PraticaProcedimenti> procedimenti = pratica.getPraticaProcedimentiList();
        it.wego.cross.xml.Procedimenti procedimentiXml = new it.wego.cross.xml.Procedimenti();
        it.wego.cross.xml.Procedimento procedimento;

        for (PraticaProcedimenti praticaProcedimento : procedimenti) {
            procedimento = new Procedimento();
            if (praticaProcedimento.getProcedimenti() != null) {

                procedimento.setIdProcedimento(praticaProcedimento.getProcedimenti().getIdProc());
                procedimento.setCodProcedimento(praticaProcedimento.getProcedimenti().getCodProc());
                procedimento.setTermini(Utils.bi(praticaProcedimento.getProcedimenti().getTermini()));

                //TODO: Verificare con Piergiorgio
                if (praticaProcedimento.getProcedimenti().getProcedimentiTestiList() != null) {
                    for (ProcedimentiTesti procedimentoTesto : praticaProcedimento.getProcedimenti().getProcedimentiTestiList()) {
                        if (procedimentoTesto.getLingue() != null && procedimentoTesto.getLingue().getCodLang().equalsIgnoreCase(DEFAULT_LANG)) {
                            procedimento.setDesProcedimento(procedimentoTesto.getDesProc());
                        }
                    }
                }
            }
            procedimento.setCodLang(DEFAULT_LANG);
            if (praticaProcedimento.getEnti() != null) {
                procedimento.setIdEnteDestinatario(Utils.bi(praticaProcedimento.getEnti().getIdEnte()));
                procedimento.setCodEnteDestinatario(String.valueOf(praticaProcedimento.getEnti().getIdEnte()));
                procedimento.setDesEnteDestinatario(praticaProcedimento.getEnti().getDescrizione());
            }

            procedimentiXml.getProcedimento().add(procedimento);
        }
        return procedimentiXml;
    }

    public static ProcedimentoDTO serializeForEvent(Procedimenti procedimento, Enti ente, String lang) {
        ProcedimentoDTO p = new ProcedimentoDTO();
        p.setCodLang(lang);
        for (ProcedimentiTesti testo : procedimento.getProcedimentiTestiList()) {
            if (testo.getLingue().getCodLang().equalsIgnoreCase(lang)) {
                if (ente != null) {
                    p.setDesProcedimento(testo.getDesProc() + " (" + ente.getDescrizione() + ")");
                } else {
                    p.setDesProcedimento(testo.getDesProc());
                }
            }
        }
        p.setIdProcedimento(procedimento.getIdProc());
        return p;
    }

    public static ProcedimentoDTO serialize(Procedimenti procedimento) {
        ProcedimentoDTO dto = new ProcedimentoDTO();
        List<ProcedimentiTestiDTO> list = new ArrayList<ProcedimentiTestiDTO>();
        dto.setCodLang(DEFAULT_LANG);
        for (ProcedimentiTesti testo : procedimento.getProcedimentiTestiList()) {
            if (testo.getLingue().getCodLang().equalsIgnoreCase(DEFAULT_LANG)) {
                dto.setDesProcedimento(testo.getDesProc());
            }
            ProcedimentiTestiDTO pt = new ProcedimentiTestiDTO();
            pt.setCodLingua(testo.getLingue().getCodLang());
            pt.setDescrizione(testo.getDesProc());
            pt.setIdLingua(testo.getLingue().getIdLang());
            pt.setIdProcedimento(testo.getProcedimentiTestiPK().getIdProc());
            list.add(pt);
        }
        dto.setProcedimentiTestiList(list);
        dto.setIdProcedimento(procedimento.getIdProc());
        dto.setTermini(procedimento.getTermini());
        //^^CS AGGIUNTA
        dto.setClassifica(procedimento.getClassifica());
        dto.setCodProcedimento(procedimento.getCodProc());
        dto.setTipoProc(procedimento.getTipoProc());
        dto.setPeso(procedimento.getPeso());

        return dto;
    }
    //^^CS AGGIUNTA
    /**
     * ^^CS ATTENZIONE!!! non serializza ProcedimentiTesti
     *
     * @param dto
     * @return
     */
    public static Procedimenti serialize(ProcedimentoDTO dto) {
        Procedimenti db = new Procedimenti();
        serialize(dto, db);
        return db;
    }

    public static Procedimenti serialize(ProcedimentoDTO dto, Procedimenti db) {
        db.setIdProc(dto.getIdProcedimento());
        db.setTermini(dto.getTermini());
        db.setClassifica(dto.getClassifica());
        db.setCodProc(dto.getCodProcedimento());
        db.setTipoProc(dto.getTipoProc().toUpperCase());
        db.setPeso(dto.getPeso());
        return db;
    }

    public static ProcedimentoDTO serialize(ProcedimentiCollegatiView p) {
        ProcedimentoDTO dto = new ProcedimentoDTO();
        dto.setCodProcedimento(p.getCodProc());
        dto.setDesProcedimento(p.getDesProc());
        dto.setIdProcedimento(p.getIdProc());
        return dto;
    }

    /**
     * ^^CS AGGIUNTTA
     */
    public static ProcedimentoDTO serialize(ProcedimentiLocalizzatiView p) {
        ProcedimentoDTO dto = new ProcedimentoDTO();
        dto.setCodProcedimento(p.getCodProc());
        dto.setDesProcedimento(p.getDesProc());
        dto.setIdProcedimento(p.getIdProc());
        dto.setCodProcedimento(p.getCodProc());
        dto.setTipoProc(p.getTipoProc());
        dto.setTermini(p.getTermini());
        return dto;
    }

    /**
     * ^^CS AGGIUNTTA
     */
    public static ProcedimentoDTO serialize(ProcedimentiEnti procedimento) {
        ProcedimentoDTO proc = new ProcedimentoDTO();
        proc.setIdEnteDestinatario(procedimento.getIdEnte().getIdEnte());
        proc.setDesEnteDestinatario(procedimento.getIdEnte().getDescrizione());
        proc.setIdProcedimento(procedimento.getIdProc().getIdProc());
        proc.setDesProcedimento(procedimento.getIdProc().getProcedimentiTestiList().get(0).getDesProc());
        proc.setIdProcedimentoEnte(procedimento.getIdProcEnte());
        return proc;
    }

    public static it.wego.cross.xml.Procedimento serializeXML(ProcedimentiEnti procedimento) {
        it.wego.cross.xml.Procedimento proc = new it.wego.cross.xml.Procedimento();
        proc.setIdEnteDestinatario(Utils.bi(procedimento.getIdEnte().getIdEnte()));
        proc.setDesEnteDestinatario(procedimento.getIdEnte().getDescrizione());
        proc.setIdProcedimento(procedimento.getIdProc().getIdProc());
        proc.setDesProcedimento(procedimento.getIdProc().getProcedimentiTestiList().get(0).getDesProc());
        proc.setTermini(Utils.bi(procedimento.getIdProc().getTermini()));
        return proc;
    }
}
