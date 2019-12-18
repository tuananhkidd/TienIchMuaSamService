package com.kidd.shopping.utils;

import java.net.IDN;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class EmailValidator {
    private static final String LOCAL_PART_ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~\u0080-\uFFFF-]";
    private static final String DOMAIN_LABEL = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
    private static final String DOMAIN = DOMAIN_LABEL + "+(\\." + DOMAIN_LABEL + "+)*";
    private static final String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
    private static final int MAX_LOCAL_PART_LENGTH = 64;
    private static final int MAX_DOMAIN_PART_LENGTH = 255;
    private static final Pattern LOCAL_PART_PATTERN = Pattern.compile(
            LOCAL_PART_ATOM + "+(\\." + LOCAL_PART_ATOM + "+)*", CASE_INSENSITIVE
    );
    private static final Pattern DOMAIN_PATTERN = Pattern.compile(
            DOMAIN + "|" + IP_DOMAIN, CASE_INSENSITIVE
    );

    public static boolean isEmailValid(String email) {
        String[] emailParts = email.split("@", 3);
        return emailParts.length == 2 && matchLocalPart(emailParts[0]) && matchDomain(emailParts[1]);
    }

    private static boolean matchLocalPart(String localPart) {
        if ( localPart.length() > MAX_LOCAL_PART_LENGTH ) {
            return false;
        }
        Matcher matcher = LOCAL_PART_PATTERN.matcher( localPart );
        return matcher.matches();
    }

    private static boolean matchDomain(String domain) {
        if ( domain.endsWith( "." ) ) {
            return false;
        }

        String asciiString;
        try {
            asciiString = IDN.toASCII( domain );
        }
        catch (IllegalArgumentException e) {
            return false;
        }

        if ( asciiString.length() > MAX_DOMAIN_PART_LENGTH ) {
            return false;
        }

        Matcher matcher = DOMAIN_PATTERN.matcher( asciiString );
        return matcher.matches();
    }
}
