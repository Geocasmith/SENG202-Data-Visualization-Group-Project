package backend.database;

import backend.csvReader;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void extractCol() throws SQLException, CsvValidationException, IOException {
        Database db = new Database();
        db.connectDatabase();
        ArrayList<Object> extractedCol = new ArrayList<>();
        extractedCol = db.extractCol(2);
        for (int i = 0; i < extractedCol.size(); i++) {
            System.out.println(extractedCol.get(i));
        }

    }
}