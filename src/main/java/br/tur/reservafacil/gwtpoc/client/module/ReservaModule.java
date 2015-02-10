package br.tur.reservafacil.gwtpoc.client.module;

import java.util.Iterator;
import java.util.List;

import br.tur.reservafacil.gwtpoc.client.service.ReservaServiceAsync;
import br.tur.reservafacil.gwtpoc.shared.vo.ReservaVO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ReservaModule implements EntryPoint {

    // grid
    private Grid gridReserva;

    public void onModuleLoad() {
	final DialogBox dialogBox = new DialogBox();
	dialogBox.setText("Remote Procedure Call");
	dialogBox.setAnimationEnabled(true);
	final HTML serverResponseLabel = new HTML();

	// inicia grid
	gridReserva = new Grid();

	// popula grid
	ReservaServiceAsync.Util.getInstance().listarTodos(new AsyncCallback<List<ReservaVO>>() {

	    @Override
	    public void onSuccess(List<ReservaVO> result) {
		gridReserva.resize(1 + result.size(), 3);
		int row = 1;
		for (Iterator<ReservaVO> iterate = result.iterator(); iterate.hasNext(); row++) {
		    ReservaVO person = (ReservaVO)iterate.next();
		    gridReserva.setWidget(row, 0, new Label(Integer.toString(person.getId())));
		    gridReserva.setWidget(row, 1, new Label(person.getNome()));
		    gridReserva.setWidget(row, 2, new Label(person.getTipo()));
		}
	    }

	    @Override
	    public void onFailure(Throwable caught) {
		dialogBox.setText("Remote Procedure Call - Failure");
		serverResponseLabel.addStyleName("serverResponseLabelError");
		serverResponseLabel.setHTML(caught.getMessage());
		dialogBox.center();
	    }
	});

	// Adiciona GRID na pagina
	RootPanel.get().add(gridReserva);
    }
}
