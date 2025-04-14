package com.hexaware.dao;

import com.hexaware.entity.Courier;
import com.hexaware.entity.CourierCompanyCollection;
import com.hexaware.entity.Employee;
import com.hexaware.exception.TrackingNumberNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CourierUserServiceCollectionImpl implements ICourierUserService {

    protected CourierCompanyCollection companyObj;

    public CourierUserServiceCollectionImpl() {
        this.companyObj = new CourierCompanyCollection();
    }

    @Override
    public String placeOrder(Courier courierObj) {
        companyObj.getCouriers().add(courierObj);
        return courierObj.getTrackingNumber();
    }

    @Override
    public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
        for (Courier c : companyObj.getCouriers()) {
            if (c.getTrackingNumber().equals(trackingNumber)) {
                return c.getStatus();
            }
        }
        throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
    }

    @Override
    public boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException {
        for (Courier c : companyObj.getCouriers()) {
            if (c.getTrackingNumber().equals(trackingNumber)) {
                c.setStatus("Cancelled");
                return true;
            }
        }
        throw new TrackingNumberNotFoundException("Cannot cancel - tracking number not found: " + trackingNumber);
    }

    @Override
    public List<Courier> getAssignedOrder(int courierStaffId) {
        List<Courier> assigned = new ArrayList<>();
        for (Courier c : companyObj.getCouriers()) {
            if (c.getEmployee() != null && c.getEmployee().getEmployeeID() == courierStaffId) {
                assigned.add(c);
            }
        }
        return assigned;
    }
}
