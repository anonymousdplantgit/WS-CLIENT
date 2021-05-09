
package com.ws.wsclient.domain.sts;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusType {

    protected StatusCodeType statusCode;
    protected String statusMessage;
    protected StatusDetailType statusDetail;

}
