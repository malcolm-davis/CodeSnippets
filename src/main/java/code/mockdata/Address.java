package code.mockdata;


public class Address {

    public static Address newAddress(String street, String city, String state, String zip) {
        return new Address(street,city,state,zip);
    }

    private Address(String street, String city, String state, String zip) {
        m_street = street;
        m_city = city;
        m_state = state;
        m_zip = zip;
    }

    private final String m_street;
    private final String m_city;
    private final String m_state;
    private final String m_zip;

}
