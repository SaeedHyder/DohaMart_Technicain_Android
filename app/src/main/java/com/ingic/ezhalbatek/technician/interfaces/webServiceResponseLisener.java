package com.ingic.ezhalbatek.technician.interfaces;

/**
 * Created on 7/17/2017.
 */

public interface webServiceResponseLisener<T> {
    public void ResponseSuccess(T result, String Tag);
    public void ResponseFailureNoResonse(String tag);
    public void ResponseFailure(String tag);
    public void ResponseBlockAccount(String tag);
}
