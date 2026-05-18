package testing;

import dao.HealthRecordDao;
import dao.HealthRecordDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;
import model.HealthRecord;
import model.User;
import org.junit.Before;
import org.junit.Test;
import utils.FormatterString;
import utils.ValidateHealthRecord;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HealthRecordTest {
    private HealthRecordDao healthRecordDao;
    private UserDao userDao;

    private String upperBp = "120";
    private String lowerBp = "80";
    private String weight = "60.5";
    private String temperature = "37.5";
    private String note = "Creating test";

    private String existUsername = "testuser01";

    @Before
    public void setUp() throws SQLException {
        healthRecordDao = new HealthRecordDaoImpl();
        userDao = new UserDaoImpl();
    }

    @Test
    public void testValidateHealthRecordValid() {
        String validateError = ValidateHealthRecord.validateEditRecord(weight , temperature , upperBp,lowerBp,note);
        assertEquals("",validateError);
    }

    @Test
    public void testValidateHealthRecordInvalidNoteExceed50(){
        note = new String(new char[51]);
        String validateError = ValidateHealthRecord.validateEditRecord(weight , temperature , upperBp,lowerBp,note);
        assertEquals("Note must not exceed 50 words" , validateError);
    }

    @Test
    public void testCreateHealthRecordValid() throws SQLException {
        float floatWeight = Float.parseFloat(weight);
        float floatTemp = Float.parseFloat(temperature);
        int intUpperBp = Integer.parseInt(upperBp);
        int intLowerBp = Integer.parseInt(lowerBp);
        boolean result = healthRecordDao.createHealthRecord(existUsername, floatWeight,floatTemp,intUpperBp,intLowerBp,note);
        assertEquals(true,result);
    }

    @Test(expected = SQLException.class)
    public void testCreateHealthRecordInvalidNoExistUsername() throws SQLException {
        String fakeUser = "fakeUser01";
        float floatWeight = Float.parseFloat(weight);
        float floatTemp = Float.parseFloat(temperature);
        int intUpperBp = Integer.parseInt(upperBp);
        int intLowerBp = Integer.parseInt(lowerBp);

        healthRecordDao.createHealthRecord(fakeUser , floatWeight,floatTemp,intUpperBp,intLowerBp,note);
    }

    @Test
    public void testEditHealthRecordValid() throws SQLException {
        HealthRecord selectRecord = healthRecordDao.getHealthRecords(existUsername).getLast();
        Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());
        String editedNote= "Edit testing : " + timestampNow;
        selectRecord.setNote(editedNote);
        healthRecordDao.editHealthRecord(selectRecord,existUsername);
        selectRecord = healthRecordDao.getHealthRecords(existUsername).getLast();
        assertEquals(editedNote , selectRecord.getNote());
    }

    @Test
    public void testDeleteHealthRecordValid() throws SQLException {
        HealthRecord selectRecord = healthRecordDao.getHealthRecords(existUsername).getLast();
        boolean result= healthRecordDao.deleteHealthRecord(selectRecord.getHealthRecordId() , existUsername);
        assertEquals(true,result);
    }
}
