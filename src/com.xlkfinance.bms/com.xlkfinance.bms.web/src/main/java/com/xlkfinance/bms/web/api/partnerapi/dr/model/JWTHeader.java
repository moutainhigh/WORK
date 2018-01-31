package com.xlkfinance.bms.web.api.partnerapi.dr.model;

import org.apache.commons.lang3.StringUtils;

public class JWTHeader {

    private static String ALG = "RS256";
    private static String TYP = "JWT";

    private String alg;
    private String typ;

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }


    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public boolean checkHeader() {
        if (ALG.equals(alg) && TYP.equals(typ)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        JWTHeader header = (JWTHeader) obj;
        if (StringUtils.equals(alg, header.getAlg())
                && StringUtils.equals(typ, header.getTyp())) {
            return true;
        } else {
            return false;
        }
    }

}
