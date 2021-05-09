
package com.ws.wsclient.domain.sts;

import lombok.*;
import oasis.names.tc.saml._1_0.assertion.AttributeDesignatorType;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeQueryType extends SubjectQueryAbstractType
{
    protected List<AttributeDesignatorType> attributeDesignator;
    protected String resource;
}
