package br.tur.reservafacil.gwtpoc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface ResourceBundle extends ClientBundle {
	public static final ResourceBundle INSTANCE = GWT.create(ResourceBundle.class);

	@Source("style.css")
	public CssResource css();

}