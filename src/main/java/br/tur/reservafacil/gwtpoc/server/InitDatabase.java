package br.tur.reservafacil.gwtpoc.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitDatabase extends HttpServlet {

    private static final long serialVersionUID = 5617556357385631152L;
    public static Connection conn;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // OPEN CONNECTION
            Class.forName("org.hsqldb.jdbcDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:mem:aname","SA","");
            Statement stm = conn.createStatement();

            // CREATE TABLE
            stm.executeUpdate("CREATE TABLE PUBLIC.TB_RESERVA (ID INTEGER, NOME VARCHAR(200), TIPO VARCHAR(20), PRIMARY KEY (ID));");

            // INSERT
            stm.executeUpdate("INSERT INTO PUBLIC.TB_RESERVA VALUES (1, 'Primeira reserva', 'hotel')");
            stm.executeUpdate("INSERT INTO PUBLIC.TB_RESERVA VALUES (2, 'Segunda reserva', 'passagem')");

        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }
    @Override
    public void destroy() {
        super.destroy();
    }
}
