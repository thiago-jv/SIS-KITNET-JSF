package sis.kitnet.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class UtilMensagens {

	public static boolean isPostback() {
		return FacesContext.getCurrentInstance().isPostback();
	}

	// evita selects desnessários na aplicação
	public static boolean isNotPostback() {
		return !isPostback();
	}

	public static void mensagemErro(String mensagem) {
		FacesContext contexto = FacesContext.getCurrentInstance();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem));
		contexto.getExternalContext().getFlash().setKeepMessages(true);
	}

	public static void mensagemInformacao(String mensagem) {
		FacesContext contexto = FacesContext.getCurrentInstance();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
		contexto.getExternalContext().getFlash().setKeepMessages(true);
	}

	public static void addAdivertenciaoMessage(String message) {
		FacesContext contexto = FacesContext.getCurrentInstance();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
		contexto.getExternalContext().getFlash().setKeepMessages(true);
	}

}
