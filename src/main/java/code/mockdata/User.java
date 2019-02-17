package code.mockdata;

public class User {

    public static User newUser(final String fname, final String lname,
            final Gender gender, Address address) {
        return new User(fname, lname, gender, address);
    }

    public String getFname() {
        return m_fname;
    }

    public Gender getGender() {
        return m_gender;
    }

    public String getLname() {
        return m_lname;
    }

    @Override
    public String toString() {
        return "User [fname=" + m_fname + ", lname=" + m_lname + ", gender=" + m_gender + "]";
    }

    public User(final String fname, final String lname, final Gender gender, Address address) {
        m_fname = fname;
        m_lname = lname;
        m_gender = gender;
        m_address = address;
    }

    private final String m_fname;

    private final String m_lname;

    private final Gender m_gender;

    private final Address m_address;

}
