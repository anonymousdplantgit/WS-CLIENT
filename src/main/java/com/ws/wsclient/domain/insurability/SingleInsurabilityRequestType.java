
package com.ws.wsclient.domain.insurability;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor @Builder
public class SingleInsurabilityRequestType {

    protected CareReceiverIdType careReceiverId;
    protected InsurabilityRequestDetailType insurabilityRequestDetail;


}
