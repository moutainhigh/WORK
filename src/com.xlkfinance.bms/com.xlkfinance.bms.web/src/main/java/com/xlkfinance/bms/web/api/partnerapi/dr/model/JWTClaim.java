package com.xlkfinance.bms.web.api.partnerapi.dr.model;

import org.apache.commons.lang3.StringUtils;

public class JWTClaim {

    private String clientId;
    private String aud;
    private Long exp;
    private Long iat;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    @Override
    public boolean equals(Object obj) {
        JWTClaim claim = (JWTClaim) obj;

        if (StringUtils.equals(clientId, claim.getClientId())
                && StringUtils.equals(aud, claim.getAud())
                && (exp.equals(claim.getExp()))
                && (iat.equals(claim.getIat()))) {
            return true;
        }else{
            return false;
        }
    }

}
