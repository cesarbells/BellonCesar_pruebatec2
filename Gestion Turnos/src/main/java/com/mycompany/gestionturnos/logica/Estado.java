package com.mycompany.gestionturnos.logica;

/**
 *
 * @author PC
 */
public enum Estado {

    EN_ESPERA {
        @Override
        public String toString() {
            return "En espera";
        }
    },
    ATENDIDO {
        @Override
        public String toString() {
            return "Atendido";
        }
    };
}
