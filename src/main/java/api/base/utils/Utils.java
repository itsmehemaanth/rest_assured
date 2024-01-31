package api.base.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static final String RESOURCE = "src/main/resources";
    public static final String INPUTFILES = "src/main/resources/inputfiles/";
    public static final String INPUTDATA = "src/main/resources/inputData/";
    private Map<String,String> headers = new HashMap<String,String>();

    public Map<String,String> getHeaders(String caseType){
        switch(caseType.toUpperCase()){
            case "BASE":
                headers.put("Content-Type","application/json");
                break;
        }
        return headers;
    }

    public String getBody(String bodyType){

//        String basePath = "src/main/resources/inputfiles/";
        String filePath = INPUTFILES+bodyType+".json" ;
        String data = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data = data+line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public Map<String,String> ReadCSV(String file,String tcId)  {
        List<String[]> allData = null;
        Map<String,String> td = new HashMap<String,String>();

        try {
            FileReader filereader = new FileReader(file+".csv");
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            String[] header = new String[0];
            String[] value = new String[0];

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
//                for (String cell : nextRecord) {
//                    System.out.print(cell + "\t");
//                }
                if(nextRecord[0].equalsIgnoreCase("TC_ID")){
                    header = nextRecord;
                }else if(nextRecord[0].equalsIgnoreCase(tcId)){
                    value = nextRecord;
                    break;
                }

                System.out.println();
            }
            for(int cnt=0;cnt<header.length;cnt++){
                td.put(header[cnt],value[cnt]);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return td;
    }

    public String replacePlaceHolders(String jsonValue, String tcId, Map<String, String> td) {
        String templateJson = jsonValue;
        for (Map.Entry<String, String> entry : td.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.contains("REQUEST_PARAM")) {
                templateJson = templateJson.replace(key, value);
            }
        }

        return templateJson;
    }

}
