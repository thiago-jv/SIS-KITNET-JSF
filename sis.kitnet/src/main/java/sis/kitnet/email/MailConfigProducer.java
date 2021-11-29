package sis.kitnet.email;

import java.io.IOException;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.SimpleMailConfig;

import lombok.Getter;
import lombok.Setter;
import sis.kitnet.model.Parametrizacao;
import sis.kitnet.repository.Parametrizacaos;

@ManagedBean
public class MailConfigProducer {

	@Getter
	@Setter
	private Parametrizacao parametrizacao;

	@Inject
	private Parametrizacaos parametrizacaos;

	@Produces
	@ApplicationScoped
	public SessionConfig getMailConfig() throws IOException {
		
		
		
		SimpleMailConfig config = new SimpleMailConfig();
		List<Parametrizacao> listaParametrizacoes = parametrizacaos.listar();
		for (Parametrizacao parametrizacao : listaParametrizacoes) {
			config.setServerHost("smtp.gmail.com");
			config.setServerPort(465);
			config.setEnableSsl(true);
			config.setAuth(true);
			config.setUsername(parametrizacao.getEmailOrigem());
			config.setPassword(parametrizacao.getSenhaEmailOrigem());
			return config;
		}
		return config;
	}
}
