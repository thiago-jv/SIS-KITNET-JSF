package sis.kitnet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import sis.kitnet.cdi.CDIServiceLocator;
import sis.kitnet.model.Grupo;
import sis.kitnet.repository.Grupos;

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter {

	private Grupos grupos;

	public GrupoConverter() {
		grupos = CDIServiceLocator.getBean(Grupos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Grupo retorno = null;
		if (value != null) {
			Long id = new Long(value);
			retorno = grupos.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Grupo entity = (Grupo) value;
			return entity.getId() == null ? null : entity.getId().toString();
		}
		return null;
	}
}

