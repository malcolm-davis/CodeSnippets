package code.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class StartEndConvert {

    public static void main(String[] args) {
        System.out.println("Start: TimeConert ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        StartEndConvert test = new StartEndConvert();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {

        long div = 100l;
        long demonitator = 100l;

        double div2 = div + demonitator;
        double demo2 = div + demonitator + div + demonitator;

        System.out.println(div/demonitator);
        System.out.println((double)div/(double)demonitator);
        System.out.println(div2/demo2);

        System.out.println(new java.text.DecimalFormat("###.##").format(Double.valueOf(23.446634)));

        //        key: START_TIME=null
        //        key: reportName=callByHourStatsReport
        //        key: selectedIncidentTypes=null
        //        key: CLIENT_NAME=Shelby County, AL
        //        key: IS_SAME_DAY_SHIFT=true
        //        key: format=html
        //        key: PARAM_END_TIME=null
        //        key: END_DATE=null
        //        key: selectedUsers=null
        //        key: START_DATE=2017-03-01
        //        key: ANSWER_TIME=null
        //        key: PARAM_START_TIME=null
        //        key: END_TIME=null
        //        key: QUEUE_NAMES=null
        //        key: OPERATOR=null
        //        key: selectedUnits=null
        //        key: SHOW_SHIFT=false
        //        key: selectedAgencies=null
        //        key: USERNAME=admin
        //        key: selectedQueues=null
        //        key: queryDispatchGroups=[cosvcs, othersvcs]
        //        key: dsn=

        // Base query: select ID, createdOn date from all calls within the parameters
//        final String startDate = ReportingParamsUtil.getStartDate(model);
//        final String startTime = ReportingParamsUtil.getStartTime(model);
//        String startCheck = startDate + " " + (StringUtils.hasText(startTime) ? startTime : "00:00:00");
//
//        final String endDate = ReportingParamsUtil.getEndDate(model);
//        final String endTime = ReportingParamsUtil.getEndTime(model);
//        String endCheck = (StringUtils.hasText(endDate) ? (endDate + ":" + endTime) : "NOW()" );


        String startDate= "2017-03-01";
        String startTime= null;
//        String start = startDate + "T" + (StringUtils.isNotBlank(startTime) ? startTime : "00:00:00");
//        String end = startDate + "T" + (StringUtils.isNotBlank(startTime) ? startTime : "23:59:59");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        String start = startDate + "" + (StringUtils.isNotBlank(startTime) ? startTime : "00:00:00");
        String end = startDate + "" + (StringUtils.isNotBlank(startTime) ? startTime : "23:59:59");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");

        try {
            Date startDATE = df.parse(start);
            Date endDATE = df.parse(end);
            String newDateString = df.format(startDATE);

            System.out.println("start="+start);
            System.out.println("startDATE="+startDATE.toString());
            System.out.println("endDATE="+endDATE.toString());
            System.out.println("date.getMonth="+startDATE.getMonth());
            System.out.println("newDateString="+newDateString);

            Date endDate = new Date();
            System.out.println("daysBetween=" + daysBetween(startDATE, endDATE));
        } catch (ParseException excep) {
            excep.printStackTrace();
        }


        final String endDate = null;
        final String endTime = null;
//
//        String end = (StringUtils.isBlank(endDate) ? (endDate + ":" + endTime) : "NOW()" );
//        Date now = new Date();
//
//        df.setTimeZone(TimeZone.getTimeZone("UTC"));
//        System.out.println(df.format(now));
    }

    /**
     * Using Calendar - THE CORRECT WAY *
     *
     * @param startDate a {@link java.util.Date} object.
     * @param endDate a {@link java.util.Date} object.
     * @return a long.
     */
    public static long daysBetween(final Date startDate,
            final Date endDate) {
        final Calendar startDateC = Calendar.getInstance();
        startDateC.setTime(startDate);

        final Calendar endDateC= Calendar.getInstance();
        endDateC.setTime(endDate);

        final Calendar date = (Calendar) startDateC.clone();
        long daysBetween = 0;  // normally start counting at 0
        while (date.before(endDateC)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }
}
