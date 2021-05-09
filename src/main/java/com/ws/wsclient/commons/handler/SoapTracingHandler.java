package com.ws.wsclient.commons.handler;

import com.ws.wsclient.commons.tracing.SoapCall;
import com.ws.wsclient.commons.tracing.TracingContextHolder;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.cxf.logging.FaultListener;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class SoapTracingHandler implements SOAPHandler<SOAPMessageContext>, FaultListener {

    private static final Logger LOG = LoggerFactory.getLogger(SoapTracingHandler.class);

    /**
     * we configure 512k as an acceptable default for maxMessageSize.
     */
    private static final int DEFAULT_MAX_MESSAGE_SIZE = 512 * 1024;

    private static final String SOAP_OUT_KEY = "com.SOAP_OUT";

    private static final String SOAP_IN_KEY = "com.SOAP_IN";

    private static final String SOAP_HANDLED_KEY = "com.SOAP_HANDLED";

    private static final String EXCEPTION_KEY = "com.EXCEPTION";

    private static final String SOAP_ADDRESS_KEY = "com.SOAP_ADDRESS_KEY";
    private static final String SOAP_SERVICE_OPERATION_KEY = "com.SOAP_SERVICE_OPERATION_KEY";
    private static final String SOAP_TIME_KEY = SOAP_IN_KEY + ".TIME";
    private static final String SOAP_OUT_TIME_KEY = SOAP_OUT_KEY + SOAP_TIME_KEY;
    private static final String SOAP_IN_TIME_KEY = SOAP_IN_KEY + SOAP_TIME_KEY;


    private String address;

    public SoapTracingHandler() {
    }

    public SoapTracingHandler(String address) {
        this.address = address;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        prepareForInsertInTracingContext(smc, false);
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext smc) {
        LOG.error("SOAP error occurred on endpoint: {}", address);
        prepareForInsertInTracingContext(smc, true);
        return true;
    }

    @Override
    public void close(MessageContext messagecontext) {
        insertInTracingContext(messagecontext);
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean faultOccurred(Exception exception, String description, Message message) {

        // when an exception occurs during the call (like timeout, not the
        // backend returning a soap fault),
        // the soap handling is aborted, this fault listener is used to log the
        // request message
        message.put(EXCEPTION_KEY, exception.getMessage());
        message.put(SOAP_IN_TIME_KEY, LocalDateTime.now());
        insertInTracingContext(message);

        // continue with default fault handling
        return false;
    }

    /**
     * prepare the soap message for insert in the TracingContext.
     *
     * @param smc     soap message context
     * @param isFault whether the soap message is a fault
     */
    private void prepareForInsertInTracingContext(SOAPMessageContext smc, boolean isFault) {
        QName wsdlServiceQName = (QName) smc.get(MessageContext.WSDL_SERVICE);
        QName wsdlOperationQName = (QName) smc.get(MessageContext.WSDL_OPERATION);
        String wsdlServiceName = wsdlServiceQName != null ? wsdlServiceQName.getLocalPart() : "unknown service";
        String wsdlOperationName = wsdlOperationQName != null ? wsdlOperationQName.getLocalPart() : "unknown operation";

        StringBuilder serviceOperation = new StringBuilder(wsdlServiceName).append("::").append(wsdlOperationName);
        StringBuilder messageTextBuilder = new StringBuilder()
                .append(new SimpleDateFormat("HH:mm:ss.SSS ").format(new Date())).append(serviceOperation);
        if (smc.getMessage().countAttachments() == 0) {
            messageTextBuilder
                    .append(": ")
                    .append(SOAPTracingUtils.getSoapTextForTracingContext(smc.getMessage(),
                            DEFAULT_MAX_MESSAGE_SIZE));
        }
        String messageText = messageTextBuilder.toString();

        boolean outboundComm = isOutboundCommunication(smc);

        if (LOG.isDebugEnabled()) {
            String tracingPartName = outboundComm ? "SOAP REQUEST : " : "SOAP RESPONSE: ";
            LOG.debug("{} {}", tracingPartName, messageText);
        }
        if (outboundComm) {
            smc.put(SOAP_OUT_KEY, messageText);
            smc.put(SOAP_OUT_TIME_KEY, LocalDateTime.now());
            smc.put(SOAP_SERVICE_OPERATION_KEY, serviceOperation.toString());
            smc.put(SOAP_ADDRESS_KEY, address);
        } else {
            smc.put(SOAP_IN_KEY, messageText);
            smc.put(SOAP_IN_TIME_KEY, LocalDateTime.now());
            if (isFault) {
                // in case the response is a fault, further processing is
                // interrupted and close is not called
                // so we do it here
                insertInTracingContext(smc);
            }
        }
    }

    /**
     * inserts the soap message in the TracingContext.
     *
     * @param messageContext message context
     */
    private void insertInTracingContext(Map<String, Object> messageContext) {

        Boolean handled = (Boolean) messageContext.get(SOAP_HANDLED_KEY);
        if (!Boolean.TRUE.equals(handled)) {
            String request = (String) messageContext.get(SOAP_OUT_KEY);
            if (request == null) {
                return;
            }
            String response = (String) messageContext.get(SOAP_IN_KEY);
            String exceptionMessage = (String) messageContext.get(EXCEPTION_KEY);

            SoapCall soapCall = new SoapCall();
            soapCall.setRequest(request);
            soapCall.setResponse(response);
            soapCall.setException(exceptionMessage);
            soapCall.setUri((String) messageContext.get(SOAP_ADDRESS_KEY));
            soapCall.setStatus((Integer) messageContext.get(Message.RESPONSE_CODE));
            soapCall.setOperation((String) messageContext.get(SOAP_SERVICE_OPERATION_KEY));
            soapCall.setStartTime((LocalDateTime) messageContext.get(SOAP_OUT_TIME_KEY));
            soapCall.setEndTime((LocalDateTime) messageContext.get(SOAP_IN_TIME_KEY));

            if (TracingContextHolder.get() != null)
                TracingContextHolder.get().getSoapCalls().add(soapCall);
            else
                System.out.println(soapCall);
            messageContext.put(SOAP_HANDLED_KEY, Boolean.TRUE);
        }

    }

    /**
     * Check whether the MessageContext is for an outbound communication.
     *
     * @param smc the message context
     * @return true if it is an inbound communication.
     */
    private boolean isOutboundCommunication(SOAPMessageContext smc) {
        Boolean outboundComm = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        return BooleanUtils.isTrue(outboundComm);
    }

}