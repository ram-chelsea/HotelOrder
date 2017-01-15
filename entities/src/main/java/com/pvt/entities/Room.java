package com.pvt.entities;


import com.pvt.constants.RoomClass;

/**
 * Describes <tt>Room</tt> entity
 */
public class Room extends Entity {

    private int roomId;
    private String roomNumber;
    private int roominess;
    private RoomClass roomClass;
    private int price;

    @Override
    public int hashCode() {
        int result = 1;
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((roomNumber == null) ? 0 : roomNumber.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((roominess == 0) ? 0 : Integer.valueOf(roominess).hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((roomClass == null) ? 0 : roomClass.hashCode());
        result = PRIME_NUMBER_FOR_HASHCODE * result + ((price == 0) ? 0 : Integer.valueOf(price).hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Room)) {
            return false;
        }
        Room other = (Room) obj;

        if (roomNumber == null) {
            if (other.roomNumber != null) {
                return false;
            }
        } else if (!roomNumber.equals(other.roomNumber)) {
            return false;
        }
        if (roominess == 0) {
            if (other.roominess != 0) {
                return false;
            }
        } else if (roominess != other.roominess) {
            return false;
        }
        if (roomClass == null) {
            if (other.roomClass != null) {
                return false;
            }
        } else if (!roomClass.equals(other.roomClass)) {
            return false;
        }
        if (price == 0) {
            if (other.price != 0) {
                return false;
            }
        } else if (price != other.price) {
            return false;
        }
        return true;
    }

    /**
     * @return the <tt>Room</tt> id
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * @param roomId id to set
     */

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * @return the <tt>Room</tt> roominess
     */

    public int getRoominess() {
        return roominess;
    }

    /**
     * @param roominess roominess to set
     */

    public void setRoominess(int roominess) {
        this.roominess = roominess;
    }

    /**
     * @return the <tt>Room</tt> class
     */

    public RoomClass getRoomClass() {
        return roomClass;
    }

    /**
     * @param roomClass roomClass to set
     */

    public void setRoomClass(RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    /**
     * @return the <tt>Room</tt> price
     */

    public int getPrice() {
        return price;
    }

    /**
     * @param price price to set
     */

    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return the <tt>Room</tt> number
     */

    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber roomNumber to set
     */

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
