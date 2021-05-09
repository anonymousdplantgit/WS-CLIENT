
package com.ws.wsclient.domain.insurability;

import be.fgov.ehealth.genericinsurability.core.v1.PackageType;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor @Builder
public class OriginType {
    protected PackageType _package;
}
