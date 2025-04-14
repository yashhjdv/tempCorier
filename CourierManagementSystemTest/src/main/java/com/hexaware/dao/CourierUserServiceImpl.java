package com.hexaware.dao;

import com.hexaware.entity.Courier;
import com.hexaware.entity.CourierCompany;
import com.hexaware.entity.Employee;
import com.hexaware.exception.TrackingNumberNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CourierUserServiceImpl implements ICourierUserService {

    protected CourierCompany companyObj;

    public CourierUserServiceImpl() {
        this.companyObj = new CourierCompany();
    }

    @Override
    public String placeOrder(Courier courierObj) {
        companyObj.addCourier(courierObj);
        return courierObj.getTrackingNumber(); // tracking number auto-generated
    }

    @Override
    public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
        for (Courier c : companyObj.getCouriers()) {
            if (c != null && c.getTrackingNumber().equals(trackingNumber)) {
                return c.getStatus();
            }
        }
        throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
    }

    @Override
    public boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException {
        Courier[] couriers = companyObj.getCouriers();
        for (Courier c : couriers) {
            if (c != null && c.getTrackingNumber().equals(trackingNumber)) {
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
            if (c != null && c.getEmployee() != null && c.getEmployee().getEmployeeID() == courierStaffId) {
                assigned.add(c);
            }
        }
        return assigned;
    }
}
