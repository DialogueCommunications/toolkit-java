/*
 * toolkit-java
 *
 * Copyright (C) 2012 Dialogue Communications Ltd.
 */

package net.dialogue.toolkit.sms;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

/**
 * List of common status codes. This list is not exhaustive.
 */
public class StatusCodes {

    /**
     * Default instance of StatusCodes.
     */
    public static final StatusCodes INSTANCE = new StatusCodes();

    // Transaction Completed (00 to 1F)
    public static class TransactionCompleted {

        public static final int START = 0x00;
        public static final int END = 0x1F;

        public static final String STATUS_SUCCESSFUL = "00";
        public static final String STATUS_SENT_TO_SME = "01";
        public static final String STATUS_REPLACED_AT_SMSC = "02";
    }

    // Temporary Errors (20 to 3F)
    public static class TemporaryError {

        public static final int START = 0x20;
        public static final int END = 0x3F;
    }

    // Permanent Errors (40 to 5F)
    public static class PermanentError {

        public static final int START = 0x40;
        public static final int END = 0x5F;

        public static final String STATUS_REMOTE_PROCEDURE_ERROR = "40";
        public static final String STATUS_INCOMPATIBLE_DESTINATION = "41";
        public static final String STATUS_CONNECTION_REJECTED_BY_SME = "42";
        public static final String STATUS_NOT_OBTAINABLE = "43";
        public static final String STATUS_QOS_NOT_AVAILABLE = "44";
        public static final String STATUS_NO_INTERWORKING_AVAILABLE = "45";
        public static final String STATUS_VALIDITY_PERIOD_EXPIRED = "46";
        public static final String STATUS_MESSAGE_DELETED_BY_SME = "47";
        public static final String STATUS_MESSAGE_DELETED_BY_SMSC = "48";
        public static final String STATUS_MESSAGE_DOES_NOT_EXIST = "49";
        public static final String STATUS_UNKNOWN_SUBSCRIBER = "4A";
        public static final String STATUS_SMSC_ERROR = "4B";
        public static final String STATUS_MAX_ATTEMPTS_REACHED = "50";
        public static final String STATUS_MAX_TTL_REACHED = "51";
        public static final String STATUS_INVALID_DATA_IN_MESSAGE = "52";
        public static final String STATUS_NON_ROUTABLE = "53";
        public static final String STATUS_AUTHENTICATION_FAILURE = "54";
        public static final String STATUS_NO_RESPONSE_FROM_SME = "55";
        public static final String STATUS_SME_REJECTED_MESSAGE = "56";
        public static final String STATUS_UNKNOWN_ERROR = "57";
        public static final String STATUS_OPERATOR_BAR = "58";
        public static final String STATUS_REQUEST_ID_NOT_FOUND = "59";
        public static final String STATUS_REQUEST_PREMIUM_CHARGE_ROUTING_ERROR = "5A";
        public static final String STATUS_SERVICE_ID_NOT_PROVISIONED = "5B";
        public static final String STATUS_MSISDN_DISCONNECTED_AND_IN_QUARANTINE = "5C";
        public static final String STATUS_VALIDITY_PERIOD_EXPIRED_WITH_NO_RECEIPT_FROM_CARRIER = "5D";
    }

    // Retry Errors (60 to 7F)
    public static class RetryError {

        public static final int START = 0x60;
        public static final int END = 0x7F;

        public static final String STATUS_CONGESTION = "60";
        public static final String STATUS_SME_BUSY = "61";
        public static final String STATUS_NO_RESPONSE_FROM_SME = "62";
        public static final String STATUS_SERVICE_REJECTED = "63";
        public static final String STATUS_QOS_NOT_AVAILABLE = "64";
        public static final String STATUS_ERROR_IN_SME = "65";
        public static final String STATUS_MAX_SUBMISSION_ATTEMPTS_REACHED = "70";
        public static final String STATUS_MAX_TTL_REACHED = "71";
        public static final String STATUS_DATABASE_SUBSYSTEM_ERROR = "72";
        public static final String STATUS_CORE_DEPENDANCY_MISSING = "73";
        public static final String STATUS_INSUFFICIENT_PREPAY_CREDIT = "74";
        public static final String STATUS_CORE_CONFIGURATION_ERROR = "75";
        public static final String STATUS_PLUGIN_SUBSYSTEM_ERROR = "76";
        public static final String STATUS_ROUTING_LOOP_DETECTED = "77";
        public static final String STATUS_O2_AGE_VERIFICATION_ERROR = "78";
        public static final String STATUS_ORANGE_AGE_VERIFICATION_ERROR = "79";
        public static final String STATUS_MESSAGE_IN_FLIGHT_UNKNOWN_STATUS = "7A";
        public static final String STATUS_EXPENDITURE_LIMIT_REACHED = "7B";
    }

    private static Map<String, String> STATUS_CODES = new HashMap<String, String>();

    static {

        // Transaction Completed (00 to 1F)
        STATUS_CODES.put(TransactionCompleted.STATUS_SUCCESSFUL, "Successful");
        STATUS_CODES.put(TransactionCompleted.STATUS_SENT_TO_SME, "Sent to SME but unable to confirm");
        STATUS_CODES.put(TransactionCompleted.STATUS_REPLACED_AT_SMSC, "Replaced at the SMSC");

        // Permanent Errors (40 to 5F)
        STATUS_CODES.put(PermanentError.STATUS_REMOTE_PROCEDURE_ERROR, "Remote procedure error");
        STATUS_CODES.put(PermanentError.STATUS_INCOMPATIBLE_DESTINATION, "Incompatible destination");
        STATUS_CODES.put(PermanentError.STATUS_CONNECTION_REJECTED_BY_SME, "Connection rejected by SME");
        STATUS_CODES.put(PermanentError.STATUS_NOT_OBTAINABLE, "Not obtainable");
        STATUS_CODES.put(PermanentError.STATUS_QOS_NOT_AVAILABLE, "Quality of service not available");
        STATUS_CODES.put(PermanentError.STATUS_NO_INTERWORKING_AVAILABLE, "No interworking available");
        STATUS_CODES.put(PermanentError.STATUS_VALIDITY_PERIOD_EXPIRED, "Validity period expired");
        STATUS_CODES.put(PermanentError.STATUS_MESSAGE_DELETED_BY_SME, "Message deleted by originating SME");
        STATUS_CODES.put(PermanentError.STATUS_MESSAGE_DELETED_BY_SMSC, "Message deleted by SMSC admin");
        STATUS_CODES.put(PermanentError.STATUS_MESSAGE_DOES_NOT_EXIST, "Message does not exist");
        STATUS_CODES.put(PermanentError.STATUS_UNKNOWN_SUBSCRIBER, "Unknown subscriber");
        STATUS_CODES.put(PermanentError.STATUS_SMSC_ERROR, "SMSC Error");
        STATUS_CODES.put(PermanentError.STATUS_MAX_ATTEMPTS_REACHED, "Maximum submission attempts reached");
        STATUS_CODES.put(PermanentError.STATUS_MAX_TTL_REACHED, "Maximum Time To Live (TTL) for message reached");
        STATUS_CODES.put(PermanentError.STATUS_INVALID_DATA_IN_MESSAGE, "Invalid data in message");
        STATUS_CODES.put(PermanentError.STATUS_NON_ROUTABLE, "Non-routable (operator used rejected message)");
        STATUS_CODES.put(PermanentError.STATUS_AUTHENTICATION_FAILURE, "Authentication failure (UCP 60 mainly could be used by login page)");
        STATUS_CODES.put(PermanentError.STATUS_NO_RESPONSE_FROM_SME, "No response from the SME");
        STATUS_CODES.put(PermanentError.STATUS_SME_REJECTED_MESSAGE, "SME rejected message");
        STATUS_CODES.put(PermanentError.STATUS_UNKNOWN_ERROR, "Unknown error");
        STATUS_CODES.put(PermanentError.STATUS_OPERATOR_BAR, "Operator bar");
        STATUS_CODES.put(PermanentError.STATUS_REQUEST_ID_NOT_FOUND, "Request ID not found");
        STATUS_CODES.put(PermanentError.STATUS_REQUEST_PREMIUM_CHARGE_ROUTING_ERROR, "Premium charge routing error");
        STATUS_CODES.put(PermanentError.STATUS_SERVICE_ID_NOT_PROVISIONED, "Service ID is not provisioned");
        STATUS_CODES.put(PermanentError.STATUS_MSISDN_DISCONNECTED_AND_IN_QUARANTINE, "MSISDN disconnected and in quarantine. MSISDN must be removed from databases.");
        STATUS_CODES.put(PermanentError.STATUS_VALIDITY_PERIOD_EXPIRED_WITH_NO_RECEIPT_FROM_CARRIER, "Validity period expired with no receipt from carrier");

        // Retry Errors (60 to 7F)
        STATUS_CODES.put(RetryError.STATUS_CONGESTION, "Congestion");
        STATUS_CODES.put(RetryError.STATUS_SME_BUSY, "SME busy");
        STATUS_CODES.put(RetryError.STATUS_NO_RESPONSE_FROM_SME, "No response from SME");
        STATUS_CODES.put(RetryError.STATUS_SERVICE_REJECTED, "Service rejected");
        STATUS_CODES.put(RetryError.STATUS_QOS_NOT_AVAILABLE, "Quality of service not available");
        STATUS_CODES.put(RetryError.STATUS_ERROR_IN_SME, "Error in SME");
        STATUS_CODES.put(RetryError.STATUS_MAX_SUBMISSION_ATTEMPTS_REACHED, "Max submission attempt reached (finalised before validity period expired)");
        STATUS_CODES.put(RetryError.STATUS_MAX_TTL_REACHED, "Max TTL for message reached (finalised when validity period expired)");
        STATUS_CODES.put(RetryError.STATUS_DATABASE_SUBSYSTEM_ERROR, "Database sub-system error");
        STATUS_CODES.put(RetryError.STATUS_CORE_DEPENDANCY_MISSING, "Core dependency missing");
        STATUS_CODES.put(RetryError.STATUS_INSUFFICIENT_PREPAY_CREDIT, "Insufficient prepay credit");
        STATUS_CODES.put(RetryError.STATUS_CORE_CONFIGURATION_ERROR, "Core configuration error");
        STATUS_CODES.put(RetryError.STATUS_PLUGIN_SUBSYSTEM_ERROR, "Plug-in sub-system error");
        STATUS_CODES.put(RetryError.STATUS_ROUTING_LOOP_DETECTED, "Routing loop detected");
        STATUS_CODES.put(RetryError.STATUS_O2_AGE_VERIFICATION_ERROR, "Age verification failure");
        STATUS_CODES.put(RetryError.STATUS_ORANGE_AGE_VERIFICATION_ERROR, "Age verification failure");
        STATUS_CODES.put(RetryError.STATUS_MESSAGE_IN_FLIGHT_UNKNOWN_STATUS, "Message in flight with unknown status");
        STATUS_CODES.put(RetryError.STATUS_EXPENDITURE_LIMIT_REACHED, "Expenditure limit reached");
    }

    /**
     * Checks if the status code provided represents a completed transaction.
     * @param statusCode The status code as integer
     * @return True if the status code is between TransactionCompleted.START and TransactionCompleted.END (inclusive), false otherwise
     * @throws IllegalArgumentException Thrown if statusCode is null or empty.
     */
    public boolean isTransactionCompleted(String statusCode) {
        if(statusCode == null || statusCode.length() == 0) {
            throw new IllegalArgumentException(
                    "No status code provided."
            );
        }
        try {
            return isTransactionCompleted(Integer.parseInt(statusCode, 16));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the status code provided represents a completed transaction.
     * @param statusCode The status code as hexadecimal string
     * @return True if the status code is between TransactionCompleted.START and TransactionCompleted.END (inclusive), false otherwise
     */
    public boolean isTransactionCompleted(int statusCode) {
        return statusCode >= TransactionCompleted.START && statusCode <= TransactionCompleted.END;
    }

    /**
     * Checks if the status code provided represents a Temporary error.
     * @param statusCode The status code as integer
     * @return True if the status code is between TemporaryError.START and TemporaryError.END (inclusive), false otherwise
     * @throws IllegalArgumentException Thrown if statusCode is null or empty.
     */
    public boolean isTemporaryError(String statusCode) {
        if(statusCode == null || statusCode.length() == 0) {
            throw new IllegalArgumentException(
                    "No status code provided."
            );
        }
        try {
            return isTemporaryError(Integer.parseInt(statusCode, 16));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the status code provided represents a Temporary error.
     * @param statusCode The status code as hexadecimal string
     * @return True if the status code is between TemporaryError.START and TemporaryError.END (inclusive), false otherwise
     */
    public boolean isTemporaryError(int statusCode) {
        return statusCode >= TemporaryError.START && statusCode <= TemporaryError.END;
    }
    
    /**
     * Checks if the status code provided represents a permanent error.
     * @param statusCode The status code as integer
     * @return True if the status code is between PermanentError.START and PermanentError.END (inclusive), false otherwise
     * @throws IllegalArgumentException Thrown if statusCode is null or empty.
     */
    public boolean isPermanentError(String statusCode) {
        if(statusCode == null || statusCode.length() == 0) {
            throw new IllegalArgumentException(
                    "No status code provided."
            );
        }
        try {
            return isPermanentError(Integer.parseInt(statusCode, 16));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the status code provided represents a permanent error.
     * @param statusCode The status code as hexadecimal string
     * @return True if the status code is between PermanentError.START and PermanentError.END (inclusive), false otherwise
     */
    public boolean isPermanentError(int statusCode) {
        return statusCode >= PermanentError.START && statusCode <= PermanentError.END;
    }
    
    /**
     * Checks if the status code provided represents a retry error.
     * @param statusCode The status code as integer
     * @return True if the status code is between RetryError.START and RetryError.END (inclusive), false otherwise
     * @throws IllegalArgumentException Thrown if statusCode is null or empty.
     */
    public boolean isRetryError(String statusCode) {
        if(statusCode == null || statusCode.length() == 0) {
            throw new IllegalArgumentException(
                    "No status code provided."
            );
        }
        try {
            return isRetryError(Integer.parseInt(statusCode, 16));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the status code provided represents a retry error.
     * @param statusCode The status code as hexadecimal string
     * @return True if the status code is between RetryError.START and RetryError.END (inclusive), false otherwise
     */
    public boolean isRetryError(int statusCode) {
        return statusCode >= RetryError.START && statusCode <= RetryError.END;
    }

    /**
     * Returns a description of the provided status code.
     * @param statusCode The status code as hexadecimal string
     * @return Description mapped from the status code
     * @throws IllegalArgumentException Thrown if statusCode is null or empty.
     */
    public String getDescription(String statusCode) {
        if(statusCode == null || statusCode.length() == 0) {
            throw new IllegalArgumentException(
                    "No status code provided."
            );
        }
        String res = STATUS_CODES.get(statusCode.toUpperCase());
        if(res == null) {
            res = "Unknown status code " + statusCode;
        }
        return res;
    }

    /**
     * Returns a description of the provided status code.
     * @param statusCode The status code as integer
     * @return Description mapped from the status code
     */
    public String getDescription(int statusCode) {
        StringBuilder b = new StringBuilder();
        new Formatter(b).format("%02X", statusCode);
        return getDescription(b.toString());
    }
}
