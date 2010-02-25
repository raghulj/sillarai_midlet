
package com.sillarai.models;

import java.io.*;
import java.util.*;
import javax.microedition.midlet.*;
import javax.microedition.rms.*;


public class Settings {

        public static String USERNAME;
        public static String PASSWORD;
        private String storeName = "_sillari_store";

        public static String SESSION_TOKEN;
	/**
	 * Singleton instance
	 */
	private static Settings store;

	/**
	 * Reference to MIDlet
	 */
	private MIDlet midlet;

	/**
	 * State - whether or not changes have been made
	 */
	private boolean valuesChanged = false;

	/**
	 * Hashtable of Settings
	 */
	private Hashtable properties = new Hashtable();

	/**
   * Singleton pattern is used to return only one instance of record store
   */
	public static synchronized Settings getInstance(MIDlet midlet)
			                       throws IOException, RecordStoreException {
		if (store == null) {
			store = new Settings(midlet);
		}
		return store;
	}

	/** Private singleton Constructor */
	private Settings(MIDlet midlet) throws IOException, RecordStoreException {
		this.midlet = midlet;
		load();
	}

	/*
   * Method never called, so comment out. /** Return true if value exists in
   * record store private boolean exists( String name ) { return getProperty(
   * name ) != null; }
   */

	/** Get property from Hashtable */
	private synchronized String getProperty(String name) {
		String value = (String) properties.get(name);
		if (value == null && midlet != null) {
			value = midlet.getAppProperty(name);
			if (value != null) {
				properties.put(name, value);
			}
		}
		return value;
	}

	/** Get boolean property */
	public boolean getBooleanProperty(String name, boolean defaultValue) {
		String value = getProperty(name);
		if (value != null) {
			return value.equals("true") || value.equals("1");
		}
		return defaultValue;
	}

	/** Get integer property */
	public int getIntProperty(String name, int defaultValue) {
		String value = getProperty(name);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
			}
		}
		return defaultValue;
	}

	/** Get string property */
	public String getStringProperty(String name, String defaultValue) {
		Object value = getProperty(name);
		return (value != null) ? value.toString() : defaultValue;
	}

      	/** Get Hash property */
	public Hashtable getHashProperty(String name, Hashtable defaultValue) {
		Object value = getProperty(name);
                Hashtable g = new Hashtable();
                try{
                g  = (value.equals(null)) ? (Hashtable)value : defaultValue;
                Enumeration e  = g.keys();
                while(e.hasMoreElements()){
                    System.out.println("-------"+e.nextElement());
                }
                }catch(Exception e){
                    System.out.println("Class cast pblm "+e);
                }
                
                
		return g;
	}

	/** Load properties from record store */
	private synchronized void load() throws IOException, RecordStoreException {
		RecordStore rs = null;
		ByteArrayInputStream bin = null;
		DataInputStream din = null;

		valuesChanged = false;
		properties.clear();

		try {
			rs = RecordStore.openRecordStore(storeName, true);
			if (rs.getNumRecords() == 0) {
				rs.addRecord(null, 0, 0);
			} else {
				byte[] data = rs.getRecord(1);
				if (data != null) {
					bin = new ByteArrayInputStream(data);
					din = new DataInputStream(bin);
					int num = din.readInt();
					while (num-- > 0) {
						String name = din.readUTF();
						String value = din.readUTF();
						properties.put(name, value);
					}
				}
			}
		} finally {
			if (din != null) {
				try {
					din.close();
				} catch (Exception e) {
				}
			}

			if (rs != null) {
				try {
					rs.closeRecordStore();
				} catch (Exception e) {
				}
			}
		}
	}

	/** Save property Hashtable to record store */
	public synchronized void save(boolean force) throws IOException,
			RecordStoreException {
		if (!valuesChanged && !force)
			return;

		RecordStore rs = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);

		try {
			dout.writeInt(properties.size());
			Enumeration e = properties.keys();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String value = properties.get(name).toString();
				dout.writeUTF(name);
				dout.writeUTF(value);
			}

			byte[] data = bout.toByteArray();

			rs = RecordStore.openRecordStore(storeName, false);
			rs.setRecord(1, data, 0, data.length);
		} finally {
			try {
				dout.close();
			} catch (Exception e) {
			}

			if (rs != null) {
				try {
					rs.closeRecordStore();
				} catch (Exception e) {
				}
			}
		}
	}

	/** Set a boolean property */
	public void setBooleanProperty(String name, boolean value) {
		setStringProperty(name, value ? "true" : "false");
	}

	/** Set an integer property */
	public void setIntProperty(String name, int value) {
		setStringProperty(name, Integer.toString(value));
	}

        /** Set Hashtable property */
        public void setHashProperty(String name,Hashtable hash){
                properties.put(name, hash);
                valuesChanged = true;
        }

        public void setObjectProperty(int id,Object obj){
                properties.put(String.valueOf(id), obj);
        }

        public Object getObjectProperty(int id, String defaultValue) {
		String value = getProperty(String.valueOf(id));
		if (value != null) {
			try {

				return value;
			} catch (NumberFormatException e) {
			}
		}
		return defaultValue;
	}

	/** Set a string property */
	public synchronized boolean setStringProperty(String name, String value) {
		if (name == null && value == null)
			return false;
		properties.put(name, value);
		valuesChanged = true;
		return true;
	}
}
