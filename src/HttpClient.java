import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;


public class HttpClient {
    static String url_post = "http://httpbin.org/post";//url test for POST
    static String url_get = "http://httpbin.org/get?course=networking&assignment=1"; //url test for GET
    final static Integer port = 80; //always set 80 for HTTP requests

    //added comments on GET, however the same comments also apply to POST
    private static Map<String, String> sendGetRequest(String url) throws Exception {

        System.out.println("***GET operation is now being processed....");

        Map<String, String> mapResponse = new HashMap<>();//mapping the components body and header
        URL objURL = new URL(url); //passing url
        System.out.println("> Connection Request..."); //initializing connection
        Socket sk = new Socket(InetAddress.getByName(objURL.getHost()), port); //IP,PORT
        System.out.println("* Connected!");
        PrintWriter pWriter = new PrintWriter(sk.getOutputStream());//send request
        pWriter.println("GET /" + objURL.getFile() + " HTTP/1.0");
        pWriter.println("Host: " + objURL.getHost());
        pWriter.println("");
        pWriter.flush(); //flush all the data out

        BufferedReader bReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
        String str;
        StringBuilder strBuilderResponse = new StringBuilder();


        boolean header = false;
        System.out.println("***Connecting to the server..........."); //make that everything is fine up until this point if this prints.
        while ((str = bReader.readLine()) != null) {
            strBuilderResponse.append(str + "\n");
            if (str.isEmpty() && !header) {
                mapResponse.put("header", strBuilderResponse.toString());
                header = true;
                strBuilderResponse = new StringBuilder(); //update the response
            }
        }
        bReader.close();
        pWriter.close();
        sk.close();
        System.out.println("The socket is now being closed.\n");//verify everything is okay so far if this prints.

        mapResponse.put("body", strBuilderResponse.toString()); //print body to th string
        return mapResponse;


    }

    private static Map<String, String> sendPostRequest(String url, String data) throws Exception {

        System.out.println("> POST request");

        Map<String, String> mapRes = new HashMap<>();
        URL objURL = new URL(url);
        System.out.println("> Connection Request...");
        Socket sk = new Socket(InetAddress.getByName(objURL.getHost()), port);
        System.out.println("* Connected!");
        PrintWriter pWriter = new PrintWriter(sk.getOutputStream());
        pWriter.println("POST /" + objURL.getFile() + " HTTP/1.0");
        pWriter.println("Host: " + objURL.getHost());
        pWriter.println("Content-Length: " + data.length());
        pWriter.println();
        pWriter.println(data);
        pWriter.println();
        pWriter.flush();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
        String str;
        StringBuilder strBuilderResponse = new StringBuilder();
        boolean header = false;

        System.out.println("> Waiting for response...");
        while ((str = bReader.readLine()) != null) {
            strBuilderResponse.append(str + "\n");
            if (str.isEmpty() && !header) {
                mapRes.put("header", strBuilderResponse.toString()); //parsing the header
                header = true;
                strBuilderResponse = new StringBuilder();
            }
        }
        bReader.close();
        pWriter.close();
        sk.close();
        System.out.println("The socket is now being closed.\n");

        mapRes.put("body", strBuilderResponse.toString());
        return mapRes;

    }

    private static void helpDisplay() {
        /**
         * HELP operation
         */
        System.out.println("httpc is a curl-like application but supports HTTP protocol only.");
        System.out.println("Usage:");
        System.out.println(" httpc command [arguments]");
        System.out.println("The commands are:");
        System.out.println("   get executes a HTTP GET request and prints the response."
                + "\n   post executes a HTTP POST request and prints the response."
                + "\n   help prints this screen");
        System.out.println("Use \"httpc help [command]\" for more information about a command.");
        System.out.println();
    }

    private static void helpPostDisplay() {
        /**
         * POST operation
         */
        System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL");
        System.out.println("Post executes a HTTP POST request for a given URL with inline data or from file.");
        System.out.println("-v Prints the detail of the response such as protocol, status, and headers."
                + "\n-h key:value Associates headers to HTTP Request with the format 'key:value'"
                + "\n-d stringAssociates an inline data to the body HTTP POST request."
                + "\n-f fileAssociates the content of a file to the body HTTP POST request. \n"
                + "Either [-d] or [-f] can be used but not both.\n");
        System.out.println();
    }

    private static void helpGetDisplay() {
        /**
         * GET operation
         */
        System.out.println("usage httpc get [-v] [-h key:value] URL");
        System.out.println("Get executes a HTTP GET request for a given URL."
                + "\n-v Prints the detail of the response such as protocol, status, and headers."
                + "\n-h key:value Associates headers to HTTP Request with the format 'key:value'");
        System.out.println();
    }

    private static void errorMessage() {
        System.out.println("ERROR!!! The input is invalid.");
        System.out.println("Please use the below command line to continue: ");
        System.out.println("httpc (get|post) [-v] (-h \"k:v\")* [-d inline-data] [-f file] URL");
        System.out.println("System is now closed.");
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> mapRespond;
        System.out.print("- httpc ");
        Scanner sc = new Scanner(System.in);
        String request = sc.nextLine();

        if (request.contentEquals("help")) {
            helpDisplay();
        } else if (request.contentEquals("help post")) {
            helpPostDisplay();
        } else if (request.contentEquals("help get")) {
            helpGetDisplay();
        } else if ((request.contains("get")) && (request.contains("-v")) && (request.contains(url_get))) {
            try {
                mapRespond = sendGetRequest(url_get);
                System.out.println("\n" + mapRespond.get("header"));
                System.out.println("\n" + mapRespond.get("body"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request.contains("get") && (request.contains(url_get))) {
            try {
                mapRespond = sendGetRequest(url_get);
                System.out.println("\n" + mapRespond.get("body"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((request.contains("post")) && (request.contains("-v")) && (request.contains(url_post))) {
            try {
                mapRespond = sendGetRequest(url_post);
                System.out.println("\n" + mapRespond.get("header"));
                System.out.println("\n" + mapRespond.get("body"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((request.contains("get")) && (request.contains("-h")) && (request.contains(url_get))) {
            try {
                mapRespond = sendGetRequest(url_get);
                System.out.println("\n" + mapRespond.get("header"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((request.contains("post")) && (request.contains("-d")) && (request.contains(url_post))) {
            try {

                mapRespond = sendGetRequest(url_get);
                System.out.println("\n" + mapRespond.get("header"));
            } catch (Exception e) {

                e.printStackTrace();
            }
        } else if ((request.contains("post")) && (request.contains("-h")) && (request.contains(url_post))) {
            try {
                mapRespond = sendPostRequest(url_post, "*test");
                System.out.println("\n" + mapRespond.get("header"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorMessage();
        }
    }
}



