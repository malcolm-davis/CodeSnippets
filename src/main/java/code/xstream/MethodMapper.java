package code.xstream;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * Review XStream FAQ docs on line:
 * <code>
 * XStream xstream = new XStream(new XppDriver(new XStream11XmlFriendlyReplacer())) {
 *     protected boolean useXStream11XmlFriendlyMapper() {
 *         return true;
 *     }
 * };
 * <code>
 *
 * See:
 * http://www.koders.com/java/fid3946322A5CAEEB939B1BB620D0F477C06FEF3EDA.aspx
 * http://www.nabble.com/Alias-and-Converter-Confusion-tc13555625.html#a14721399
 *
 * @author Malcolm G. Davis
 */
public class MethodMapper extends MapperWrapper {

    /**
     * Constructor
     */
    public MethodMapper(final Mapper wrapped) {
        super(wrapped);
    }

    /**
     * Constructor
     */
    public MethodMapper(final Mapper wrapped, String packageName) {
        super(wrapped);
        m_packageName = packageName;
    }


    /**
     * @see com.thoughtworks.xstream.mapper.MapperWrapper#serializedMember(java.lang.Class, java.lang.String)
     * @return capitalized version of member
     */
    @Override
    @SuppressWarnings("unchecked")
    public String serializedMember(final Class type, final String memberName) {
        String modified = memberName;
        if( type!=null && type.getPackage()!=null ) {
            if( m_packageName!=null && type.getPackage().getName().contains(m_packageName) ) {
                if( memberName.startsWith(M_) ) {
                    modified = memberName.substring((M_.length()));
                }
            }
        }

        return StringUtils.capitalize(modified);
    }

    /**
     * @see com.thoughtworks.xstream.mapper.MapperWrapper#realMember(java.lang.Class, java.lang.String)
     * @return real member value
     */
    @SuppressWarnings("unchecked")
    @Override
    public String realMember(final Class type, final String serialized) {
        if( type!=null && type.getPackage()!=null ) {
            if( m_packageName!=null && type.getPackage().getName().contains(m_packageName) ) {
                return (M_ + StringUtils.uncapitalize(serialized));
            }
        }
        return StringUtils.uncapitalize(serialized);
    }

    private String m_packageName;
    private static final String M_ = "m_";
}
