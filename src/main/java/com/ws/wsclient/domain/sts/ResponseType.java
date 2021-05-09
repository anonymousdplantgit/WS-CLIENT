
package com.ws.wsclient.domain.sts;

import lombok.*;
import oasis.names.tc.saml._1_0.assertion.AssertionType;

import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseType     extends ResponseAbstractType
{

    protected StatusType status;
    protected List<AssertionType> assertion;

}
