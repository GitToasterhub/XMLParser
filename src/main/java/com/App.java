package com;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	
    	sendGet();
    	//Custom Parser class usage
    	XMLParser parser=new XMLParser();
    	StringBuilder response=parser.requestGet();
    	parser.formatXML(response);
    	News news=parser.deSerialize(response);
    	System.out.println(news.getNews().get(0).getDescription());
    	//Custom Parser class is ready with deSerialized object news
    	
    }
    @Deprecated
    static void sendGet() throws Exception {

		String url = "http://24tv.ua/rss/all.xml";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
	
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		int end=0;
		end=response.indexOf("<item>");
        response.replace(0, end, "<rss xmlns:dc=\"http://purl.org/dc/elements/1.1/\" version=\"2.0\"><news>");
        int start=response.indexOf("</channel>");
        response.replace(start, response.length(), "</news></rss>");
        
		//print result
		System.out.println(response);
		FileWriter fw=new FileWriter("e:/out.xml");
		fw.write(response.toString());
		fw.close();
		Serializer ser=new Persister();
		News news=ser.read(News.class,response.toString());
	
        System.out.println(news.getNews().get(0).getDescription());
	}

}

