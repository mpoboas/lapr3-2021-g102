package lapr.project.controller;

import lapr.project.model.Company;

public class App {
    private Company company;

    private App(){
        this.company = new Company("Company");
        bootstrap();
    }

    public Company getCompany(){ return this.company;}

    private void bootstrap(){}

    // Extracted from https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    private static App singleton = null;

    public static App getInstance()
    {
        if(singleton == null)
        {
            synchronized(App.class)
            {
                singleton = new App();
            }
        }
        return singleton;
    }
}
