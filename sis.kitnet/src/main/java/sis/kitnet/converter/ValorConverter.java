package sis.kitnet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import sis.kitnet.cdi.CDIServiceLocator;
import sis.kitnet.model.Valor;
import sis.kitnet.repository.Valores;

@FacesConverter(forClass = Valor.class)
public class ValorConverter implements Converter {

	private Valores valores;

	public ValorConverter() {
		valores = CDIServiceLocator.getBean(Valores.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Valor retorno = null;
		if (value != null) {
			Long idValor = new Long(value);
			retorno = valores.porId(idValor);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Valor entity = (Valor) value;
			return entity.getId() == null ? null : entity.getId().toString();
		}
		return null;
	}

}

