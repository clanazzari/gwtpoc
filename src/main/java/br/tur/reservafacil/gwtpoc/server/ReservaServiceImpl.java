package br.tur.reservafacil.gwtpoc.server;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.tur.reservafacil.gwtpoc.client.service.ReservaService;
import br.tur.reservafacil.gwtpoc.shared.vo.ReservaVO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ReservaServiceImpl extends RemoteServiceServlet implements ReservaService {

    public List<ReservaVO> listarTodos() throws IllegalArgumentException {

	List<ReservaVO> lista = new ArrayList<ReservaVO>();
	try {

	    Statement stmt = InitDatabase.conn.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT * FROM PUBLIC.TB_RESERVA");
	    while (rs.next()) {
		ReservaVO reservaVO = new ReservaVO(rs.getInt("ID"), rs.getString("NOME"), rs.getString("TIPO"));
		lista.add(reservaVO);
	    }

	} catch (Exception e) {
	    throw new IllegalArgumentException(e);
	}

	return lista;
    }
}
