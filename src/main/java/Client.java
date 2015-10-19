/**
 * Created by zfx on 2015/10/19.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
public class Client {
    private StringBuffer uri;
    private HttpURLConnection con;
    public Client(StringBuffer a) {
        uri = a;
        URL url = null;
        try {
            url = new URL(uri.toString());
            con = (HttpURLConnection)url.openConnection();

        } catch (IOException e) {

        }
    }
    public HttpURLConnection getCon(){
        return con;
    }
    public int getReposeCode(){
        try {
            return con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 404;
    }
    protected InputStream getStream() throws IOException {
        return con.getInputStream();
    }
    protected String getData() throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(getStream()));
        String str;
        while((str = br.readLine())!=null){
            buffer.append(str);
        }
        return buffer.toString();
    }

    public static void main(String[] args) throws IOException {

        Client c = new Client(new StringBuffer("https://api.github.com/repos/docker/docker/issues/2000"));

        if(c.getReposeCode()==404){
            System.out.println("404");
        }
        else
            System.out.println(c.getData());

    }
}
