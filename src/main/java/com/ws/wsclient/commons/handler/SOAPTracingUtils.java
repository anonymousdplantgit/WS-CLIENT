
package com.ws.wsclient.commons.handler;

import com.ws.wsclient.commons.io.MaxSizeByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

final class SOAPTracingUtils {

    private static final String DEFAULT_MESSAGE_ENCODING = "utf-8";

    private static final Logger LOG = LoggerFactory.getLogger(SOAPTracingUtils.class);

    /**
     * Private default ctr for utility class.
     */
    private SOAPTracingUtils() {
    }

    /**
     * Get the string representation of the SOAP message for the TracingContext.
     *
     * @param message The SOAP message for which we need the String repres.
     * @param maxSize max size of the text, everything after this is ignored
     * @return The String repres. of the SOAP message for the TracingContext.
     */
    static String getSoapTextForTracingContext(SOAPMessage message, int maxSize) {
        String messageText;
        try {
            messageText = getOriginalSoapFromMessage(message, maxSize);
        } catch (Exception e) {
            LOG.error("An error occurred", e);
            // We catch everything to prevent runtimeexception for the logging
            // from breaking the business logic.
            messageText = "Undecodable message due to : " + e.getMessage();
        }
        return messageText;
    }

    /**
     * Returns the message encoding (e.g. utf-8)
     *
     * @param msg The message for which we need the encoding.
     * @return The encoding of the message.
     * @throws SOAPException if an error occured in the SOAP message.
     */
    private static String getMessageEncoding(SOAPMessage msg) throws SOAPException {
        String encoding = DEFAULT_MESSAGE_ENCODING;
        if (msg.getProperty(SOAPMessage.CHARACTER_SET_ENCODING) != null) {
            encoding = msg.getProperty(SOAPMessage.CHARACTER_SET_ENCODING).toString();
        }
        return encoding;
    }

    /**
     * Returns the original text of the SOAP message.
     *
     * @param message The SOAP message to decode.
     * @param maxSize max size of the text, everything after this is ignored
     * @return The original text of the SOAP message.
     * @throws SOAPException If an errorOcured while manipulating the soap message.
     * @throws IOException   If an errorOcured while manipulating the soap message.
     */
    private static String getOriginalSoapFromMessage(SOAPMessage message, int maxSize) throws SOAPException,
            IOException {
        String messageText = null;
        if (message != null) {
            ByteArrayOutputStream baos = new MaxSizeByteArrayOutputStream(maxSize);
            message.writeTo(baos);
            messageText = baos.toString(getMessageEncoding(message));
        }
        return messageText;
    }

}
