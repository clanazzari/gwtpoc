package br.tur.reservafacil.gwtpoc.client.module;

import java.util.List;

import br.tur.reservafacil.gwtpoc.client.service.ReservaServiceAsync;
import br.tur.reservafacil.gwtpoc.client.util.Messages;
import br.tur.reservafacil.gwtpoc.client.util.ResourceBundle;
import br.tur.reservafacil.gwtpoc.shared.ReservaVO;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Reserva implements EntryPoint {

	// Tabela de registros
	final CellTable<ReservaVO> cellTable = new CellTable<ReservaVO>(ReservaVO.KEY_PROVIDER);
	final ListDataProvider<ReservaVO> dataProvider = new ListDataProvider<ReservaVO>();

	// DialogBox para nova reserva
	final DialogBox boxNovaReserva = new DialogBox(true);
	final FlowPanel panelNovaReserva = new FlowPanel();

	// fields
	final TextBox nameField = new TextBox();
	final TextBox tipoField = new TextBox();
	final FlowPanel flowPanel = new FlowPanel();

	// Constants
	private final Messages messages = GWT.create(Messages.class);

	/**
	 * On Load
	 */
	public void onModuleLoad() {
		ResourceBundle.INSTANCE.css().ensureInjected();
		flowPanel.add(new HTMLPanel ("h1", messages.labelReservaFacil()));
		flowPanel.setStyleName(ResourceBundle.INSTANCE.css().center());

		// Carrega lista de reservas
		logJs("Log Javasc ript");
		this.carregarLista();

		// Carrega Form de nova reserva
		this.carregarFormReserva(); 
  
		// formulario de inclusao
		final Button btNovo = new Button(messages.buttonNew());
		btNovo.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				boxNovaReserva.center();
			}
		});
//		btNovo.addStyleName(ResourceBundle.INSTANCE.css().buttonInsert());

		flowPanel.add(btNovo);
		RootPanel.get().add(flowPanel);
	}

	private native void logJs(String mensagem)/*-{
		console.log(mensagem);
	}-*/;

	/**
	 * Carrega Tabela inicial da base
	 */
	private void carregarFormReserva() {

		nameField.setName(messages.labelName());
		tipoField.setName(messages.labelType()); 
		boxNovaReserva.setText(messages.buttonNew());

		panelNovaReserva.add(new Label(messages.labelName()));
		panelNovaReserva.add(nameField);

		panelNovaReserva.add(new Label(messages.labelType()));
		panelNovaReserva.add(tipoField);

		final Button btAdicionar = new Button(messages.buttonAdd(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				ReservaServiceAsync.Util.getInstance().inserir(
						nameField.getValue(), tipoField.getValue(),
						new AsyncCallback<Integer>() {

							@Override
							public void onSuccess(Integer id) {
								ReservaVO reservaVO = new ReservaVO(
										id,
										nameField.getValue(), 
										tipoField.getValue());
								dataProvider.getList().add(reservaVO);
								dataProvider.refresh();
								cellTable.redraw();
								boxNovaReserva.hide();
							}

							@Override
							public void onFailure(Throwable caught) {
								mensagemErro(caught);
								boxNovaReserva.hide();
							}
						});
			}
		});

		final Button btFechar = new Button(messages.buttonClose(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				boxNovaReserva.hide();
			}
		});

		panelNovaReserva.add(btAdicionar);
		panelNovaReserva.add(btFechar);
		boxNovaReserva.add(panelNovaReserva);
	}

	/**
	 * Carrega Tabela inicial da base
	 */
	private void carregarLista() {

		// ADICIONA COLUNAS
		final TextCell idCell = new TextCell();
		final EditTextCell nomeCell = new EditTextCell();
		final EditTextCell tipoCell = new EditTextCell();

		Column<ReservaVO, String> idColumn = new Column<ReservaVO, String>(idCell) {
			@Override
			public String getValue(ReservaVO object) {
				return object.getId().toString();
			}
		};
		Column<ReservaVO, String> nameColumn = new Column<ReservaVO, String>(nomeCell) {
			@Override
			public String getValue(ReservaVO object) {
				return object.getNome();
			}
		};
		Column<ReservaVO, String> tipoColumn = new Column<ReservaVO, String>(tipoCell) {
			@Override
			public String getValue(ReservaVO object) {
				return object.getTipo();
			}
		};
		Column<ReservaVO, String> buttonColumn = new Column<ReservaVO, String>(new ButtonCell()) {
			@Override
			public String getValue(ReservaVO object) {
				return messages.buttonRemove();
			}
		};
		cellTable.addColumn(idColumn, messages.labelId());
		cellTable.addColumn(nameColumn, messages.labelName());
		cellTable.addColumn(tipoColumn, messages.labelType());
		cellTable.addColumn(buttonColumn, " ");

		// remove registro
		buttonColumn.setFieldUpdater(new FieldUpdater<ReservaVO, String>() {
			public void update(final int index,final ReservaVO object, final String value) {

				ReservaServiceAsync.Util.getInstance().remover(object.getId(), 
						new AsyncCallback<Void>() {
							@Override
							public void onSuccess(Void result) {
								dataProvider.getList().remove(object);
								dataProvider.refresh();
								cellTable.redraw();
							}

							@Override
							public void onFailure(Throwable caught) {
								mensagemErro(caught);
								cellTable.redraw();
							}
						});
			}
		});

		// altera nome
		nameColumn.setFieldUpdater(new FieldUpdater<ReservaVO, String>() {
			@Override
			public void update(final int index, final ReservaVO object,
					final String value) {

				ReservaServiceAsync.Util.getInstance().alterar(object.getId(),
						value, object.getTipo(), new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								object.setNome(value);
								cellTable.redraw();
							}

							@Override
							public void onFailure(Throwable caught) {
								mensagemErro(caught);
								cellTable.redraw();
							}
						});
			}
		});

		// altera tipo
		tipoColumn.setFieldUpdater(new FieldUpdater<ReservaVO, String>() {
			@Override
			public void update(final int index, final ReservaVO object,
					final String value) {

				ReservaServiceAsync.Util.getInstance().alterar(object.getId(),
						object.getNome(), value, new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								object.setTipo(value);
								cellTable.redraw();
							}

							@Override
							public void onFailure(Throwable caught) {
								mensagemErro(caught);
								cellTable.redraw();
							}
						});
			}
		});

		// popula registros
		ReservaServiceAsync.Util.getInstance().listarTodos(
				new AsyncCallback<List<ReservaVO>>() {

					@Override
					public void onSuccess(List<ReservaVO> result) {
						dataProvider.setList(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						mensagemErro(caught);
					}
				});
		dataProvider.addDataDisplay(cellTable);
		dataProvider.refresh();

		// Adiciona GRID na pagina
		flowPanel.add(cellTable);
	}

	private final void mensagemErro(Throwable t) {
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		dialogBox.setText(t.getMessage());
		dialogBox.center();
		final Button closeButton = new Button(messages.buttonClose());
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
		dialogBox.add(closeButton);
	}
}
