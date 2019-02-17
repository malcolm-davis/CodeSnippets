package code.cdi;

import javax.inject.Named;

@Named("hondaAutoService")
public class HondaAutoService implements AutoService{

    @Override
    public void getService() {
        System.out.println("You chose Honda auto service");
    }
}