package com.example.oscar.compass;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class FingerprintActivity extends AppCompatActivity {
    private static final int FINGERPRINT_PERMISSION_REQUEST_CODE = 1;
    private static final String KEY_NAME = "exempel";
    private FingerprintManager mFingerprintManager;
    private KeyStore mKeyStore;
    private KeyGenerator mKeyGenerator;
    private CancellationSignal mCancellationSignal;
    private Cipher mCipher;
    private AlertDialog mFingerPrintDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        mFingerprintManager = (FingerprintManager)getSystemService(FINGERPRINT_SERVICE);
        checkFingerprintConditions();
    }

    /**
     * Method that checks the premissions to use the fingerprint sensor
     */
    public void checkFingerprintConditions(){
        if(mFingerprintManager.isHardwareDetected()){
            if(mFingerprintManager.hasEnrolledFingerprints()){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] { Manifest.permission.USE_FINGERPRINT}, FINGERPRINT_PERMISSION_REQUEST_CODE);
                }else{
                    showFingerPrintDialog();
                }
            }else{
                showAlertDialog("Finger Print Not Registered!", "Go to Settings -> Security -> Fingerprint and register at leastone fingerprint");
            }
        }else{
            showAlertDialog("Finger Print Sensor Not Found!", "Finger Print Sensor could not be found on your phone.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[]permissions, int[] state) {
        if(requestCode == FINGERPRINT_PERMISSION_REQUEST_CODE && state[0] == PackageManager.PERMISSION_GRANTED){
            showFingerPrintDialog();
        }
    }

    /**
     * Method that shows an AlertDialog on the screen
     * @param title
     * @param message
     */
    public void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(this).setTitle(title)
                .setMessage(message).setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * Method that shows the dialog on the screen that tells the user to put his/hers finger on the sensor
     */
    public void showFingerPrintDialog() {//First Initialize the FingerPrint Settings
        if (initFingerPrintSettings()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.fingerprintdialog)
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mCancellationSignal.cancel();
                            mFingerPrintDialog.dismiss();
                        }
                    });
            // Create the AlertDialog object and return it
            mFingerPrintDialog = builder.create();
            //Stops the cancelling of the fingerprint dialog
            // by back press or touching accidentally on screen
            mFingerPrintDialog.setCanceledOnTouchOutside(false);
            mFingerPrintDialog.setCancelable(false);
            mFingerPrintDialog.show();
        } else {
            showAlertDialog("Error!", "Error in initiating FingerPrint Cipher or Key!");
        }
    }


    /**
     * Method that init the finger print sensors and checks the permissions for using the fingerprint sensor again
     * @return
     */
    public boolean initFingerPrintSettings() {
        // /CancellationSignal requests authenticate api to stop scanning
        mCancellationSignal = new CancellationSignal();
        if (initKey() && initCipher()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.USE_FINGERPRINT},FINGERPRINT_PERMISSION_REQUEST_CODE);
            }else{
                mFingerprintManager.authenticate(
                        new FingerprintManager.CryptoObject(mCipher),
                        mCancellationSignal,
                        0,
                        new AuthenticationListener(),
                        null
                );
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that init the key for the fingerprint
     * @return
     */
    public boolean initKey() {
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
            mKeyStore.load(null);
            mKeyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            mKeyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT |
                    KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).
                            build());
            mKeyGenerator.generateKey();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method that init the chiper
     * @return
     */
    public boolean initCipher() {
        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES +
                    "/" + KeyProperties.BLOCK_MODE_CBC +
                    "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            mCipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyStoreException | CertificateException |
                UnrecoverableKeyException | IOException |
                NoSuchAlgorithmException | InvalidKeyException |
                NoSuchPaddingException e) {
            return false;
        }
    }


    /**
     * Method that checks if the finger that is on the sensor is valid or not. Íf the finger is valid then it starts the StepActivity
     * if the finger isn't valid then it return a toast that tells the user that the finger isn't valid
     */
    private class AuthenticationListener extends FingerprintManager.AuthenticationCallback{
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString ) {
            //Fingerprint not valid
            Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            //Sensor may be dirty
        }
        @Override
        public void onAuthenticationFailed() { //Fingerprintvalid but not recognized
            Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            //Everything went OKT
            Toast.makeText(getApplicationContext(), "Authentication Success!", Toast.LENGTH_LONG).show();
            mFingerPrintDialog.dismiss();
            Intent i = new Intent(getApplicationContext(),StepActivity.class);
            startActivity(i);
        }
    }
}
