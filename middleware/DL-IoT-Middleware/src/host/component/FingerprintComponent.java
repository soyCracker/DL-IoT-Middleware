package host.component;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.widget.Toast;


@TargetApi(23)
public class FingerprintComponent {
	
	private KeyguardManager keyguardManager;
	private FingerprintManagerCompat fingerManagerCompat;
	private CancellationSignal cancellationSignal;
	private KeyStore keyStore;
	private KeyGenerator keyGenerator;
	private String keyName="dliotmiddleware";
	private Cipher cipher;
	private FingerprintManagerCompat.CryptoObject cryptoObject;
	private char authResult;
	
	public void initFingerprint(Context c)
	{
		keyguardManager = (KeyguardManager)c.getSystemService(Activity.KEYGUARD_SERVICE);
	    fingerManagerCompat = FingerprintManagerCompat.from(c);

	    if (!keyguardManager.isKeyguardSecure()) {
	       Toast.makeText(c,
	                "Secure lock screen hasn't set up.\n"
	                + "Go to 'Settings -> Security -> Fingerprint' to set up a fingerprint",
	                Toast.LENGTH_SHORT).show();
	        return;
	    }
	    
	    if (!fingerManagerCompat.isHardwareDetected()) {
	        Toast.makeText(c,
	                "No Fingerprint reader",
	                Toast.LENGTH_LONG).show();
	        return;
	    }
	    
	    if (!fingerManagerCompat.hasEnrolledFingerprints()) {
	        Toast.makeText(c,
	                "Go to 'Settings -> Security -> Fingerprint' and register at least one fingerprint",
	                Toast.LENGTH_SHORT).show();
	        return;
	    }
	    
	    initKey(c);
	    initCipher(c);
	}

	public char FingerprintStartListening(Context c) 
	{
		cryptoObject=new FingerprintManagerCompat.CryptoObject(cipher);
		
	    cancellationSignal = new CancellationSignal();
	    fingerManagerCompat.authenticate(cryptoObject,
	                    0,
	                    cancellationSignal,
	                    new FingerprintManagerCompat.AuthenticationCallback() {
	                        @Override
	                        public void onAuthenticationError(int errorCode, CharSequence errString) {
	                            authResult='E';
	                        }
	                        @Override
	                        public void onAuthenticationFailed() {
	                            authResult='F';
	                        }
	                        @Override
	                        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
	                            authResult='S';
	                        }
	                    },
	                    null);
	    if(authResult=='E')
	    {
	    	Toast.makeText(c,"Authentication Error",Toast.LENGTH_SHORT).show();
	    }
	    else if(authResult=='F')
	    {
	    	Toast.makeText(c,"Authentication Failed",Toast.LENGTH_SHORT).show();
	    }
	    else if(authResult=='S')
	    {
	    	Toast.makeText(c,"Authentication Succeeded",Toast.LENGTH_SHORT).show();
	    }
	    return authResult;
	}

	public void FingerprintStopListening() 
	{
	    cancellationSignal.cancel();
	    cancellationSignal = null;
	}
	
	public void initKey(Context c)
	{
		//獲取 KeyStore
		try {
		    keyStore = KeyStore.getInstance("dliotmiddleware");
		} catch (KeyStoreException e) {
		    Toast.makeText(c,"Failed to get an instance of KeyStore",Toast.LENGTH_SHORT).show();
		}		
		// 對稱加密， 創建 KeyGenerator
		try {
		    keyGenerator = KeyGenerator
		            .getInstance(KeyProperties.KEY_ALGORITHM_AES, "dliotmiddleware");
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
		    Toast.makeText(c,"Failed to get an instance of KeyGenerator",Toast.LENGTH_SHORT).show();
		}	
		// 生成Key
		try {
		    keyStore.load(null);
		    KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(keyName,
		            KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
		            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
		            .setUserAuthenticationRequired(true)
		            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
		    
		    keyGenerator.init(builder.build());
		    keyGenerator.generateKey();
		} catch (CertificateException | NoSuchAlgorithmException | IOException | InvalidAlgorithmParameterException e) {
		    e.printStackTrace();
		}
	}
	
	public void initCipher(Context c)
	{
		try {
		    cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
		            + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {		    
		    Toast.makeText(c,"Failed to create Cipher",Toast.LENGTH_SHORT).show();
		}
		
		try {
		    keyStore.load(null);
		    SecretKey key = (SecretKey) keyStore.getKey(keyName, null);
		    cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
		    Toast.makeText(c,"Failed to initialize Cipher",Toast.LENGTH_SHORT).show();
		}
	}
}
