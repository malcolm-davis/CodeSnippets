package code.jmx;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

public class JmxSystemInfo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start: SystemInfo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        JmxSystemInfo test = new JmxSystemInfo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println("runtimeMXBean.getStartTime()="+runtimeMXBean.getStartTime());
        System.out.println("runtimeMXBean.getUptime()="+runtimeMXBean.getUptime());
        System.out.println("runtimeMXBean.getVmName()="+runtimeMXBean.getVmName());
        System.out.println("runtimeMXBean.getVmVendor()="+runtimeMXBean.getVmVendor());
        System.out.println("runtimeMXBean.getVmVersion()="+runtimeMXBean.getVmVersion());

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        System.out.println("operatingSystemMXBean.getArch()="+operatingSystemMXBean.getArch());
        System.out.println("operatingSystemMXBean.getName()="+operatingSystemMXBean.getName());
        System.out.println("operatingSystemMXBean.getVersion()="+operatingSystemMXBean.getVersion());
        System.out.println("operatingSystemMXBean.getSystemLoadAverage()="+operatingSystemMXBean.getSystemLoadAverage());
        printCpuLoad(operatingSystemMXBean);

        System.out.println("Runtime.getRuntime().availableProcessors()="+Runtime.getRuntime().availableProcessors());
        System.out.println("Runtime.getRuntime().freeMemory()="+memFormat(Runtime.getRuntime().freeMemory()));
        System.out.println("Runtime.getRuntime().totalMemory()="+memFormat(Runtime.getRuntime().totalMemory()));
        System.out.println("Runtime.getRuntime().maxMemory()="+memFormat(Runtime.getRuntime().maxMemory()));
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        System.out.println("memory.getHeapMemoryUsage()="+memFormat(memory.getHeapMemoryUsage().getUsed()));
        System.out.println("memory.getNonHeapMemoryUsage()="+memFormat(memory.getNonHeapMemoryUsage().getUsed()));
        printMemory(memory);

        ClassLoadingMXBean classes = ManagementFactory.getClassLoadingMXBean();
        System.out.println("classes.getLoadedClassCount()="+classes.getLoadedClassCount());
        System.out.println("classes.getTotalLoadedClassCount()="+classes.getTotalLoadedClassCount());
        System.out.println("classes.getUnloadedClassCount()="+classes.getUnloadedClassCount());

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        System.out.println("threadMXBean.getThreadCount()="+threadMXBean.getThreadCount());

        long allThreadsCpuTime = 0L;
        long[] threadIds = threadMXBean.getAllThreadIds();
        for ( int i = 0; i < threadIds.length; i++ ) {
            allThreadsCpuTime += threadMXBean.getThreadCpuTime( threadIds[i] );
        }
        System.out.println("allThreadsCpuTime="+allThreadsCpuTime);

    }


    private static void printCpuLoad(OperatingSystemMXBean mxBean) {
        for (Method method : mxBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            String methodName = method.getName();
//            if (methodName.startsWith("get") && methodName.contains("Cpu") && methodName.contains("Load")
//                    && Modifier.isPublic(method.getModifiers())) {
//
            if (methodName.startsWith("get") && Modifier.isPublic(method.getModifiers())) {

                Object value;
                try {
                    value = method.invoke(mxBean);
                } catch (Exception e) {
                    value = e;
                }
                System.out.println("\t"+methodName + "=" + value + "  ("+value.getClass().getName()+")");
            }
        }
    }

    private static void printMemory(MemoryMXBean mxBean) {
        for (Method method : mxBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            String methodName = method.getName();
            if (methodName.startsWith("get") && Modifier.isPublic(method.getModifiers())) {

                Object value;
                try {
                    value = method.invoke(mxBean);
                } catch (Exception e) {
                    value = e;
                }
                System.out.println("\t"+methodName + "=" + value);
            }
        }
    }

    private String memFormat(double freeMemory) {
        double value = (freeMemory>0) ? freeMemory/(1024*1024) : 0;
        double roundOff = Math.round(value * 100.0) / 100.0;
        return ""+roundOff;
    }

}
