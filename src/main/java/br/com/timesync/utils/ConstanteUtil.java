package br.com.timesync.utils;

public abstract class ConstanteUtil {


    private static final String FUSO_HORARIO_BRASIL = "-03:00";
    private static final String NOME_APLICACAO = "Time Sync";

    public static String getFusoHorarioBrasil() {
        return FUSO_HORARIO_BRASIL;
    }

    public static String getNomeAplicacao() {
        return NOME_APLICACAO;
    }

}
