package code.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlTest {

    public static void main(String[] args) {
        System.out.println("Start: MysqlTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        MysqlTest test = new MysqlTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    @SuppressWarnings("resource")
    private void run() {
        // https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-connect-drivermanager.html
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Connection conn = null;
        try {
            // conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e911guiwar?user=e911&password=e911");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e911guiwar", "e911", "e911");

            // Do something with the Connection
            // https://examples.javacodegeeks.com/core-java/sql/jdbc-batch-insert-example/
            // http://viralpatel.net/blogs/batch-insert-in-java-jdbc/
            Statement stmt = null;
            try{
                stmt = conn.createStatement();
                conn.setAutoCommit(false);

                // Batch the batches
                final int MAX_BATCH_SIZE = 500;

                // iterate list
                for(int count=1; count<=10000; count++) {
                    //stmt.addBatch("insert into PERSONS values ('Java','CodeGeeks',"+i+","+i+")");

                    if(count % MAX_BATCH_SIZE == 0) {
                        int[] result = stmt.executeBatch();
                        System.out.println("The number of rows inserted: "+ result.length);
                    }
                }

                int[] result = stmt.executeBatch();
                System.out.println("The number of rows inserted: "+ result.length);

                conn.commit();
            }catch(Exception e){
                conn.rollback();
                e.printStackTrace();
            } finally{
                if(stmt!=null) {
                    stmt.close();
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }

        // jdbc:mysql://localhost:3306/e911guiwar
        // mdavis
        // admin
    }

}
