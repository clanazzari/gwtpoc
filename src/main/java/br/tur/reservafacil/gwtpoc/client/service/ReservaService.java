package br.tur.reservafacil.gwtpoc.client.service;

import java.util.List;

import br.tur.reservafacil.gwtpoc.shared.vo.ReservaVO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface ReservaService extends RemoteService {
    public List<ReservaVO> listarTodos() throws IllegalArgumentException;
}
