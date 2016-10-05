package com.fbi.picturemode.config;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class ConfigAuthorization {

  public static String[] clientIds = new String[]{
      "c993023fb6928fc21d1986926f8ae03695fb7bd39766cdea129cf545d585250e",
      "2c6634035d04ba623d18d90ba584c12cfdb9ee4a1c69636be7f87c6f494f0674",
      "1f8572745b71decb20fbfa42ac3f17334490d1778404c9ac8a3a81df0addb8da",
      "b096032ac2438db550b5657b8f5ad7ea179fcc048ff70c3afd011d040cc54ac2",
      "aa5390d38f9c2ebed5fcc6f895496688202c1dcb15ac92b495c5647243355995"
  };

  public static int currentClientIdIndex = 0;

  public static String getNextClientId() {
    if (currentClientIdIndex == clientIds.length - 1) {
      currentClientIdIndex = 0;
    } else {
      currentClientIdIndex++;
    }
    return clientIds[currentClientIdIndex];
  }

}
