package com.utopia.designmyexperience_api.dao;

import com.utopia.designmyexperience_api.model.Activity;
import com.utopia.designmyexperience_api.model.Offering;
import com.utopia.designmyexperience_api.model.Service;

import java.util.List;

public interface IOfferingDao {
    /**
     * Get the offering of a business owner
     * @param id of the business owner
     * @return list of the offerings made by a business owner
     */
    public List<Offering> getOfferings(int id);


    /**
     *
     * @param id offering id
     * @return simple offering
     */
    public Offering getOffering(int id);

    /**
     * Get an activity
     * @param id of the offering associated withe the activity
     * @return activity
     */
    public Activity getActivity(int id);

    /**
     * Get a service
     * @param id activity id
     * @return service
     */
    public Service getService(int id);

    /**
     *
     * @return List of offerings for the client
     */
    public List<Offering> getOfferings();

    /**
     *
     * @return id of the offering create
     */
    public int createService(Service service);

    /**
     *
     * @return id of the offering create
     */
    public int createActivity(Activity activity);

    /**
     *
     * @return List of the upcoming activities
     */
    public List<Activity> getAllUpcomingActivities();

    /**
     *
     * @return Lit of all the services
     */
    List<Service> getAllServices();


    /**
     *
     * @param offeringId offering id
     * @return capacity remaining
     */
    public int getRemainingCapacity(int offeringId);

    /**
     *
     * @return List of the upcoming services
     */
    public List<Service> getAllUpcomingServices();

    /**
     *
     * @param id service id
     */
    public void deleteService(int id);

    /**
     *
     * @param id activity id
     */
    public void deleteActivity(int id);

}
