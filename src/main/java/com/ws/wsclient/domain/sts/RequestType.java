
package com.ws.wsclient.domain.sts;

import lombok.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestType
    extends RequestAbstractType
{
    protected QueryAbstractType query;
    protected SubjectQueryAbstractType subjectQuery;
    protected AuthenticationQueryType authenticationQuery;
    protected AttributeQueryType attributeQuery;
    protected AuthorizationDecisionQueryType authorizationDecisionQuery;
    protected List<String> assertionIDReference;
    protected List<String> assertionArtifact;

}
