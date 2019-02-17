package code.fastfileread;


public class Parse {

    public static void main(String[] args) {
        System.out.println("Start: Parse ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        Parse test = new Parse();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        String tx  = "update CallHistory set agencies=null, aliAddress1=null, aliAddress2=null, aliCity=null, aliCounty=null, aliName=null, aliPayload=null, aliState=null, aliUpdatedOn=null, aliX=null, aliY=null, aliZip=null, alternateNumber=null, callbackNumber='9317594639', callerName=null, confidence=null, esn=null, firstLocalParticipant='4fb3593f-fb46-499b-ad4f-883563f20d5b', locationAddress1='', locationAddress2='', locationCity='', locationCountry='', locationCounty='', locationHash='d41d8cd98f00b204e9800998ecf8427e', locationState='', locationX=0.0, locationY=0.0, locationZip='', locationURI=null, psapID=null, reassociatedClusterUUID=null, status='RELEASED', telcoID=null, trunkClass=null, uncertainty=null, updatedOn='2017-09-28 20:48:18.874' where id='d9d71581-af49-4552-8c4b-c8dd2a50725c'";

        int end = tx.lastIndexOf("'");
        int start = end - 36;

        System.out.println(tx.substring(start, end));
    }

}
