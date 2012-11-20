package net.dialogue.toolkit.sms;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * Contains details of an individual submission.
 */
@XStreamAlias(value = "sms")
public class Sms implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("X-E3-ID")
    private String id;
    @XStreamAsAttribute
    @XStreamAlias("X-E3-Recipients")
    private String recipient;
    @XStreamAsAttribute
    @XStreamAlias("X-E3-Submission-Report")
    private String submissionReport;
    @XStreamAsAttribute
    @XStreamAlias("X-E3-Error-Description")
    private String errorDescription;

    /**
     * Gets the unique submission identifier.
     *
     * @return The submission identifier if successful or null otherwise
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique submission identifier.
     *
     * @param id The submission identifier if successful or null otherwise
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the recipient of the submission.
     *
     * @return The recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Sets the recipient of the submission.
     *
     * @param recipient The recipient
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Gets the outcome (status code) of the submission; "00" means successful.
     *
     * @return The submission report
     */
    public String getSubmissionReport() {
        return submissionReport;
    }

    /**
     * Sets the outcome (status code) of the submission; "00" means successful.
     *
     * @param submissionReport The submission report
     */
    public void setSubmissionReport(String submissionReport) {
        this.submissionReport = submissionReport;
    }

    /**
     * Gets the error description of failed submissions.
     *
     * @return The error description if failed or null otherwise
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Sets the error description of failed submissions.
     *
     * @param errorDescription The error description if failed or null otherwise
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * Checks if the submission was successful.
     *
     * @return True if the submission was successful, false otherwise.
     */
    public boolean isSuccessful() {
        return "00".equals(submissionReport);
    }

    /**
     * Returns a string representation of this Sms instance.
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Sms{" +
                "id='" + id + '\'' +
                ", recipient='" + recipient + '\'' +
                ", submissionReport='" + submissionReport + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}