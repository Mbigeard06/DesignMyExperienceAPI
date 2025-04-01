package com.utopia.designmyexperience_api.service;

import com.utopia.designmyexperience_api.dao.IOfferingDao;
import com.utopia.designmyexperience_api.dao.OfferingDao;
import com.utopia.designmyexperience_api.model.Activity;
import com.utopia.designmyexperience_api.model.Offering;
import com.utopia.designmyexperience_api.model.Service;
import com.utopia.designmyexperience_api.model.enums.OfferingTypes;
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

    /**
     * Get all offerings.
     * @return List of all offerings
     */
    public List<Offering> getOfferings() {
        return offeringDao.getOfferings();
    }

    /**
     * Create a new activity offering.
     * @param activity the activity to be created
     * @return the ID of the newly created activity
     */
    public int createActivity(Activity activity) {
        return offeringDao.createActivity(activity);
    }

    /**
     * Create a new service offering.
     * @param service the service to be created
     * @return the ID of the newly created service
     */
    public int createService(Service service) {
        return offeringDao.createService(service);
    }

    /**
     * Get all services.
     * @return list of all services
     */
    public List<Service> getAllServices() {
        return offeringDao.getAllServices();
    }

    /**
     * Get upcoming activities with startDate > now.
     * @return list of upcoming activities
     */
    public List<Activity> getUpcomingActivities() {
        return offeringDao.getAllUpcomingActivities();
    }

    /**
     * Get all services that are still avaible
     * @return List of available services
     */
    public List<Service> getAllUpcomingServices(){
        return offeringDao.getAllUpcomingServices();
    }

    /**
     *
     * @param serviceId id of the service to be deleted
     */
    public void deleteService(int serviceId){
        this.offeringDao.deleteService(serviceId);
    }

    /**
     *
     * @param activityId id of the activity to be deleted
     */
    public void deleteActivity(int activityId){
        this.offeringDao.deleteActivity(activityId);
    }

    /**
     * Delete an offering based on its type (activity or service)
     * @param offeringId the ID of the offering to delete
     */
    public void deleteOffering(int offeringId){
        Offering offering = getOffering(offeringId);
        if (offering == null) {
            throw new RuntimeException("Offering not found with ID: " + offeringId);
        }
        if (offering.getType() == OfferingTypes.Activity) {
            deleteActivity(offeringId);
        } else if (offering.getType() == OfferingTypes.Service) {
            deleteService(offeringId);
        } else {
            throw new RuntimeException("Unknown offering type for offering ID: " + offeringId);
        }
    }
}