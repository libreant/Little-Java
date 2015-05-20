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

import java.io.Serializable;

public class DataFormat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7392267301845986397L;
	private String dname;
	private int dsize;
	private byte[] dtext;

	public String getDname(){
		return dname;
	}
	public int getDsize()
	{
		return dsize;
	}
	public byte[] getDtext(){
		return dtext;
	}
	public void setDname(String name){
		this.dname=name;
	}
	public void setDsize(int size){
		this.dsize=size;
	}
	public void setDtext(byte[] text){
		this.dtext=text;
	}
	public DataFormat(){
		this.dname="";
		this.dsize=0;
		this.dtext=new byte[0];
	}
}
