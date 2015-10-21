import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by zfx on 2015/10/19.
 */
public class IssueDown {
    private TreeMap<String,String> issues;
    private TreeMap<String,String> comments;
    private TreeMap<String,String> events;
    private ArrayList<String> ids;
    private ArrayList<String> mis;
    private String uriBase;
    public static final String clientId = "6701d27a7747d3a68f04";
    public static final String clientSecret= "0de67dc4a9fed164135cbe1266b72c3779df359d";
    public String end;//?client_id=6701d27a7747d3a68f04&client_secret=0de67dc4a9fed164135cbe1266b72c3779df359d
    //?client_id=9e08256d21154103bd05&client_secret=6c12e87337f2054be616401d9128ba40ebc03e8b
    public IssueDown(String s,String x){
        uriBase = s;
        issues = new TreeMap<String, String>();
        comments = new TreeMap<String, String>();
        events = new TreeMap<String, String>();
        ids = new ArrayList<String>();
        mis = new ArrayList<String>();
        end = new String(x);
    }
    public void downById(String id){

        String issue = downIssue(id);
        if(issue==null) {
            mis.add(id);
            System.out.println("mis " + id);
        }
        else{
            String comment = downComment(id);
            String event = downEvent(id);
            issues.put(id,issue);
            comments.put(id,comment);
            events.put(id, event);
            ids.add(id);
            System.out.println(id);
        }


    }
    public String downIssue(String id){
        Client c = new Client(new StringBuffer(uriBase+"/"+id +end));
        System.out.println(uriBase+"/"+id +end);
        try {

                return c.getData();
        } catch (IOException e) {

        }
        return null;
    }
    public String downComment(String id){
        Client c = new Client(new StringBuffer(uriBase+"/"+id+"/comments"+end));
        try {

                return c.getData();
        } catch (IOException e) {

        }
        return null;
    }
    public String downEvent(String id){
        Client c = new Client(new StringBuffer(uriBase+"/"+id+"/events"+end));
        try {

                return c.getData();
        } catch (IOException e) {

        }
        return null;
    }
    public void saveFile(String file,TreeMap<String,String> data){
        FileWriter fo = null;
        try {
            fo = new FileWriter(new File(file));
            Iterator it = data.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                fo.write((String) entry.getKey());
                fo.write(":");
                if(entry.getValue()!=null)
                    fo.write((String) entry.getValue());
                fo.write("\n");
            }
            fo.close();
            data.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveFile(String file,ArrayList<String> data){
        FileWriter fo = null;
        try {
            fo = new FileWriter(new File(file));
            Iterator it = data.iterator();
            while(it.hasNext()) {
                String entry = (String) it.next();
                fo.write((String) entry);

                fo.write("\n");

            }
            fo.close();
            data.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAllFiles(String file1,String file2,String file3,String file4){
        saveFile(file1,issues);
        saveFile(file2,comments);
        saveFile(file3,events);
        saveFile(file4,ids);

    }

    public static void main(String[] args){

        IssueDown issueDown = new IssueDown("https://api.github.com/repos/docker/docker/issues",args[2].replaceAll("\"",""));
        int c=0;
        //System.out.println(args[1]);

        int i=Integer.parseInt(args[0]);
        for(;i<Integer.parseInt(args[1]);i++) {
            issueDown.downById(String.valueOf(i));
            c++;
            if(c>=200){
                issueDown.saveAllFiles("./issues"+String.valueOf(i), "./comments"+String.valueOf(i),"./events"+String.valueOf(i),"./ids"+String.valueOf(i));
                c=0;
            }

        }
        issueDown.saveAllFiles("./issues"+String.valueOf(i), "./comments"+String.valueOf(i),"./events"+String.valueOf(i),"./ids"+String.valueOf(i));


    }

}
