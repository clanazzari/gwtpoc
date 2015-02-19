package br.tur.reservafacil.gwtpoc.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.tur.reservafacil.gwtpoc.client.service.ReservaService;
import br.tur.reservafacil.gwtpoc.shared.ReservaVO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ReservaServiceImpl extends RemoteServiceServlet implements
		ReservaService {

	public List<ReservaVO> listarTodos() throws IllegalArgumentException {

		List<ReservaVO> lista = new ArrayList<ReservaVO>();
		try {
			Statement stmt = InitDatabase.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PUBLIC.TB_RESERVA");
			while (rs.next()) {
				ReservaVO reservaVO = new ReservaVO(
						rs.getInt("ID"), 
						rs.getString("NOME"), 
						rs.getString("TIPO"));
				lista.add(reservaVO);
			}

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

		return lista;
	}

	public Integer inserir(final String nome, final String tipo) throws IllegalArgumentException {

		try {
			
			ResultSet rsId = InitDatabase.conn.createStatement().executeQuery("call NEXT VALUE FOR PUBLIC.SQ_RESERVA");
			Integer id = null;
			if (rsId.next()){
				id = rsId.getInt(1);
				System.out.println("id = " + id);
			}

			PreparedStatement stmt = InitDatabase.conn.prepareCall("INSERT INTO PUBLIC.TB_RESERVA VALUES (?, ?, ?);");
			stmt.setInt(1, id);
			stmt.setString(2, nome);
			stmt.setString(3, tipo);
			stmt.executeUpdate();

			return id;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

	}

	public void remover(final Integer id) throws IllegalArgumentException {
		try {
			PreparedStatement stmt = InitDatabase.conn.prepareStatement("DELETE FROM PUBLIC.TB_RESERVA WHERE ID = ?");
			stmt.setInt(1, id);

			stmt.executeUpdate();

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public void alterar(final Integer id,final String nome,final String tipo) throws IllegalArgumentException {
		try {
			System.out.println("entrou " + id + " nome " + nome);
			PreparedStatement stmt = InitDatabase.conn.prepareStatement("UPDATE PUBLIC.TB_RESERVA SET NOME = ?, TIPO = ? WHERE ID = ?");
			stmt.setString(1, nome);
			stmt.setString(2, tipo);
			stmt.setInt(3, id);

			stmt.executeUpdate();
			System.out.println("foi");

		} catch (Exception e) {
			System.out.println("erro: " + e.getMessage());
			throw new IllegalArgumentException(e);
		}
	}

}
