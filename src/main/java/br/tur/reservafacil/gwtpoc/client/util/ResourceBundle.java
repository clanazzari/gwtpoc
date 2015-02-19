package br.tur.reservafacil.gwtpoc.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface ResourceBundle extends ClientBundle {
	public static final ResourceBundle INSTANCE = GWT.create(ResourceBundle.class);

	@Source("style.css")
	public Css css();

	@Source("Messages.properties")
	public TextResource defaultText();
}