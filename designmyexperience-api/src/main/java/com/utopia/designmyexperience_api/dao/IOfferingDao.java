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


}
