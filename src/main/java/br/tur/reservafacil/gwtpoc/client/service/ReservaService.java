package br.tur.reservafacil.gwtpoc.client.service;

import java.util.List;

import br.tur.reservafacil.gwtpoc.shared.vo.ReservaVO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("service")
public interface ReservaService extends RemoteService {
    public List<ReservaVO> listarTodos();  
    public Integer inserir(final String  nome, final String tipo) throws IllegalArgumentException;
    public void remover(final Integer id) throws IllegalArgumentException;
    public void alterar(final Integer id, final String nome, final String tipo) throws IllegalArgumentException;
}
