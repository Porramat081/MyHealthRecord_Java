package testing;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.PasswordHasher;
import utils.ValidateSignup;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserTest {
    private User testExistingUser;
    private UserDao userDao;

    String firstName;
    String lastName;
    String username;
    String samplePassword;
    Timestamp timestampNow;

    @Before
    public void setUp()  {
        userDao = new UserDaoImpl();

        firstName = "newUser";
        lastName = "Tester";
        username = "newUsername";
        samplePassword = "Password123456!";
        timestampNow = Timestamp.valueOf(LocalDateTime.now());
    }

    @After
    public void reset(){
        firstName = null;
        lastName = null;
        username = null;
        samplePassword = null;
        timestampNow = null;
    }

    @Test
    public void testValidateSignUpValid() {
        String validateResult = ValidateSignup.validateForm(username,samplePassword, firstName,lastName ,samplePassword);
        assertEquals("",validateResult);
    }

    @Test(expected = SQLException.class)
    public void testValidateSignUpInvalidUsernameNotUnique() throws SQLException {
        username = "testuser01";
        userDao.createUser(username,samplePassword,firstName,lastName);
    }

    @Test
    public void testValidateSignUpInvalidPasswordWeakNoUpperCase(){
        samplePassword = "weakjaa123456";
        String validateError = ValidateSignup.validateForm(username,samplePassword,firstName,lastName,samplePassword);
        assertEquals("Password must include at least one uppercase letter",validateError);
    }

    @Test
    public void testLoginValid() throws SQLException {
        User loginUser = userDao.getUser("testuser01" , "Rightpassword888!");
        assertEquals("testuser01" , loginUser.getUsername());
    }

    @Test
    public void testLoginInvalid() throws SQLException{
        User loginUser = userDao.getUser("testuser01" , "WrongPassword000!");
        assertEquals(null , loginUser);
    }

    @Test
    public void updateFirstnameLastnameValid() throws SQLException{
        User selectUser = userDao.getUser("testuser01" , "Rightpassword888!");
        String newFirstname = "FirstnameEdited";
        String newLastname = "LastnameEdited";
        selectUser.setFirstname(newFirstname);
        selectUser.setLastname(newLastname);
        userDao.editUser(selectUser);

        User updatedUser = userDao.getUser("testuser01" , "Rightpassword888!");
        boolean testResult = updatedUser.getFirstname().equals(newFirstname) && updatedUser.getLastname().equals(newLastname);
        assertEquals(true , testResult);
    }

    @Test
    public void checkHashingPassword() throws NoSuchAlgorithmException, SQLException {
        String passwordInDb = userDao.getTestHashPasswordFromDb();
        String hashedPassword = PasswordHasher.hashPassword("Rightpassword888!");
        boolean compareResult = passwordInDb.equals(hashedPassword);
        assertEquals(true,compareResult);
    }
}
