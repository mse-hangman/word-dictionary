import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DataParsing {
    public static void main(String[] args) throws IOException, ParseException {
        // Using BufferReader
        BufferedReader reader = new BufferedReader(new FileReader("/Users/johyeongchan/MSEProject/hangman-server/hangman_dictionary.txt"));
        String str;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayListAfterRemove = new ArrayList<>();
        System.out.println("Success file reading!");

        // Store each json type string to Arraylist
        while((str = reader.readLine()) != null){
            arrayList.add(str);
        }
        reader.close();

        // remove last "," in each json type string
        for(int i =1; i < arrayList.size()-1; i++){
            char[] arr = arrayList.get(i).toCharArray();
            char[] arr2 = Arrays.copyOfRange(arr, 0, arr.length-1);
            String newString = new String(arr2);
            arrayListAfterRemove.add(newString);
        }
        System.out.println("Success distinguishing each json type char array!");
        System.out.println("Success converting char array to Arraylist!");

        // JSONObject
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObjectEach = new JSONObject();

        // String to JSON
        JSONParser jsonParser = new JSONParser();
        for(int i =0; i<arrayListAfterRemove.size(); i++){
            jsonObjectEach = (JSONObject) jsonParser.parse(arrayListAfterRemove.get(i));
            jsonArray.add(jsonObjectEach);
        }
        System.out.println("Success adding JSONObject to JSONArray");

        // Make JSON file from JSONArray
        makeJSONFile(jsonArray);
        System.out.println("Success making JSON file from JSONArray!");
    }

    // Method that makes JSON file from JSONArray
    public static void makeJSONFile(JSONArray jsonArray) throws IOException {
        String filePath = "/Users/johyeongchan/MSEProject/hangman-server/hangman.json";

        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

        for(int i =0; i<jsonArray.size(); i++){
            writer.write(jsonArray.get(i).toString());
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }
}
