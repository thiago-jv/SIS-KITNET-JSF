package sis.kitnet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import sis.kitnet.cdi.CDIServiceLocator;
import sis.kitnet.model.Inquilino;
import sis.kitnet.repository.Inquilinos;

@FacesConverter(forClass = Inquilino.class)
public class InquilinoConverter implements Converter {

	private Inquilinos inquilinos;

	public InquilinoConverter() {
		inquilinos = CDIServiceLocator.getBean(Inquilinos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Inquilino retorno = null;
		if (value != null) {
			Long idIquilino = new Long(value);
			retorno = inquilinos.porId(idIquilino);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Inquilino entity = (Inquilino) value;
			return entity.getId() == null ? null : entity.getId().toString();
		}
		return null;
	}

}

