package com.peruviansy.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.omnifaces.util.Servlets;
import org.omnifaces.util.Utils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.peruviansy.model.Persona;
import com.peruviansy.service.IPersonaService;
import com.peruviansy.service.Impl.PersonaServiceImpl;

@Named
@ViewScoped
public class PersonaBean implements Serializable{
	
	private List<Persona> lstPersonas=new ArrayList<Persona>();
	private List<Persona> lstReporte=new ArrayList<Persona>();
	private String url=new String();
	private String url2=new String();
	private String extension;
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
	private List<Carpeta> lstCarpeta=new ArrayList<Carpeta>();
	
	@Inject
	private Persona persona;
	
	@Inject
	private IPersonaService service;
	private UploadedFile file;
	
	
	@PostConstruct
	public void init() 
	{     this.lstReporte.clear();
	      nombre=new String();
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

    
    //GETTERS Y SETTERS
	
	public List<Carpeta> getLstCarpeta() {
		return lstCarpeta;
	}

	public void setLstCarpeta(List<Carpeta> lstCarpeta) {
		this.lstCarpeta = lstCarpeta;
	}
    
    
    
	public String getUrl2() {
		return url2;
	}

	public List<Persona> getLstReporte() {
		return lstReporte;
	}

	public void setLstReporte(List<Persona> lstReporte) {
		this.lstReporte = lstReporte;
	}

	public List<Part> getFiles() {
		return files;
	}

	public void setFiles(List<Part> files) {
		this.files = files;
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
	  
	  public void convertirMayusculas()
	  {   this.apellido=this.apellido.toUpperCase();
		  
	  }
	  
	  public void convertirNombreMayusculas() {
			// TODO Auto-generated method stub
		  this.nombre=this.nombre.toUpperCase();
		}
	  
	  public void myFileUpload() throws IOException {
		 
		File file2 = new File(file.getFileName());  
	      // System.out.println(file2.getCanonicalPath()); 
	      //System.out.println(file2.getPath()); 
	     // System.out.println(file.getFileName()); 
		  //InputStream fi=file.getInputstream();
		 // OutputStream out=new FileOutputStream(file.getFileName());
         // byte[] cont=Utils.toByteArray(file.getInputstream());
         // out.write(cont);
		  InputStream content=file.getInputstream();
		  OutputStream out=new FileOutputStream(file.getFileName());
		  this.extension=file.getContentType();
		  
		 if(extension.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")||
				 extension.equalsIgnoreCase("application/vnd.ms-excel")) {
		  //System.out.println("ggggggg   "+file.getContentType());
          byte[] cont=Utils.toByteArray(content);
          out.write(cont);
          out.close();
          content.close();
		  
		  this.url=this.file.getFileName()  ;
		  this.url2=this.url;
		  	  
		  //System.out.println(this.url2);
		  registrar();
		  File fichero = new File(file.getFileName());

			  if (fichero.delete())
		        System.out.println("El fichero ha sido borrado satisfactoriamente"+file.getFileName());
			  else
		        System.out.println("El fichero no pudó ser borrado"+file.getFileName());

		  //FacesMessage msg=new FacesMessage("Extension :"+ extension);
		  //FacesContext.getCurrentInstance().addMessage(null,msg);
		 }else 
		 {
			  FacesMessage msg=new FacesMessage("Extension no permitida :"+ extension);
			  FacesContext.getCurrentInstance().addMessage(null,msg);
		 }
	  }
	  
	  public void myFileUploadMasivo() throws IOException, EncryptedDocumentException, InvalidFormatException 
	  {   
		  
		  String namefile="";
	     
		  int id=1;
		  try {
			    if (files != null) {
			        for (Part file1 : files) {
			        	//SE TIENE QUE CREER UN NUEVO OBJETO EN EL FOR ,POR QUE SINO TODOS LOS OBJETOS
			        	//REFERENCIAN AL ULTIMO ARCHIVO INGRESADO
			        	Carpeta c=new Carpeta();
			            namefile = Servlets.getSubmittedFileName(file1);
			            this.extension = file1.getContentType();
			         
			            long size = file1.getSize();
			            InputStream content = file1.getInputStream();
			          
			   if(extension.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")||
			   				 extension.equalsIgnoreCase("application/vnd.ms-excel")) {
			            InputStream fi=file1.getInputStream();
			  		    OutputStream out=new FileOutputStream(namefile);
			            byte[] cont=Utils.toByteArray(content);
			            out.write(cont);
			            out.close();
			            content.close();
			            
			  		 // if(archivoExcel.exists()) 
			  		     this.url2=namefile;
			  		     //System.out.println(id);
			  		     c.setId(id);
			  		     System.out.println(namefile);
			  		     c.setNombre(namefile);
			  		     c.setUrl(namefile);
			  		     c.setTamano(size/1024);
			  		     this.lstCarpeta.add(c);
			  			  //System.out.println(this.url2);
			  			  registrar();	
			  			  
			  			  File fichero = new File(namefile);

			  			  if (fichero.delete())
			  		          System.out.println("El fichero ha sido borrado satisfactoriamente"+namefile);
			  			  else
			  		          System.out.println("El fichero no pudó ser borrado"+namefile);
			  			  
			  			  FacesMessage msg=new FacesMessage("Archivo cargado "+this.url2);
			  			  FacesContext.getCurrentInstance().addMessage(null,msg);
			  			  id++;

			     }else {
			    	 FacesMessage msg=new FacesMessage("Extension no permitida :"+ extension);
					  FacesContext.getCurrentInstance().addMessage(null,msg);
			     }
			        
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
		
		service.registrar(this.extension,this.url2);
		
	    }catch(Exception e) 
		{
		e.printStackTrace();
	    }

   }
	
	public void listarxPersona() {
		try {
		  this.monto=0;
		  Persona per=new Persona();
		  System.out.println("******fechainicio*"+fechainicio);
		  if(!this.apellido.equalsIgnoreCase("") && apellido!=null)
	        this.convertirMayusculas();
		  if(!this.nombre.equalsIgnoreCase("") && nombre!=null)
		        this.convertirNombreMayusculas();
		  
	      per.setNombre(this.nombre);
		  per.setDependencia(this.apellido);
		  System.out.println("******fechainicio*"+fechainicio);
		  Calendar cal= Calendar.getInstance();
		  if(fechainicio!=null) 
		  {
		     cal.setTime(fechainicio);
		     fechainicio1=LocalDate.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH));
		  }
		  else {
			  fechainicio1=null;
		  }
		  Calendar cal1=Calendar.getInstance(); 
		  
		  if(fechafinal!=null)
		  {
			  cal1.setTime(fechafinal);
			  fechafinal1=LocalDate.of(cal1.get(Calendar.YEAR),cal1.get(Calendar.MONTH)+1,cal1.get(Calendar.DAY_OF_MONTH));
		  }
		  else 
		  {
			  fechafinal1=null;
		  }
		  List<Persona> lst=new ArrayList<Persona>();
		  this.lstReporte=lst;
		  this.lstReporte= service.listarxPersona(per,fechainicio1,this.fechafinal1);
	  
		}catch(Exception e) 
		  {
			System.out.println(e.getMessage());
		  }
	}

	
}

	
