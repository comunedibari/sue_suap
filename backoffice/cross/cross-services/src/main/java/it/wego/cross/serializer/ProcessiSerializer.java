package it.wego.cross.serializer;

import it.wego.cross.dto.ProcessoDTO;
import it.wego.cross.entity.Processi;
import org.springframework.stereotype.Component;

/**
 *
 * @author CS
 */
@Component
public class ProcessiSerializer {

    public static ProcessoDTO serilize(Processi processoDB) {
        ProcessoDTO processo = new ProcessoDTO();
        processo.setCodProcesso(processoDB.getCodProcesso());
        processo.setIdProcesso(processoDB.getIdProcesso());
        processo.setDesProcesso(processoDB.getDesProcesso());
        return processo;
    }
}
