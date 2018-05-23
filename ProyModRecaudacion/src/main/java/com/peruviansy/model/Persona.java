package com.peruviansy.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table(name="persona")
public class Persona implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue(strategy=GenerationType.TABLE,generator="persona_gen")
	//@SequenceGenerator(name="persona_gen",sequenceName="a")
	//@TableGenerator(name="persona_gen",table="SEQUENCES",pkColumnName="SEQ_NAME",valueColumnName="SEQ_NUMBER",
	//pkColumnValue="SEQUENCE",allocationSize=10)
	@Column(name="id",updatable=false,nullable=false)
	private long id;
	
	@Column(name="moneda")
	private String moneda;
	@Column(name="dependencia")
    private String dependencia;
	@Column(name="concepto")
    private String concepto;
	@Column(name="numero")
    private String numero;
	@Column(name="codigo")
	private String codigo;
	@Column(name="nombre")
	private String nombre;
	@Column(name="importe")
	private double importe;
	@Column(name="url")
	private String url;
	@Column(name="fecha")
	private LocalDate fecha;

	public Persona() {
	
		this.moneda="";
		this.dependencia="";
		this.concepto="";
        this.numero="";
        this.codigo="";
        this.nombre="";
        this.importe=0.0;
        fecha= LocalDate.of(2018,1,1);
	}

	
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getDependencia() {
		return dependencia;
	}
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	


	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	
	

}
