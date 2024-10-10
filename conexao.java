import java.net.*;
import java.util.Scanner;
import java.io.*;
import org.json.*;

class RequestsTerminal{
	/**
		@param api 
		link da api de livros
		@param url 
		classe URL
	*/
	public static String api = "";
	public HttpURLConnection httpcon; 
	private URL url;
	
	RequestsTerminal(String api) throws  MalformedURLException, IOException{
		this.api = api;
		this.url = new URL(api);
		this.httpcon = (HttpURLConnection) url.openConnection();
	}
	
	
	public static void main(String args[]) throws IOException{
		Scanner s = new Scanner(System.in);
		System.out.print("cep:");
		String cep = s.nextLine();
		String api = "https://viacep.com.br/ws/*/json";
		api = api.replace("*",cep);
		RequestsTerminal RL = new RequestsTerminal(api);
		
		try{	
			RL.httpcon.getRequestMethod();
			System.out.println("URL: "+ api);
			
			if(RL.httpcon.getResponseCode()== HttpURLConnection.HTTP_OK){
				BufferedReader input = new BufferedReader(new InputStreamReader(RL.httpcon.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = input.readLine()) != null){	
					sb.append(line);
					System.out.println(line);
				}
				input.close();
				RL.httpcon.disconnect();
				
				JSONObject obj = new JSONObject(sb);
			}
			RL.httpcon.disconnect();
			System.exit(0);
		}catch(Exception e){
			System.err.println("Code de resposta: "+ RL.httpcon.getResponseCode());
			System.err.println("Erro: "+ e);
			System.exit(-1);
		}finally{
			RL.httpcon.disconnect();
			System.exit(0);
		}
	}
}