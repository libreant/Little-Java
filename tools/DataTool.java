 /*
    Copyright (C)  2015  libreant.
    Permission is granted to copy, distribute and/or modify this document
    under the terms of the GNU Free Documentation License, Version 1.3
    or any later version published by the Free Software Foundation;
    with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
    A copy of the license is included in the section entitled "GNU
    Free Documentation License".
*/




package org.libreant.little.tools;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

import org.libreant.little.tools.DataFormat;
import org.libreant.little.twofish.Twofish;



public class DataTool {
	private String infilename;
	private String outfilename;
	private String key;
	
	public void setInfilename(String infilename){
		this.infilename=infilename;
	}
	public void setOutfilename(String outfilename){
		this.outfilename=outfilename;
	}
	public void setKey(String key){
		this.key=key;
	}
	public String getInfilename(){
		return infilename;
	}
	public String getOutfilename(){
		return outfilename;
	}
	public String getKey(){
		return key;
	}
	
	public  DataTool(String in,String out,String key){
		setInfilename(in);
		setOutfilename(out);
		setKey(key);
	}
	
	public void enCode(){
		if (this.outfilename==null ||this.outfilename.equals(""))
			this.outfilename=this.infilename+".en_";
		try {
			
			DataFormat df=new DataFormat();
			df.setDname(this.infilename);
			FileInputStream in=new FileInputStream(this.infilename);
			BufferedInputStream inbf=new BufferedInputStream(in);
			
			int n=-1;
			int sum=0;
			byte[] buf=new byte[16];
			
			ArrayList dset=new ArrayList();
			while((n=inbf.read(buf, 0, 16))!=-1){
				sum+=n;
				dset.add(Twofish.antEncrypt(buf, 0, Twofish.antKey(this.getKey().getBytes())));
			}
			ByteBuffer data=ByteBuffer.allocate((sum/16+1)*16);
			for(Object b : dset.toArray()){
				data.put((byte[])b);
			}
			df.setDsize(sum);
			df.setDtext(data.array());
			
			
			
			FileOutputStream out=new FileOutputStream(this.outfilename,false);
			ObjectOutputStream outobj=new ObjectOutputStream(out);
			
			outobj.writeObject(df);
			outobj.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void deCode(){
		if (this.outfilename==null ||this.outfilename.equals(""))
			this.outfilename="";
		try {
			
			FileInputStream in=new FileInputStream(this.infilename);
			ObjectInputStream inobj=new ObjectInputStream(in);
			DataFormat df=(DataFormat)inobj.readObject();
			if(this.outfilename=="")
				this.outfilename=df.getDname();
			FileOutputStream out=new FileOutputStream(this.outfilename,false);
			BufferedOutputStream outbf=new BufferedOutputStream(out);

			
			int sum=df.getDsize();
			
			for(int i=0; i<df.getDsize()/16+1 ;i++){
				sum-=16;
				byte[] buf=Twofish.antDecrypt(df.getDtext(), i*16, Twofish.antKey(this.getKey().getBytes()));
		
				if(sum<0)
					outbf.write(buf, 0, 16+sum);
				else
					outbf.write(buf, 0, 16);
			}
			outbf.close();
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException{

		
		DataTool a=new DataTool("/tmp/a.pdf","","12345678");
		a.enCode();
		DataTool c=new DataTool("/tmp/a.pdf.en_","/tmp/xx","12345678");
		c.deCode();
		
	/*ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("/tmp/df.l",false));
	DataFormat df=new DataFormat();
	df.setDname("readme");
	df.setDsize(10);
	df.setDtext("1234567890".getBytes());
	out.writeObject(df);
	
	ObjectInputStream in=new ObjectInputStream(new FileInputStream("/tmp/df.l"));
	DataFormat d=(DataFormat)in.readObject();
	System.out.println(d.getDname());
	System.out.println(d.getDsize());
	System.out.println(new String(d.getDtext()));
	BufferedInputStream bin=new BufferedInputStream(new FileInputStream("/tmp/df.l"));
	
	*/
	}
}
