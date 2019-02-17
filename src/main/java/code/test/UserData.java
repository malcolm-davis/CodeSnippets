package code.test;

public class UserData {

    public String getName() {
        return m_name;
    }

    public String getPassword() {
        return m_password;
    }

    public UserData(final String name, final String password) {
        m_name = name;
        m_password = password;
    }

    private final String m_name;

    private final String m_password;
}
