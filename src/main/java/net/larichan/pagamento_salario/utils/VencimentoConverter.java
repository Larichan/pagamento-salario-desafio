package net.larichan.pagamento_salario.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.larichan.pagamento_salario.models.Vencimento;
import net.larichan.pagamento_salario.repositories.VencimentoRepository;

@Component
@Scope("session")
public class VencimentoConverter implements Converter {
@Autowired
    private VencimentoRepository repository;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        if(value != null && !value.isEmpty()) {
            return repository.findById(Integer.parseInt(value)).get();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        if(value instanceof Vencimento) {
            Vencimento vencimento = (Vencimento) value;
            return vencimento.getId().toString();
        }
        return "";
    }
}
