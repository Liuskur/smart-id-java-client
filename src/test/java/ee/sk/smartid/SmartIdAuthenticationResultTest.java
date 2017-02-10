package ee.sk.smartid;

import ee.sk.smartid.exception.TechnicalErrorException;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.security.cert.CertificateEncodingException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SmartIdAuthenticationResultTest {
  @Test
  public void getSignatureValueInBase64() {
    SmartIdAuthenticationResult authenticationResult = new SmartIdAuthenticationResult();
    authenticationResult.setValueInBase64("SGVsbG8gU21hcnQtSUQgc2lnbmF0dXJlIQ==");
    assertEquals("SGVsbG8gU21hcnQtSUQgc2lnbmF0dXJlIQ==", authenticationResult.getValueInBase64());
  }

  @Test
  public void getSignatureValueInBytes() {
    SmartIdAuthenticationResult authenticationResult = new SmartIdAuthenticationResult();
    authenticationResult.setValueInBase64("VGVyZSBhbGxraXJpIQ==");
    assertArrayEquals("Tere allkiri!".getBytes(), authenticationResult.getValue());
  }

  @Test(expected = TechnicalErrorException.class)
  public void incorrectBase64StringShouldThrowException() {
    SmartIdAuthenticationResult authenticationResult = new SmartIdAuthenticationResult();
    authenticationResult.setValueInBase64("!IsNotValidBase64Character");
    authenticationResult.getValue();
  }

  @Test
  public void getCertificate() throws CertificateEncodingException {
    SmartIdAuthenticationResult authenticationResult = new SmartIdAuthenticationResult();
    authenticationResult.setCertificate(CertificateParser.parseX509Certificate(DummyData.CERTIFICATE));
    assertEquals(DummyData.CERTIFICATE, Base64.encodeBase64String(authenticationResult.getCertificate().getEncoded()));
  }
}