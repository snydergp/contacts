/**
 * 
 */
package com.snyder.contacts.client.serverinterface;

/**
 * 
 * @author greg
 */
public interface ErrorHandler<T extends Throwable>
{

	void onFailure(T thowable);

}
