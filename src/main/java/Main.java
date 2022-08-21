import java.io.*;
import java.util.HashMap;

public class Main {

    private static HashMap<String, Integer> ADDRESSES = new HashMap<String, Integer>(0);

    public static void main(String[] args) {
        if (args.length<2){
            System.out.println("no files specified [addresses] [database]");
        }
        String addressesFile = args[0];
        String databaseFile = args[1];
        readAddresses(addressesFile);
        processDatabase(databaseFile);
    }

    private static void processDatabase(String databaseFile) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(databaseFile);
        } catch (FileNotFoundException e) {
            System.out.println("file not found "+databaseFile);
            System.exit(-1);
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("output.txt");
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(-1);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        System.out.println("reading database");
        try {
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line;
            int x = 0;
            while ((line = bufferReader.readLine()) != null) {
                String address = line.trim();
                x++;
                if (x%1_000_000==0){
                    System.out.println(x);
                    bufferedWriter.flush();
                }
                if (!ADDRESSES.containsKey(address)){
                    continue;
                }
                Integer c=ADDRESSES.get(address);
                if (c==1){
                    ADDRESSES.remove(address);
                    bufferedWriter.append(address);
                    bufferedWriter.newLine();
                }else{
                    ADDRESSES.put(address,1);
                }
            }
            bufferReader.close();
            bufferedWriter.close();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }

    private static void readAddresses(String filename){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filename);
        } catch (FileNotFoundException e) {
            System.out.println("file not found "+filename);
            System.exit(-1);
        }
        System.out.println("reading addresses");
        try {
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line;
            int x = 0;
            while ((line = bufferReader.readLine()) != null) {
                ADDRESSES.put(line.trim(),0);
                x++;
                if (x%1_000_000==0){
                    System.out.println(x);
                }
            }
            bufferReader.close();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }

}
