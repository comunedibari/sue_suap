/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.errori;

import it.wego.cross.beans.ErroreBean;
import it.wego.cross.entity.Errori;

/**
 *
 * @author giuseppe
 */
public enum ErroreType {

    IMRIC {

        @Override
        public ErroreBean execute(Errori errore) {
//            GestioneEmailRicezione ger = new GestioneEmailRicezione(errore);
//            return ger.execute();
            return null;
        }
    },
    PDATA {

        @Override
        public ErroreBean execute(Errori errore) {
//            GestioneProtocollo gp = new GestioneProtocollo(errore);
//            return gp.execute();
            return null;
        }
    },
    PDER {

        @Override
        public ErroreBean execute(Errori errore) {
//            GenerazioneEvento ge = new GenerazioneEvento(errore);
//            return ge.execute();
            return null;
        }
    },
    PDST {

        @Override
        public ErroreBean execute(Errori errore) {
            //TODO: aggiungi popolamento staging
            return null;
        }
    },
    WSMPD {

        @Override
        public ErroreBean execute(Errori errore) {
            //TODO: popolamento mypage
            return null;
        }
    },
    WSMRE {

        @Override
        public ErroreBean execute(Errori errore) {
            //TODO: popolamento mypage
            return null;
        }
    },
    GERI {

        @Override
        public ErroreBean execute(Errori errore) {
            //TODO: richiesta su ric
            return null;
        }
    },
    GERM {

        @Override
        public ErroreBean execute(Errori errore) {
//            GestioneEmailRicezione ger = new GestioneEmailRicezione(errore);
//            return ger.execute();
            return null;
        }
    },
    WSMPR {

        @Override
        public ErroreBean execute(Errori errore) {
            //TODO: ricevuta su mypage
            return null;
        }
    },
    ERAS {

        @Override
        public ErroreBean execute(Errori errore) {
            //TODO: ricevuta su mypage
            return null;
        }
    };

    public abstract ErroreBean execute(Errori errore);
}
