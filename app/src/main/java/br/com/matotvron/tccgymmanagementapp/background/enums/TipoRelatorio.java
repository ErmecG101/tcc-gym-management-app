package br.com.matotvron.tccgymmanagementapp.background.enums;

import androidx.annotation.NonNull;

/**
 * Original idea was to implement types of reports, however we opted for simplicity.
 * @deprecated Deprecated for simplicity.
 */
public enum TipoRelatorio {

    NORMAL, PREVENTIVA, MANUTENCAO;


    /**
     * Get the defined ID for each type of report.
     * @param value Report to get the ID from
     * @return Returns 0-2 possible IDs, returns -1 if it wasn't a valid Report Type.
     */
    public static int getIdByTipoRelatorio(@NonNull TipoRelatorio value){
        switch (value){
            case NORMAL:
                return 0;
            case PREVENTIVA:
                return 1;
            case MANUTENCAO:
                return 2;
            default:
                return -1;
        }
    }

}
