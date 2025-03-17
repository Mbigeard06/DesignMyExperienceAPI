package com.utopia.designmyexperience_api.service;

import com.utopia.designmyexperience_api.dao.IOfferingDao;
import com.utopia.designmyexperience_api.dao.OfferingDao;
import com.utopia.designmyexperience_api.model.Activity;
import com.utopia.designmyexperience_api.model.Offering;
import com.utopia.designmyexperience_api.model.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@org.springframework.stereotype.Service
public class OfferingService {

    private IOfferingDao offeringDao;

    @Autowired
    public OfferingService(OfferingDao offeringDao) {
        this.offeringDao = offeringDao;
    }

    /**
     * Get a list of all offerings for a business owner.
     * @param businessOwnerId the business owner ID
     * @return List of offerings
     */
    public List<Offering> getOfferingsByBusinessOwner(int businessOwnerId) {
        return offeringDao.getOfferings(businessOwnerId);
    }

    /**
     * Get a generic offering by ID.
     * @param offeringId the offering ID
     * @return the Offering object
     */
    public Offering getOffering(int offeringId) {
        return offeringDao.getOffering(offeringId);
    }

    /**
     * Get an activity by offering ID.
     * @param activityId the activity ID
     * @return the Activity object
     */
    public Activity getActivity(int activityId) {
        return offeringDao.getActivity(activityId);
    }

    /**
     * Get a service by offering ID.
     * @param serviceId the service ID
     * @return the Service object
     */
    public Service getService(int serviceId) {
        return offeringDao.getService(serviceId);
    }
}