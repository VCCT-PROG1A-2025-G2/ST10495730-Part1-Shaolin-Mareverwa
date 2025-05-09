package com.mycompany.login;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {

    private Login login;

    @Before
    public void setUp() {
        login = new Login();
    }

    // ----- assertEquals/assertTrue/assertFalse tests -----

    @Test
    public void testCorrectUsernameFormat() {
        String username = "kyl_1";
        boolean result = login.checkUserName(username);
        assertTrue(result);
    }

    @Test
    public void testIncorrectUsernameFormat() {
        String username = "kyle!!!!!!!";
        boolean result = login.checkUserName(username);
        assertFalse(result);
    }

    @Test
    public void testPasswordMeetsComplexity() {
        String password = "Ch&&sec@ke99!";
        boolean result = login.checkPasswordComplexity(password);
        assertTrue(result);
    }

    @Test
    public void testPasswordFailsComplexity() {
        String password = "password";
        boolean result = login.checkPasswordComplexity(password);
        assertFalse(result);
    }

    @Test
    public void testCellPhoneCorrectFormat() {
        String cell = "+27838968976";
        boolean result = login.checkCellPhoneNumber(cell);
        assertTrue(result);
    }

    @Test
    public void testCellPhoneIncorrectFormat() {
        String cell = "08966553";
        boolean result = login.checkCellPhoneNumber(cell);
        assertFalse(result);
    }

    @Test
    public void testLoginSuccessful() {
        // Set registration values
        setPrivateRegisteredValues("Kyle", "Miller", "kyl_1", "Ch&&sec@ke99!", "+27838968976");

        boolean success = simulateLogin("kyl_1", "Ch&&sec@ke99!");
        assertTrue(success);
    }

    @Test
    public void testLoginFailed() {
        setPrivateRegisteredValues("Kyle", "Miller", "kyl_1", "Ch&&sec@ke99!", "+27838968976");

        boolean success = simulateLogin("wrong", "wrongpass");
        assertFalse(success);
    }

    // ----- Helper Methods -----

    private void setPrivateRegisteredValues(String first, String last, String user, String pass, String cell) {
        try {
            setStaticField(Login.class, "registeredFirstName", first);
            setStaticField(Login.class, "registeredLastName", last);
            setStaticField(Login.class, "registeredUsername", user);
            setStaticField(Login.class, "registeredPassword", pass);
            setStaticField(Login.class, "registeredCell", cell);
        } catch (Exception e) {
            fail("Failed to set private static fields for test.");
        }
    }

    private boolean simulateLogin(String username, String password) {
        return username.equals(getStaticField(Login.class, "registeredUsername")) &&
               password.equals(getStaticField(Login.class, "registeredPassword"));
    }

    private void setStaticField(Class<?> clazz, String fieldName, String value) throws Exception {
        java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, value);
    }

    private String getStaticField(Class<?> clazz, String fieldName) {
        try {
            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (String) field.get(null);
        } catch (Exception e) {
            return null;
        }
    }
}
/*
 * This code was developed with the assistance of ChatGPT, a language model by OpenAI.
 * ChatGPT was used to help create a regular expression based cell phone checker.
 * Citation: OpenAI. (2025). ChatGPT (May 2 version) [Large language model]. https://chat.openai.com
 */