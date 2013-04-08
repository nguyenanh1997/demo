package demo.util.applet.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

public class UploadFile implements Runnable{
	
	private List<File> files = new ArrayList<File>();
	private String host;
	private String port;
	
	
	public UploadFile(String host, String port) {
		super();
		this.host = host;
		this.port = port;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	@Override
	public void run() {
		try {
			Socket socket = new Socket(host, Integer.parseInt(port));
			BufferedOutputStream outputStream =new BufferedOutputStream(socket.getOutputStream());
			//fileCount:5;size:1024;0000000;
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("fileCout:");
			stringBuffer.append(files.size());
			stringBuffer.append(";");
			for(File file:files){
				stringBuffer.append("size:");
				stringBuffer.append(file.length());
				stringBuffer.append(";");
				stringBuffer.append(Base64.encodeBase64String(FileUtils.readFileToByteArray(file)));
				stringBuffer.append(";");
			}
			outputStream.write(stringBuffer.toString().getBytes());
			outputStream.flush();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
}