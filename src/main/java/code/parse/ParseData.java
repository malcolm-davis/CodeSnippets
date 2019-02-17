package code.parse;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ParseData {

    private static final String XML_END_TAG = "\"/>";
    private static final String ID = " id=\"";
    private static final String MAP_AS = " map-as=\"";
    private static final String PATH = " path=\"";
    private static final String NAME = " name=\"";
    private static final String FIELD = " field=\"";

    public static void main(String[] args) {
        System.out.println("Start: ParseData ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ParseData test = new ParseData();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }


    enum DiffAction { Added, Removed, Changed };

    private void run() {
        //  1st line: a stands for added, d for deleted and c for changed.

        List<String> list = new ArrayList<String>(Arrays.asList(DATA.split("\r\n")));
        DiffAction action = null;
        List<String> newField = new ArrayList<>();
        List<String> oldField  = new ArrayList<>();

        Map<String,String>nameSub = new HashMap<String,String>();
        nameSub.put("abstractDialButtonOrdering", "DirectoryButtonOrdering & SoftphoneButtonOrdering");
        nameSub.put("abstractDialButton", "DirectoryButton & SoftphoneButton");
        nameSub.put("abstractHasLocation", "Fact");

        for(String line : list) {
            // System.out.println(line);
            if( line.startsWith("diff -x") ) {
                printDiff(action, newField, oldField);

                int index = line.lastIndexOf('/') + 1;
                int end = line.length()-4;
                System.out.println("");

                String name = line.substring(index, end);
                System.out.println(nameSub.getOrDefault(name, name));
                action = null;
                continue ;
            }

            // 14c14,16
            if( StringUtils.isNumeric(line.substring(0, 1))
                    && StringUtils.containsAny(line, "a",  "d",  "c") ) {
                printDiff(action, newField, oldField);

                if( line.contains("a") ) {
                    action = DiffAction.Added;
                } else if( line.contains("d") ) {
                    action = DiffAction.Removed;
                } else if( line.contains("c") ) {
                    action = DiffAction.Changed;
                } else {
                    System.out.println("--ERROR---");
                }
                continue ;
            }

            if( line.startsWith("<") || line.startsWith(">") ) {
                int start = 0;
                int end = 0;
                if( line.contains(NAME) ) {
                    start = line.indexOf(NAME) + NAME.length();
                    end = line.indexOf("\" ");
                } else if( line.contains(PATH) ) {
                    start = line.indexOf(PATH) + PATH.length();
                    end = line.indexOf(XML_END_TAG);
                } else if( line.contains(MAP_AS) ) {
                    start = line.indexOf(MAP_AS) + MAP_AS.length();
                    end = line.indexOf(XML_END_TAG);
                } else if( line.contains(ID) ) {
                    start = line.indexOf(ID) + ID.length();
                    end = line.indexOf(XML_END_TAG);
                } else if( line.contains(FIELD) ) {
                    start = line.indexOf(FIELD) + FIELD.length();
                    end = line.indexOf(XML_END_TAG);
                } else {
                    //System.out.println("<>Nothing found for="+line);
                }
                if(start>0 && end>0) {
                    if( line.startsWith(">") ) {
                        newField.add(line.substring(start, end));
                    } else {
                        oldField.add(line.substring(start, end));
                    }
                }
                continue ;
            }
        }
        printDiff(action, newField, oldField);

    }

    private void printDiff(DiffAction action, List<String> newField,
            List<String> oldField) {
        if(action==null) {
            return ;
        }
        if( !newField.equals(oldField) ) {
            if( DiffAction.Changed.equals(action) ) {
                System.out.println("\t"+oldField + " replaced by " + newField);
            } else if( DiffAction.Removed.equals(action) ) {
                System.out.println("\t"+ action + " " + oldField);
            } else {
                System.out.println("\t"+ action + " " + newField);
            }
        } else if(!newField.isEmpty()) {
            System.out.println("\tSerializer change for " + newField);
        }
        newField.clear();
        oldField.clear();
    }

    public static final String DATA =
            "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/abstractDialButtonOrdering.xml /projects/codereview/trunk/src/java/com/clj2/business/config/abstractDialButtonOrdering.xml\r\n" +
                    "19,22c19\r\n" +
                    "<         <collection name=\"DispatchGroups\" get-method=\"getDispatchGroupNames\" set-method=\"setDispatchGroupNames\"\r\n" +
                    "<           create-type=\"java.util.LinkedHashSet\">\r\n" +
                    "<             <value name=\"dispatchGroup\" type=\"java.lang.String\"/>\r\n" +
                    "<         </collection>\r\n" +
                    "---\r\n" +
                    ">         <value name=\"dispatchGroup\" get-method=\"getDispatchGroupName\" set-method=\"setDispatchGroupName\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/abstractDialButton.xml /projects/codereview/trunk/src/java/com/clj2/business/config/abstractDialButton.xml\r\n" +
                    "10c10\r\n" +
                    "<         <value name=\"preferredNumber\" field=\"preferredNumber\" style=\"attribute\" usage=\"optional\"/>\r\n" +
                    "---\r\n" +
                    ">         <value name=\"preferredContact\" field=\"preferredContact\" style=\"attribute\" usage=\"optional\"/>\r\n" +
                    "14,16c14\r\n" +
                    "<         <collection name=\"DispatchGroups\" get-method=\"getDispatchGroupNames\" set-method=\"setDispatchGroupNames\" create-type=\"java.util.LinkedHashSet\">\r\n" +
                    "<             <value name=\"dispatchGroup\" type=\"java.lang.String\"/>\r\n" +
                    "<         </collection>\r\n" +
                    "---\r\n" +
                    ">         <value name=\"dispatchGroup\" get-method=\"getDispatchGroupName\" set-method=\"setDispatchGroupName\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/abstractHasLocation.xml /projects/codereview/trunk/src/java/com/clj2/business/config/abstractHasLocation.xml\r\n" +
                    "8,9c8\r\n" +
                    "<         <structure name=\"Location\" field=\"location\" map-as=\"model.location\"/>\r\n" +
                    "<         <value name=\"locationHash\" field=\"locationHash\" usage=\"optional\"/>\r\n" +
                    "---\r\n" +
                    ">         <structure name=\"Location\" field=\"location\" map-as=\"model.location\" usage = \"optional\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/applicationContext.xml /projects/codereview/trunk/src/java/com/clj2/business/config/applicationContext.xml\r\n" +
                    "19,30d18\r\n" +
                    "<     <!-- E911Config PersistentObject -->\r\n" +
                    "<     <bean id=\"configPersistentObject\" class=\"org.dellroad.stuff.pobj.PersistentObject\"\r\n" +
                    "<       init-method=\"start\" destroy-method=\"stop\" p:file=\"${e911.config.file}\" p:checkInterval=\"1000\">\r\n" +
                    "<         <property name=\"delegate\">\r\n" +
                    "<             <util:constant static-field=\"com.clj2.business.config.E911Config.SCHEMA_PBOJ_DELEGATE\"/>\r\n" +
                    "<         </property>\r\n" +
                    "<     </bean>\r\n" +
                    "< \r\n" +
                    "<     <!-- E911Config PersistentObjectTransactionManager -->\r\n" +
                    "<     <bean id=\"configTransactionManager\" class=\"org.dellroad.stuff.pobj.PersistentObjectTransactionManager\"\r\n" +
                    "<       p:persistentObject-ref=\"configPersistentObject\" p:readOnlySharedRoot=\"true\"/>\r\n" +
                    "< \r\n" +
                    "32,44c20\r\n" +
                    "<     <bean class=\"com.clj2.business.config.E911ConfigSynchronizer\" p:filename=\"config.xml\"\r\n" +
                    "<       p:persistentObject-ref=\"configPersistentObject\" p:syncUrlPath=\"${e911.config.sync.path}\"\r\n" +
                    "<       p:localSyncTopology=\"BALANCED_TREE\" p:remoteSyncTopology=\"BALANCED_TREE\">\r\n" +
                    "<         <property name=\"gitRepository\">\r\n" +
                    "<             <bean class=\"com.clj2.business.util.E911GitRepository\" c:dir=\"${e911.config.sync.dir}\"/>\r\n" +
                    "<         </property>\r\n" +
                    "<     </bean>\r\n" +
                    "< \r\n" +
                    "<     <!-- Task scheduler and executor definitions -->\r\n" +
                    "<     <task:scheduler id=\"taskScheduler\" pool-size=\"10\"/>\r\n" +
                    "<     <task:executor id=\"taskExecutor\" pool-size=\"5-25\" queue-capacity=\"100\" rejection-policy=\"DISCARD\"/>\r\n" +
                    "< \r\n" +
                    "<     <task:annotation-driven executor=\"taskExecutor\" scheduler=\"taskScheduler\"/>\r\n" +
                    "---\r\n" +
                    ">     <bean id=\"e911ConfigSynchronizer\" class=\"com.clj2.business.config.E911ConfigSynchronizer\"/>\r\n" +
                    "50d25\r\n" +
                    "< \r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/asteriskConfig.xml /projects/codereview/trunk/src/java/com/clj2/business/config/asteriskConfig.xml\r\n" +
                    "9,10c9\r\n" +
                    "<     <include path=\"dialSetup.xml\"/>\r\n" +
                    "<     <include path=\"tandemTransfer.xml\"/>\r\n" +
                    "---\r\n" +
                    ">     <include path=\"phoneDirectory.xml\"/>\r\n" +
                    "18a18\r\n" +
                    ">         <namespace uri=\"http://www.w3.org/1999/XSL/Transform\" default=\"none\" prefix=\"xsl\"/>\r\n" +
                    "34,36d33\r\n" +
                    "<         <collection name=\"DialSetups\" field=\"dialSetups\" create-type=\"java.util.ArrayList\">\r\n" +
                    "<             <structure name=\"DialSetup\" map-as=\"dialSetup\"/>\r\n" +
                    "<         </collection>\r\n" +
                    "49,51c46\r\n" +
                    "<         <collection name=\"TandemTransfers\" field=\"tandemTransfers\" create-type=\"java.util.ArrayList\">\r\n" +
                    "<             <structure name=\"TandemTransfer\" map-as=\"tandemTransfer\"/>\r\n" +
                    "<         </collection>\r\n" +
                    "---\r\n" +
                    ">         <structure name=\"PhoneDirectory\" field=\"phoneDirectory\" map-as=\"phoneDirectory\"/>\r\n" +
                    "58a54,61\r\n" +
                    ">         <structure name=\"IncomingPidfTransform\" usage=\"optional\" test-method=\"hasIncomingPidfTransform\">\r\n" +
                    ">             <structure name=\"transform\" field=\"incomingPidfTransform\" ns=\"http://www.w3.org/1999/XSL/Transform\" usage=\"optional\"\r\n" +
                    ">               marshaller=\"org.jibx.extras.DomElementMapper\" unmarshaller=\"org.jibx.extras.DomElementMapper\"/>\r\n" +
                    ">         </structure>\r\n" +
                    ">         <structure name=\"OutgoingPidfTransform\" usage=\"optional\" test-method=\"hasOutgoingPidfTransform\">\r\n" +
                    ">             <structure name=\"transform\" field=\"outgoingPidfTransform\" ns=\"http://www.w3.org/1999/XSL/Transform\" usage=\"optional\"\r\n" +
                    ">               marshaller=\"org.jibx.extras.DomElementMapper\" unmarshaller=\"org.jibx.extras.DomElementMapper\"/>\r\n" +
                    ">         </structure>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/autodialContext.xml /projects/codereview/trunk/src/java/com/clj2/business/config/autodialContext.xml\r\n" +
                    "10,12c10,12\r\n" +
                    "<         <value name=\"defaultPhoneNumber\" field=\"defaultPhoneNumber\" usage=\"optional\"/>\r\n" +
                    "<         <collection name=\"PhoneNumbers\" field=\"phoneNumbers\" create-type=\"java.util.LinkedHashSet\" usage=\"optional\">\r\n" +
                    "<             <value name=\"phoneNumber\" type=\"java.lang.String\"/>\r\n" +
                    "---\r\n" +
                    ">         <value name=\"defaultContact\" field=\"defaultContact\" usage=\"optional\"/>\r\n" +
                    ">         <collection name=\"Contacts\" field=\"contacts\" create-type=\"java.util.LinkedHashSet\" usage=\"optional\">\r\n" +
                    ">             <value name=\"contact\" type=\"java.lang.String\"/>\r\n" +
                    "17a18\r\n" +
                    ">         <value name=\"dispatchGroup\" get-method=\"getDispatchGroupName\" set-method=\"setDispatchGroupName\" usage=\"optional\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/cadFormatter.xml /projects/codereview/trunk/src/java/com/clj2/business/config/cadFormatter.xml\r\n" +
                    "7,9c7,9\r\n" +
                    "<         <value name=\"format\" get-method=\"getFormat\" set-method=\"setFormat\"\r\n" +
                    "<           serializer=\"org.dellroad.stuff.jibx.ParseUtil.serializeString\"\r\n" +
                    "<           deserializer=\"org.dellroad.stuff.jibx.ParseUtil.deserializeString\"/>\r\n" +
                    "---\r\n" +
                    ">         <value name=\"format\" get-method=\"getFormatter\" set-method=\"setFormatter\"\r\n" +
                    ">           serializer=\"com.clj2.business.lib.serial.NamedELArgumentFormatter.serialize\"\r\n" +
                    ">           deserializer=\"com.clj2.business.lib.serial.NamedELArgumentFormatter.deserialize\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/cadOutput.xml /projects/codereview/trunk/src/java/com/clj2/business/config/cadOutput.xml\r\n" +
                    "7a8\r\n" +
                    ">     <include path=\"hostPort.xml\"/>\r\n" +
                    "15c16,17\r\n" +
                    "<         <structure name=\"CADFormatter\" field=\"cadFormatter\" map-as=\"cadFormatter\"/>\r\n" +
                    "---\r\n" +
                    ">         <value name=\"sendEraseSpill\" field=\"sendEraseSpill\" default=\"false\"/>\r\n" +
                    ">         <structure name=\"CADFormatter\" field=\"cadFormatter\" map-as=\"cadFormatter\" usage=\"optional\"/>\r\n" +
                    "20c22,23\r\n" +
                    "<         <structure field=\"serialConfig\"/>\r\n" +
                    "---\r\n" +
                    ">         <structure field=\"serialConfig\" usage=\"optional\"/>\r\n" +
                    "---\r\n" +
                    "7a8\r\n" +
                    ">         <structure name=\"TCPConfig\" field=\"tcpConfig\" map-as=\"hostPort\" usage=\"optional\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/callConfig.xml /projects/codereview/trunk/src/java/com/clj2/business/config/callConfig.xml\r\n" +
                    "20c20,22\r\n" +
                    "<         <structure name=\"CurrentUserCallTableSection\" field=\"currentUserCallTableSection\" map-as=\"currentUserCallTableSection\" usage=\"optional\"/>\r\n" +
                    "---\r\n" +
                    ">         <collection name=\"CurrentUserCallTableSections\" field=\"currentUserCallTableSections\" create-type=\"java.util.ArrayList\" usage=\"optional\">\r\n" +
                    ">             <structure name=\"CurrentUserCallTableSection\" map-as=\"currentUserCallTableSection\" />\r\n" +
                    ">         </collection>\r\n" +
                    "Only in /projects/codereview/trunk/src/java/com/clj2/business/config: callDiversion.xml\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/cdrFormatter.xml /projects/codereview/trunk/src/java/com/clj2/business/config/cdrFormatter.xml\r\n" +
                    "7,9c7,9\r\n" +
                    "<         <value name=\"format\" get-method=\"getFormat\" set-method=\"setFormat\"\r\n" +
                    "<           serializer=\"org.dellroad.stuff.jibx.ParseUtil.serializeString\"\r\n" +
                    "<           deserializer=\"org.dellroad.stuff.jibx.ParseUtil.deserializeString\"/>\r\n" +
                    "---\r\n" +
                    ">         <value name=\"format\" get-method=\"getFormatter\" set-method=\"setFormatter\"\r\n" +
                    ">           serializer=\"com.clj2.business.lib.serial.NamedELArgumentFormatter.serialize\"\r\n" +
                    ">           deserializer=\"com.clj2.business.lib.serial.NamedELArgumentFormatter.deserialize\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/cdrOutput.xml /projects/codereview/trunk/src/java/com/clj2/business/config/cdrOutput.xml\r\n" +
                    "10d9\r\n" +
                    "<         <value name=\"redialSpillSupport\" field=\"redialSpillSupport\" default=\"true\"/>\r\n" +
                    "12a12\r\n" +
                    ">         <value name=\"sendAsXML\" field=\"sendAsXML\" usage=\"optional\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/clusterConfig.xml /projects/codereview/trunk/src/java/com/clj2/business/config/clusterConfig.xml\r\n" +
                    "10a11\r\n" +
                    ">     <include path=\"rapidSOSConfig.xml\"/>\r\n" +
                    "16a18,19\r\n" +
                    ">         <value name=\"federation\" field=\"federationUUID\" deserializer=\"org.dellroad.stuff.jibx.ParseUtil.deserializeUUID\"\r\n" +
                    ">           usage=\"optional\"/>\r\n" +
                    "34a38\r\n" +
                    ">         <structure name=\"RapidSOSConfig\" field=\"rapidSOSConfig\" map-as=\"rapidSOSConfig\" usage=\"optional\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/currentUserCallTableSection.xml /projects/codereview/trunk/src/java/com/clj2/business/config/currentUserCallTableSection.xml\r\n" +
                    "6a7\r\n" +
                    ">         <value name=\"initialRows\" field=\"initialRows\" default=\"3\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/directoryEntryType.xml /projects/codereview/trunk/src/java/com/clj2/business/config/directoryEntryType.xml\r\n" +
                    "9a10\r\n" +
                    ">         <value name=\"dispatchGroup\" field=\"dispatchGroupName\" usage=\"optional\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/directoryEntry.xml /projects/codereview/trunk/src/java/com/clj2/business/config/directoryEntry.xml\r\n" +
                    "9c9\r\n" +
                    "<         <value name=\"preferredNumber\" field=\"preferredNumber\" style=\"attribute\" usage=\"optional\"/>\r\n" +
                    "---\r\n" +
                    ">         <value name=\"preferredContact\" field=\"preferredContact\" style=\"attribute\" usage=\"optional\"/>\r\n" +
                    "12,15c12\r\n" +
                    "<         <collection name=\"DispatchGroups\" get-method=\"getDispatchGroupNames\" set-method=\"setDispatchGroupNames\"\r\n" +
                    "<           create-type=\"java.util.LinkedHashSet\">\r\n" +
                    "<             <value name=\"dispatchGroup\" type=\"java.lang.String\"/>\r\n" +
                    "<         </collection>\r\n" +
                    "---\r\n" +
                    ">         <value name=\"dispatchGroup\" get-method=\"getDispatchGroupName\" set-method=\"setDispatchGroupName\" usage=\"optional\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/dispatchGroup.xml /projects/codereview/trunk/src/java/com/clj2/business/config/dispatchGroup.xml\r\n" +
                    "6d5\r\n" +
                    "<     <include path=\"fallbackDispatchGroup.xml\"/>\r\n" +
                    "12a12\r\n" +
                    ">     <include path=\"callDiversion.xml\"/>\r\n" +
                    "18d17\r\n" +
                    "<         <structure name=\"FallbackDispatchGroup\" field=\"fallbackDispatchGroup\" map-as=\"fallbackDispatchGroup\" usage=\"optional\"/>\r\n" +
                    "30a30,35\r\n" +
                    ">         <collection name=\"Diversions\" field=\"diversions\" create-type=\"java.util.ArrayList\" usage=\"optional\">\r\n" +
                    ">             <structure name=\"CallDiversion\" map-as=\"callDiversion\"/>\r\n" +
                    ">         </collection>\r\n" +
                    ">         <collection name=\"ACDCascade\" field=\"acdCascade\" create-type=\"java.util.ArrayList\" usage=\"optional\">\r\n" +
                    ">             <value name=\"dispatchGroup\" type=\"java.lang.String\"/>\r\n" +
                    ">         </collection>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/e911Init.xml /projects/codereview/trunk/src/java/com/clj2/business/config/e911Init.xml\r\n" +
                    "7a8,11\r\n" +
                    ">     <include path=\"adminPhone.xml\"/>\r\n" +
                    ">     <include path=\"position.xml\"/>\r\n" +
                    ">     <include path=\"aiu.xml\"/>\r\n" +
                    ">     <include path=\"dialSetup.xml\"/>\r\n" +
                    "10a15\r\n" +
                    ">         <value name=\"configMaster\" field=\"configMaster\" usage=\"optional\"/>\r\n" +
                    "18a24,35\r\n" +
                    ">         <collection name=\"AdminPhones\" field=\"adminPhones\" create-type=\"java.util.ArrayList\">\r\n" +
                    ">             <structure name=\"AdminPhone\" map-as=\"adminPhone\"/>\r\n" +
                    ">         </collection>\r\n" +
                    ">         <collection name=\"Positions\" field=\"positions\" create-type=\"java.util.ArrayList\">\r\n" +
                    ">             <structure name=\"Position\" map-as=\"position\"/>\r\n" +
                    ">         </collection>\r\n" +
                    ">         <collection name=\"AIUs\" field=\"aius\" create-type=\"java.util.ArrayList\">\r\n" +
                    ">             <structure name=\"AIU\" map-as=\"aiu\"/>\r\n" +
                    ">         </collection>\r\n" +
                    ">         <collection name=\"DialSetups\" field=\"dialSetups\" create-type=\"java.util.ArrayList\">\r\n" +
                    ">             <structure name=\"DialSetup\" map-as=\"dialSetup\"/>\r\n" +
                    ">         </collection>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/e911Service.xml /projects/codereview/trunk/src/java/com/clj2/business/config/e911Service.xml\r\n" +
                    "13,15d12\r\n" +
                    "<     <include path=\"adminPhone.xml\"/>\r\n" +
                    "<     <include path=\"position.xml\"/>\r\n" +
                    "<     <include path=\"aiu.xml\"/>\r\n" +
                    "27a25\r\n" +
                    ">     <include path=\"printer.xml\"/>\r\n" +
                    "57,65d54\r\n" +
                    "<         <collection name=\"AdminPhones\" field=\"adminPhones\" create-type=\"java.util.ArrayList\">\r\n" +
                    "<             <structure name=\"AdminPhone\" map-as=\"adminPhone\"/>\r\n" +
                    "<         </collection>\r\n" +
                    "<         <collection name=\"Positions\" field=\"positions\" create-type=\"java.util.ArrayList\">\r\n" +
                    "<             <structure name=\"Position\" map-as=\"position\"/>\r\n" +
                    "<         </collection>\r\n" +
                    "<         <collection name=\"AIUs\" field=\"aius\" create-type=\"java.util.ArrayList\" usage=\"optional\">\r\n" +
                    "<             <structure name=\"AIU\" map-as=\"aiu\"/>\r\n" +
                    "<         </collection>\r\n" +
                    "98a88,90\r\n" +
                    ">         </collection>\r\n" +
                    ">         <collection name=\"Printers\" field=\"printers\" create-type=\"java.util.ArrayList\" usage=\"optional\">\r\n" +
                    ">             <structure name=\"Printer\" map-as=\"printer\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/extVoicemail.xml /projects/codereview/trunk/src/java/com/clj2/business/config/extVoicemail.xml\r\n" +
                    "7d6\r\n" +
                    "<         <value name=\"password\" field=\"password\" usage=\"optional\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/fact.xml /projects/codereview/trunk/src/java/com/clj2/business/config/fact.xml\r\n" +
                    "11,12c11,12\r\n" +
                    "<         <value name=\"url\" field=\"url\"/>\r\n" +
                    "<         <value name=\"name\" field=\"name\"/>\r\n" +
                    "---\r\n" +
                    ">         <value name=\"url\" field=\"url\" usage=\"optional\"/>\r\n" +
                    ">         <value name=\"name\" field=\"name\" usage=\"optional\"/>\r\n" +
                    "13a14\r\n" +
                    ">         <value name=\"phoneNumber\" field=\"phoneNumber\" usage=\"optional\"/>\r\n" +
                    "Only in /projects/codereview/4.0-branch/src/java/com/clj2/business/config: fallbackDispatchGroup.xml\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/guiConfig.xml /projects/codereview/trunk/src/java/com/clj2/business/config/guiConfig.xml\r\n" +
                    "27a28,30\r\n" +
                    ">         <collection name=\"ALICustomLabels\" field=\"aliCustomLabels\" create-type=\"java.util.ArrayList\" usage=\"optional\">\r\n" +
                    ">             <value name=\"label\" type=\"java.lang.String\"/>\r\n" +
                    ">         </collection>\r\n" +
                    "Only in /projects/codereview/trunk/src/java/com/clj2/business/config: guiConnectionLostBanner.xml\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/guiConnectionStatuses.xml /projects/codereview/trunk/src/java/com/clj2/business/config/guiConnectionStatuses.xml\r\n" +
                    "5a6\r\n" +
                    ">     <include path=\"guiConnectionLostBanner.xml\"/>\r\n" +
                    "10a12\r\n" +
                    ">         <structure name=\"ConnectionLostBanner\" field=\"connectionLostBanner\" map-as=\"guiConnectionLostBanner\" usage=\"optional\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/guiTabs.xml /projects/codereview/trunk/src/java/com/clj2/business/config/guiTabs.xml\r\n" +
                    "15a16\r\n" +
                    ">         <value name=\"hideSoftphoneButtonTab\" field=\"hideSoftphoneButtonTab\" usage=\"optional\" default=\"true\"/>\r\n" +
                    "18a20\r\n" +
                    ">         <value name=\"hideLineOrganizerTab\" field=\"hideLineOrganizerTab\" usage=\"optional\" default=\"false\"/>\r\n" +
                    "Only in /projects/codereview/trunk/src/java/com/clj2/business/config: hostPort.xml\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/incidentPriority.xml /projects/codereview/trunk/src/java/com/clj2/business/config/incidentPriority.xml\r\n" +
                    "8a9,10\r\n" +
                    ">         <value name=\"abbreviation\" field=\"abbreviation\"/>\r\n" +
                    ">         <value name=\"position\" field=\"position\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/incidentStatus.xml /projects/codereview/trunk/src/java/com/clj2/business/config/incidentStatus.xml\r\n" +
                    "8a9\r\n" +
                    ">         <value name=\"abbreviation\" field=\"abbreviation\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/localAreaCode.xml /projects/codereview/trunk/src/java/com/clj2/business/config/localAreaCode.xml\r\n" +
                    "7c7\r\n" +
                    "<         <value name=\"areaCode\" field=\"areaCode\" deserializer=\"com.clj2.business.config.ParseUtil.deserializeInt\"/>\r\n" +
                    "---\r\n" +
                    ">         <value name=\"areaCode\" field=\"areaCode\" type=\"java.lang.Integer\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/location.xml /projects/codereview/trunk/src/java/com/clj2/business/config/location.xml\r\n" +
                    "9a10,11\r\n" +
                    ">         <value name=\"FCCID\" field=\"fccID\" usage=\"optional\"/>\r\n" +
                    ">         <value name=\"PSAPName\" field=\"psapName\" usage=\"optional\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/outboundRoute.xml /projects/codereview/trunk/src/java/com/clj2/business/config/outboundRoute.xml\r\n" +
                    "9a10\r\n" +
                    ">         <value name=\"destination\" field=\"destination\" usage=\"optional\"/>\r\n" +
                    "Only in /projects/codereview/trunk/src/java/com/clj2/business/config: phoneDirectory.xml\r\n" +
                    "Only in /projects/codereview/trunk/src/java/com/clj2/business/config: phoneEntry.xml\r\n" +
                    "Only in /projects/codereview/trunk/src/java/com/clj2/business/config: printer.xml\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/queueACD.xml /projects/codereview/trunk/src/java/com/clj2/business/config/queueACD.xml\r\n" +
                    "14a15\r\n" +
                    ">         <value name=\"abandonedCallSchedulingEnabled\" field=\"abandonedCallSchedulingEnabled\" usage=\"optional\" default=\"false\"/>\r\n" +
                    "Only in /projects/codereview/trunk/src/java/com/clj2/business/config: rapidSOSConfig.xml\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/sipPeer.xml /projects/codereview/trunk/src/java/com/clj2/business/config/sipPeer.xml\r\n" +
                    "24a25\r\n" +
                    ">         <value name=\"requestProgress\" field=\"requestProgress\" usage=\"optional\" default=\"false\"/>\r\n" +
                    "28a30,31\r\n" +
                    ">         <value name=\"sendRequestURI\" field=\"sendRequestURI\" usage=\"optional\" default=\"false\"/>\r\n" +
                    ">         <value name=\"sendOriginalCallerId\" field=\"sendOriginalCallerId\" usage=\"optional\" default=\"false\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/siteConfig.xml /projects/codereview/trunk/src/java/com/clj2/business/config/siteConfig.xml\r\n" +
                    "27a28,30\r\n" +
                    ">         <value name=\"callRecordingAge\" field=\"callRecordingAge\" usage=\"optional\" default=\"0s\"\r\n" +
                    ">           serializer=\"org.dellroad.stuff.jibx.ParseUtil.serializeTimeInterval\"\r\n" +
                    ">           deserializer=\"org.dellroad.stuff.jibx.ParseUtil.deserializeTimeInterval\"/>\r\n" +
                    "30a34\r\n" +
                    ">         <value name=\"caseNumberReset\" field=\"caseNumberResetPolicy\" usage=\"optional\"/>\r\n" +
                    "38a43,49\r\n" +
                    ">         <value name=\"monitored\" field=\"monitored\" default=\"false\"/>\r\n" +
                    ">         <value name=\"disbanded\" field=\"disbanded\" default=\"false\"/>\r\n" +
                    ">         <value name=\"autoTddChallenge\" field=\"autoTddChallenge\" default=\"false\"/>\r\n" +
                    ">         <value name=\"autoTddChallengeBackoffPeriod\" field=\"autoTddChallengeBackoffPeriod\" default=\"2s\"\r\n" +
                    ">                serializer=\"org.dellroad.stuff.jibx.ParseUtil.serializeTimeInterval\"\r\n" +
                    ">                deserializer=\"org.dellroad.stuff.jibx.ParseUtil.deserializeTimeInterval\"/>\r\n" +
                    ">         <value name=\"sessionTimeout\" field=\"sessionTimeout\" default=\"5\" usage=\"optional\"/>\r\n" +
                    "43a55,58\r\n" +
                    ">         <value name=\"useEcatsIpService\" field=\"useEcatsIpService\" usage=\"optional\"/>\r\n" +
                    ">         <value name=\"ecatsUri\" field=\"ecatsUri\" usage=\"optional\"/>\r\n" +
                    ">         <value name=\"ecatsUriProxyIp\" field=\"ecatsUriProxyIp\" usage=\"optional\"/>\r\n" +
                    ">         <value name=\"ecatsUriProxyPort\" field=\"ecatsUriProxyPort\" usage=\"optional\"/>\r\n" +
                    "Only in /projects/codereview/trunk/src/java/com/clj2/business/config: sys\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/tandemTransfer.xml /projects/codereview/trunk/src/java/com/clj2/business/config/tandemTransfer.xml\r\n" +
                    "5a6\r\n" +
                    ">     <include path=\"phoneEntry.xml\"/>\r\n" +
                    "7,9c8\r\n" +
                    "<         <value name=\"dtmf\" field=\"dtmf\" style=\"attribute\"/>\r\n" +
                    "<         <value name=\"destination\" field=\"destination\" style=\"attribute\"/>\r\n" +
                    "<         <value name=\"includeInSpeedDial\" field=\"includeInSpeedDial\" usage=\"optional\" default=\"true\"/>\r\n" +
                    "---\r\n" +
                    ">         <structure map-as=\"phoneEntry\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/trunk.xml /projects/codereview/trunk/src/java/com/clj2/business/config/trunk.xml\r\n" +
                    "13a14\r\n" +
                    ">         <value name=\"requestProgress\" field=\"requestProgress\" usage=\"optional\" default=\"false\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/unitStatus.xml /projects/codereview/trunk/src/java/com/clj2/business/config/unitStatus.xml\r\n" +
                    "9a10\r\n" +
                    ">         <value name=\"abbreviation\" field=\"abbreviation\"/>\r\n" +
                    "11a13,15\r\n" +
                    ">         <value name=\"canonicalStatus\" get-method=\"getCanonicalUnitStatusUUID\" set-method=\"setCanonicalUnitStatusUUID\"\r\n" +
                    ">                usage=\"optional\"\r\n" +
                    ">                deserializer=\"com.clj2.business.util.UUIDTrackingMarshaller.deserializeAutoGenerated\"/>\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/unitTypeGroup.xml /projects/codereview/trunk/src/java/com/clj2/business/config/unitTypeGroup.xml\r\n" +
                    "8a9\r\n" +
                    ">         <value name=\"icon\" field=\"icon\"/>\r\n" +
                    "Common subdirectories: /projects/codereview/4.0-branch/src/java/com/clj2/business/config/updates and /projects/codereview/trunk/src/java/com/clj2/business/config/updates\r\n" +
                    "diff -x '*.java' -I '^.*Copyright.*$' -I '^.*$Id:.*$' /projects/codereview/4.0-branch/src/java/com/clj2/business/config/webaccessoryConfig.xml /projects/codereview/trunk/src/java/com/clj2/business/config/webaccessoryConfig.xml\r\n" +
                    "15a16\r\n" +
                    ">         <value name=\"hideDecisionStationDuringSegmentation\" field=\"hideDecisionStationDuringSegmentation\" usage=\"optional\" default=\"false\"/>";


}
