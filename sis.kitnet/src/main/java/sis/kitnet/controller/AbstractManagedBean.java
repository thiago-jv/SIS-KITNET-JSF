package sis.kitnet.controller;


public abstract class AbstractManagedBean {

	protected abstract void limpar();

	protected abstract void inicializar();
	
	protected abstract String salvar();
	
}
