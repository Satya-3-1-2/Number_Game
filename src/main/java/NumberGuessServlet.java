import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NumberGuessServlet")
public class NumberGuessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private int targetNumber;
    private int attempts;
    
    @Override
    public void init() {
        targetNumber = new Random().nextInt(100) + 1;
        attempts = 0;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userGuess = Integer.parseInt(request.getParameter("guess"));
        attempts++;

        String result = processGuess(userGuess);
        String outputPage = prepareOutputPage(result);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(outputPage);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String outputPage = "<html><body><h1>Game Over!</h1></body></html>";
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(outputPage);
    }

    private String processGuess(int userGuess) {
        if (userGuess == targetNumber) {
            return "Congratulations! You guessed the correct number in " + attempts + " attempts.";
        } else if (userGuess < targetNumber) {
            return "Try a higher number.";
        } else {
            return "Try a lower number.";
        }
    }

    private String prepareOutputPage(String result) {
        return "<html><body><h1>" + result + "</h1>"
             + "<p>Guess again:</p>"
             + "<form action=\"NumberGuessServlet\" method=\"get\">"
             + "<input type=\"number\" name=\"guess\" required>"
             + "<br><br>"
             + "<input type=\"submit\" value=\"Submit Guess\">"
             + "</form></body></html>";
    }
}
