/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livebustracker.cpanel.exceptions;

/**
 *
 * @author mani
 */
public class EntityExistsException extends Exception {

    public EntityExistsException(String message) {
        super(message);
    }
}
