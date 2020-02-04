/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.beans.JsonResponse;
import it.wego.cross.beans.grid.GridCittadinanzaBean;
import it.wego.cross.beans.grid.GridComponentBean;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.validator.impl.AlphabeticValidatorImpl;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Giuseppe
 */
@Component
public enum AperturaPraticheAction {

    trovaCittadinanza {

                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    String descrizione = request.getParameter("descrizione");
                    GridCittadinanzaBean json = new GridCittadinanzaBean();
                    AlphabeticValidatorImpl alphaValid = new AlphabeticValidatorImpl();
                    if (alphaValid.Controlla(descrizione)) {
//                        List<CittadinanzaDTO> cittadinanze = anagraficheService.findCittadinanze(descrizione);
//                        json.setRows(cittadinanze);
                    } else {
////                        jsonError.add("ERROR");
//                        json.setErrors(jsonError);
                    }
                    return json;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    trovaNazionalita {

                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    trovaProvincia {
                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    trovaComune {
                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    trovaFormaGiuridica {
                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    trovaTipoCollegio {
                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    salvaRecapitoSingolo {
                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    eliminaAnagraficaInXML {
                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    salvaAnagraficaInXML {
                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    salvaAnagrafica {
                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    salvaCFAnagraficaXML {
                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    checkCF {
                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            },
    getAnagrafica {
                @Override
                public GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }

                @Override
                public JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request) {
                    return null;
                }
            };

    @Autowired
    private AnagraficheService anagraficheService;

    public abstract GridComponentBean getJsonGrid(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request);

    public abstract JsonResponse getJsonResponse(PraticaDTO pratica, AnagraficaDTO anagrafica, HttpServletRequest request);
}
