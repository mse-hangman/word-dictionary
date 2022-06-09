import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DataParsing {
    public static void main(String[] args) throws IOException, ParseException {
        // Read text file using BufferReader
        BufferedReader reader = new BufferedReader(new FileReader("/Users/johyeongchan/MSEProject/word-dictionary/hangman_dictionary.txt"));
        System.out.println("Success file reading!");


        // Store each json type string to Arraylist
        String jsonStringWithComma;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayListAfterRemove = new ArrayList<>();

        while((jsonStringWithComma = reader.readLine()) != null){
            arrayList.add(jsonStringWithComma);
        }
        reader.close();

        // remove last "," in each json type string
        for(int i =50; i < 1050; i++){
            char[] arr = arrayList.get(i).toCharArray();
            char[] arrDeleteComma = Arrays.copyOfRange(arr, 0, arr.length-1);
            String jsonString = new String(arrDeleteComma);
            arrayListAfterRemove.add(jsonString);
        }
        System.out.println("Success distinguishing each json type char array!");
        System.out.println("Success converting char array to Arraylist!");

        // JSONObject
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObjectBefore = new JSONObject(); // JSONObject before fit in format
        JSONObject jsonObjectAfter = new JSONObject(); // JSONObject after fit in format

        // String to JSON
        JSONParser jsonParser = new JSONParser();
        for(int i =0; i<arrayListAfterRemove.size(); i++){
            jsonObjectBefore = (JSONObject) jsonParser.parse(arrayListAfterRemove.get(i));
            addToJSONArray(jsonArray, jsonObjectBefore, i);
        }
        System.out.println("Success adding JSONObject to JSONArray");

        // Make JSON file from JSONArray
        makeJSONFile(jsonArray);
        System.out.println("Success making JSON file from JSONArray!");
    }

    // Method that distinguish word and add to jsonArray
    public static void addToJSONArray(JSONArray jsonArray, JSONObject jsonObjectBefore, int i){
        JSONObject jsonObjectAfter = new JSONObject();
        // If word's length is between 3 and 10, add to jsonArray
        if(jsonObjectBefore.get("word").toString().length()>=3 && jsonObjectBefore.get("word").toString().length()<11){
            jsonObjectAfter.put("id", i);
            jsonObjectAfter.put("word", jsonObjectBefore.get("word"));
            jsonObjectAfter.put("word_description", jsonObjectBefore.get("definition"));
            jsonObjectAfter.put("hint", "no hint");
            jsonObjectAfter.put("word_count", jsonObjectBefore.get("word").toString().length());
            jsonArray.add(jsonObjectAfter);
        }
    }

    // Method that makes JSON file from JSONArray
    public static void makeJSONFile(JSONArray jsonArray) throws IOException {
        String filePath = "/Users/johyeongchan/MSEProject/word-dictionary/hangman.json";
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.write(jsonArray.toJSONString());
        writer.flush();
        writer.close();
    }
}
