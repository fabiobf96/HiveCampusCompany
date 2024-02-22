package it.hivecampuscompany.hivecampus.logic.boundary;

import it.hivecampuscompany.hivecampus.logic.exception.OTPDismatchException;

import java.io.File;

public class OpenAPIBoundary {
    public File mokeFirmaDigitale(File file, String otp) throws OTPDismatchException {
        if(otp.equals("AAAAAAAA")){
            return file;
        }
        throw new OTPDismatchException("OTP password does not match");
    }
}
