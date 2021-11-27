package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvFileUtils {

    public static void generateCsvFile (String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObj = mapper.readTree(jsonString);

        Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonObj.elements().next();
        firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(new File("exported-file.csv"), jsonObj);
    }
}
