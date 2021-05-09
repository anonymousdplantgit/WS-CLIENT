
package com.ws.wsclient.domain.insurability;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor @Builder
public class InsurabilityRequestDetailType {

    protected InsurabilityRequestTypeType insurabilityRequestType;
    protected PeriodType period;
    protected InsurabilityContactTypeType insurabilityContactType;
    protected String insurabilityReference;

}
