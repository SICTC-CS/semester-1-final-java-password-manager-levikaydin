import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter; 
import java.io.FileReader; 
import java.io.IOException; 
import java.io.FileNotFoundException; 

public class Main{
    public static void main(String[] args) throws IOException {
        Scanner ui= new Scanner(System.in);
        String str = read();
        String newPass = ui.nextLine();
        String newUser = ui.nextLine();
        str = write(str,newPass,newUser);
        
    }

    // Geeks for geeks
    public static String write(String str, String password, String username) throws IOException { 
        
        str+=(username+","+password+"\n");
        // attach a file to FileWriter
        FileWriter fw=new FileWriter("Accounts.txt"); 
        
        fw.write(str); 
        // read character wise from string and write  
        // into FileWriter  
        //for (int i = 0; i < password.length(); i++) 
        //    fw.write(password.charAt(i)); 
  
        System.out.println("Writing successful"); 
        //close the file  
        fw.close(); 
        return read();
    } 

    //geeks for geeks
    public static String read() throws IOException { 
        // variable declaration 
        int ch; 
        String str="";

        // check if File exists or not 
        FileReader fr = new FileReader("Accounts.txt"); 
        try{ 
            fr = new FileReader("Accounts.txt"); 
        }catch(FileNotFoundException fe){ 
            System.out.println("File not found"); 
        } 
  
        // read from FileReader till the end of file 
        while ((ch=fr.read())!=-1){ 
            System.out.print((char)ch);
            str+=(char)ch;
        }
        // close the file 
        fr.close(); 
        return str;
    } 
}