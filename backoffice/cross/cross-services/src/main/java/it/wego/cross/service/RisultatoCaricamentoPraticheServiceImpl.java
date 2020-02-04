package it.wego.cross.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.wego.cross.dao.RisultatoCaricamentoPraticheDao;
import it.wego.cross.dto.RisultatoCaricamentoPraticheDTO;
import it.wego.cross.entity.RisultatoCaricamentoPratiche;
import it.wego.cross.serializer.RisultatoCaricamentoPraticheSerializer;

@Service
public class RisultatoCaricamentoPraticheServiceImpl implements RisultatoCaricamentoPraticheService {

    @Autowired
    private RisultatoCaricamentoPraticheDao risultatoCaricamentoPraticheDao;
    @Autowired
    private RisultatoCaricamentoPraticheSerializer RisultatoCaricamentoPraticheSerializer;

    @Override
    public RisultatoCaricamentoPratiche findRisultatoCaricamentoPraticheByIdentificativoPratica(String identificativoPratica) {
        RisultatoCaricamentoPratiche risultato = risultatoCaricamentoPraticheDao.findRisultatoCaricamentoPraticheByIdentificativoPratica(identificativoPratica);
        return risultato;
    }

    @Override
    public void inserisci(RisultatoCaricamentoPratiche risultatoCaricamentoPratiche) throws Exception {
    	risultatoCaricamentoPraticheDao.insert(risultatoCaricamentoPratiche);
    	risultatoCaricamentoPraticheDao.flush();
    }

    @Override
    public void aggiorna(RisultatoCaricamentoPratiche risultatoCaricamentoPratiche) throws Exception {
    	risultatoCaricamentoPraticheDao.update(risultatoCaricamentoPratiche);
    	risultatoCaricamentoPraticheDao.flush();
    }

    @Override
    public Long countRisultatoCaricamentoPratiche() {
        Long count = risultatoCaricamentoPraticheDao.countAllRisultatoCaricamentoPratiche();
        return count;
    }
    

    @Override
    public List<RisultatoCaricamentoPraticheDTO> getRisultatoCaricamentoPratiche() {
        List<RisultatoCaricamentoPratiche> risultatoCaricamentoPratiche = risultatoCaricamentoPraticheDao.findAll();
        List<RisultatoCaricamentoPraticheDTO> dtos = new ArrayList<RisultatoCaricamentoPraticheDTO>();
        if (risultatoCaricamentoPratiche != null && !risultatoCaricamentoPratiche.isEmpty()) {
            for (RisultatoCaricamentoPratiche risultato : risultatoCaricamentoPratiche) {
                RisultatoCaricamentoPraticheDTO dto = RisultatoCaricamentoPraticheSerializer.serialize(risultato);
                dtos.add(dto);
            }
        }
        return dtos;
    }

//    @Override
//    public Long countRisultatoCaricamentoPraticheAttivi() {
//        Long result;
//        result = RisultatoCaricamentoPraticheDao.countAllRisultatoCaricamentoPraticheAttivi();
//        return result;
//    }
    @Override
    public RisultatoCaricamentoPraticheDTO findRisultatoCaricamentoPraticheByIdentificativoPratica(Integer id) {
        RisultatoCaricamentoPratiche risultato = risultatoCaricamentoPraticheDao.findByIdRisultatoCaricamentoPratiche(id);
        if (risultato != null) {
        	RisultatoCaricamentoPraticheDTO  dto = RisultatoCaricamentoPraticheSerializer.serialize(risultato);
            return dto;
        } else {
            return null;
        }
    }

	@Override
	public RisultatoCaricamentoPraticheDTO findRisultatoCaricamentoPraticheById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
