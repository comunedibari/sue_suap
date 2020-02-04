//import it.wego.cross.xml.Anagrafiche;
//import it.wego.cross.xml.Recapito
//import groovy.lang.Binding;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneDinamicaType;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CampoHrefType;
//
//def updateAnagrafica(Binding binding) {
//    // h00257
//    Anagrafiche anagrafica = binding.getVariable("anagrafica");
//    DichiarazioneDinamicaType dichiarazione = binding.getVariable("dichiarazione");
//    String counter = binding.getVariable("counter");
//    Recapito r = null;
//    if (anagrafica.getAnagrafica() != null) {
//        if (anagrafica.getAnagrafica().getRecapiti() != null ){
//            if (anagrafica.getAnagrafica().getRecapiti().getRecapito() != null){
//                if (!anagrafica.getAnagrafica().getRecapiti().getRecapito().isEmpty()) {
//                    r = anagrafica.getAnagrafica().getRecapiti().getRecapito().get(0);
//                }
//            }
//        }
//        for (CampoHrefType campo : dichiarazione.getCampiHref().getCampoHrefArray()) {
//            if (campo.getNome().equals("d001" + counter) && !campo.getValoreUtente()) {
//                if (campo.getValoreUtente().equals("1")) {
//                    anagrafica = null;
//                    break;
//                }
//            }
//            if (campo.getNome().equals("d011" + counter) && !campo.getValoreUtente()) {
//                if (campo.getValoreUtente().equals("I")) {
//                    anagrafica.getAnagrafica().setTipoAnagrafica("F");
//                    anagrafica.getAnagrafica().setVarianteAnagrafica("I");
//                    if (r != null){
//                        r.setIdTipoIndirizzo(BigInteger.valueOf(1));
//                        r.setDesTipoIndirizzo("RESIDENZA");
//                    }
//                    break;
//                } else {
//                    anagrafica.getAnagrafica().setTipoAnagrafica("G");
//                    if (r != null){                    
//                        r.setIdTipoIndirizzo(BigInteger.valueOf(3));
//                        r.setDesTipoIndirizzo("SEDE");
//                    }
//                    break;
//                }
//            }
//        }
//    }
//}
//import it.wego.cross.xml.Anagrafiche;
//import it.wego.cross.xml.Recapito
//import groovy.lang.Binding;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneDinamicaType;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CampoHrefType;
//
//def updateAnagrafica(Binding binding) {
//    // h00100
//    Anagrafiche anagrafica = binding.getVariable("anagrafica");
//    DichiarazioneDinamicaType dichiarazione = binding.getVariable("dichiarazione");
//    String counter = binding.getVariable("counter");
//    Recapito r = null;
//    if (anagrafica.getAnagrafica() != null) {
//        if (anagrafica.getAnagrafica().getRecapiti() != null ){
//            if (anagrafica.getAnagrafica().getRecapiti().getRecapito() != null){
//                if (!anagrafica.getAnagrafica().getRecapiti().getRecapito().isEmpty()) {
//                    r = anagrafica.getAnagrafica().getRecapiti().getRecapito().get(0);
//                }
//            }
//        }
//		String codiceFiscale = anagrafica.getAnagrafica().getCodiceFiscale();
//		if (!codiceFiscale && codiceFiscale.matches("[0-9]+")) {
//			//è il CF di una Persona Giuridica
//			r.setIdTipoIndirizzo(BigInteger.valueOf(3));
//			r.setDesTipoIndirizzo("SEDE");
//		} else {
//			//è il CF di una Persona Fisica
//			r.setIdTipoIndirizzo(BigInteger.valueOf(1));
//			r.setDesTipoIndirizzo("RESIDENZA");
//		}		
//    }
//}
//
//import it.wego.cross.xml.Anagrafiche;
//import it.wego.cross.xml.Recapito
//import groovy.lang.Binding;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneDinamicaType;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CampoHrefType;
//
//def updateAnagrafica(Binding binding) {
//    // h00182
//    Anagrafiche anagrafica = binding.getVariable("anagrafica");
//    DichiarazioneDinamicaType dichiarazione = binding.getVariable("dichiarazione");
//    String counter = binding.getVariable("counter");
//    Recapito r = null;
//    if (anagrafica.getAnagrafica() != null) {
//        if (anagrafica.getAnagrafica().getRecapiti() != null ){
//            if (anagrafica.getAnagrafica().getRecapiti().getRecapito() != null){
//                if (!anagrafica.getAnagrafica().getRecapiti().getRecapito().isEmpty()) {
//                    r = anagrafica.getAnagrafica().getRecapiti().getRecapito().get(0);
//                }
//            }
//        }
//		for (CampoHrefType campo : dichiarazione.getCampiHref().getCampoHrefArray()) {
//			if (campo.getNome().equals("d001" + counter) && !campo.getValoreUtente()) {
//				if (campo.getValoreUtente().equals("1")) {
//					anagrafica.getAnagrafica().setTipoAnagrafica("F");
//					anagrafica.getAnagrafica().setVarianteAnagrafica("I");
//					r.setIdTipoIndirizzo(BigInteger.valueOf(1));
//					r.setDesTipoIndirizzo("RESIDENZA");
//					break;
//				} else {
//					anagrafica.getAnagrafica().setTipoAnagrafica("G");
//					r.setIdTipoIndirizzo(BigInteger.valueOf(3));
//					r.setDesTipoIndirizzo("SEDE");
//					break;
//				}
//			}
//		}
//    }
//}
//import it.wego.cross.xml.Anagrafiche;
//import it.wego.cross.xml.Recapito
//import groovy.lang.Binding;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneDinamicaType;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CampoHrefType;
//
//import it.wego.cross.xml.Anagrafiche;
//import it.wego.cross.xml.Recapito
//import groovy.lang.Binding;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneDinamicaType;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CampoHrefType;
//
//def updateAnagrafica(Binding binding) {
//    // h00098
//    Anagrafiche anagrafica = binding.getVariable("anagrafica");
//    DichiarazioneDinamicaType dichiarazione = binding.getVariable("dichiarazione");
//    String counter = binding.getVariable("counter");
//    BigInteger idTipoQualifica = null;
//    String desTipoQualifica = null;
//    if (anagrafica.getAnagrafica() != null) {
//		for (CampoHrefType campo : dichiarazione.getCampiHref().getCampoHrefArray()) {
//			if (campo.getNome().equals("d010" + counter) && !campo.getValoreUtente()) {
//				idTipoQualifica = BigInteger.valueOf((5);
//				desTipoQualifica = "Progettista";
//				break;
//			} else if (campo.getNome().equals("d020" + counter) && !campo.getValoreUtente()) {
//				idTipoQualifica = BigInteger.valueOf((5);
//				desTipoQualifica = "Progettista";
//				break;
//			} else if (campo.getNome().equals("d090" + counter) && !campo.getValoreUtente()) {
//				idTipoQualifica = BigInteger.valueOf((5);
//				desTipoQualifica = "Progettista";
//				break;
//			} else if (campo.getNome().equals("d030" + counter) && !campo.getValoreUtente()) {
//				idTipoQualifica = BigInteger.valueOf((6);
//				desTipoQualifica = "Direttore lavori";
//				break;
//			} else if (campo.getNome().equals("d040" + counter) && !campo.getValoreUtente()) {
//				idTipoQualifica = BigInteger.valueOf((6);
//				desTipoQualifica = "Direttore lavori";
//				break;
//			}
//		}
//		anagraficha.setIdTipoQualifica(idTipoQualifica);
//		anagraficha.setDesTipoQualifica(desTipoQualifica);
//    }
//import it.wego.cross.xml.Anagrafiche;
//import it.wego.cross.xml.Recapiti;
//import it.wego.cross.xml.Recapito;
//import groovy.lang.Binding;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneDinamicaType;
//import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CampoHrefType;
//import it.wego.cross.plugins.aec.AeCGestionePratica;
//import java.lang.String;
//
//def updateAnagrafica(Binding binding) {
//    // H000000011 - Genova
//    Anagrafiche anagrafica = binding.getVariable("anagrafica");
//    DichiarazioneDinamicaType dichiarazione = binding.getVariable("dichiarazione");
//    String counter = binding.getVariable("counter");
//    int sezione = binding.getVariable("sezione");
//    String varianteAnagrafica = null;
//	String tipoAnagrafica = "G";
//    if (anagrafica.getAnagrafica() != null) {
//		for (CampoHrefType campo : dichiarazione.getCampiHref().getCampoHrefArray()) {
//		    if (AeCGestionePratica.containsString(campo.getNome(), "P", counter, sezione)) {
//		        String valueDecodificato = AeCGestionePratica.getValoreCampo(campo.getNome(), campo, counter, sezione);
//			    if (valueDecodificato != null && valueDecodificato == "1") {
//				    varianteAnagrafica = "I";
//					tipoAnagrafica = "F";
//			    }
//		    }
//		}
//		anagrafica.getAnagrafica().setVarianteAnagrafica(varianteAnagrafica);
//		anagrafica.getAnagrafica().setTipoAnagrafica(tipoAnagrafica);
//		if (anagrafica.getAnagrafica().getRecapiti() != null) {
//			for (Recapito recapito: anagrafica.getAnagrafica().getRecapiti().getRecapito()) {
//				if (recapito.getIdTipoIndirizzo() == 3) {
//					recapito.setIdTipoIndirizzo(1);
//					recapito.setDesTipoIndirizzo("RESIDENZA");
//				}
//			}
//		}
//    }
//}
//}