// iban.java: Clasa pentru procesarea tranzacțiilor IBAN
package turnserver;

import java.io.Serializable;

public class IbanTransaction implements Serializable {
    private String contBancar;
    private String ibanDest;
    private String deviceType;
    private boolean biometricsRequired;

    public IbanTransaction(String contBancar, String ibanDest, String deviceType, boolean biometricsRequired) {
        this.contBancar = contBancar;
        this.ibanDest = ibanDest;
        this.deviceType = deviceType;
        this.biometricsRequired = biometricsRequired;
    }

    public String getContBancar() { return contBancar; }
    public String getIbanDest() { return ibanDest; }
    public String getDeviceType() { return deviceType; }
    public boolean isBiometricsRequired() { return biometricsRequired; }

    public String processTransaction() {
        if (biometricsRequired) {
            return "Tranzacție de pe mobil: autentificare biometrică necesară.";
        } else {
            return "Tranzacție de pe PC: continuă fără biometrie.";
        }
    }
}
