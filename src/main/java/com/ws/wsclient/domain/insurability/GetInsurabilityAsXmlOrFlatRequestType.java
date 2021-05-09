
package com.ws.wsclient.domain.insurability;

import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor @Builder
public class GetInsurabilityAsXmlOrFlatRequestType
{
    private CommonInputType commonInput;
    private RecordCommonInputType recordCommonInput;
    private SingleInsurabilityRequestType request;

}
