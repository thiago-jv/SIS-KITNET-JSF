package sis.kitnet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import sis.kitnet.cdi.CDIServiceLocator;
import sis.kitnet.model.Apartamento;
import sis.kitnet.repository.Apartamentos;

@FacesConverter(forClass = Apartamento.class)
public class ApartamentoConverter implements Converter {

	private Apartamentos apartamentos;

	public ApartamentoConverter() {
		apartamentos = CDIServiceLocator.getBean(Apartamentos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Apartamento retorno = null;
		if (value != null) {
			Long idApartamento = new Long(value);
			retorno = apartamentos.porId(idApartamento);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Apartamento entity = (Apartamento) value;
			return entity.getId() == null ? null : entity.getId().toString();
		}
		return null;
	}

}

