package MyPackage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String city=request.getParameter("word");
		
		PrintWriter out=response.getWriter();
		try 
		{
		String weatherInfo=getWeatherInfo(city);
		
		JsonObject weatherData=parseWeatherInfo(weatherInfo);
		
		out.println("<html>");
		
		out.println("<head><title>Weather Information</title>");
		
		out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css'>");
		
		out.println("</head>");
		out.println("<body style='text=align:center;'>");
		
		out.println("<h1 class='mb-4'>Weather Information of "+city+"</h1>");
		out.println("<div><b>Location:</b>"+weatherData.get("location")+"</div>");
		out.println("<div><b>Time:</b>"+weatherData.get("time")+"</div>");
		out.println("<div><b>Region:</b>"+weatherData.get("region")+"</div>");
		out.println("<div><b>Temperature:</b>"+weatherData.get("temperature")+"</div>");
		out.println("<div><b>Weather Type:</b>"+weatherData.get("type")+"</div>");


		out.println("</body>");

		}
		catch(Exception e) 
		{
			
			out.println("<html>");
			out.println("<head><title>Error</title></head>");
			out.println("<h1 class='mb-4'>Error Retrieving Weather Information</h1>");
			out.println("<p>"+e.getMessage()+"</p>");
			
			out.println("</body>");
			out.println("</html>");
		}
		finally {
			out.close();
		}
	}
	
	private String getWeatherInfo(String city) throws IOException, InterruptedException {
	    String apiKey = "a53f3f425468e6b76c0f6359290cb7a7";
	    String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid="+apiKey;
	    // Building and sending the HTTP request
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(apiUrl))
	            .GET()
	            .build();

	    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
	    System.out.println(response.body());
	    return response.body();
	}

    
	private JsonObject parseWeatherInfo(String weatherInfo) {
	    JsonParser parser = new JsonParser();
	    JsonObject json = parser.parse(weatherInfo).getAsJsonObject();

	    JsonObject weatherData = new JsonObject();
	    
	    // Check if the response contains error information
	    if (json.has("cod") && json.get("cod").getAsString().equals("404")) {
	        weatherData.addProperty("error", json.get("message").getAsString());
	        return weatherData;
	    }

	    // Extract weather data
	    JsonObject currentData = json.getAsJsonObject("main");
	    JsonObject weatherDetails = json.getAsJsonArray("weather").get(0).getAsJsonObject();
	    
	    weatherData.addProperty("location", json.get("name").getAsString());
	    weatherData.addProperty("time", json.getAsJsonPrimitive("dt").getAsLong()); 
	    weatherData.addProperty("region", json.getAsJsonObject("sys").get("country").getAsString());
	    weatherData.addProperty("temperature", currentData.getAsJsonPrimitive("temp").getAsDouble());
	    weatherData.addProperty("type", weatherDetails.getAsJsonPrimitive("description").getAsString());

	    return weatherData;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
