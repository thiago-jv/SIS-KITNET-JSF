package sis.kitnet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import sis.kitnet.cdi.CDIServiceLocator;
import sis.kitnet.model.Predio;
import sis.kitnet.repository.Predios;

@FacesConverter(forClass = Predio.class)
public class PredioConverter implements Converter {

	private Predios predios;

	public PredioConverter() {
		predios = CDIServiceLocator.getBean(Predios.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Predio retorno = null;
		if (value != null) {
			Long idPredio = new Long(value);
			retorno = predios.porId(idPredio);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Predio entity = (Predio) value;
			return entity.getId() == null ? null : entity.getId().toString();
		}
		return null;
	}

}

