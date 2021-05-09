
package com.ws.wsclient.domain.insurability;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor @Builder
public class CommonInputType {
    protected RequestType request;
    protected OriginType origin;

}
