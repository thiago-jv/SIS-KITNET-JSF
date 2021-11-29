package sis.kitnet.util;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractManager {

	@Inject
	protected EntityManager manager;
	
	@Inject
	protected FacesContext facesContext;

	@Inject
	protected HttpServletResponse response;
	
}
