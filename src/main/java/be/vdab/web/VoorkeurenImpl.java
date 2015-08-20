package be.vdab.web;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.INTERFACES)
public class VoorkeurenImpl implements Voorkeur, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String foto;

	@Override
	public String getFoto() {
		return foto;
	}

	@Override
	public void setFoto(String foto) {
		this.foto = foto;
	}

}
