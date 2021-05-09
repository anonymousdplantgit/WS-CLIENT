
package com.ws.wsclient.domain.sts;

import lombok.*;
import oasis.names.tc.saml._1_0.assertion.SubjectType;

import javax.xml.bind.annotation.*;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class SubjectQueryAbstractType
    extends QueryAbstractType
{

    protected SubjectType subject;

}
