
package com.ws.wsclient.domain.sts;

import lombok.*;
import oasis.names.tc.saml._1_0.assertion.ActionType;
import oasis.names.tc.saml._1_0.assertion.EvidenceType;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorizationDecisionQueryType
    extends SubjectQueryAbstractType
{

    protected List<ActionType> action;
    protected EvidenceType evidence;
    protected String resource;
}
