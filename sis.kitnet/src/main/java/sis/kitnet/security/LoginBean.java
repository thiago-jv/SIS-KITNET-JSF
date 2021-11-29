package sis.kitnet.security;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.util.UtilMensagens;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletRequest request;

	@Inject
	private HttpServletResponse response;

	@Inject
	private ExternalContext context;

	@Getter
	@Setter
	private String apelido;

	public String preRender() {
		if ("true".equals(request.getParameter("invalid"))) {
			UtilMensagens.mensagemErro("Usuário ou senha inválido!");
			apelido = null;
		}
		return "";
	}

	public void login() throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward(request, response);
		facesContext.responseComplete();
	}

	public boolean isAdministrador() {
		return context.isUserInRole("ADMINISTRADOR");
	}

}