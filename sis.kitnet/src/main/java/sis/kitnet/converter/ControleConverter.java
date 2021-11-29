package sis.kitnet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import sis.kitnet.cdi.CDIServiceLocator;
import sis.kitnet.model.Controle;
import sis.kitnet.repository.Controles;

@FacesConverter(forClass = Controle.class)
public class ControleConverter implements Converter {

	private Controles controles;

	public ControleConverter() {
		controles = CDIServiceLocator.getBean(Controles.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Controle retorno = null;
		if (value != null) {
			Long idControle = new Long(value);
			retorno = controles.porId(idControle);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Controle entity = (Controle) value;
			return entity.getId() == null ? null : entity.getId().toString();
		}
		return null;
	}

}

