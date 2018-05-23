package com.peruviansy.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IService<T> {

	void registrar(T t,String url) throws Exception;
	void modificar(T t) throws Exception;
	List<T> listar() throws Exception;
	T ListarPorId(T t) throws Exception;
	List<T> listarxPersona(T t,LocalDate inicio,LocalDate fin)  throws Exception;
	
}
