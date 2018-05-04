package com.example.kaitlynchandler.quizit2;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

;

public class RequestManager {
    
    
    static class Response{
        private int code;
        private JSONObject body;
        public Response(int code, JSONObject body){
          this.code = code;
          this.body = body;     
        }
        public int getCode(){
            return code;
        }
        public JSONObject getBody(){
            return body;
        }
        public String toString(){
            return ""+code;
        }
    }
    
    private static Response sendRequest(String URI, String method, JSONObject body){
        try{
            URL url = new URL("http://104.251.219.173:5000"+URI);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);
            
            if(body != null){
                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                wr.write(body.toString());
                wr.close();
            }
            
            int code = connection.getResponseCode();
            if(code == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String output = br.lines().collect(Collectors.joining());
                try{
                    return new Response(code, (JSONObject) new JSONParser().parse(output));
                }catch(ParseException pe){
                    return null;
                }
                
            }
            
            return new Response(code, null);
            
            
            
        }catch(IOException e){
            
        }
        
        return null;
            
    }

    /*
    This method returns a String either saying login failed or if login is successful.
    */
    public static String login(String email, String password) {
        JSONObject cred = new JSONObject();
        cred.put("email", email);
        cred.put("password", password);
        Response response = sendRequest("/api/user", "POST", cred);
        if(response ==null || response.getCode()!=200){
            return "Login Failed";
        }else{
            return (String)response.getBody().get("response");
        }
    }
    
    /*
    This method allows users to sign up, given first, last name and email, password and account type. It will
    either return "Account created" or "registration failed"
    */
    public static String signUp(String firstName, String lastName, String email,
                                String password, String accountType){
        
        if(!email.matches(".*@.*")){
            return "Invalid email format";
        }
        JSONObject cred = new JSONObject();
        cred.put("first_name", firstName);
        cred.put("last_name", lastName);
        cred.put("email", email);
        cred.put("password", password);
        cred.put("account_type", accountType);
        Response response = sendRequest("/api/user", "PUT", cred);
        if(response.getCode()==409){ // user already exists
            return "User account already exists";
        }
        if(response.getCode()==204){
            return "Account created";
        }
        return "Registration failed";            
    }
    /*
    This method returns a quiz object, given the quizID.
    */
    public static JSONObject getQuizByID(String ID){
        Response response = sendRequest("/api/quiz/"+ID, "GET", null);
        if(response.getCode()==200){
            return response.getBody();
        }
        return null;
    }
    /*
    This method returns a user, being passed in a URI.
    */
    public static JSONObject getUser(String URI){
        return sendRequest(URI,"GET",null).getBody();
    }
    /*
    This method deletes a user from the db.
    */
    public static boolean deleteUser(String id){
        JSONObject user = new JSONObject();
        user.put("_id", id);
        int code = sendRequest("/api/user", "DELETE", user).getCode();
        if(code==204){
            return true;
        }
        return false;

    }
     /*
    This method adds a quiz, given the title, expiration, owner, number of questions a quiz object.
    */
    public static boolean addQuiz(String title, String expiration, String owner,
            int questions, JSONObject quiz){
        JSONObject cred = new JSONObject();
        cred.put("title", title);
        cred.put("expiration_time", expiration);
        cred.put("owner", owner);
        cred.put("questions", questions);
        cred.put("quiz", quiz);
        
        System.out.println(cred.toJSONString());
        Response response = sendRequest("/api/quiz", "PUT", cred);
        
        if(response.getCode()==204){ //success
            return true;
        }
        
        System.out.println(response.toString());
        return false;
    }

     /*
    This method returns a quiz ID, given the unique quiz code.
    */
    public static String getQuizIDByCode(String code){
        JSONObject cred = new JSONObject();
        cred.put("code", code);
        Response response = sendRequest("/api/quiz", "POST", cred);
        if(response ==null || response.getCode()!=200){
            return "Invalid";
        }else{
            return (String)response.getBody().get("response");
        }
     }
    
    /*
    This method returns a quiz question, given the quizID and a specific question number.
    */
    public static JSONObject getQuizQuestion(String quizID, int questionNumber){
        Response response = sendRequest("/api/quiz/"+quizID+"/"+questionNumber, "GET", null);
        if(response.getCode()==200){
            return response.getBody();
        }
        return null;
    }
}
    

