/*
 * toolkit-java
 *
 * Copyright (C) 2012 Dialogue Communications Ltd.
 */

package net.dialogue.toolkit.sms;

/**
 * List of common status codes. This list is not exhaustive.
 */
public class StatusCodes {

    // Transaction Completed (00 to 1F)
    public static final String STATUS_SUCCESSFUL                            = "00";

    // Permanent Errors (40 to 5F)
    public static class PermanentErrors {
        public static final String STATUS_REMOTE_PROCEDURE_ERROR            = "40";
        public static final String STATUS_INCOMPATIBLE_DESTINATION          = "41";
        public static final String STATUS_CONNECTION_REJECTED_BY_SME        = "42";
        public static final String STATUS_NOT_OBTAINABLE                    = "43";
        public static final String STATUS_NO_INTERWORKING_AVAILABLE         = "45";
        public static final String STATUS_VALIDITY_PERIOD_EXPIRED           = "46";
        public static final String STATUS_MESSAGE_DELETED_BY_SME            = "47";
        public static final String STATUS_MESSAGE_DELETED_BY_SMSC           = "48";
        public static final String STATUS_UNKNOWN_SUBSCRIBER                = "4A";
        public static final String STATUS_SMSC_ERROR                        = "4B";
        public static final String STATUS_MAX_ATTEMPTS_REACHED              = "50";
        public static final String STATUS_MAX_TTL_REACHED                   = "51";
        public static final String STATUS_INVALID_DATA_IN_MESSAGE           = "52";
        public static final String STATUS_NON_ROUTABLE                      = "53";
        public static final String STATUS_NO_RESPONSE_FROM_SME              = "55";
        public static final String STATUS_SME_REJECTED_MESSAGE              = "56";
        public static final String STATUS_UNKNOWN_ERROR                     = "57";
        public static final String STATUS_OPERATOR_BAR                      = "58";
        public static final String STATUS_SERVICE_ID_NOT_PROVISIONED        = "5B";
        public static final String STATUS_MSISDN_IN_QUARANTINE              = "5C";
    }

    // Retry Errors (60 to 7F)
    public static class RetryErrors {
        public static final String STATUS_NO_RESPONSE_FROM_SME              = "62";
        public static final String STATUS_ERROR_IN_SME                      = "65";
        public static final String STATUS_MAX_ATTEMPTS_REACHED              = "70";
        public static final String STATUS_MAX_TTL_REACHED                   = "71";
        public static final String STATUS_INSUFFICIENT_PREPAY_CREDIT        = "74";
        public static final String STATUS_MESSAGE_IN_FLIGHT_UNKNOWN_STATUS  = "7A";
        public static final String STATUS_EXPENDITURE_LIMIT_REACHED         = "7B";
    }
}
