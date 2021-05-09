
package com.ws.wsclient.domain.sts;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationQueryType
    extends SubjectQueryAbstractType
{
    protected String authenticationMethod;
}
