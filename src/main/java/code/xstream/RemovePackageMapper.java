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
 * @author Malcolm G. Davis
 */
public class RemovePackageMapper extends MapperWrapper {
    /**
     *
     * @param wrapped
     */
    public RemovePackageMapper(final Mapper wrapped) {
        super(wrapped);
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
            if( type.getPackage().getName().contains(COM_XPANDEDREPORTS) ) {
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
            if( type.getPackage().getName().contains(COM_XPANDEDREPORTS) ) {
                return (M_ + StringUtils.uncapitalize(serialized));
            }
        }
        return StringUtils.uncapitalize(serialized);
    }

    private static final String COM_XPANDEDREPORTS = "com.xpandedreports";
    private static final String M_ = "m_";
}
