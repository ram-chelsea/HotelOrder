package com.pvt.entities;


import com.pvt.constants.OrderStatus;

import java.sql.Date;

/**
 * Describes <tt>Order</tt> entity
 */
public class Order extends Entity {

    private int orderId;
    private User user;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;
    private OrderStatus orderStatus;
    private int totalPrice;

    @Override
    public int hashCode() {
        int result = 1;
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((orderId == 0) ? 0 : Integer.valueOf(orderId).hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((user == null) ? 0 : user.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((room == null) ? 0 : room.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((checkInDate == null) ? 0 : checkInDate.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((checkOutDate == null) ? 0 : checkOutDate.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((totalPrice == 0) ? 0 : Integer.valueOf(totalPrice).hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Order)) {
            return false;
        }
        Order other = (Order) obj;

        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        if (room == null) {
            if (other.room != null) {
                return false;
            }
        } else if (!room.equals(other.room)) {
            return false;
        }
        if (checkInDate == null) {
            if (other.checkInDate != null) {
                return false;
            }
        } else if (!checkInDate.equals(other.checkInDate)) {
            return false;
        }
        if (checkOutDate == null) {
            if (other.checkOutDate != null) {
                return false;
            }
        } else if (!checkOutDate.equals(other.checkOutDate)) {
            return false;
        }
        if (orderStatus == null) {
            if (other.orderStatus != null) {
                return false;
            }
        } else if (!orderStatus.equals(other.orderStatus)) {
            return false;
        }
        if (totalPrice == 0) {
            if (other.totalPrice != 0) {
                return false;
            }
        } else if (totalPrice != other.totalPrice) {
            return false;
        }
        return true;
    }

    /**
     * @return the <tt>Order</tt> id
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @param orderId id to set
     */

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the <tt>Order</tt> booker <tt>User</tt>
     */

    public User getUser() {
        return user;
    }

    /**
     * @param user <tt>User</tt> to set
     */

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the <tt>Order</tt> room to be ordered
     */

    public Room getRoom() {
        return room;
    }

    /**
     * @param room room to set
     */

    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @return the <tt>Order</tt> start date
     */

    public Date getCheckInDate() {
        return checkInDate;
    }

    /**
     * @param checkInDate order start date to set
     */

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * @return the <tt>Order</tt> ending date
     */

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * @param checkOutDate order ending date to set
     */

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * @return the <tt>Order</tt> status
     */

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus status to set
     */

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return the <tt>Order</tt> price
     */

    public int getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice price to set
     */

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


}
