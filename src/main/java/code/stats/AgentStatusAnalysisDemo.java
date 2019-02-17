package code.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AgentStatusAnalysisDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("Start: AgentStatusAnalysisDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        AgentStatusAnalysisDemo test = new AgentStatusAnalysisDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    public static final String LOGIN = "LOGIN";
    public static final String LOGOUT = "LOGOUT";
    public static final String NOT_READY = "NOT_READY";
    public static final String OPERATOR = "OPERATOR";
    public static final String ANSWER_TIME = "ANSWER_TIME";


    private void run() throws Exception {
//        StopWatch loginStopWatch = new StopWatch();
//
//        loginStopWatch.start();
//        Thread.sleep(1000);
//        loginStopWatch.stop();
//        Thread.sleep(100);
//        System.out.println(loginStopWatch.elapsed());
//
//        loginStopWatch.start();
//        Thread.sleep(100);
//        loginStopWatch.start();
//        Thread.sleep(100);
//        loginStopWatch.stop();
//        System.out.println(loginStopWatch.elapsed());



        List<AgentStatusData>data = new ArrayList<AgentStatusData>();
        data.add(new AgentStatusData("admin","NOT_READY", "2016-11-02 11:39:28"));
        data.add(new AgentStatusData("admin","ON_ACD_CALL", "2016-11-02 11:43:18"));
        data.add(new AgentStatusData("admin","NOT_READY", "2016-11-02 11:43:41"));
        data.add(new AgentStatusData("admin","ON_ACD_CALL", "2016-11-02 11:47:23"));
        data.add(new AgentStatusData("admin","NOT_READY", "2016-11-02 11:47:26"));
        data.add(new AgentStatusData("admin","LOGOUT", "2016-11-02 15:30:25"));
        data.add(new AgentStatusData("admin","NOT_READY", "2016-11-03 09:48:50"));
        data.add(new AgentStatusData("admin","LOGIN", "2016-11-03 09:48:50"));
        data.add(new AgentStatusData("admin","LOGOUT", "2016-11-03 09:49:57"));
        data.add(new AgentStatusData("admin","LOGIN", "2016-11-03 10:49:04"));
        data.add(new AgentStatusData("admin","NOT_READY", "2016-11-03 10:49:05"));
        data.add(new AgentStatusData("admin","LOGOUT", "2016-11-03 10:55:56"));
        data.add(new AgentStatusData("tyork","NOT_READY", "2016-11-02 11:39:28"));
        data.add(new AgentStatusData("tyork","ON_ACD_CALL", "2016-11-02 11:43:18"));
        data.add(new AgentStatusData("tyork","NOT_READY", "2016-11-02 11:43:41"));
        data.add(new AgentStatusData("tyork","ON_ACD_CALL", "2016-11-02 11:47:23"));
        data.add(new AgentStatusData("tyork","NOT_READY", "2016-11-02 11:47:26"));
        data.add(new AgentStatusData("tyork","LOGOUT", "2016-11-02 15:30:25"));
        data.add(new AgentStatusData("tyork","NOT_READY", "2016-11-03 09:48:50"));
        data.add(new AgentStatusData("tyork","LOGIN", "2016-11-03 09:48:50"));
        data.add(new AgentStatusData("tyork","LOGOUT", "2016-11-03 09:49:57"));
        data.add(new AgentStatusData("tyork","LOGIN", "2016-11-03 10:49:04"));
        data.add(new AgentStatusData("tyork","NOT_READY", "2016-11-03 10:49:05"));
        data.add(new AgentStatusData("tyork","LOGOUT", "2016-11-03 10:55:56"));
        data.add(new AgentStatusData("tyork","LOGIN", "2016-11-01 11:15:43"));
        data.add(new AgentStatusData("tyork","NOT_READY", "2016-11-03 11:15:44"));
        data.add(new AgentStatusData("tyork","LOGOUT", "2016-11-01 11:15:46"));
        data.add(new AgentStatusData("tyork","LOGIN", "2016-11-03 11:18:49"));
        data.add(new AgentStatusData("tyork","NOT_READY", "2016-11-03 11:18:50"));
        data.add(new AgentStatusData("tyork","LOGOUT", "2016-11-03 13:24:22"));
        data.add(new AgentStatusData("tyork","NOT_READY", "2016-11-04 07:03:03"));
        data.add(new AgentStatusData("tyork","LOGIN", "2016-11-04 07:03:03"));
        data.add(new AgentStatusData("tyork","LOGOUT", "2016-11-04 08:26:55"));
        data.add(new AgentStatusData("tyork","NOT_READY", "2016-11-04 13:48:52"));
        data.add(new AgentStatusData("tyork","LOGIN", "2016-11-04 13:48:52"));
        data.add(new AgentStatusData("tyork","LOGOUT", "2016-11-04 13:50:59"));
        data.add(new AgentStatusData("admin","LOGIN", "2016-11-03 11:15:43"));
        data.add(new AgentStatusData("admin","NOT_READY", "2016-11-03 11:15:44"));
        data.add(new AgentStatusData("admin","LOGOUT", "2016-11-03 11:15:46"));
        data.add(new AgentStatusData("admin","LOGIN", "2016-11-03 11:18:49"));
        data.add(new AgentStatusData("admin","NOT_READY", "2016-11-03 11:18:50"));
        data.add(new AgentStatusData("admin","LOGOUT", "2016-11-03 13:24:22"));
        data.add(new AgentStatusData("admin","NOT_READY", "2016-11-04 07:03:03"));
        data.add(new AgentStatusData("admin","LOGIN", "2016-11-04 07:03:03"));
        data.add(new AgentStatusData("admin","LOGOUT", "2016-11-04 08:26:55"));
        data.add(new AgentStatusData("admin","NOT_READY", "2016-11-04 13:48:52"));
        data.add(new AgentStatusData("admin","LOGIN", "2016-11-04 13:48:52"));
        data.add(new AgentStatusData("admin","LOGOUT", "2016-11-04 13:50:59"));


        // group and sort users/agents for calculations
        Map<String, List<AgentStatusData>> agentStatusMap =
                data.stream().collect(Collectors.groupingBy(AgentStatusData::getUsername));

        for( List<AgentStatusData>agentData : agentStatusMap.values() ) {
            agentData.sort((a1, a2) -> Long.valueOf(a1.getTimestamp()).compareTo(Long.valueOf(a2.getTimestamp())));
        }

        final long BEGINNING = -1L;

        /// need 3 calculations: total time logged in, not ready, ready.
        for( List<AgentStatusData>agentData : agentStatusMap.values() ) {

            String userName = "";
            StopWatch loginStopWatch = new StopWatch();
            StopWatch readyStopWatch = new StopWatch();
            StopWatch notReadyStopWatch = new StopWatch();
            long timeMarker = BEGINNING;

            // iterate through a single agents data
            for (AgentStatusData agentStatusData : agentData) {
                userName = agentStatusData.getUsername();

                // Any status could be the first status
                // assume LOGIN in unless have LOGOUT
                // assume NOT_READY with LOGIN

                // ignore if the first marker is LOGOUT
                if( BEGINNING==timeMarker && LOGOUT.equals(agentStatusData.getStatus()) ) {
                    continue ;
                }
                timeMarker = agentStatusData.getTimestamp();

                System.out.println( String.format("%10s\t%-15s\t%d",userName, agentStatusData.getStatus(), timeMarker));


                // what should occur with events that occur prior to a login?
                // stop timing
                if( LOGOUT.equals(agentStatusData.getStatus()) ) {
                    loginStopWatch.stop(agentStatusData.getTimestamp());
                    readyStopWatch.stop(agentStatusData.getTimestamp());
                    notReadyStopWatch.stop(agentStatusData.getTimestamp());

                } else if( LOGIN.equals(agentStatusData.getStatus()) ) {
                    loginStopWatch.stop(agentStatusData.getTimestamp());
                    readyStopWatch.stop(agentStatusData.getTimestamp());
                    notReadyStopWatch.stop(agentStatusData.getTimestamp());

                    loginStopWatch.start(agentStatusData.getTimestamp());
                    notReadyStopWatch.start(agentStatusData.getTimestamp());

                } else if( NOT_READY.equals(agentStatusData.getStatus()) ) {
                    if( !loginStopWatch.isRunning() ) {
                        loginStopWatch.start(agentStatusData.getTimestamp());
                    }
                    if( !notReadyStopWatch.isRunning() ) {
                        notReadyStopWatch.start(agentStatusData.getTimestamp());
                    }
                    readyStopWatch.stop(agentStatusData.getTimestamp());

                } else {
                    // all other status values are the user doing something useful
                    if( !loginStopWatch.isRunning() ) {
                        loginStopWatch.start(agentStatusData.getTimestamp());
                    }
                    notReadyStopWatch.stop(agentStatusData.getTimestamp());
                    readyStopWatch.start(agentStatusData.getTimestamp());
                    if( !readyStopWatch.isRunning() ) {
                        readyStopWatch.start(agentStatusData.getTimestamp());
                    }
                }
            }

            loginStopWatch.stop(timeMarker);
            notReadyStopWatch.stop(timeMarker);
            readyStopWatch.stop(timeMarker);



            System.out.println(userName);
            System.out.println("\tloginStopWatch.elapsed()="+loginStopWatch.timeFormat());
            System.out.println("\tnotReadyStopWatch.elapsed()="+notReadyStopWatch.timeFormat());
            System.out.println("\tnotReadyStopWatch %="+notReadyStopWatch.percent(loginStopWatch.elapsed()));
            System.out.println("\treadyStopWatch.elapsed()="+readyStopWatch.timeFormat());
            System.out.println("\treadyStopWatch %="+readyStopWatch.percent(loginStopWatch.elapsed()));
        }


//
//        for (AgentStatusData agentStatusData : data) {
//            if (LOGIN.equals(agentStatusData.getStatus())) {
//                if (previousAuthenticationTimestamp < 1) {
//                    previousAuthenticationTimestamp = agentStatusData.getTimestamp();
//                }
//                distribute(maximumAgentCounts, loggedInAgentCount, previousAuthenticationTimestamp, agentStatusData.getTimestamp());
//                loggedInAgentCount++;
//                previousAuthenticationTimestamp = agentStatusData.getTimestamp();
//            } else if (LOGOUT.equals(status)) {
//                if (previousAuthenticationTimestamp < 1) {
//                    previousAuthenticationTimestamp = agentStatusData.getTimestamp();
//                }
//                distribute(maximumAgentCounts, loggedInAgentCount, previousAuthenticationTimestamp, agentStatusData.getTimestamp());
//                loggedInAgentCount--;
//                previousAuthenticationTimestamp = agentStatusData.getTimestamp();
//                // Distribute previous ACD status till logout
//                if (previousEvent != null && !LOGIN.equals(previousEvent.getStatus())
//                        && !LOGOUT.equals(previousEvent.getStatus())) {
//                    distribute(eventCounts[AgentStatus.valueOf(previousEvent.getStatus()).ordinal()],
//                        previousEvent.getTimestamp(), agentStatusData.getTimestamp());
//                }
//            } else if (Arrays.stream(AgentStatus.values()).noneMatch(agentStatus -> agentStatus.name().equals(status))) {
//                continue;
//            }
//        }
    }

    class StopWatch {
        long totalTime;
        long start;
        boolean isRunning;

        public boolean isRunning() {
            return isRunning;
        }

        public String percent(long totalElapsed) {
            return "" + ((double)elapsed()/ totalElapsed) * 100;
        }

        public void start(long startTime) {
            if(isRunning) {
                // ignore, don't step on running timer
                // Note: The data is not sequential
                System.out.println(">>>      Overriding start timer     <<<<");
                return ;
            }
            start = startTime;
            isRunning = true;
        }

        public void stop(long stopTime) {
            // ignore
            if(!isRunning) {
                return ;
            }
            totalTime += (stopTime-start);
            isRunning = false;
        }

        public long elapsed() {
            return totalTime;
        }

        public String timeFormat() {
            long hours = TimeUnit.MILLISECONDS.toHours(elapsed());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed()) - TimeUnit.HOURS.toMinutes(hours);
            long seconds= TimeUnit.MILLISECONDS.toSeconds(elapsed()) - (TimeUnit.HOURS.toSeconds(hours) + TimeUnit.MINUTES.toSeconds(minutes));
            return String.format("%02d:%02d:%02d",hours, minutes, seconds);
        }
    }

}
