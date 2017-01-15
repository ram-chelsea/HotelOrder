package com.pvt.entities;


import java.io.Serializable;

/**
 * Describes the abstract entity <tt>Entity</tt>
 */
public abstract class Entity implements Serializable {

    protected static final long serialVersionUID = 1L;
    /**
     * Prime number constant being used for hashcode calculating
     */
    protected static final int PRIME_NUMBER_FOR_HASHCODE = 31;
}
