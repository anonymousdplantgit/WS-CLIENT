
package com.ws.wsclient.domain.insurability;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor @Builder
public class CareReceiverIdType {
    protected String inss;
    protected String regNrWithMut;
    protected String mutuality;
}
