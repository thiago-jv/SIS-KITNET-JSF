package sis.kitnet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import sis.kitnet.cdi.CDIServiceLocator;
import sis.kitnet.model.Parametrizacao;
import sis.kitnet.repository.Parametrizacaos;

@FacesConverter(forClass = Parametrizacao.class)
public class ParametrizacaoConverter implements Converter {

	private Parametrizacaos parametrizacaos;

	public ParametrizacaoConverter() {
		parametrizacaos = CDIServiceLocator.getBean(Parametrizacaos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Parametrizacao retorno = null;
		if (value != null) {
			Long idParametrizacao = new Long(value);
			retorno = parametrizacaos.porId(idParametrizacao);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Parametrizacao entity = (Parametrizacao) value;
			return entity.getId() == null ? null : entity.getId().toString();
		}
		return null;
	}

}

