/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voicemail;


import java.io.FileNotFoundException;

import java.io.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author beki
 */
public class Server {
     
    //static FileInputStream in = null;
    static PrintWriter out = null;
    private static String key="1000";
    public static ArrayList<String> users = new ArrayList();
    public static void main(String[] args){
        System.out.println("Key here: ");
        Scanner sc = new Scanner(System.in);
        String pin = sc.next();
        if(Server.key.equals(pin)){
        Server.menu();
                }
    }
    private static void menu(){
        Server user= new Server();
        String pin="";
        String pnumber="";
        Scanner sc = new Scanner(System.in);
        String order = "";
        while(!"q".equals(order)){
            System.out.println("Start :");
            order=sc.next();
            if("reg".equals(order)){
            System.out.println("phone :");
            order=sc.next();
            pnumber = order;
            System.out.println("pin :");
            order=sc.next();
            pin = order;
                try {
                    user.register(pin, pnumber);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if("del".equals(order)){
            System.out.println("phone :");
            order=sc.next();
            pnumber = order;
            System.out.println("pin :");
            order=sc.next();
            pin = order;
            user.delete(pin, pnumber);
            }
            else if("q".equals(order)){
                break;
            }
            
        }
    }
    
    void putVoice(String recv, String text,String date, String snd) throws IOException{
        System.out.println("Data "+recv+":"+snd+";"+date+"?"+text);
        FileWriter voice = new FileWriter("Voice.txt",true);
            BufferedWriter vbuffer = new BufferedWriter(voice);
            out = new PrintWriter(vbuffer);
            
            out.println(recv+":"+snd+";"+date+"?"+text);
            
            out.close();
    }
    ArrayList<String> getVoice(String recv) throws IOException{
        ArrayList<String> voiceList = new ArrayList<>();
        
                Scanner sc = new Scanner(new File("Voice.txt"));
       
            Boolean found = false;
            int line = 0;
            String currentLine;
            sc.next();
            while (sc.hasNext()){
                currentLine = sc.nextLine();
                System.out.println(currentLine);
                if(currentLine.contains(recv+":")){
                    System.out.println(currentLine);
                    voiceList.add(currentLine.substring(currentLine.indexOf(":")+1));
    }
            }
            System.out.println(voiceList);
            return voiceList;
    }
   
    
    
    
    void register(String pass, String number) throws FileNotFoundException, IOException{
        FileWriter reg;
       
            reg = new FileWriter("User.txt",true);
        
            BufferedWriter regb = new BufferedWriter(reg);
            out = new PrintWriter(regb);
            if(checkServer(number,pass)==false){
                try {
            out.println(pass+":"+number);
            
            out.close();
            }
            
         catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    Boolean checkServer(String number,String pass) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("User.txt"));
       
            Boolean found = false;
            int line = 0;
            String currentLine;
            
            while (sc.hasNext()){
                currentLine = sc.nextLine();
                if(currentLine.contains(number)){
                    System.out.println(currentLine);
                    found = true;
                }
            }
      return found;
    }
    void deleteMail(String mail){
        BufferedReader reader = null;
        try {
            File inputFile = new File("Voice.txt");
            File tempFile = new File("myTempFile.txt");
            reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String lineToRemove = mail;
            System.out.println(lineToRemove);
            String currentLine;
            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                System.out.println(trimmedLine);
                if(trimmedLine.equals(lineToRemove)) continue;
                
                writer.write(currentLine + System.getProperty("line.separator"));
            }           
            writer.close();
            reader.close();
            
            boolean delsuccessful = inputFile.delete();
            boolean successful = tempFile.renameTo(inputFile);
                                 
            System.out.println(delsuccessful+""+successful);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
    void delete(String pass, String number){
        BufferedReader reader = null;
        try {
            File inputFile = new File("User.txt");
            File tempFile = new File("myTempFile.txt");
            reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String lineToRemove = pass+":"+number;
            System.out.println(lineToRemove);
            String currentLine;
            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                System.out.println(trimmedLine);
                if(trimmedLine.equals(lineToRemove)) continue;
                
                writer.write(currentLine + System.getProperty("line.separator"));
            }           
            writer.close();
            reader.close();
            
            boolean delsuccessful = inputFile.delete();
            boolean successful = tempFile.renameTo(inputFile);
                                 
            System.out.println(delsuccessful+""+successful);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
