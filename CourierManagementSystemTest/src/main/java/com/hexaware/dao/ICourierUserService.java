package com.hexaware.dao;

import com.hexaware.entity.Courier;
import com.hexaware.exception.DbConnectionException;
import com.hexaware.exception.TrackingNumberNotFoundException;

import java.util.List;

public interface ICourierUserService {

    /**
     * Place a new courier order.
     * @param courierObj Courier object created using values entered by users
     * @return The unique tracking number for the courier order
     */
    String placeOrder(Courier courierObj) throws DbConnectionException;

    /**
     * Get the status of a courier order.
     * @param trackingNumber The tracking number of the courier order.
     * @return The status of the courier order (e.g., yetToTransit, In Transit, Delivered).
     * @throws TrackingNumberNotFoundException if tracking number is invalid or not found
     */
    String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException;

    /**
     * Cancel a courier order.
     * @param trackingNumber The tracking number of the courier order to be canceled.
     * @return true if cancellation is successful, false otherwise
     * @throws TrackingNumberNotFoundException if the order is not found
     */
    boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException;

    /**
     * Get a list of orders assigned to a specific courier staff member
     * @param courierStaffId The ID of the courier staff member.
     * @return A list of courier orders assigned to the staff member.
     */
    List<Courier> getAssignedOrder(int courierStaffId);
}
