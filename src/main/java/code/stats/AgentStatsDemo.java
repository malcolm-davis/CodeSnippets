package code.stats;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class AgentStatsDemo {



    public static void main(final String[] args) {
        System.out.println("Start: AgentStatsDemo ");
        System.out.println("---------------------------------------------");

        final long start = System.currentTimeMillis();

        final AgentStatsDemo test = new AgentStatsDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {

        Map<String, AgentStats>agentMap = new TreeMap<String, AgentStats>();

        addAgentStat(agentMap, "Fred", 10000, 10000, 10000);
        addAgentStat(agentMap, "Fred", 10000, 10000, 10000);
        addAgentStat(agentMap, "Fred", 10000, 10000, 10000);
        addAgentStat(agentMap, "Fred", 10000, 10000, 10000);
        addAgentStat(agentMap, "Fred", 10000, 10000, 10000);
        addAgentStat(agentMap, "Maxine", 1000, 1000, 1000);

        // Determine totals for later use
        long totalAgentCalls = 0l;
        for (AgentStats stat : agentMap.values()) {
            totalAgentCalls += stat.getAgentCallCount();
        }

        // determine agent % of totals
        double answerTotals = 0.0d;
        double ringTotals = 0.0d;
        double talkTotals = 0.0d;
        Map<String, Double>agentPercentOfTotalMap = new HashMap<String, Double>();
        for (AgentStats stat : agentMap.values()) {
            Double agentPercent = Double.valueOf(((double)stat.getAgentCallCount()/(double)totalAgentCalls)*100.0);
            agentPercentOfTotalMap.put(stat.getAgentId(), agentPercent);

            answerTotals += stat.getAnswerTimeTotal();
            ringTotals += stat.getRingTimeTotal();
            talkTotals += stat.getTalkTimeTotal();
        }

        // total weighted averages
        // double value = (agent1Sum/(totalTime)*agent1Avg) + (agent2Sum/(totalTime)*agent2Avg) + ...
        double answerWeightedAverage = 0d;
        double ringWeightedAverage = 0d;
        double talkWeightedAverage = 0d;
        double answerMaxTimeTotal = 0d;
        double ringMaxTimeTotal = 0d;
        double talkMaxTimeTotal = 0d;
        for (AgentStats stat : agentMap.values()) {
            answerWeightedAverage+=(stat.getAnswerTimeTotal()/answerTotals) * stat.getAnswerAverage();
            ringWeightedAverage+=(stat.getRingTimeTotal()/ringTotals) * stat.getRingTimeAverage();
            talkWeightedAverage+=(stat.getTalkTimeTotal()/talkTotals) * stat.getTalkTimeAverage();

            answerMaxTimeTotal += stat.getAnswerTimeMax();
            ringMaxTimeTotal += stat.getRingTimeMax();
            talkMaxTimeTotal += stat.getTalkTimeMax();
        }

        double answerMaxTimeAverage = answerMaxTimeTotal/totalAgentCalls;
        double ringMaxTimeAverage = ringMaxTimeTotal/totalAgentCalls;
        double talkMaxTimeAverage = talkMaxTimeTotal/totalAgentCalls;

        for (AgentStats stat : agentMap.values()) {
            System.out.print(stat.getAgentId() + ", ");
            System.out.print(stat.getAgentCallCount() + ", ");
            System.out.print(agentPercentOfTotalMap.get(stat.getAgentId()) + ", ");
            System.out.print(formatToSeconds(stat.getAnswerAverage()) + ", ");
            System.out.print(formatToSeconds(stat.getAnswerTimeMax()) + ", ");

            System.out.print(formatToSeconds(stat.getRingTimeAverage()) + ", ");
            System.out.print(formatToSeconds(stat.getRingTimeMax()) + ", ");

            System.out.print(formatToSeconds(stat.getTalkTimeAverage()) + ", ");
            System.out.print(formatToSeconds(stat.getTalkTimeMax()) + "\r\n");
        }

        System.out.print("Total / Weighted Avg, ");
        System.out.print(totalAgentCalls + ", ");
        System.out.print("100%, ");
        System.out.print(formatToSeconds(answerWeightedAverage) + ", ");
        System.out.print(formatToSeconds(answerMaxTimeAverage) + ", ");

        System.out.print(formatToSeconds(ringWeightedAverage) + ", ");
        System.out.print(formatToSeconds(ringMaxTimeAverage) + ", ");

        System.out.print(formatToSeconds(talkWeightedAverage) + ", ");
        System.out.print(formatToSeconds(talkMaxTimeAverage) + "\r\n");

    }

    public String formatToSeconds(long millis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        long remainer = millis - (seconds*1000);
        return seconds + "." + remainer;
    }

    public String formatToSeconds(double millis) {
        long millisL =Double.valueOf(millis).longValue();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millisL);

        long remainer = millisL - (seconds*1000);
        return seconds + "." + remainer;
    }

    private void addAgentStat(Map<String, AgentStats> agentMap, String agentId,
            final long answerTime, final long ringTime,
            final long talkTime) {
        if(agentId==null) {
            return ;
        }
        AgentStats stats = agentMap.get(agentId);
        if(stats==null) {
            stats = new AgentStats(agentId);
            agentMap.put(agentId,stats);
        }

        stats.addRecord(answerTime, ringTime, talkTime);
    }

    class AgentStats {

        public void addRecord(final long answerTime, final long ringTime,
                final long talkTime) {
            agentCallCount++;
            answerTimeTotal += answerTime;
            ringTimeTotal += ringTime;
            talkTimeTotal += talkTime;
            answerTimeMax = Math.max(answerTimeMax, answerTime);
            ringTimeMax = Math.max(ringTimeMax, ringTime);
            talkTimeMax = Math.max(talkTimeMax, talkTime);
        }

        public int getAgentCallCount() {
            return agentCallCount;
        }

        public String getAgentId() {
            return agentId;
        }

        public double getAnswerAverage() {
            return answerTimeTotal / agentCallCount;
        }

        public long getAnswerTimeMax() {
            return answerTimeMax;
        }

        public long getAnswerTimeTotal() {
            return answerTimeTotal;
        }

        public double getRingTimeAverage() {
            return ringTimeTotal / agentCallCount;
        }

        public long getRingTimeMax() {
            return ringTimeMax;
        }

        public long getRingTimeTotal() {
            return ringTimeTotal;
        }

        public double getTalkTimeAverage() {
            return talkTimeTotal / agentCallCount;
        }

        public long getTalkTimeMax() {
            return talkTimeMax;
        }

        public long getTalkTimeTotal() {
            return talkTimeTotal;
        }

        public AgentStats(@SuppressWarnings("hiding") final String agentId) {
            this.agentId = agentId;
        }

        private final String agentId;

        private long answerTimeTotal;

        private long ringTimeTotal;

        private long talkTimeTotal;

        private int agentCallCount;

        private long answerTimeMax;

        private long ringTimeMax;

        private long talkTimeMax;
    }
}
