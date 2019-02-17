package code.interfaceTest;


public class InterfaceExample {

    public static void main(String[] args) {
        new InterfaceExample().run();
    }

    void run() {
        Test[] test1 = {
            new UserLoadTest(),
            new UserSaveTest(),
        };

        Test[] test2 = {
            new UserLoadTest(),
            new UserSaveTest(),
        };

        Test[] test3 = {
            new UserLoadTest(),
            new UserSaveTest(),
            new UserDeleteTest()
        };

        run(test1);
        run(test2);
        run(test3);
    }

    interface Test {
        boolean action(String inputOrState);
    }

    class UserLoadTest implements Test {
        @Override
        public boolean action(String inputOrState) {
            return true;
        }
    }

    class UserSaveTest implements Test {
        @Override
        public boolean action(String inputOrState) {
            return true;
        }
    }

    class UserCancelTest implements Test {
        @Override
        public boolean action(String inputOrState) {
            return true;
        }
    }

    class UserDeleteTest implements Test {
        @Override
        public boolean action(String inputOrState) {
            if( "false".equalsIgnoreCase(inputOrState)) {
                return false;
            }
            return true;
        }
    }

    static void run(Test[] tests) {
        for (Test test : tests) {
            System.out.println(test.getClass().getSimpleName() + "=" + test.action("false"));
        }
    }

}
