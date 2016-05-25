package com.example.android.encryptmsg;

import android.content.ClipboardManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.ClipData;
import android.view.View;
import com.example.android.encryptmsg.DecryptionDialogFragment.DecryptKeyInputListener;
import com.example.android.encryptmsg.KeyDialogFragment.KeyInputListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements KeyInputListener,DecryptKeyInputListener {


    private static final String DEFAULT_PUBLIC_KEY=
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQWPnx7dNA4NbsbGWcYUJqD/58vt/Gq/orV5N0G+l9Pcdu/NVWW/A8sru3yBh4PYvlFnRdKG4PgJL1s6ivJ6LNAKQGE4KmbWR35JaaXjoIzf96n+54vFDgmPO1ygUYaUq15GyfwWNGsVMuxswhXdfS0z0fFyLKta0NLqNrGoZbqQIDAQAB";

    private static final String DEFAULT_PRIVATE_KEY=
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANBY+fHt00Dg1uxsZZxhQmoP/ny+38ar+itXk3Qb6X09x2781VZb8Dyyu7fIGHg9i+UWdF0obg+AkvWzqK8nos0ApAYTgqZtZHfklppeOgjN/3qf7ni8UOCY87XKBRhpSrXkbJ/BY0axUy7GzCFd19LTPR8XIsq1rQ0uo2sahlupAgMBAAECgYBBmOIJE9IBiUjh4zcSx2R7tFFxwT7/dEItx0L7uZYIAp16Cky5BfVbhwKnWG6r3OTg2fMFKaeNZfPfPBOOIjKVO2eKqORerzGQGVESo1TBEzQxN23ZMaOz26gxtCQs/lnoBSXYdQlnPlmEhC8BPvsqqbJGij5n0cBe+utoE15/AQJBAO/37ypY/GckJmfM0hXCC5j0/WCXyutss1C/0qQ3g5zc1ckbkj0+Znc/igUq4VcoNn9ZchMGplsEfLi6xUjbYpkCQQDeRD6bXCPQZy9VUgIxTO7KhqzYIFrL8TwnOZoxK0mVIj1RT/DrkW5cdWwpIfCGLBRD2Sbq/K2FT3NDlRavhHuRAkAZMB4ThNmhfeLmHFKk5ARRns2FuQkcowFDZY8Xm5gd+/QYsuMruIQK2kmDd1fFzTvDLEFUC+ukPBlXTotstVJpAkEAuega9Zd73TRxQsv1SYcvs/YxXBSz3SFJTwW/EqFCSpr47qoOuPpLdMpCTaU1qQP1b0qSYro16OsrXbu13djSAQJBALY4Rv5v84dDoEIptjJkQdVIllDjs4Cl4I/3II/CbRSU5VFJTmhCKXzWD+ixFnVXraihi1ymmTZccSO1DuamnSQ=";


    private static final String DEFAULT_PUBLIC_KEY2=
            "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKvAjmFfQDc1/u6mcbOKUs+xI7/YQnEpFoQa5LoDr7QoZMDrhhi3VWKtEgPlITf75exSCyduASwS1xgfKpXAYCMCAwEAAQ==";
    private static final String DEFAULT_PRIVATE_KEY2=
            "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAq8COYV9ANzX+7qZxs4pSz7Ejv9hCcSkWhBrkugOvtChkwOuGGLdVYq0SA+UhN/vl7FILJ24BLBLXGB8qlcBgIwIDAQABAkBlfHllVgg214QU9mr3IQP/z3cFVD8maJXyZ4XB2I3JEKDsvnkf06jUUU8vxMl/mr+czuxyAoDeC6lCWfzjxkiBAiEA37frHLbT37kvhzcuq/tiKB0hYqo/81Gqao450dJKlmMCIQDEiQWpU2tJIedX5zp99dTJdCguebWpGiounABB4qlbQQIgFbpKFl7XUwosSIieesuOa/DwViHM1jffQjwP94XiApsCIDgGP2DncLvmOEZsmdXyT/glbpbhBxHvbXrf6YrejByBAiEAkEo2Dvpry1dGS7lDMQTVvRlQ19qQGVTeeAgW5a3q07g=";
    private static BASE64Decoder decoder = new BASE64Decoder();
    private static BASE64Encoder encoder = new BASE64Encoder();
    private ClipboardManager Clipboard;
    private ClipData Contents;
    private EditText contentText;
    private String Cipher=null;
    private String Messages=null;

    @Override
    public void onSubmitkey(String key) {
        contentText=(EditText)findViewById(R.id.contentText);
        String target=contentText.getText().toString();
        try {
            Cipher = send(target, key);
            contentText.setText(Cipher);
            Clipboard.setText(Cipher);
            Toast.makeText(this, "the cipher has been put on clipboard",
                    Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onSubmitDecryptkey(String key) {
        contentText=(EditText)findViewById(R.id.contentText);
        String target=contentText.getText().toString();
        try{
            Messages=recive(target,key);
            contentText.setText(Messages);

            Clipboard.setText(Messages);
            Toast.makeText(this, "the messages has been put on clipboard",
                    Toast.LENGTH_SHORT).show();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String content = null;
        contentText = (EditText) findViewById(R.id.contentText);



    }
    @Override
    protected void onStart() {
        super.onStart();
        String newcontent = null;
        Clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (Clipboard.hasPrimaryClip()) {
            Contents = Clipboard.getPrimaryClip();
            newcontent = Contents.getItemAt(0).getText().toString();
            if (!(contentText.getText().toString().equals(newcontent))) {
                contentText.setText(newcontent);
                Toast.makeText(this, "the new content has been put on text",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void Encryption(View view){
        DialogFragment keyFragment = new KeyDialogFragment();
        keyFragment.show(getSupportFragmentManager(), "key");



    }
    public void Decryption(View view){
        DialogFragment DecryptKeyFragment=new DecryptionDialogFragment();
        DecryptKeyFragment.show(getSupportFragmentManager(),"Decrypt key");

    }



    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString().toUpperCase();
    }

    public  String send(String strToSend,String pwd) throws Exception {
        //calc hash
        String hash=md5(strToSend);
        //pri jiami hash
        String enHash=encoder.encode(RSAUtils.encryptByPrivateKey(hash.getBytes(),DEFAULT_PRIVATE_KEY));
        //cast1
        String strWithHash=enHash+","+strToSend;
        //aes
        AES sendAes = new AES(pwd);
        String afterAes=sendAes.encrypt(strWithHash.getBytes("UTF8"));
        //pub2 jiami
        String encrptedPWD=encoder.encode(RSAUtils.encryptByPublicKey(pwd.getBytes(),DEFAULT_PUBLIC_KEY2));
        //cast2
        String strReadyToSend=encrptedPWD+","+afterAes;

       // System.out.println("send: "+strReadyToSend);
        return strReadyToSend;

    }

    public String recive(String strRecived,String pwd) throws IOException {

        String[] cast2 = strRecived.split(",");
        String encrptedPWD=cast2[0];
        String afterAes=cast2[1];
        //decrypt aesPWD
        byte[] dat= decoder.decodeBuffer(encrptedPWD);
        byte[] decodedData = new byte[0];
        try {
            decodedData = RSAUtils.decryptByPrivateKey(dat,
                    DEFAULT_PRIVATE_KEY2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String password = new String(decodedData);
        //check pwd
        if (pwd.equals(password)){
            //decrypt afterAes
            AES recAes = new AES(password);
            String beforeAes= recAes.decrypt(afterAes);
            //get hash,signedStr
            String[] cast1 = beforeAes.split(",");
            String strToSend=cast1[1];
            String signedStr=cast1[0];
            //decrypt signedStr
            byte[] dat2= decoder.decodeBuffer(signedStr);
            byte[] decodedData2 = new byte[0];
            try {
                decodedData2 = RSAUtils.decryptByPublicKey(dat2,
                        DEFAULT_PUBLIC_KEY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String hash = new String(decodedData2);
            //calc hash
            String calcHash=md5(strToSend);
            if (calcHash.equals(hash)){
                System.out.println("JIEGUO: "+strToSend);
                return strToSend;
            }else{
                //auth error
                return "you press wrong button";
            }
        }else {
            //aes password error
            return "you don`t have the permission";
        }

    }


}
