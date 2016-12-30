/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.thinking;


import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TimerService;
import javax.enterprise.inject.Produces;
/**
 *
 * @author achavanga
 */
public class Bootstrapper {

    // Resources
    @Produces
    @Resource(mappedName = "java:comp/EJBContext")
    private SessionContext sessionContext;	

    @Resource(mappedName = "java:comp/TimerService")
    @Produces
    private TimerService timerService;

}