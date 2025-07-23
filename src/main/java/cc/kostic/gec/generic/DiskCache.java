package cc.kostic.gec.generic;

import cc.kostic.gec.endpoints.deribit.DeribitRsp;

import java.io.*;

public class DiskCache {
	
	
	
	public static void writeToStorage(Object o, String filename) {
		try (FileOutputStream fos = new FileOutputStream(filename);
			 BufferedOutputStream bos = new BufferedOutputStream(fos);
			 ObjectOutputStream oos = new ObjectOutputStream(bos);) {
			oos.writeObject(o);
			System.out.println("written " + filename);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	
	public static Object getFromStorage(String filename) {
		Object rezult = null;
		try (FileInputStream fis = new FileInputStream(filename);
			 BufferedInputStream bis = new BufferedInputStream(fis);
			 ObjectInputStream ois = new ObjectInputStream(bis);){
			rezult = ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
		return rezult;
	}
}
