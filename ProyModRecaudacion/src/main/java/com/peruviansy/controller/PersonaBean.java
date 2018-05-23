package com.peruviansy.controller;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.servlet.http.Part;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import org.omnifaces.util.Servlets;
import org.omnifaces.util.Utils;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.peruviansy.model.Persona;
import com.peruviansy.service.IPersonaService;
import com.peruviansy.service.Impl.PersonaServiceImpl;

@Named
@ViewScoped
public class PersonaBean implements Serializable{
	
	private List<Persona> lstPersonas=new ArrayList<Persona>();
	private String url=new String();
	private String url2=new String();
	
	private List<Part> files;
	private String nombre;
	private String apellido;
	private double monto;
	private int nro;
	private File file2;
	private Date fechainicio;
	private Date fechafinal;
	private LocalDate fechainicio1;
	private LocalDate fechafinal1;
	
	@Inject
	private Persona persona;
	
	@Inject
	private IPersonaService service;
	private UploadedFile file;

	
	@PostConstruct
	public void init() 
	{     nombre=new String();
	      this.apellido=new String();
	    	this.listar();
	}
	  
    public void listar()
    {
    	try {
    		lstPersonas=service.listar();
    		
    	}catch(Exception e) {
    		
    	}
    }
    
    public void listarxId() {
    	try 
    	{
    		//lstPersonas=service.ListarPorId(this.persona);
    	}catch(Exception e) {
    		
    	}
    }

   	 public List<Part> getFiles() {
		return files;
	}

	public void setFiles(List<Part> files) {
		this.files = files;
	}
	
	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public Date getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public Date getFechafinal() {
		return fechafinal;
	}

	public void setFechafinal(Date fechafinal) {
		this.fechafinal = fechafinal;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public int getNro() {
		return nro;
	}

	public void setNro(int nro) {
		this.nro = nro;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<Persona> getLstPersonas() {
		return lstPersonas;
	}

	public void setLstPersonas(List<Persona> lstPersonas) {
		this.lstPersonas = lstPersonas;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public UploadedFile getFile() {
		return file;
	}
	
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	  public void upload() {
	        if(file != null) {
	            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
	            FacesContext.getCurrentInstance().addMessage(null, message);
	        }
	        this.url=file.getFileName();
	    }
	  
	  public void myFileUpload() throws IOException {
		 
		  File file2 = new File(file.getFileName());  
	      // System.out.println(file2.getCanonicalPath()); 
	      //System.out.println(file2.getPath()); 
	      //System.out.println(file2.getAbsoluteFile()); 
		 System.out.println("****"+file.getFileName()); 
		 InputStream fi=file.getInputstream();
		 OutputStream out=new FileOutputStream(file.getFileName());
                 byte[] cont=Utils.toByteArray(file.getInputstream());
                 out.write(cont);
		  
		  this.url=this.file.getFileName()  ;
		  this.url2=this.url;
		  //ServletContext servletContext=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
		 // this.url2=servletContext.getRealPath("")+File.separator+"upload"+File.separator+this.url;
		  //FacesMessage msg=new FacesMessage("Succesful"+this.url+" is Uploaded");
		  //FacesContext.getCurrentInstance().addMessage(null,msg);
		  //System.out.println(this.url2);
		  registrar();
		  
	  }
	  
	  public void myFileUploadMasivo() throws IOException, EncryptedDocumentException, InvalidFormatException 
	  {String namefile="";
		  
		  
		  try {
			    if (files != null) {
			        for (Part file : files) {
			            namefile = Servlets.getSubmittedFileName(file);
			            String type = file.getContentType();
			            long size = file.getSize();
			            InputStream content = file.getInputStream();
			            //System.out.println("HOLA HENRY");
			            //System.out.println(name);
			            // System.out.println(type);
			            //File archivoExcel = new File("D:/"+namefile);
			  		    //abrir el archivo con POI
					InputStream fi=file.getInputStream();
				 OutputStream out=new FileOutputStream(namefile);
                		 byte[] cont=Utils.toByteArray(content);
                		 out.write(cont);
					out.close();
					content.close();
					  
			            
			  		 // if(archivoExcel.exists()) 
			  		     this.url2=namefile;
			  			 
			  			  //System.out.println(this.url2);
			  			  registrar();	
					File fichero = new File("D:/"+namefile);

			  			  if (fichero.delete())
			  		        System.out.println("El fichero ha sido borrado satisfactoriamente"+namefile);
			  			  else
			  		        System.out.println("El fichero no pudó ser borrado"+namefile);
			  			  
			  			  FacesMessage msg=new FacesMessage("Archivo cargado "+this.url2);
			  			  FacesContext.getCurrentInstance().addMessage(null,msg);
			  		  
			        }
			    }
			 }catch(Exception e) {
				 System.out.println(e.getMessage());
			 } 
	  }
	  

	public void registrar() 
	{
		try {
			
		//Calendar cal=Calendar.getInstance();
		//cal.setTime(fechaSeleccionada);
		
		//LocalDate localDate=LocalDate.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		
		Persona per=new Persona();
		per.setNombre("Henry Vargas");
		//System.out.println("wwwwwww"+this.url2);
		
		service.registrar(per,this.url2);
		
	    }catch(Exception e) 
		{
		e.printStackTrace();
	    }

   }
	
	public void listarxPersona() {
		try {
		  this.monto=0;
		  Persona per=new Persona();
	     
		  per.setNombre(this.nombre);
		  per.setDependencia(this.apellido);
		  
		  Calendar cal= Calendar.getInstance();
		  cal.setTime(fechainicio);
		  fechainicio1=LocalDate.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH));
		  
		  Calendar cal1=Calendar.getInstance(); 
		  cal1.setTime(fechafinal);
		  fechafinal1=LocalDate.of(cal1.get(Calendar.YEAR),cal1.get(Calendar.MONTH)+1,cal1.get(Calendar.DAY_OF_MONTH));
		  
		  //List<Persona> lst=new ArrayList<>();
		  //this.lstPersonas=lst;
		  this.lstPersonas= service.listarxPersona(per,fechainicio1,this.fechafinal1);
		  for(Persona p : lstPersonas) {
			  monto=monto+p.getImporte();
		  }
		  this.nro=lstPersonas.size();
		  
		  //System.out.println(cal1.get(Calendar.MONTH)+"****"+cal.get(Calendar.MONTH));
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
